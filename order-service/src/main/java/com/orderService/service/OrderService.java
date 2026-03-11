package com.orderService.service;

import com.orderService.entities.Order;
import com.orderService.enums.EnumOrderStatus;
import com.orderService.event.OrderCreatedEvent;
import com.orderService.event.OrderEventPublisher;
import com.orderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderEventPublisher orderEventPublisher;

    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    @Transactional
    public Order createOrder(Order order){
        String idempotencyKey = order.getIdempotencyKey();
        Order existingOrder = orderRepository.findByIdempotencyKey(idempotencyKey);

        if (existingOrder != null){
            return existingOrder;
        }

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getUsrId(),
                savedOrder.getEventId(),
                savedOrder.getTicketQuantity(),
                savedOrder.getTicketPrice(),
                savedOrder.getTotalValue()
        );

        orderEventPublisher.publishOrderCreated(event);
        return savedOrder;
    }

    @Transactional
    public Optional<Order> cancelOrder(Long id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            order.get().setOrderStatus(EnumOrderStatus.CANCELLED);
            orderRepository.save(order.get());
            return order;
        }
        return Optional.empty();
    }
}
