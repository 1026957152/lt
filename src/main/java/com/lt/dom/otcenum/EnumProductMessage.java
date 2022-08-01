package com.lt.dom.otcenum;

public enum EnumProductMessage {


    Add_information_to_confirmation_emails("Emails","Add information to confirmation emails()"),
    Add_attachment_to_emails("Emails","Add attachment to emails"),
    Also_send_supplier_notification_email_to_these_recipients("Emails","Also send supplier notification email to these recipient(s)"),
    Show_a_QR_Code_for_each_Participant("Emails","Show a QR Code for each Participant"),

    Send_me_an_SMS_when_I_get_a_booking("SMS","Send me an SMS when I get a booking"),
    Send_an_SMS_to_the_customer_to_confirm_his_order("SMS","Send an SMS to the customer to confirm his order"),

    ;

    EnumProductMessage(String sms, String s) {

    }
}
