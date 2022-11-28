package com.lt.dom.credit;

public enum EnumActionType {
    internal("barcode"),


    invoice_created("invoice.created"),
    // data.object is an invoice
    // Occurs whenever a new invoice is created. To learn how webhooks can be used with this event, and how they can affect it, see Using Webhooks with Subscriptions.

    invoice_deleted("invoice.deleted"),
    // data.object is an invoice
    // Occurs whenever a draft invoice is deleted.

     //       invoice_finalization_failed("invoice.finalization_failed"),
    // data.object is an invoice
    // Occurs whenever a draft invoice cannot be finalized. See the invoice’s last finalization error for details.

            invoice_finalized("invoice.finalized"),
    // data.object is an invoice
    // Occurs whenever a draft invoice is finalized and updated to be an open invoice.

    invoice_marked_uncollectible("invoice.marked_uncollectible"),
    // data.object is an invoice
    // Occurs whenever an invoice is marked uncollectible.

            invoice_paid("invoice.paid"),
    // data.object is an invoice
    // Occurs whenever an invoice payment attempt succeeds or an invoice is marked as paid out-of-band.

            invoice_payment_action_required("invoice.payment_action_required"),
    // data.object is an invoice
    // Occurs whenever an invoice payment attempt requires further user action to complete.

    invoice_payment_failed("invoice.payment_failed"),
    // data.object is an invoice
    // Occurs whenever an invoice payment attempt fails, due either to a declined payment or to the lack of a stored payment method.

            invoice_payment_succeeded("invoice.payment_succeeded"),
    // data.object is an invoice
    // Occurs whenever an invoice payment attempt succeeds.

            invoice_sent("invoice.sent"),
    // data.object is an invoice
    // Occurs whenever an invoice email is sent out.

    invoice_upcoming("invoice.upcoming"),
    // data.object is an invoice
    // Occurs X number of days before a subscription is scheduled to create an invoice that is automatically charged—where X is determined by your subscriptions settings. Note: The received Invoice object will not have an invoice ID.

    invoice_updated("invoice.updated"),
    // data.object is an invoice
    // Occurs whenever an invoice changes (e.g., the invoice amount).

    invoice_voided("invoice.voided"),
    // data.object is an invoice
    // Occurs whenever an invoice is voided.

    ;


    EnumActionType(String barcode) {

    }
}
