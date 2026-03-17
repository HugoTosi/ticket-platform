package com.orderService.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ticket_contracts.events.PaymentProcessedEvent;

@Service
public class PaymentProcessedConsumer {
    @KafkaListener(topics = "payment-processed-topic", groupId = "order-service")
    public void recivePaymentProcessed(PaymentProcessedEvent paymentProcessed){
        System.out.println("Evento Recebido: " + paymentProcessed.toString());
    }
}
