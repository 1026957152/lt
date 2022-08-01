package com.lt.dom.oct;

public class BookingPayment {

    private String cardNumber;//	The cardNumber associated with the payment (Disney rewards only)
    private String cardNumberLast4;//	The last 4 digits of the cardNumber associated with the payment
    private String encryptedNumber;//	The encrypted cardNumber associated with the payment (gift cards only)
    private int balance;//	The total value available for payment
    private int  amount;//	The total value of payment applied to the order
}
