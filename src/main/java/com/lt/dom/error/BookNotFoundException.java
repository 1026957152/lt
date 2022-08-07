package com.lt.dom.error;

import com.lt.dom.otcenum.Enumfailures;

@SuppressWarnings("serial")
public class BookNotFoundException extends RuntimeException {

    private long id;
    private String object;
    private String detail;
    public BookNotFoundException(long id,String object) {
        this.id = id;
        this.object = object;
    }

    public BookNotFoundException(String document_id, String simpleName) {

        this.object = document_id;
        this.detail = simpleName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}