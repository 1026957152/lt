package com.lt.dom.OctResp;

import com.lt.dom.otcenum.EnumPublicationObjectType;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class RedemptionEntryResp {





    private EnumPublicationObjectType relatedObjectType;









    private String holder;
    private int redeemed_amount;
    private String campaign_name;
    private String voucher_code;
    private LocalDateTime redeem_at;
    private int redeemed_quantity;

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public int getRedeemed_amount() {
        return redeemed_amount;
    }

    public void setRedeemed_amount(int redeemed_amount) {
        this.redeemed_amount = redeemed_amount;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }


    private RedemptionStatus result;


    public void setRelatedObjectType(EnumPublicationObjectType relatedObjectType) {
        this.relatedObjectType = relatedObjectType;
    }

    public EnumPublicationObjectType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRedeem_at(LocalDateTime redeem_at) {
        this.redeem_at = redeem_at;
    }

    public LocalDateTime getRedeem_at() {
        return redeem_at;
    }

    public void setRedeemed_quantity(int redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public int getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public static enum RedemptionStatus {
        SUCCESS,FAILURE;
    }


    public RedemptionStatus getResult() {
        return result;
    }

    public void setResult(RedemptionStatus result) {
        this.result = result;
    }




  //  public String holder ;//持有人姓名
    public String holder_id = "测试持有身份证号" ;//持有身份证号
    public String holder_phone= "测试持有人手机号";// 持有人手机号

    public String redeemer_name ="核销机构";// 核销机构
    public String redeemer_code ="核销机构编码";//核销机构编号

    public String redeemer_employee_code="核销职工编号" ;//核销人职工编号
    public String redeemer_employee_name ="核销人姓名" ;//核销人姓名
    public String redeemer_employee_id="核销人身份证号"; // 核销人身份证号

    public String code="核销编号";// 核销编号
   // public String campaign_name; //券活动名称
   // public String voucher_code;// 券编号
    public String published_at="领券时间";//  领券时间
    public String redeemed_at="核销时间"; // 核销时间
 //   public String  redeemed_quantity;// 核销数量
 //   public String  redeemed_amount;// 核销金额
}
