package com.lt.dom.otcReq;

import com.lt.dom.oct.EarningRule;
import com.lt.dom.otcenum.EnumRewardType;

import java.util.List;


public class RewardReq {
    private EarningRule earningRules;

    public static class EarningRule {

        private Integer score;
        private String level;

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }

    public EarningRule getEarningRules() {
        return earningRules;
    }

    public void setEarningRules(EarningRule earningRules) {
        this.earningRules = earningRules;
    }

    public static class Parameters {

        private Product product;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }


    public static class Product {

        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
    private Parameters parameters;

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    private EnumRewardType type;

    private String idId;
    private String url;

    private long stock;
    private String name;
    private long redeemed;
    private String description_text;


    public String getName() {
        return name;
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

    public long getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(long redeemed) {
        this.redeemed = redeemed;
    }

    public String getDescription_text() {
        return description_text;
    }

    public void setDescription_text(String description_text) {
        this.description_text = description_text;
    }


    private List<MuseumReq.MediaReq> medias;

    public List<MuseumReq.MediaReq> getMedias() {
        return medias;
    }

    public void setMedias(List<MuseumReq.MediaReq> medias) {
        this.medias = medias;
    }
}
