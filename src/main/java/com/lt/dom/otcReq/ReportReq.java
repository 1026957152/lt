package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumReportMetric;
import com.lt.dom.otcenum.EnumReportPeriod;

import java.time.LocalDate;

public class ReportReq {  // 这个就是机器了啊


    private EnumReportMetric metric;
    private LocalDate report_date_from;
    private LocalDate report_date_to;

    private Long product;
    private Long agent;

    public LocalDate getReport_date_from() {
        return report_date_from;
    }

    public void setReport_date_from(LocalDate report_date_from) {
        this.report_date_from = report_date_from;
    }

    public LocalDate getReport_date_to() {
        return report_date_to;
    }

    public void setReport_date_to(LocalDate report_date_to) {
        this.report_date_to = report_date_to;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getAgent() {
        return agent;
    }

    public void setAgent(Long agent) {
        this.agent = agent;
    }
}
