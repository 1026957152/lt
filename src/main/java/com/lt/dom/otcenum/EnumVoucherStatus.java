package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumVoucherStatus {
    Created("Created"),
    Published("Published"),
    Issued("Issued"),

  //  Redeemed("Redeemed"),
    Available("Available"),
    Allocated("Allocated"),
    Unavailable("Unavailable"),

   // Available("Available"),
   // Expired("Expired"),
  //  Reedeemed("Reedeemed"),




    InGracePeriod("In Grace Period"),
    PartiallyRedeemed("In Grace Period"),
    Valid("In Grace Period"),

    Chargeback("In Grace Period"),//	A chargeback has been received for the original payment, the chargeback may end up being successfully reversed later and returned back to a valid status, otherwise it may remain chargeback.
    Expired("In Grace Period"),//	The expiryDate attribute is now in the past and the code has no grace period set, any remaining balance cannot be used unless the expiry is changed.
    FraudulentCodes("In Grace Period"),// confirmed as fraudulent will have this status which does not change.
    NotYetValid("In Grace Period"),//	Codes in this state have a validFromDate attribute with a future date in.
    PotentiallyFraudulent("In Grace Period"),//	This code has triggered fraud checks and further manual checks are being made. It will transition back to its original status or to fraudulent based on the outcome.
    Redeemed("In Grace Period"),//	The balance of the code has been entirely used.
    Void("In Grace Period"),//	The code has been refunded or otherwise disabled.



/*    In Grace Period	The expiryDate attribute is in the past but the graceExpiryDate is in the future. This code may still be redeemed as normal. Use of grace periods is optional, some codes will not have a graceExpiryDate.
    Partially Redeemed	Part of the codes balance has been used. A codes balance transactions history will breakdown what was used and when.
    Valid	Can be redeemed.*/
    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumVoucherStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.voucher.status."
                + this.name());
        return displayStatusString;
    }

}
