package com.paymentService.event;

import com.paymentService.enums.EnumPaymentStatus;

import java.time.LocalDateTime;

public class PaymentProcessedEvent {
    private Long paymentId;
    private Long orderId;
    private EnumPaymentStatus paymentStatus;

    public PaymentProcessedEvent() {
    }

    public PaymentProcessedEvent(Long paymentId, Long orderId, EnumPaymentStatus paymentStatus) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
    }
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public EnumPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(EnumPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
