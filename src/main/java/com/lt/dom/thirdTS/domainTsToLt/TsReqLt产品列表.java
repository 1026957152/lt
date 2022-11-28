package com.lt.dom.thirdTS.domainTsToLt;

import com.lt.dom.thirdPft.domainLtToPft.LvToPftRetDat;

import javax.validation.constraints.NotNull;

public class TsReqLt产品列表 {

    @NotNull
    private String method ;//:接口方法名,固定值：item_list
    private Integer item_id ;//:产品ID，用于搜索具体的产品
    private Integer page ;//:列表页码
    private Integer size ;//:每页数量
    private String key_word ;//:产品标题搜索关键词


    @NotNull
    private String _sig;//:请求签名，见说明文档

    @NotNull
    private String _pid;//:合作伙伴id

    @NotNull
    private String format;//:返回数据格式，json

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

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

    public String get_sig() {
        return _sig;
    }

    public void set_sig(String _sig) {
        this._sig = _sig;
    }

    public String get_pid() {
        return _pid;
    }

    public void set_pid(String _pid) {
        this._pid = _pid;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
