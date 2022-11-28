package com.lt.dom;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.MediaResp;
import com.lt.dom.oct.Artwork;


//https://metmuseum.github.io/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeroCardInfoResp {

    private String price;

    private String option;
    private String purchase;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }
}
