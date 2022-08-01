package com.lt.dom.error;

import com.lt.dom.otcenum.Enumfailures;

@SuppressWarnings("serial")
public class Missing_customerException extends RuntimeException {

    private long id;
    private String object;
    private String detail;


    public Missing_customerException(long id, String object,String detail) {
        this.id = id;
        this.object = object;
        this.detail = detail;

    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}