package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumOrderStatus;
import com.lt.dom.otcenum.EnumPayChannel;

import java.time.LocalDate;

public class RedemptionFilters {  // 这个就是机器了啊



    private LocalDate Booking_Date;//: This is the date of when the booking was created, online or internally.
    private LocalDate FulfilmentDate;//: This is the date of when your guests attend (or complete) their experience with you.
    private EnumOrderStatus OrderStatus;//: Select an order status from the list to show only those order statuses.
    private EnumPayChannel PaymentStatus;//: Select a payment status from the list to show only those payment statuses.

    public LocalDate getBooking_Date() {
        return Booking_Date;
    }

    public void setBooking_Date(LocalDate booking_Date) {
        Booking_Date = booking_Date;
    }

    public LocalDate getFulfilmentDate() {
        return FulfilmentDate;
    }

    public void setFulfilmentDate(LocalDate fulfilmentDate) {
        FulfilmentDate = fulfilmentDate;
    }

    public EnumOrderStatus getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(EnumOrderStatus orderStatus) {
        OrderStatus = orderStatus;
    }

    public EnumPayChannel getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(EnumPayChannel paymentStatus) {
        PaymentStatus = paymentStatus;
    }
}
