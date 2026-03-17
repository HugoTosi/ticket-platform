package com.orderService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ticket_contracts.enums.EnumPaymentMethod;

public class OrderRequestDto {
    @NotNull(message = "usrId não deve ser vazio")
    private Long usrId;
    @NotNull(message = "eventId não deve ser vazio")
    private Long eventId;
    @NotNull(message = "ticketQuantity não deve ser vazio")
    private Integer ticketQuantity;
    @NotNull(message = "ticketPrice não deve ser vazio")
    private Double ticketPrice;
    @NotBlank(message = "idempotencyKey não deve ser vazio")
    private String idempotencyKey;
    @NotNull(message = "paymentMethod não deve ser vazio")
    private EnumPaymentMethod paymentMethod;

    public EnumPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(EnumPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
