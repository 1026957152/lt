package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class AgentProduct extends BaseWithoutId{

    @EmbeddedId
    AgentProductKey id;


    @JsonBackReference
    @ManyToOne
    @MapsId("agentId")
    @JoinColumn(name = "third_party_id")
    AgentConnection agentConnection;

    @JsonBackReference
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;
    private Float net; // 分销价
    private Long sku;
    private Float original; //票面原价
    private Float market; // 市场价


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private String code;

    private String name;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AgentProductKey getId() {
        return id;
    }

    public void setId(AgentProductKey id) {
        this.id = id;
    }

    public AgentConnection getAgent() {
        return agentConnection;
    }

    public void setAgent(AgentConnection agentConnection) {
        this.agentConnection = agentConnection;
    }

    public void setNet(Float net) {

        this.net = net;
    }

    public Float getNet() {
        return net;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Long getSku() {
        return sku;
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
}
