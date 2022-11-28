package com.lt.dom.otcReq;


import javax.validation.constraints.NotNull;


public class ValueListItemEidtReq {



    @NotNull
    private String value;

    private String metadata;
    private Integer index;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
