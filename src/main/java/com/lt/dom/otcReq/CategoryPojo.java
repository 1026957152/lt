package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class CategoryPojo {


    @NotNull
    private String name;//	The staff entered "pinned note" on the booking
    @NotNull
    

private String code;//	The staff entered note form the "workflow" tab on the booking
    @NotNull
    private String category;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}
