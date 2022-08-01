package com.lt.dom.OctResp;


import java.util.List;
import java.util.Map;

public class ReportSaleResp {

    private int total_sales;//"580.10",
    private int average_sales;//"145.03",

    private int total_orders;// 4,
    private int total_items; // 31,
    private int total_tax; //"26.10",
    private int total_shipping;//"20.00",
    private int total_discount; //"0.00",
    private int totals_grouped_by; //"day",
    private Map<String,Item> totals; //"day",

    public class Item{
        private int sales;//"-17.00",
        private int orders; //  1,
        private int items; //1,
        private int tax;//"0.00",
        private int shipping; //"0.00",
        private int discount; //"0.00",
        private int customers; // 0
    }

}
