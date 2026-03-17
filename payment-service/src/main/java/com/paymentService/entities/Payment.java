package com.paymentService.entities;

import jakarta.persistence.*;
import ticket_contracts.enums.EnumPaymentMethod;
import ticket_contracts.enums.EnumPaymentStatus;
import ticket_contracts.events.OrderCreatedEvent;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_tb")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    @Enumerated(EnumType.STRING)
    private EnumPaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private EnumPaymentMethod paymentMethod;
    private Double totalValue;

    public Payment() {
    }

    public Payment(Long orderId, Double totalValue, EnumPaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.createdAt = LocalDateTime.now();
        this.totalValue = totalValue;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = EnumPaymentStatus.PROCESSING;
    }

    public Payment OrderCreatedToPayment(OrderCreatedEvent orderCreated){
        return new Payment();
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public EnumPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(EnumPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public EnumPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(EnumPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
