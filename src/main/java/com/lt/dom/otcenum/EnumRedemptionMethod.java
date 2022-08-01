package com.lt.dom.otcenum;

public enum EnumRedemptionMethod {


    CreditCardSwipe("Credit Card Swipe"),
    AMCGiftCardSwipe("AMC Gift Card Swipe"),
    AMCStubsCardSwipe("AMC Stubs Card Swipe"),
    ConfirmationCode("Confirmation Code"),
    QRCode("QR Code"),


    barcode("barcode"),
    reference_number("reference number"),
    Barcodescan("Barcode scan"),
    FacialRecognition("Facial Recognition"),
    NFCtap("NFC tap");


    EnumRedemptionMethod(String s) {

    }
}
