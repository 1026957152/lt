package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.CrossSell;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.Entity;



public class CrossSellResp extends BaseResp {



    private String code;

    private Long product;
    @JsonProperty("title")
    private Long crossSellProduct;


    public static CrossSellResp from(CrossSell movie) {
        CrossSellResp crossSellResp = new CrossSellResp();

        return crossSellResp;
    }
}
