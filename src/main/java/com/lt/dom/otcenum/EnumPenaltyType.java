package com.lt.dom.otcenum;

public enum EnumPenaltyType {
    AmountPenalty("  Amount Penalty"),
    NightPenalty("Night Penalty"),
    PercentPenalty("Percent Penalty"),
   // FacialRecognition("Facial Recognition"),
  //  NFCtap("NFC tap"),

    NON_REFUNDABLE,
    PERCENTAGE,
            NONE,
    NIGHTS,
     FLAT,
   // The way in which the cancellation fee is calculated. This can be "NONE" in case a full refund is paid out, "NON_REFUNDABLE" if no refund is paid out and the full amount is due, "PERCENTAGE" if a percentage of the total price for the stay is charged as cancellation fee, "NIGHTS" if a defined number of initial nights of the stay are charged in full as cancellation fee or "FLAT" in case a flat cancellation fee is charged.

    ; //https://docs.impala.travel/docs/booking-api/c2NoOjQwMDU5Njg-cancellation-policy
    //Amount Penalty

    //Cancellations made after [start date time of penalty window] will result in a [amount] [currency] fee.

    //Night Penalty

    //Cancellations made after [start date time of penalty window] are subject to a [number of nights] night penalty plus taxes and fees.

 //   Percent Penalty

   // Cancellations made after [start date time of penalty window] are subject to a [percentage] percentage of total price penalty.
   // It is possible to have penalties of both amount and night amount and percent, but not a night and percent penalty in the same time frame.

    EnumPenaltyType(String barcode) {

    }
    EnumPenaltyType() {

    }
}
