package com.lt.dom.error;

import com.lt.dom.otcenum.Enumfailures;

@SuppressWarnings("serial")
public class BookNotFoundException extends RuntimeException {

    private long id;
    private String object;
    public BookNotFoundException(long id,String object) {
        this.id = id;
        this.object = object;
    }

    public BookNotFoundException(String document_id, String simpleName) {

        this.object = document_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}