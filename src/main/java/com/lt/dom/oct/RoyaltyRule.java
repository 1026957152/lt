package com.lt.dom.oct;

import java.util.List;

public class RoyaltyRule {


    private String royalty_mode;
    private String refund_mode;
    private String allocation_mode;

    private List<RoyaltyRuleData> royaltyRuleData;

    public List<RoyaltyRuleData> getRoyaltyRuleData() {
        return royaltyRuleData;
    }

    public void setRoyaltyRuleData(List<RoyaltyRuleData> royaltyRuleData) {
        this.royaltyRuleData = royaltyRuleData;
    }

/*      "royalty_mode": "rate",
              "refund_mode": "no_refund",
              "allocation_mode": "receipt_reserved",
              "data": [
    {
        "level": 0,
            "value": 11
    },
    {
        "level": 1,
            "value": 12
    }
        ]*/
}
