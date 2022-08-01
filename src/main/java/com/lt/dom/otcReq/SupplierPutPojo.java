package com.lt.dom.otcReq;

import javax.validation.constraints.Pattern;

public class SupplierPutPojo {


    private String description;//	string	The user’s first name
    private String BusinessCategory;//	string	The user’s first name

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessCategory() {
        return BusinessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        BusinessCategory = businessCategory;
    }
}
