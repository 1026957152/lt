package com.lt.dom.OctResp;

import java.util.List;


public class HomeDashboardResp {
    private List latestBookings;

    public HomeSummary getSummary() {
        return summary;
    }

    public void setSummary(HomeSummary summary) {
        this.summary = summary;
    }

    private HomeSummary summary;
    private List<ProductResp> products;

    public void setProducts(List<ProductResp> products) {
        this.products = products;
    }

    public List<ProductResp> getProducts() {
        return products;
    }

    public <R> void setLatestBookings(List latestBookings) {
        this.latestBookings = latestBookings;
    }

    public List getLatestBookings() {
        return latestBookings;
    }
}
