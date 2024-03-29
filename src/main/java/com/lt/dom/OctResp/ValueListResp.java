package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import com.lt.dom.otcenum.EnumValueListItemType;
import com.lt.dom.otcenum.EnumValueListType;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class ValueListResp extends BaseResp{

    private String alias;
    private String name;
    private EnumValueListItemType item_type;

    private String created_by;

    private String metadata;
    

private String code;
    private int number;
    private List<ValueListItem> items;
    private EnumValueListType type;
    private String type_text;
    private String status;


    public static ValueListResp from(ValueList user, List<ValueListItem> valueListItems) {

        ValueListResp valueListResp = new ValueListResp();
        valueListResp.setAlias(user.getAlias());
        valueListResp.setCode(user.getCode());
        valueListResp.setMetadata(user.getMetadata());
        valueListResp.setName(user.getName());
        valueListResp.setItem_type(user.getItem_type());

        valueListResp.setStatus("测试");
        valueListResp.setNumber(valueListItems.size());
        valueListResp.setType(user.getType());
        valueListResp.setType_text(user.getType().toString());
        valueListResp.setCreatedDate(user.getCreatedDate());
        valueListResp.setModifiedDate(user.getModifiedDate());
        valueListResp.setItems(valueListItems);
        return valueListResp;
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

    public void setItems(List<ValueListItem> items) {
        this.items = items;
    }

    public List<ValueListItem> getItems() {
        return items;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
