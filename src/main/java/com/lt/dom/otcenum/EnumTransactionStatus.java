package com.lt.dom.otcenum;

public enum EnumTransactionStatus {
    barcode("barcode"),
    reference_number("reference number"),
    Barcodescan("Barcode scan"),
    FacialRecognition("Facial Recognition"),
    NFCtap("NFC tap"),

/*    created	Created
    pending	Pending
    system_approved	Approved by system
    manual_approval	Approved by admin
    processed	Processed
    frozen	Frozen
    unapproved	Unapproved
    failed	Failed
    completed	Completed
    rejected	Rejected
    refund	Refund*/
    ;


    EnumTransactionStatus(String barcode) {

    }
}
