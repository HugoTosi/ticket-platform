package com.paymentService.service;

import com.paymentService.entities.Payment;
import com.paymentService.event.PaymentProcessedPublisher;
import com.paymentService.exception.PaymentProcessingException;
import com.paymentService.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ticket_contracts.enums.EnumPaymentStatus;
import ticket_contracts.events.OrderCreatedEvent;
import ticket_contracts.events.PaymentProcessedEvent;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
public class PaymentService {
    @Autowired
    PaymentProcessedPublisher paymentProcessedPublisher;
    @Autowired
    PaymentRepository paymentRepository;

    public Payment orderCreatedToPayment(OrderCreatedEvent orderCreated){
        return new Payment(orderCreated.getOrderId(), orderCreated.getTotalValue(), orderCreated.getPaymentMethod());
    }

    public boolean paymentAlreadyProcessed(Long orderId){
        return paymentRepository.findByOrderId(orderId) != null;
    }

    @Retryable(retryFor = PaymentProcessingException.class, maxAttempts = 3, backoff = @Backoff(2500))
    public void processPayment(OrderCreatedEvent orderCreatedEvent){
        Payment payment = orderCreatedToPayment(orderCreatedEvent);

        if (paymentAlreadyProcessed(payment.getOrderId())){
            log.warn("Pagamento já processado: orderId={}",payment.getOrderId());
            return;
        } else {
            int randomChanceOfApproval = new Random().nextInt(10);
            log.info("randomChanceOfApproval: ={}", randomChanceOfApproval);
            if (randomChanceOfApproval >= 4){
                payment.setPaymentStatus(EnumPaymentStatus.APPROVED);
            } else if (randomChanceOfApproval >=2) {
                payment.setPaymentStatus(EnumPaymentStatus.DECLINED);
            } else {
             throw new PaymentProcessingException("Falha tecnica ao processar pagamento");
            }
        }
        payment.setProcessedAt(LocalDateTime.now());
        payment = paymentRepository.save(payment);
        log.info("Pagamento processado: paymentId={}, paymentStatus={}", payment.getId(), payment.getPaymentStatus());

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(
                payment.getId(),  payment.getOrderId(), payment.getPaymentStatus()
        );
        paymentProcessedPublisher.publishPaymentProcessed(paymentProcessedEvent);
    }

    @Recover
    public void recoverPaymentProcessingException(PaymentProcessingException paymentProcessingException, OrderCreatedEvent orderCreatedEvent){
        Payment payment = orderCreatedToPayment(orderCreatedEvent);
        payment.setPaymentStatus(EnumPaymentStatus.FAILED);
        payment.setProcessedAt(LocalDateTime.now());
        payment = paymentRepository.save(payment);
        log.warn("Pagamento falhou após todas as tentativas: paymentId={}, orderId={}", payment.getId(), payment.getOrderId());
        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(
                payment.getId(),  payment.getOrderId(), payment.getPaymentStatus()
        );
        paymentProcessedPublisher.publishPaymentProcessed(paymentProcessedEvent);

    }
    

}
