package com.lt.dom.otcenum;

public enum EnumValidationStatus {
    AwaitingDependentMemberRevalidation("barcode"), //Awaiting dependent member revalidation.
    AwaitingRevalidation("reference number"), //Awaiting revalidation
    NewAwaitingValidation("Barcode scan"),//New awaiting validation
    Validating("Facial Recognition"), //Validating
    ValidationFailed("NFC tap"), //Validation failed.

    ValidationSucceeded("NFC tap"), //   Validation succeeded.
    None("NFC tap"),
    Unknow("Unknow"),

    ;


    EnumValidationStatus(String barcode) {

    }
}
