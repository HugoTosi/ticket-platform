package com.paymentService.event;

import com.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ticket_contracts.events.OrderCreatedEvent;


@Service
public class OrderCreatedConsumer {
    @Autowired
    PaymentService paymentService;

    @KafkaListener(topics = "order-created-topic", groupId = "payment-service")
    public void reciveOrderCreated(OrderCreatedEvent order){
        paymentService.processPayment(order);

    }
}
