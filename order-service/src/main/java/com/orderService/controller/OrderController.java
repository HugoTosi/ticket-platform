package com.orderService.controller;

import com.orderService.entities.Order;
import com.orderService.service.OrderService;
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
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable("id") Long id){
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()){
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id){
        Optional<Order> canceledOrder = orderService.cancelOrder(id);

        if (canceledOrder.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(canceledOrder.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order não encontrada");
        }
    }
}
