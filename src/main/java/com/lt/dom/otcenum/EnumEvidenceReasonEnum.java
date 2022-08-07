package com.lt.dom.otcenum;

public enum EnumEvidenceReasonEnum {

    not_received("Merchandise or service was not received."),

    fraudulent("The cardholder did not make the transaction."),

    duplicate("There were multiple copies of a charge for a single purchase, or the charge was paid by other means."),

    other("All other types of disputes."),

    merchandise_not_as_described("The merchandise was not as described."),

    service_not_as_described("The service was not as described."),

    canceled(""),

    ;


    EnumEvidenceReasonEnum(String barcode) {
    }
}
