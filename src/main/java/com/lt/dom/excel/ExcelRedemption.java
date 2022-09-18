package com.lt.dom.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lt.dom.oct.RedemptionEntry;

import java.time.LocalDateTime;

public class ExcelRedemption {

/*    @ExcelProperty("旅行社")
    private String agent;

    @ExcelProperty("旅行社编号")
    private String agent_code;*/

  //  agent_name	Agent name (if an agent is set on the booking)
  //  agent_code	Agent code (if an agent is set on the booking)
  //  agent_id



    @ExcelProperty("核销记录编号")
    

private String code;


    @ExcelProperty("团单编号")
    private String tour_booking_code;


    @ExcelProperty("旅行团")
    private String tour_name;

    @ExcelProperty("团号")
    private String tour_code;

    @ExcelProperty("导游姓名")
    private String guide_name;

    @ExcelProperty("导游手机号")
    private String guide_tel_mobile;

    @ExcelProperty("导游手机号")
    private String guide_id;



    @ExcelProperty("商户名称")
    private String merchant_name;

    @ExcelProperty("商户编号")
    private String merchant_code;


    @ExcelProperty("游客姓名")
    private String holder_real_name;
    @ExcelProperty("游客身份证号")
    private String holder_id_card;
    @ExcelProperty("游客手机号")
    private String holder_phone;

    @ExcelProperty("核销时游客经度")
    private float customer_latitude;
    @ExcelProperty("核销时游客维度")
    private float customer_longitude;

    @ExcelProperty("核销时商户经度")
    private float merchant_latitude;
    @ExcelProperty("核销时商户维度")
    private float merchant_longitude;





    @ExcelProperty("优惠券类型")
    private String campaign_name;
    @ExcelProperty("优惠券类型编号")
    private String campaign_code;
    @ExcelProperty("优惠券编号")
    private String voucher_code;


    @ExcelProperty("核销时间")
    private LocalDateTime redeem_at;



    @ExcelProperty("核销工作人员电话")
    private String written_off_staff_name;
    @ExcelProperty("核销工作人员姓名")
    private String written_off_staff_phone;

    @ExcelProperty("核销工作人员身份证号")
    private String written_off_staff_id;
    @ExcelProperty("领券时间")
    private LocalDateTime published_at;
    @ExcelProperty("核销金额")
    private int amount ;

    @ExcelProperty("核销数量")
    private Long quantity ;


    @ExcelProperty("审核资料文件名")
    private String filename;



    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }



    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public LocalDateTime getRedeem_at() {
        return redeem_at;
    }

    public void setRedeemed_at(LocalDateTime redeem_at) {
        this.redeem_at = redeem_at;
    }

    public LocalDateTime getPublished_at() {
        return published_at;
    }

    public void setPublished_at(LocalDateTime published_at) {
        this.published_at = published_at;
    }




    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

    public static ExcelRedemption from(RedemptionEntry x) {
        ExcelRedemption excelVoucher = new ExcelRedemption();
      //  excelVoucher.setAgent(x.getCode());

       // excelVoucher.setTour_name(x.getCode());
      //  excelVoucher.setTour_code(x.getCode());
        excelVoucher.setCode(x.getCode());
        excelVoucher.setCampaign_name(x.getCampaign_name());
        excelVoucher.setCampaign_code(x.getCampaign_code());


        excelVoucher.setHolder_phone(x.getHolder_phone());
        excelVoucher.setHolder_real_name(x.getHolder_real_name());
        excelVoucher.setHolder_id_card(x.getHolder_id_card());
        excelVoucher.setCustomer_latitude(x.getCustomer_longitude());
        excelVoucher.setCustomer_longitude(x.getCustomer_longitude());



        excelVoucher.setMerchant_code(x.getMerchant_code());
        excelVoucher.setMerchant_name(x.getMerchant_name());
        excelVoucher.setMerchant_latitude(x.getMerchant_latitude());
        excelVoucher.setMerchant_longitude(x.getMerchant_longitude());


        excelVoucher.setPublished_at(x.getPublished_at());
        excelVoucher.setRedeemed_at(x.getRedeemed_at());

        excelVoucher.setAmount(x.getRedeemed_amount());
        excelVoucher.setQuantity(x.getRedeemed_quantity());
        excelVoucher.setWritten_off_staff_name(x.getWritten_off_staff_name());
        excelVoucher.setWritten_off_staff_phone(x.getWritten_off_staff_phone());
        excelVoucher.setWritten_off_staff_id(x.getWritten_off_staff_id());
        excelVoucher.setVoucher_code(x.getVoucher_code());
        return excelVoucher;
    }




    public void setHolder_id_card(String holder_id_card) {
        this.holder_id_card = holder_id_card;
    }

    public String getHolder_id_card() {
        return holder_id_card;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getHolder_real_name() {
        return holder_real_name;
    }

    public void setHolder_real_name(String holder_real_name) {
        this.holder_real_name = holder_real_name;
    }

    public String getHolder_phone() {
        return holder_phone;
    }

    public void setHolder_phone(String holder_phone) {
        this.holder_phone = holder_phone;
    }

    public float getCustomer_latitude() {
        return customer_latitude;
    }

    public void setCustomer_latitude(float customer_latitude) {
        this.customer_latitude = customer_latitude;
    }

    public float getCustomer_longitude() {
        return customer_longitude;
    }

    public void setCustomer_longitude(float customer_longitude) {
        this.customer_longitude = customer_longitude;
    }

    public float getMerchant_latitude() {
        return merchant_latitude;
    }

    public void setMerchant_latitude(float merchant_latitude) {
        this.merchant_latitude = merchant_latitude;
    }

    public float getMerchant_longitude() {
        return merchant_longitude;
    }

    public void setMerchant_longitude(float merchant_longitude) {
        this.merchant_longitude = merchant_longitude;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getCampaign_code() {
        return campaign_code;
    }

    public void setCampaign_code(String campaign_code) {
        this.campaign_code = campaign_code;
    }

    public void setRedeem_at(LocalDateTime redeem_at) {
        this.redeem_at = redeem_at;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_code(String tour_code) {
        this.tour_code = tour_code;
    }

    public String getTour_code() {
        return tour_code;
    }

    public String getTour_booking_code() {
        return tour_booking_code;
    }

    public void setTour_booking_code(String tour_booking_code) {
        this.tour_booking_code = tour_booking_code;
    }

    public String getGuide_name() {
        return guide_name;
    }

    public void setGuide_name(String guide_name) {
        this.guide_name = guide_name;
    }

    public String getGuide_tel_mobile() {
        return guide_tel_mobile;
    }

    public void setGuide_tel_mobile(String guide_tel_mobile) {
        this.guide_tel_mobile = guide_tel_mobile;
    }

    public String getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(String guide_id) {
        this.guide_id = guide_id;
    }

    public String getWritten_off_staff_name() {
        return written_off_staff_name;
    }

    public void setWritten_off_staff_name(String written_off_staff_name) {
        this.written_off_staff_name = written_off_staff_name;
    }

    public String getWritten_off_staff_phone() {
        return written_off_staff_phone;
    }

    public void setWritten_off_staff_phone(String written_off_staff_phone) {
        this.written_off_staff_phone = written_off_staff_phone;
    }

    public void setWritten_off_staff_id(String written_off_staff_id) {
        this.written_off_staff_id = written_off_staff_id;
    }

    public String getWritten_off_staff_id() {
        return written_off_staff_id;
    }
}