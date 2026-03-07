package com.orderService.event;

public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private Long eventId;
    private Integer ticketQuantity;
    private Double ticketPrice;
    private Double totalValue;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long orderId, Long userId, Long eventId, Integer ticketQuantity, Double ticketPrice, Double totalValue) {
        this.orderId = orderId;
        this.userId = userId;
        this.eventId = eventId;
        this.ticketQuantity = ticketQuantity;
        this.ticketPrice = ticketPrice;
        this.totalValue = totalValue;
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
