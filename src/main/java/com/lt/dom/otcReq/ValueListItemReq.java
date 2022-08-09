package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class ValueListItemReq {



    @NotNull
    private Long value_list;
    @NotNull
    private String value;

    private String metadata;

    public Long getValue_list() {
        return value_list;
    }

    public void setValue_list(Long value_list) {
        this.value_list = value_list;
    }

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
}
