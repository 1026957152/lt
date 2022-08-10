package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import com.lt.dom.otcenum.EnumValueListItemType;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;


public class ValueListResp {

    private String alias;
    private String name;
    private EnumValueListItemType item_type;
    private LocalDateTime created;
    private String created_by;

    private String metadata;
    private String code;
    private int number;

    public static EntityModel<ValueListResp> from(ValueList user, List<ValueListItem> valueListItems) {

        ValueListResp valueListResp = new ValueListResp();
        valueListResp.setAlias(user.getAlias());
        valueListResp.setCode(user.getCode());
        valueListResp.setMetadata(user.getMetadata());
        valueListResp.setName(user.getName());
        valueListResp.setItem_type(user.getItem_type());
        valueListResp.setCreated(user.getCreated());
        valueListResp.setCreated_by(user.getCreated_by());
        valueListResp.setNumber(valueListItems.size());
        EntityModel<ValueListResp> entityModel = EntityModel.of(valueListResp);

        return entityModel;
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

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
