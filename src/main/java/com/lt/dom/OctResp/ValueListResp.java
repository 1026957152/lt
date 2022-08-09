package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumValueListItemType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


public class ValueListResp {

    private String alias;
    private String name;
    private EnumValueListItemType item_type;
    private LocalDateTime created;
    private String created_by;

    private String metadata;
    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
