package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class ExtraService extends Base {

    @JsonProperty("id")
    private Integer idX;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("unit_price")
    private Double unitPrice;
    @JsonProperty("name")
    private String name;
    @JsonProperty("position")
    private Integer position;

    @Transient
    @JsonProperty("pricings")
    private List<PricingsDTO> pricings;
    @JsonProperty("pricing_method")
    private String pricingMethod;

/*    {
        "id": 416,
            "amount": 0,
            "unit_price": 0.0,
            "name": "Goods Insurance",
            "position": 2,
            "pricings": [
        {
            "id": 2146,
                "fees": 0.0,
                "position": 1,
                "level_price": 1000000000.0,
                "display_level_price": "Rp 1 billion",
                "display_fees": "Free",
                "display_fees_without_currency": "Free"
        }
        ],
        "pricing_method": "by_options"
    }*/


    @NoArgsConstructor
    @Data
    public static class PricingsDTO {
        @JsonProperty("id")
        private Integer idX;
        @JsonProperty("fees")
        private Double fees;
        @JsonProperty("position")
        private Integer position;
        @JsonProperty("level_price")
        private Double levelPrice;
        @JsonProperty("display_level_price")
        private String displayLevelPrice;
        @JsonProperty("display_fees")
        private String displayFees;
        @JsonProperty("display_fees_without_currency")
        private String displayFeesWithoutCurrency;
    }
}