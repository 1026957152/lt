package com.lt.dom.otcenum;

public enum EnumRefundStatus {
    pending("barcode"),
    succeeded("succeeded"),
    failed("failed"),



    requires_action("requires_action"),
    canceled("canceled"),




//    pending, succeeded, or failed. For other types of refunds, it can be pending, requires_action, succeeded, failed, or canceled

    ;


    EnumRefundStatus(String barcode) {

    }
}
