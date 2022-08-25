package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumTourBookingStatus_ {
    // The order's current status:

    Draft(""),
    Pending("Customer started the checkout process but did not complete it. Incomplete orders are assigned a 'Pending' status and can be found under the More tab in the View Orders screen."),
    AwaitingBill_photo_image("旅投审核完成，客户需要完成 — Customer has completed the checkout process and payment has been confirmed."),
    PendingBill_photo_image(""),

    AwaitingFundAppropriation("旅投审核完成，客户需要完成 — Customer has completed the checkout process and payment has been confirmed."),
   // PendingPayment("旅投审核完成，客户需要完成 — Customer has completed the checkout process and payment has been confirmed."),
    Fulfillment(""),






    AwaitingPayment(" — Customer has completed the checkout process, but payment has yet to be confirmed. Authorize only transactions that are not yet captured have this status."),
    AwaitingShipment(" — Order has been pulled and packaged and is awaiting collection from a shipping provider."),
    AwaitingPickup(" — Order has been packaged and is awaiting customer pickup from a seller-specified location."),
    PartiallyShipped(" — Only some items in the order have been shipped."),
            Completed(" — Order has been shipped/picked up, and receipt is confirmed; client has paid for their digital product, and their file(s) are available for download."),
            Shipped(" — Order has been shipped, but receipt has not been confirmed; seller has used the Ship Items action. A listing of all orders with a 'Shipped' status can be found under the More tab of the View Orders screen."),
            Cancelled(" — Seller has cancelled an order, due to a stock inconsistency or other reasons. Stock levels will automatically update depending on your Inventory Settings. Cancelling an order will not refund the order. This status is triggered automatically when an order using an authorize-only payment gateway is voided in the control panel before capturing payment."),
    Declined(" — Seller has marked the order as declined."),
            Refunded(" — Seller has used the Refund action to refund the whole order. A listing of all orders with a 'Refunded' status can be found under the More tab of the View Orders screen."),
            Disputed(" — Customer has initiated a dispute resolution process for the PayPal transaction that paid for the order or the seller has marked the order as a fraudulent order."),
    ManualVerification(" Required — Order on hold while some aspect, such as tax-exempt documentation, is manually confirmed. Orders with this status must be updated manually. Capturing funds or other order actions will not automatically update the status of an order marked Manual Verification Required."),
    PartiallyRefunded(" — Seller has partially refunded the order."),
    ;



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumTourBookingStatus_(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.tour_booking.status."
                + this.name());
        return displayStatusString;
    }

    }
