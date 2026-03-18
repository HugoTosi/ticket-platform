package com.orderService.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ticket_contracts.events.OrderCreatedEvent;

@Slf4j
@Service
public class OrderEventPublisher {
    private KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreated(OrderCreatedEvent orderCreatedEvent){
        kafkaTemplate.send("order-created-topic", orderCreatedEvent);
        log.info("Enviando evento kafka (OrderCreated): orderId={}", orderCreatedEvent.getOrderId());
    }
}
