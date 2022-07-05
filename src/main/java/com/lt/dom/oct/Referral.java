package com.lt.dom.oct;

import java.util.List;

public class Referral {

    List<RoyaltyRuleData> royaltyRuleDataList ;

    public List<RoyaltyRuleData> getRoyaltyRuleDataList() {
        return royaltyRuleDataList;
    }

    public void setRoyaltyRuleDataList(List<RoyaltyRuleData> royaltyRuleDataList) {
        this.royaltyRuleDataList = royaltyRuleDataList;
    }

    private  int level;    //"level": 0,
    private String tpee; // 邀请码，   特定产品营销码

    //product_id, compainId 和 userId


    private String compaign_id;
    private String product_id;
    private String userId;

    public String getTpee() {
        return tpee;
    }

    public void setTpee(String tpee) {
        this.tpee = tpee;
    }

    public String getCompaign_id() {
        return compaign_id;
    }

    public void setCompaign_id(String compaign_id) {
        this.compaign_id = compaign_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
/*    private String originalUser;
    private List<String> referralUser;*/

}
