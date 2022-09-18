package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import com.lt.dom.otcenum.EnumValueListItemType;
import com.lt.dom.otcenum.EnumValueListType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


public class ValueListReq {



   // @NotEmpty
    private String alias;
    @NotEmpty
    private String name;

    private EnumValueListItemType item_type;


    private String metadata;
    @NotNull
    private EnumValueListType type;


    private List<Long> item_ids;
    private String type_text;


    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static ValueListReq from(ValueList valueList, List<ValueListItem> valueListItems) {
        ValueListReq valueListReq = new ValueListReq();
        valueListReq.setName(valueList.getName());

        valueListReq.setType(valueList.getType());
        valueListReq.setType_text(valueList.getType().toString());
        valueListReq.setItem_ids(valueListItems.stream().map(x->Long.valueOf(x.getValue())).collect(Collectors.toList()));

        return valueListReq;
    }

    public List<Long> getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(List<Long> item_ids) {
        this.item_ids = item_ids;
    }

    public void setType(EnumValueListType type) {
        this.type = type;
    }

    public EnumValueListType getType() {
        return type;
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



    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }


    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }
}
