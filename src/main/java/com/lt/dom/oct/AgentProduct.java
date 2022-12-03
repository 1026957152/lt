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
    Agent agent;

    @JsonBackReference
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;
    private Float net;
    private Long sku;


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

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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
}
