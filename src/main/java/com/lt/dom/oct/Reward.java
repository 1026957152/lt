package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumRewardType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Reward extends Base{

    private String name;



    @NotNull
    private EnumRewardType type;

    private String idId;
    private String url;
    @NotNull
    private long stock;
    private long supplier;
    private long redeemed;
    private String description;
    private String code;
    private Long product;
    private Integer earningRule_score;
    private String earningRule_level;

    public String getIdId() {
        return idId;
    }

    public void setIdId(String idId) {
        this.idId = idId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumRewardType getType() {
        return type;
    }

    public void setType(EnumRewardType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setRedeemed(long redeemed) {
        this.redeemed = redeemed;
    }

    public long getRedeemed() {
        return redeemed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getProduct() {
        return product;
    }

    public void setEarningRule_score(Integer earningRule_score) {
        this.earningRule_score = earningRule_score;
    }

    public Integer getEarningRule_score() {
        return earningRule_score;
    }

    public void setEarningRule_level(String earningRule_level) {
        this.earningRule_level = earningRule_level;
    }

    public String getEarningRule_level() {
        return earningRule_level;
    }
}
