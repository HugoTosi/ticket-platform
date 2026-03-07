package com.orderService.controller;

import com.orderService.entities.Order;
import com.orderService.enums.EnumOrderStatus;
import com.orderService.event.OrderCreatedEvent;
import com.orderService.event.OrderEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.orderService.repository.OrderRepository;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderEventPublisher orderEventPublisher;

    @GetMapping("/allOrders")
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id){
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/order/create")
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        try{
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

            return ResponseEntity.ok("");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("");
        }
    }

    @PatchMapping("/order/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id){
        try{
            orderRepository.changeStatus(id, EnumOrderStatus.CANCELLED);
            return ResponseEntity.ok("");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("");
        }
    }
}
