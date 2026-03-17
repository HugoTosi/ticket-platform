package com.paymentService.event;

import com.paymentService.dto.OrderCreated;
import com.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class OrderCreatedConsumer {
    @Autowired
    PaymentService paymentService;

    @KafkaListener(topics = "order-created-topic", groupId = "payment-service")
    public void reciveOrderCreated(OrderCreated order){
        paymentService.processPayment(order);

    }
}
