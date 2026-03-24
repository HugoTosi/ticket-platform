package com.orderService.service;

import com.orderService.dto.OrderRequestDto;
import com.orderService.dto.OrderResponseDto;
import com.orderService.entities.Order;
import com.orderService.enums.EnumOrderStatus;
import com.orderService.event.OrderEventPublisher;
import com.orderService.event.PaymentProcessedConsumer;
import com.orderService.exception.InvalidPaymentStatusException;
import com.orderService.exception.OrderNotFoundException;
import com.orderService.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket_contracts.enums.EnumPaymentStatus;
import ticket_contracts.events.OrderCreatedEvent;
import ticket_contracts.events.PaymentProcessedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
            log.info("Order já cadastrada: Id={}, idempotencyKey={}", existingOrder.getId(), existingOrder.getIdempotencyKey());
            return OrderResponseDto.orderToOrderResponse(existingOrder);
        }

        Order savedOrder = orderRepository.save(order);
        log.info("Order criada: Id={}", savedOrder.getId());

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
            log.info("Order cancelada: id={}", order.get().getId());
            return Optional.of(OrderResponseDto.orderToOrderResponse(order.get()));
        }
        log.warn("Order não encontrada para cancelar: id={}", id);
        return Optional.empty();
    }

    public void processPaymentResult(PaymentProcessedEvent paymentProcessedEvent){
        Optional<Order> order = orderRepository.findById(paymentProcessedEvent.getOrderId());
        if (order.isPresent()){
            order.get().setOrderStatus(mapPaymentStatusToOrderStatus(paymentProcessedEvent.getPaymentStatus()));
            order.get().setUpdateAt(LocalDateTime.now());
            orderRepository.save(order.get());
            log.info("Order status atualizado: orderId={}, orderStatus={}", order.get().getId(), order.get().getOrderStatus());
        } else {
            log.error("Order nao encontrada para alterar status com base no paymentProcessedEvent: orderId{}",
                    paymentProcessedEvent.getOrderId());
            throw new OrderNotFoundException("Order nao encontrada: orderId:" + paymentProcessedEvent.getOrderId());
        }
    }

    public EnumOrderStatus mapPaymentStatusToOrderStatus(EnumPaymentStatus paymentStatus){
        return switch (paymentStatus){
            case PENDING, PROCESSING -> throw new InvalidPaymentStatusException("Status invalido para atualizar order" + paymentStatus);
            case APPROVED -> EnumOrderStatus.PAID;
            case DECLINED -> EnumOrderStatus.DECLINED;
            case FAILED -> EnumOrderStatus.FAILED;
        };
    }
}
