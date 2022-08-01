package com.lt.dom.otcenum;

public enum EnumPaymentOption {

    //A list of potential payment options for the current order. Some order items will negate the ability to use certain forms of payment, such as purchasing a giftcard will remove the ability to pay with one:
    creditCard,
            giftCard,
    loyalty,
            payPal,
    venmo,
            applePay,
    googlePay,
            bitPay,

}
