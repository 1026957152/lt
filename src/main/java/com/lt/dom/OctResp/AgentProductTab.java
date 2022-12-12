package com.lt.dom.OctResp;

import com.lt.dom.oct.AgentProduct;

public  class AgentProductTab {

        private Long agent;


    private Float net; // 分销价
    private Long sku;
    private Float original; //票面原价
    private Float market; // 市场价
    private String productCode;
    private String productName;

    public static AgentProductTab from(AgentProduct ex) {

        AgentProductTab agentProductTab = new AgentProductTab();
        agentProductTab.setMarket(ex.getMarket());
        agentProductTab.setNet(ex.getNet());
        agentProductTab.setOriginal(ex.getOriginal());
        agentProductTab.setProductCode(ex.getProduct().getCode());
        agentProductTab.setProductName(ex.getProduct().getName());
        return agentProductTab;
    }

    public Float getNet() {
        return net;
    }

    public void setNet(Float net) {
        this.net = net;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Float getOriginal() {
        return original;
    }

    public void setOriginal(Float original) {
        this.original = original;
    }

    public Float getMarket() {
        return market;
    }

    public void setMarket(Float market) {
        this.market = market;
    }

    public Long getAgent() {
            return agent;
        }

        public void setAgent(Long agent) {
            this.agent = agent;
        }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}