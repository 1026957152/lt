package com.lt.dom.OctResp.home;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.ComponentRightResp;
import com.lt.dom.OctResp.MediaResp;
import com.lt.dom.OctResp.MuseumResp;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.oct.Comment;
import org.javatuples.Pair;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeMuseumResp {


    private List<MediaResp> carousel;

    private Boolean show_policy;
    private String policy;

    private List<EntityModel<MuseumResp>> museums;

    public static void noteShow(HomeMuseumResp homeResp, Pair<Boolean, String> stringPair) {

        homeResp.setShow_policy(stringPair.getValue0());

        if(stringPair.getValue0()){
            homeResp.setPolicy(stringPair.getValue1());
        }
    }




    public void setCarousel(List<MediaResp> carousel) {
        this.carousel = carousel;
    }

    public List<MediaResp> getCarousel() {
        return carousel;
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


    public void setMuseums(List<EntityModel<MuseumResp>> museums) {
        this.museums = museums;
    }

    public List<EntityModel<MuseumResp>> getMuseums() {
        return museums;
    }
}
