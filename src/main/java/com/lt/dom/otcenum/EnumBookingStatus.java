package com.lt.dom.otcenum;

public enum EnumBookingStatus {
   // The order's current status:




/*    NB: If cancel_reason reason is set then the booking may be cancelled, for example it's possible to have a "Provisional" booking that is cancelled. You must check that cancel_reason is 0 if you want to ensure the booking is not cancelled
    status_text	Textual representation of the booking status.
            E.g. "Confirmed (A)"*/
    Quote,
    Provisional,
    Confirmed,
    Confirmed_archived,

}
