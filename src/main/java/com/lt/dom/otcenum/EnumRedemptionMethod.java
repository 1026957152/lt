package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumRedemptionMethod {


    CreditCardSwipe("Credit Card Swipe"),
    AMCGiftCardSwipe("AMC Gift Card Swipe"),
    AMCStubsCardSwipe("AMC Stubs Card Swipe"),
    ConfirmationCode("Confirmation Code"),
    QRCode("QR Code");



    EnumRedemptionMethod(String s) {

    }

    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumRedemptionMethod.values()).map(x->{

            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }
}
