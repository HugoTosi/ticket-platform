package com.paymentService.dto;

import com.paymentService.enums.EnumPaymentMethod;

public class OrderCreated {
    private Long orderId;
    private Long userId;
    private Long eventId;
    private Integer ticketQuantity;
    private Double ticketPrice;
    private Double totalValue;
    private EnumPaymentMethod paymentMethod;

    public OrderCreated() {
    }

    @Override
    public String toString() {
        return "OrderCreated{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", eventId=" + eventId +
                ", ticketQuantity=" + ticketQuantity +
                ", ticketPrice=" + ticketPrice +
                ", totalValue=" + totalValue +
                ", paymentMethod=" + paymentMethod +
                '}';
    }

    public EnumPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(EnumPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(Integer ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
}
