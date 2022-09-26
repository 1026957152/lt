package com.lt.dom.OctResp.layout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.ProductResp;
import org.javatuples.Pair;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeResp {



    private List<EntityModel<ProductResp>> recommend_products;
    private List<EntityModel> recommend_attractions;

    private LayoutResp layout ;
    private Boolean show_policy;
    private String policy;

    public LayoutResp getLayout() {
        return layout;
    }

    public void setLayout(LayoutResp layout) {
        this.layout = layout;
    }

    public static void noteShow(MeResp homeResp, Pair<Boolean, String> stringPair) {

        homeResp.setShow_policy(stringPair.getValue0());
        if(stringPair.getValue0()){
            homeResp.setPolicy(stringPair.getValue1());
        }else{
            homeResp.setPolicy(null);
        }
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
}
