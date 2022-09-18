package com.lt.dom.otcenum;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;


public class ValueListResp {


    private long id;


    private String alias;
    private String name;
    private EnumValueListItemType item_type;
    private LocalDateTime created;
    private String created_by;

    private String metadata;
    
@Column(unique=true) 
private String code;
    private EnumValueListType type;

    private EnumLogicalType logical_type;



    public EnumLogicalType getLogical_type() {
        return logical_type;
    }

    public void setLogical_type(EnumLogicalType logical_type) {
        this.logical_type = logical_type;
    }

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

    public void setType(EnumValueListType type) {
        this.type = type;
    }

    public EnumValueListType getType() {
        return type;
    }
}
