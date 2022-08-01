package com.lt.dom.otcReq;


import com.lt.dom.oct.Campaign;

public class ScenarioAddCampaignReq {

    private long campaign;

    public long getCampaign() {
        return campaign;
    }

    public void setCampaign(long campaign) {
        this.campaign = campaign;
    }

    private String name;

    private String note;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
