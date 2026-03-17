package com.orderService.service;

import com.orderService.dto.OrderRequestDto;
import com.orderService.dto.OrderResponseDto;
import com.orderService.entities.Order;
import com.orderService.enums.EnumOrderStatus;
import com.orderService.event.OrderCreatedEvent;
import com.orderService.event.OrderEventPublisher;
import com.orderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderEventPublisher orderEventPublisher;

    public List<OrderResponseDto> getAllOrder(){
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> listResponse = new ArrayList<>();

        for(Order order : orders){
            listResponse.add(OrderResponseDto.orderToOrderResponse(order));
        }
        return listResponse;
    }

    public Optional<OrderResponseDto> getOrderById(Long id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            return Optional.of(OrderResponseDto.orderToOrderResponse(order.get()));
        }
        return Optional.empty();
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto){

        Order order = Order.orderRequestToOrder(orderRequestDto);

        String idempotencyKey = order.getIdempotencyKey();
        Order existingOrder = orderRepository.findByIdempotencyKey(idempotencyKey);

        if (existingOrder != null){
            return OrderResponseDto.orderToOrderResponse(existingOrder);
        }

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getUsrId(),
                savedOrder.getEventId(),
                savedOrder.getTicketQuantity(),
                savedOrder.getTicketPrice(),
                savedOrder.getTotalValue(),
                savedOrder.getPaymentMethod()
        );

        orderEventPublisher.publishOrderCreated(event);
        return OrderResponseDto.orderToOrderResponse(savedOrder);
    }

    @Transactional
    public Optional<OrderResponseDto> cancelOrder(Long id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            order.get().setOrderStatus(EnumOrderStatus.CANCELLED);
            order.get().setUpdateAt(LocalDateTime.now());
            orderRepository.save(order.get());
            return Optional.of(OrderResponseDto.orderToOrderResponse(order.get()));
        }
        return Optional.empty();
    }
}
