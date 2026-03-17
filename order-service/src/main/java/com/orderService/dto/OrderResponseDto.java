package com.orderService.dto;

import com.orderService.entities.Order;
import com.orderService.enums.EnumOrderStatus;
import ticket_contracts.enums.EnumPaymentMethod;

public class OrderResponseDto {
    private final Long id;
    private final Double totalValue;
    private final EnumOrderStatus orderStatus;
    private final EnumPaymentMethod paymentMethod;

    public OrderResponseDto(Long id, Double totalValue, EnumOrderStatus orderStatus, EnumPaymentMethod paymentMethod) {
        this.id = id;
        this.totalValue = totalValue;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
    }

    public static OrderResponseDto orderToOrderResponse(Order order){
        return new OrderResponseDto(
                order.getId(),
                order.getTotalValue(),
                order.getOrderStatus(),
                order.getPaymentMethod()
        );
    }

    public EnumPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Long getId() {
        return id;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public EnumOrderStatus getOrderStatus() {
        return orderStatus;
    }
}
