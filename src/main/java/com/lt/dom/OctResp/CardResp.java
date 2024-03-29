package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Card;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class CardResp {


    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;
    @JsonProperty("billing")
    private BillingDTO billing;
    @JsonProperty("company")
    private Object company;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("email")
    private String email;
    @JsonProperty("individual")
    private Object individual;
    @JsonProperty("livemode")
    private Boolean livemode;
    @JsonProperty("metadata")
    private MetadataDTO metadata;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("redaction")
    private Object redaction;
    @JsonProperty("requirements")
    private RequirementsDTO requirements;
    @JsonProperty("spending_controls")
    private SpendingControlsDTO spendingControls;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;

    public static CardResp of(Card e) {
        return null;
    }

    public static CardResp from(Card theatre) {
        return null;
    }

    @NoArgsConstructor
    @Data
    public static class BillingDTO {
        @JsonProperty("address")
        private AddressDTO address;

        @NoArgsConstructor
        @Data
        public static class AddressDTO {
            @JsonProperty("city")
            private String city;
            @JsonProperty("country")
            private String country;
            @JsonProperty("line1")
            private String line1;
            @JsonProperty("line2")
            private Object line2;
            @JsonProperty("postal_code")
            private String postalCode;
            @JsonProperty("state")
            private String state;
        }
    }

    @NoArgsConstructor
    @Data
    public static class MetadataDTO {
        @JsonProperty("order_id")
        private String orderId;
    }

    @NoArgsConstructor
    @Data
    public static class RequirementsDTO {
        @JsonProperty("disabled_reason")
        private Object disabledReason;
        @JsonProperty("past_due")
        private List<?> pastDue;
    }

    @NoArgsConstructor
    @Data
    public static class SpendingControlsDTO {
        @JsonProperty("allowed_categories")
        private List<?> allowedCategories;
        @JsonProperty("blocked_categories")
        private List<?> blockedCategories;
        @JsonProperty("spending_limits")
        private List<SpendingLimitsDTO> spendingLimits;
        @JsonProperty("spending_limits_currency")
        private String spendingLimitsCurrency;

        @NoArgsConstructor
        @Data
        public static class SpendingLimitsDTO {
            @JsonProperty("amount")
            private Integer amount;
            @JsonProperty("categories")
            private List<?> categories;
            @JsonProperty("interval")
            private String interval;
        }
    }
}
