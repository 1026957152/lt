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
    private Long redeemed_quantity;
    private String holder_real_name;
    private String holder_name;

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

    public void setRedeemed_quantity(Long redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }

    public Long getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setHolder_id(String holder_id) {
        this.holder_id = holder_id;
    }

    public String getHolder_id() {
        return holder_id;
    }

    public void setHolder_phone(String holder_phone) {
        this.holder_phone = holder_phone;
    }

    public String getHolder_phone() {
        return holder_phone;
    }

    public void setHolder_real_name(String holder_real_name) {
        this.holder_real_name = holder_real_name;
    }

    public String getHolder_real_name() {
        return holder_real_name;
    }

    public void setRedeemer_code(String redeemer_code) {
        this.redeemer_code = redeemer_code;
    }

    public String getRedeemer_code() {
        return redeemer_code;
    }

    public void setRedeemer_name(String redeemer_name) {
        this.redeemer_name = redeemer_name;
    }

    public String getRedeemer_name() {
        return redeemer_name;
    }

    public void setRedeemer_employee_code(String redeemer_employee_code) {
        this.redeemer_employee_code = redeemer_employee_code;
    }

    public String getRedeemer_employee_code() {
        return redeemer_employee_code;
    }

    public void setRedeemer_employee_id(String redeemer_employee_id) {
        this.redeemer_employee_id = redeemer_employee_id;
    }

    public String getRedeemer_employee_id() {
        return redeemer_employee_id;
    }

    public void setRedeemer_employee_name(String redeemer_employee_name) {
        this.redeemer_employee_name = redeemer_employee_name;
    }

    public String getRedeemer_employee_name() {
        return redeemer_employee_name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setPublished_at(LocalDateTime published_at) {
        this.published_at = published_at;
    }

    public LocalDateTime getPublished_at() {
        return published_at;
    }

    public void setRedeemed_at(LocalDateTime redeemed_at) {
        this.redeemed_at = redeemed_at;
    }

    public LocalDateTime getRedeemed_at() {
        return redeemed_at;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public String getHolder_name() {
        return holder_name;
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
    public LocalDateTime published_at;//  领券时间
    public LocalDateTime redeemed_at; // 核销时间
 //   public String  redeemed_quantity;// 核销数量
 //   public String  redeemed_amount;// 核销金额
}
