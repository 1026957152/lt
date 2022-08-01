package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumProductComponentSource;
import com.lt.dom.otcenum.EnumRoyaltyRuleCategory;
import com.lt.dom.otcenum.EnumWhenSettle;

public class RoyaltyRulePojo {


    private String royalty_mode;
    private String refund_mode;
    private String allocation_mode;





    private int amount ;// 折扣金额，在 type 为 1 时必传，取值范围 1~1000000000。
    private int percent;// 折扣百分比，在 type 为 2 时必传。例如值为 "20" 表示 8 折，值为 "100" 表示免费。

    private EnumRoyaltyRuleCategory category;
    private EnumWhenSettle enumWhenSettle;
    private EnumProductComponentSource supplier; // own, partner
    private long recipient; //结算账号


    public EnumProductComponentSource getSupplier() {
        return supplier;
    }

    public void setSupplier(EnumProductComponentSource supplier) {
        this.supplier = supplier;
    }


    public String getRoyalty_mode() {
        return royalty_mode;
    }

    public void setRoyalty_mode(String royalty_mode) {
        this.royalty_mode = royalty_mode;
    }

    public String getRefund_mode() {
        return refund_mode;
    }

    public void setRefund_mode(String refund_mode) {
        this.refund_mode = refund_mode;
    }

    public String getAllocation_mode() {
        return allocation_mode;
    }

    public void setAllocation_mode(String allocation_mode) {
        this.allocation_mode = allocation_mode;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public EnumRoyaltyRuleCategory getCategory() {
        return category;
    }

    public void setCategory(EnumRoyaltyRuleCategory category) {
        this.category = category;
    }

    public EnumWhenSettle getEnumWhenSettle() {
        return enumWhenSettle;
    }

    public void setEnumWhenSettle(EnumWhenSettle enumWhenSettle) {
        this.enumWhenSettle = enumWhenSettle;
    }

    public long getRecipient() {
        return recipient;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
