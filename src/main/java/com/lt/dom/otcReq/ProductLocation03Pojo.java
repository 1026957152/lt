package com.lt.dom.otcReq;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ProductLocation03Pojo {


    private String name;

    @NotEmpty
    @Min(15)
    private String briefdescription;
    @NotEmpty
    @Min(100)
    private String longdescription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBriefdescription() {
        return briefdescription;
    }

    public void setBriefdescription(String briefdescription) {
        this.briefdescription = briefdescription;
    }

    public String getLongdescription() {
        return longdescription;
    }

    public void setLongdescription(String longdescription) {
        this.longdescription = longdescription;
    }
}
