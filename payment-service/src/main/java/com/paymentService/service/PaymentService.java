package com.paymentService.service;

import com.paymentService.dto.OrderCreated;
import com.paymentService.entities.Payment;
import com.paymentService.enums.EnumPaymentStatus;
import com.paymentService.event.PaymentProcessedEvent;
import com.paymentService.event.PaymentProcessedPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    @Autowired
    PaymentProcessedPublisher paymentProcessedPublisher;

    public void processPayment(OrderCreated orderCreated){
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

    public Payment orderCreatedToPayment(OrderCreated orderCreated){
        return new Payment(orderCreated.getOrderId(), orderCreated.getTotalValue(), orderCreated.getPaymentMethod());
    }
}
