package com.lt.dom.OctResp;

import java.util.List;
import java.util.Map;


public class HomeDashboardResp {
    private List latestBookings;
    private Map login;

    public Map getSummary() {
        return summary;
    }

    public void setSummary(Map summary) {
        this.summary = summary;
    }

    private Map summary;
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

    public <K, V> void setLogin(Map login) {
        this.login = login;
    }

    public Map getLogin() {
        return login;
    }
}
