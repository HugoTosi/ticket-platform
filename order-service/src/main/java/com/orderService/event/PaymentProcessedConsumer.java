package com.orderService.event;

import com.orderService.dto.PaymentProcessed;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessedConsumer {
    @KafkaListener(topics = "payment-processed-topic", groupId = "order-service")
    public void recivePaymentProcessed(PaymentProcessed paymentProcessed){
        System.out.println("Evento Recebido: " + paymentProcessed.toString());
    }
}
