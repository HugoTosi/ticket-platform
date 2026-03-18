package com.paymentService.event;

import com.paymentService.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ticket_contracts.events.OrderCreatedEvent;

@Slf4j
@Service
public class OrderCreatedConsumer {
    @Autowired
    PaymentService paymentService;

    @KafkaListener(topics = "order-created-topic", groupId = "payment-service")
    public void reciveOrderCreated(OrderCreatedEvent order){
        log.info("OrderCreatedEvent recebida: OrderId={}", order.getOrderId());
        paymentService.processPayment(order);

    }
}
