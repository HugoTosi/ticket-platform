package com.orderService.event;

import com.orderService.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ticket_contracts.events.PaymentProcessedEvent;

@Slf4j
@Service
public class PaymentProcessedConsumer {
    @Autowired
    OrderService orderService;

    @KafkaListener(topics = "payment-processed-topic", groupId = "order-service")
    public void recivePaymentProcessed(PaymentProcessedEvent paymentProcessed){
        log.info("PaymentProcessedEvent recebido: paymentId={}, paymentStatus={}",
                paymentProcessed.getPaymentId(),
                paymentProcessed.getPaymentStatus());

        orderService.processPaymentResult(paymentProcessed);
    }
}
