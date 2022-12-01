package com.lt.dom.thirdTS.domainTsToLt;

import com.lt.dom.thirdPft.domainLtToPft.LvToPftRetDat;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotNull;

public class TsReqLt产品列表 extends TsReqLtBase{


    private Integer item_id ;//:产品ID，用于搜索具体的产品
    private Integer page ;//:列表页码
    private Integer size ;//:每页数量
    private String key_word ;//:产品标题搜索关键词






    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getKey_word() {
        return key_word;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
