package com.lt.dom.otcReq;


import com.lt.dom.otcenum.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReportArrivalReq {  // 这个就是机器了啊


    private EnumReportMetric metric;
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

    private LocalDate report_data_from;

    private LocalDate report_data_to;


    private LocalDate create_date_from;
    private LocalDate create_date_to;
    private LocalDate check_date_from;
    private LocalDate check_date_to;



    public LocalDate getReport_data_to() {
        return report_data_to;
    }

    public void setReport_data_to(LocalDate report_data_to) {
        this.report_data_to = report_data_to;
    }

    private Long supplier;
    private Long product;
    private EnumComponentVoucherStatus voucherStatus;


    private EnumReportType reportType;

    public EnumReportMetric getMetric() {
        return metric;
    }

    public void setMetric(EnumReportMetric metric) {
        this.metric = metric;
    }

    public LocalDate getReport_data_from() {
        return report_data_from;
    }

    public void setReport_data_from(LocalDate report_data_from) {
        this.report_data_from = report_data_from;
    }

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

    @NotNull
    private EnumReportBookingCompression compression;


    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public void setCompression(EnumReportBookingCompression compression) {
        this.compression = compression;
    }

    public EnumReportBookingCompression getCompression() {
        return compression;
    }

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
