package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lt.dom.otcenum.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class ComponentVoucherReq {  // 这个就是机器了啊

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private EnumVoucherRedemptionStatus check_in_status;
    private Long agent;

    public EnumVoucherRedemptionStatus getCheck_in_status() {
        return check_in_status;
    }

    public void setCheck_in_status(EnumVoucherRedemptionStatus check_in_status) {
        this.check_in_status = check_in_status;
    }

    public EnumComponentVoucherStatus getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(EnumComponentVoucherStatus voucherStatus) {
        this.voucherStatus = voucherStatus;
    }




    private LocalDate create_date_from;
    private LocalDate create_date_to;
    private LocalDate check_date_from;
    private LocalDate check_date_to;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private List<LocalDate> check_date_from_to;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private List<LocalDate> create_date_from_to;

    public List<LocalDate> getCheck_date_from_to() {
        return check_date_from_to;
    }

    public void setCheck_date_from_to(List<LocalDate> check_date_from_to) {
        this.check_date_from_to = check_date_from_to;
    }

    public List<LocalDate> getCreate_date_from_to() {
        return create_date_from_to;
    }

    public void setCreate_date_from_to(List<LocalDate> create_date_from_to) {
        this.create_date_from_to = create_date_from_to;
    }

    public LocalDate getCreate_date_from() {
        return create_date_from;
    }

    public void setCreate_date_from(LocalDate create_date_from) {
        this.create_date_from = create_date_from;
    }

    public LocalDate getCreate_date_to() {
        return create_date_to;
    }

    public void setCreate_date_to(LocalDate create_date_to) {
        this.create_date_to = create_date_to;
    }

    public LocalDate getCheck_date_from() {
        return check_date_from;
    }

    public void setCheck_date_from(LocalDate check_date_from) {
        this.check_date_from = check_date_from;
    }

    public LocalDate getCheck_date_to() {
        return check_date_to;
    }

    public void setCheck_date_to(LocalDate check_date_to) {
        this.check_date_to = check_date_to;
    }


    private Long supplier;
    private Long product;
    private EnumComponentVoucherStatus voucherStatus;


    private EnumReportType reportType;

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public EnumReportType getReportType() {
        return reportType;
    }

    public void setReportType(EnumReportType reportType) {
        this.reportType = reportType;
    }

/*
    @NotNull
    private EnumReportBookingCompression compression;
*/


    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

/*
    public void setCompression(EnumReportBookingCompression compression) {
        this.compression = compression;
    }

    public EnumReportBookingCompression getCompression() {
        return compression;
    }
*/

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public Long getAgent() {
        return agent;
    }

    public void setAgent(Long agent) {
        this.agent = agent;
    }
}
