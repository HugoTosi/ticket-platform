package com.paymentService.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ticket_contracts.events.PaymentProcessedEvent;

@Slf4j
@Service
public class PaymentProcessedPublisher {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentProcessedPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentProcessed(PaymentProcessedEvent paymentProcessedEvent){
        paymentProcessedEvent.setPaymentId(01L); //Teste
        kafkaTemplate.send("payment-processed-topic", paymentProcessedEvent);
        log.info("Enviando evento kafka (paymentProcessed): paymentId={}", paymentProcessedEvent.getPaymentId());
    }
}
