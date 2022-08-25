package com.lt.dom.error;

import com.lt.dom.otcenum.Enumfailures;

@SuppressWarnings("serial")
public class BookNotFoundException extends RuntimeException {

    private long id;
    private Enumfailures error = Enumfailures.resource_not_found;

    public Enumfailures getError() {
        return error;
    }

    public void setError(Enumfailures error) {
        this.error = error;
    }

    private String message;
    private String detail;
    public BookNotFoundException(long id,String message) {
        this.id = id;
        this.message = message;
    }

    public BookNotFoundException(String message,String detail) {

        this.message = message;
        this.detail = detail;
    }
    public BookNotFoundException(Enumfailures error, String message) {

        this.error = error;
        this.message = message;
        this.detail = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}