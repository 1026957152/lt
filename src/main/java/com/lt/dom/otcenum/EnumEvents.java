package com.lt.dom.otcenum;

public enum EnumEvents {
    campaign$validation_rules$created("campaign.validation_rules.created"),
    campaign$created("campaign.created"),
    campaign$disabled("campaign.disabled"),
    campaign$export_mailchimp_scheduled("campaign.export_mailchimp_done"),
    campaign$export_mailchimp_done("campaign.export_mailchimp_done"),


    campaign$validation_rules$deleted("campaign.validation_rules.deleted"),
    campaign$validation_rules$updated("campaign.validation_rules.updated"),
    campaign$updated("campaign.updated"),
    campaign$deleted("campaign.deleted"),
    campaign$export_mailchimp_failed("campaign.export_mailchimp_failed"),



    campaign$enabled("campaign.enabled"),


    redemption$succeeded("redemption.succeeded"),
    redemption$failed("redemption.failed"),
    redemption$rollback$failed("redemption.rollback.failed"),
    redemption$rollback_succeeded(" redemption.rollback.succeeded"),
    bulk_redemption$succeeded("redemption.succeeded"),
    bulk_redemption$failed("redemption.failed"),



    voucher$validation$succeeded("voucher.validation.succeeded"),
    voucher$updated("voucher.updated"),
    voucher$enabled("voucher.enabled"),
    voucher$gift$balance_added("voucher.gift.balance_added"),





    voucher$validation_rules$updated("voucher.validation_rules.updated"),
    voucher$published("voucher.published"),
    voucher$validation_rules$deleted("voucher.validation_rules.deleted"),
    voucher$disabled("voucher.disabled"),
    voucher$bulk_published("voucher.published"),


    voucher$validation$failed("voucher.validation.failed"),
    voucher$validation_rules$created("voucher.validation_rules.created"),
    voucher$deleted("voucher.deleted"),
    voucher$created("voucher.created"),




    customer$rewarded("customer.rewarded"),
    customer$rewarded$loyalty_points("customer.rewarded.loyalty_points"),
    customer$sms$sent("customer.sms.sent"),
    customer$email$failed("customer.email.failed"),



    customer$deleted("customer.deleted"),
    customer$created("customer.created"),
            customer$updated("customer.updated"),
    customer$segment$left("customer.segment.left"),


    customer$sms$failed("customer.sms.failed"),
    customer$segment$entered("customer.segment.entered"),
    customer$email$sent("customer.email.sent"),



    order$canceled("order.canceled"),
    order$created("order.created"),
            order$updated("order.updated"),
            order$paid("order.paid"),



    product$updated("product.updated"),
    product$created("product.created"),
    product$deleted(" product.deleted"),


    charge$expired("charge.expired"), //Occurs whenever an uncaptured charge expires.

    charge$failed("charge.failed"), //Occurs whenever a failed charge attempt occurs.

    charge$pending("charge.failed"),//Occurs whenever a pending charge is created.
    charge$refunded("charge.refunded"),//Occurs whenever a charge is refunded, including partial refunds.
    charge$succeeded("charge.succeeded"),//Occurs whenever a charge is successful.
    charge$updated("charge.updated"),// Occurs whenever a charge description or metadata is updated.




    order$CREATE("order.CREATE"),// Occurs whenever a charge description or metadata is updated.

    gift$DELIVERY_CONFIRMED("gift.DELIVERY_CONFIRMED"),//Occurs whenever a pending charge is created.
    gift$DELIVERY_BOUNCED("gift.DELIVERY_BOUNCED"),//Occurs whenever a charge is refunded, including partial refunds.
    gift$REDEEMED("gift.REDEEMED"),//Occurs whenever a charge is successful.
    gift$PAYOUT_FAILED("gift.PAYOUT_FAILED"),// Occurs whenever a charge description or metadata is updated.

    gift$PAYOUT_CANCELED("gift.PAYOUT_CANCELED"),// Occurs whenever a charge description or metadata is updated.

/*
    order.CREATE	Emitted only when the ordering channel is one other than the API
    gift.DELIVERY_CONFIRMED	GiftRocket successfully delivered the gift via sms or email
    gift.DELIVERY_BOUNCED	GiftRocket failed to deliver the gift via sms or email
    gift.REDEEMED	Recipient redeemed their gift
    gift.PAYOUT_FAILED	The payout failed and the gift is again in a redeemable state
    gift.PAYOUT_CANCELED	The payout was canceled by an account admin and the gift is again in a redeemable state
    funding.NFS	Your current order exceeds your funding balance.*/


    qrcode_reader$scan("charge.failed")

    ;

    EnumEvents(String barcode) {

    }
}
