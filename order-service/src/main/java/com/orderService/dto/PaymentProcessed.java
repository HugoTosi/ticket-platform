package com.orderService.dto;

import com.orderService.enums.EnumPaymentStatus;


public class PaymentProcessed {
    private Long paymentId;
    private Long orderId;
    private EnumPaymentStatus paymentStatus;

    @Override
    public String toString() {
        return "PaymentProcessed{" +
                "paymentId=" + paymentId +
                ", orderId=" + orderId +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public EnumPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setPaymentStatus(EnumPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
