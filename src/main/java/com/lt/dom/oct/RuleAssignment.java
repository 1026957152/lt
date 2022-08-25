package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RuleAssignment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String note;

    private long clain_redeem;


    private long campaign;

    private long value_list;

    private long rule;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getClain_redeem() {
        return clain_redeem;
    }

    public void setClain_redeem(long clain_redeem) {
        this.clain_redeem = clain_redeem;
    }

    public long getCampaign() {
        return campaign;
    }

    public void setCampaign(long campaign) {
        this.campaign = campaign;
    }
}
