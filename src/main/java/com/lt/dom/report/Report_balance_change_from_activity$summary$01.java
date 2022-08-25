package com.lt.dom.report;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lt.dom.oct.RedemptionEntry;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.Traveler;

import java.time.LocalDateTime;

public class Report_balance_change_from_activity$summary$01 {

    @ExcelProperty("旅行社")
    private String agent;

    @ExcelProperty("旅行社编号")
    private String agent_code;

  //  agent_name	Agent name (if an agent is set on the booking)
  //  agent_code	Agent code (if an agent is set on the booking)
  //  agent_id
    @ExcelProperty("旅行团")
    private String tour_name;

    @ExcelProperty("团号")
    private String tourId;

    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("身份证号")
    private String id;
    @ExcelProperty("手机号")
    private String phone;


    @ExcelProperty("导游姓名")
    private String lead_customer_name;

    @ExcelProperty("导游手机号")
    private String lead_customer_tel_mobile;

    @ExcelProperty("优惠券编号")
    private String voucher_code;


    @ExcelProperty("核销时间")
    private LocalDateTime redeem_at;


    @ExcelProperty("领券时间")
    private LocalDateTime issued_at;


    @ExcelProperty("审核资料文件名")
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public String getLead_customer_name() {
        return lead_customer_name;
    }

    public void setLead_customer_name(String lead_customer_name) {
        this.lead_customer_name = lead_customer_name;
    }

    public String getLead_customer_tel_mobile() {
        return lead_customer_tel_mobile;
    }

    public void setLead_customer_tel_mobile(String lead_customer_tel_mobile) {
        this.lead_customer_tel_mobile = lead_customer_tel_mobile;
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

    public void setRedeem_at(LocalDateTime redeem_at) {
        this.redeem_at = redeem_at;
    }

    public LocalDateTime getIssued_at() {
        return issued_at;
    }

    public void setIssued_at(LocalDateTime issued_at) {
        this.issued_at = issued_at;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public static Report_balance_change_from_activity$summary$01 from(RedemptionEntry x) {
        Report_balance_change_from_activity$summary$01 excelVoucher = new Report_balance_change_from_activity$summary$01();
        excelVoucher.setAgent(x.getCode());
        excelVoucher.setTour_name(x.getCode());
        excelVoucher.setTourId(x.getCode());
        excelVoucher.setName(x.getCode());
        excelVoucher.setId(x.getRelatedObjectType().name());
        excelVoucher.setPhone(x.getRelatedObjectType().name());
        return excelVoucher;
    }

    public static Report_balance_change_from_activity$summary$01 from(RedemptionEntry x, Traveler traveler, Supplier supplier) {
        Report_balance_change_from_activity$summary$01 excelVoucher = new Report_balance_change_from_activity$summary$01();
        excelVoucher.setAgent("supplier.getName()");
        excelVoucher.setTour_name("supplier.getName()");
        excelVoucher.setTourId("supplier.getName()");
        excelVoucher.setLead_customer_name("supplier.getName()");
        excelVoucher.setLead_customer_tel_mobile("supplier.getName()");
        excelVoucher.setVoucher_code(x.getVoucher()+"");
        excelVoucher.setIssued_at(x.getCreatedAt());
        excelVoucher.setRedeem_at(x.getCreatedAt());
        excelVoucher.setName(traveler.getName());
        excelVoucher.setId(traveler.getIdNo());
        excelVoucher.setPhone(traveler.getTel_home());
        return excelVoucher;
    }


}