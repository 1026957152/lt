package com.lt.dom.OctResp.home;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.ComponentRightResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.vo.GalleryImageVo;
import org.javatuples.Pair;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomePassResp {



    private CollectionModel campaignCategories;
    private List<EntityModel<ProductResp>> recommend_products;
    private List<EntityModel> recommend_attractions;
    private List<PhotoResp> carousel;
    private List<EntityModel<FeatureResp>> features;
    private Boolean show_policy;
    private String policy;
    private List<EntityModel<ComponentRightResp>> component_rights;
    private List<GalleryImageVo> gallery;

    public static void noteShow(HomePassResp homeResp, Pair<Boolean, String> stringPair) {

        homeResp.setShow_policy(stringPair.getValue0());

        if(stringPair.getValue0()){
            homeResp.setPolicy(stringPair.getValue1());
        }
    }


    public void setCampaignCategories(CollectionModel campaignCategories) {
        this.campaignCategories = campaignCategories;
    }

    public CollectionModel getCampaignCategories() {
        return campaignCategories;
    }

    public void setRecommend_products(List<EntityModel<ProductResp>> products) {

        this.recommend_products = products;
    }

    public List<EntityModel<ProductResp>> getRecommend_products() {
        return recommend_products;
    }

    public  void setRecommend_attractions(List<EntityModel> recommend_attractions) {
        this.recommend_attractions = recommend_attractions;
    }

    public List<EntityModel> getRecommend_attractions() {
        return recommend_attractions;
    }

    public void setCarousel(List<PhotoResp> carousel) {
        this.carousel = carousel;
    }

    public List<PhotoResp> getCarousel() {
        return carousel;
    }

    public  void setFeatures(List<EntityModel<FeatureResp>> features) {
        this.features = features;
    }

    public List<EntityModel<FeatureResp>> getFeatures() {
        return features;
    }

    public void setShow_policy(Boolean show_policy) {
        this.show_policy = show_policy;
    }

    public Boolean getShow_policy() {
        return show_policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getPolicy() {
        return policy;
    }

    public void setComponent_rights(List<EntityModel<ComponentRightResp>> component_rights) {
        this.component_rights = component_rights;
    }

    public List<EntityModel<ComponentRightResp>> getComponent_rights() {
        return component_rights;
    }

    public void setGallery(List<GalleryImageVo> gallery) {
        this.gallery = gallery;
    }

    public List<GalleryImageVo> getGallery() {
        return gallery;
    }
}
