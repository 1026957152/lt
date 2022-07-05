package com.lt.dom.otcReq;

import com.lt.dom.domain.SettleAccount;
import com.lt.dom.oct.RoyaltyRule;

public class RoyaltyPojo {  //分润模板

    //一级，二级，特定;

    private String type; //    //供应商 ，平台，分销一级，分销二级，特定


    private String product_id;
    private String supplier_id;


    private RoyaltyRule royaltyRule;


    private SettleAccount settle_account;
    private long productId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SettleAccount getSettle_account() {
        return settle_account;
    }

    public void setSettle_account(SettleAccount settle_account) {
        this.settle_account = settle_account;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
