package com.lt.dom.otcenum;

public enum EnumPaymentStatus {
    Initialized("barcode"),
    Credited("reference number"),
    Pending("Barcode scan"),
    Authorized("Facial Recognition"),
    Captured("NFC tap"),
    Refunded("NFC tap"),
    Voided("NFC tap"),
    //Initialized" "Credited" "Pending" "Authorized" "Captured" "Refunded" "Voided"

//https://dev.wix.com/api/rest/wix-cashier/payments/transactions-list
    INITIALIZED,
    IN_PROCESS,
    APPROVED,
    PENDING,
    PENDING_MERCHANT,
    PENDING_BUYER,
    BUYER_CANCELED,
    TIMEOUT,
    REFUND,
    PARTIAL_REFUND,
    VOID,
    CHARGE_BACK,
    EXPIRED,
    DECLINED,
    FAILED,
    COMPLETED_FUNDS_HELD,
    TPA_CANCELED,
    OFFLINE,
    DISPUTE,
    UNDEFINED;

    EnumPaymentStatus(String barcode) {

    }
    EnumPaymentStatus() {

    }
}
