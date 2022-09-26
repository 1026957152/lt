package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.EnumLogicalType;
import com.lt.dom.otcenum.EnumValueListItemType;
import com.lt.dom.otcenum.EnumValueListType;
import com.lt.dom.otcenum.EnumValueType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class ValueList {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;


    private String alias;
    private String name;
    private EnumValueListItemType item_type;
    private LocalDateTime created;
    private String created_by;

    private String metadata;
    
//##@Column(unique=true) 
private String code;
    private EnumValueListType type;

    private EnumLogicalType logical_type;
    private String type_text;
    private EnumValueType item_value_type;
    private LocalDateTime update_at;


    public static List List(List<ValueList> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getName()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

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

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setItem_value_type(EnumValueType item_value_type) {
        this.item_value_type = item_value_type;
    }

    public EnumValueType getItem_value_type() {
        return item_value_type;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }
}
