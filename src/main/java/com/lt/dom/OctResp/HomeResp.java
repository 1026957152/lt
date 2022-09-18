package com.lt.dom.OctResp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;


public class HomeResp {



    private CollectionModel campaignCategories;
    private List<EntityModel<ProductResp>> products;
    private List<EntityModel> attractions;
    private List<PhotoResp> carousel;


    public void setCampaignCategories(CollectionModel campaignCategories) {
        this.campaignCategories = campaignCategories;
    }

    public CollectionModel getCampaignCategories() {
        return campaignCategories;
    }

    public void setProducts(List<EntityModel<ProductResp>> products) {

        this.products = products;
    }

    public List<EntityModel<ProductResp>> getProducts() {
        return products;
    }

    public  void setAttractions(List<EntityModel> attractions) {
        this.attractions = attractions;
    }

    public List<EntityModel> getAttractions() {
        return attractions;
    }

    public void setCarousel(List<PhotoResp> carousel) {
        this.carousel = carousel;
    }

    public List<PhotoResp> getCarousel() {
        return carousel;
    }
}
