package com.lt.dom.OctResp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;


public class HomeResp {




    private String phone;


    private CollectionModel campaigns;
    private CollectionModel campaignCategories;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCampaigns(CollectionModel campaigns) {
        this.campaigns = campaigns;
    }

    public CollectionModel getCampaigns() {
        return campaigns;
    }


    public void setCampaignCategories(CollectionModel campaignCategories) {
        this.campaignCategories = campaignCategories;
    }

    public CollectionModel getCampaignCategories() {
        return campaignCategories;
    }
}
