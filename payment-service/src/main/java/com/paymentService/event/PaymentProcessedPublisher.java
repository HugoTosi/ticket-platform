package com.paymentService.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessedPublisher {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentProcessedPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentProcessed(PaymentProcessedEvent paymentProcessedEvent){
        paymentProcessedEvent.setPaymentId(01L);
        kafkaTemplate.send("payment-processed-topic", paymentProcessedEvent);
    }
}
