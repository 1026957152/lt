package com.lt.dom.otcenum;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumSettings {
    default_product_tax_code("默认产品税务代码",EnumValueType.string_),
    default_shipping_tax_code("默认税务代码",EnumValueType.string_),
    home_page_high_Quality_Product_recommendation("reference number",EnumValueType.int_),

    Logo("reference number",EnumValueType.image_),

    //User notifications


    //Customer notifications
    //Automatic messages
    Email_notification_for_new_reservation_requests("Email notification for new reservation requests",EnumValueType.boolean_),
    SMS_notification_for_new_reservation_requests("SMS notification for new reservation requests",EnumValueType.boolean_),
    Email_notification_for_new_reservation("Email notification for new reservation",EnumValueType.boolean_),
    SMS_notification_for_new_reservation("SMS notification for new reservation",EnumValueType.boolean_),


    //Active
    //Disable,



    Terms_and_conditions("Terms and conditions",EnumValueType.string_),
    Require_terms_to_be_accepted("Require terms to be accepted",EnumValueType.boolean_),



    Text_displayed_when_booking_is_waiting_for_confirmation("Text displayed when booking is waiting for confirmation",EnumValueType.string_),

    Text_displayed_on_confirmation_page("Text displayed on confirmation page",EnumValueType.string_),

    Text_displayed_when_there_is_no_availability("Text displayed when there is no availability",EnumValueType.string_),
            ;

    String barcode;
    EnumValueType type;

    public EnumValueType getType() {
        return type;
    }

    public void setType(EnumValueType type) {
        this.type = type;
    }

    EnumSettings(String barcode, EnumValueType type) {

        this.barcode = barcode;
        this.type = type;
    }

    @Autowired
    private MessageSource messageSource;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.settings."
                + this.name());
        return displayStatusString;
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EnumResp {

        private String id;
        private String value_type;
        private String text;
        private String key;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue_type() {
            return value_type;
        }

        public void setValue_type(String value_type) {
            this.value_type = value_type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static EnumResp of(EnumSettings e) {
        EnumResp enumResp = new EnumResp();
        enumResp.setText(e.toString());
        enumResp.setKey(e.name());
        enumResp.setId(e.name());
        enumResp.setValue_type(e.getType().name());
        return enumResp;
    }
}
