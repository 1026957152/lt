package com.lt.dom.OctResp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;


public class HomeResp {



    private CollectionModel campaignCategories;



    public void setCampaignCategories(CollectionModel campaignCategories) {
        this.campaignCategories = campaignCategories;
    }

    public CollectionModel getCampaignCategories() {
        return campaignCategories;
    }
}
