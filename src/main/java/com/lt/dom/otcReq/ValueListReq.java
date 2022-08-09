package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumValueListItemType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class ValueListReq {



    @NotEmpty
    private String alias;
    @NotEmpty
    private String name;
    @NotNull
    private EnumValueListItemType item_type;


    private String metadata;



    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumValueListItemType getItem_type() {
        return item_type;
    }

    public void setItem_type(EnumValueListItemType item_type) {
        this.item_type = item_type;
    }



    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
