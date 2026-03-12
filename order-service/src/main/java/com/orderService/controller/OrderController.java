package com.orderService.controller;

import com.orderService.dto.OrderRequestDto;
import com.orderService.dto.OrderResponseDto;
import com.orderService.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/allOrders")
    public List<OrderResponseDto> getAllOrder(){
        return orderService.getAllOrder();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrderResponseDto>> getOrderById(@PathVariable("id") Long id){
        Optional<OrderResponseDto> order = orderService.getOrderById(id);
        if (order.isPresent()){
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto order){
        OrderResponseDto createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id){
        Optional<OrderResponseDto> canceledOrder = orderService.cancelOrder(id);

        if (canceledOrder.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(canceledOrder.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order não encontrada");
        }
    }
}
