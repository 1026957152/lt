package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumReportBookingCompression;
import com.lt.dom.otcenum.EnumReportBookingSourceGroupby;
import com.lt.dom.otcenum.EnumReportMetric;

import java.time.LocalDate;

public class ReportBookingSourceReq {  // 这个就是机器了啊


    private EnumReportMetric metric;

    private LocalDate make_date_from;
    private LocalDate make_date_to;
    private LocalDate confirmed_date_from;
    private LocalDate confirmed_date_to;
    private LocalDate start_date_from;
    private LocalDate start_date_to;

    private Long agent;
    private Long product;
    private EnumReportBookingSourceGroupby groupby;
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
}
