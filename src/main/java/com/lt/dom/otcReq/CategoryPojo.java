package com.lt.dom.otcReq;

import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class CategoryPojo {


    @NotNull
    private String name;//	The staff entered "pinned note" on the booking


    private String category;


    private Long parent_category_id;

    private List<PhotoResp> images;

    public List<PhotoResp> getImages() {
        return images;
    }

    public void setImages(List<PhotoResp> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(Long parent_category_id) {
        this.parent_category_id = parent_category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}
