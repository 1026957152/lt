package com.lt.dom.otcenum;

public enum EnumPaymentStatus {
    open,
   // The checkout session is still in progress. Payment processing has not started

            complete,
   // The checkout session is complete. Payment processing may still be in progress

            expired,


    FAILED,
  //  This enumeration value indicates that the buyer attempted to pay for the order, but the payment has failed.
    PAID,
   // This enumeration value indicates that the item has been paid in full. Once this PAID value is returned in an order management call, it is safe for the seller to ship the item to the buyer.
            PENDING,
/*    Initialized("barcode"),
    Credited("reference number"),
    Pending("Barcode scan"),
    Authorized("Facial Recognition"),
    Captured("NFC tap"),
    Refunded("NFC tap"),
    Voided("NFC tap"),*/
    //Initialized" "Credited" "Pending" "Authorized" "Captured" "Refunded" "Voided"

/*//https://dev.wix.com/api/rest/wix-cashier/payments/transactions-list
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
    UNDEFINED,



   // UNDEFINED,
    COMPLETE,
    PENDING_CASHIER,
    REJECTED,
    READY,
    CANCELED,
    REFUNDED,
 //   PENDING_MERCHANT,
    WIX_PAY_FAILURE,
    PENDING_MARK_AS_PAID,
    //        PENDING_BUYER;*/


;

    EnumPaymentStatus(String barcode) {

    }
    EnumPaymentStatus() {

    }
}
