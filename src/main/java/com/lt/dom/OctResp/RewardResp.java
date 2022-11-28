package com.lt.dom.OctResp;

import com.lt.dom.oct.Reward;
import com.lt.dom.otcReq.RewardReq;
import com.lt.dom.otcenum.EnumRewardType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


public class RewardResp {


    private EarningRule earningRule;
    private String name;
    private long remaining;

    public EarningRule getEarningRule() {
        return earningRule;
    }

    public void setEarningRule(EarningRule earningRule) {
        this.earningRule = earningRule;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    public long getRemaining() {
        return remaining;
    }

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



    private EnumRewardType type;

    private long stock;




    private long supplier;
    private long redeemed;
    private String description;
    private MediaResp media;

    public EnumRewardType getType() {
        return type;
    }

    public void setType(EnumRewardType type) {
        this.type = type;
    }



    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(long redeemed) {
        this.redeemed = redeemed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static RewardResp from(Reward referral) {


        RewardResp rewardResp = new RewardResp();
        rewardResp.setName(referral.getName());
        rewardResp.setStock(referral.getStock());
        rewardResp.setDescription(referral.getDescription());
        rewardResp.setRedeemed(referral.getRedeemed());
        rewardResp.setRemaining(referral.getStock()-referral.getRedeemed());
        rewardResp.setType(referral.getType());
        EarningRule e = new EarningRule();

        e.setLevel(referral.getEarningRule_level());
        e.setScore(referral.getEarningRule_score());
        rewardResp.setEarningRule(e);


        return rewardResp;
    }

    public void setMedia(MediaResp media) {
        this.media = media;
    }

    public MediaResp getMedia() {
        return media;
    }
}
