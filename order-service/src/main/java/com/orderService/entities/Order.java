package com.orderService.entities;

import com.orderService.enums.EnumOrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_tb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usrId;
    private Long eventId;
    private Integer ticketQuantity;
    private Double ticketPrice;
    @Enumerated(EnumType.STRING)
    private EnumOrderStatus orderStatus;
    private Double totalValue;
    private LocalDateTime dateOrder;

    public Order() {
    }

    public Order(Long usrId, Long eventId, Integer ticketQuantity, Double ticketPrice) {
        this.usrId = usrId;
        this.eventId = eventId;
        this.ticketQuantity = ticketQuantity;
        this.ticketPrice = ticketPrice;
        this.orderStatus = EnumOrderStatus.PENDING_PAYMENT;
        this.totalValue = calcTotalValue();
        this.dateOrder = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
        this.usrId = usrId;
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

    public EnumOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(EnumOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalValue() {
        return totalValue;
    }


    public LocalDateTime getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(LocalDateTime dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Double calcTotalValue(){
        return ticketQuantity * ticketPrice;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
