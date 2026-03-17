package com.paymentService.service;

import com.paymentService.entities.Payment;
import com.paymentService.event.PaymentProcessedPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticket_contracts.enums.EnumPaymentStatus;
import ticket_contracts.events.OrderCreatedEvent;
import ticket_contracts.events.PaymentProcessedEvent;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    @Autowired
    PaymentProcessedPublisher paymentProcessedPublisher;

    public void processPayment(OrderCreatedEvent orderCreated){
        //Simulacao para evento assincrono para processamento de pagamento
        //int randomChanceOfApproval = new Random().nextInt(10);
        int randomChanceOfApproval = 9; //Teste
        Payment payment = orderCreatedToPayment(orderCreated);

        if (randomChanceOfApproval >= 4){
            //aprovado
            payment.setPaymentStatus(EnumPaymentStatus.APPROVED);
            payment.setProcessedAt(LocalDateTime.now());

            PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(
                    payment.getId(),  payment.getOrderId(), payment.getPaymentStatus()
            );
            paymentProcessedPublisher.publishPaymentProcessed(paymentProcessedEvent);
        } else if (randomChanceOfApproval >= 2){
            //recusa
        } else {
            //falha e tenta dnv
        }
    }

    public Payment orderCreatedToPayment(OrderCreatedEvent orderCreated){
        return new Payment(orderCreated.getOrderId(), orderCreated.getTotalValue(), orderCreated.getPaymentMethod());
    }
}
