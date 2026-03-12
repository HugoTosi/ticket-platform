package com.orderService.dto;

import com.orderService.entities.Order;
import com.orderService.enums.EnumOrderStatus;

public class OrderResponseDto {
    private final Long id;
    private final Double totalValue;
    private final EnumOrderStatus orderStatus;

    public OrderResponseDto(Long id, Double totalValue, EnumOrderStatus orderStatus) {
        this.id = id;
        this.totalValue = totalValue;
        this.orderStatus = orderStatus;
    }

    public static OrderResponseDto orderToOrderResponse(Order order){
        return new OrderResponseDto(
                order.getId(),
                order.getTotalValue(),
                order.getOrderStatus()
        );
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
