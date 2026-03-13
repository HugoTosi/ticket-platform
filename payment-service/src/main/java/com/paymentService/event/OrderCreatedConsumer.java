package com.paymentService.event;

import com.paymentService.dto.OrderCreated;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatedConsumer {

    @KafkaListener(topics = "order-created-topic", groupId = "payment-service")
    public void reciveOrderCreated(OrderCreated order){
        System.out.println("Evento recebido: " + order.toString());
    }
}
