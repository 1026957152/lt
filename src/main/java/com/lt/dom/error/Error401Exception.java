package com.lt.dom.error;

import com.lt.dom.otcenum.Enumfailures;

@SuppressWarnings("serial")
public class Error401Exception extends RuntimeException {

    private Enumfailures error;
    private String message;
    private String detail;

    public Error401Exception(Enumfailures object, String message) {

        this.error = object;
        this.message = message;

    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Error401Exception(Enumfailures object, String message, String detail) {

        this.error = object;
        this.message = message;
        this.detail = detail;

    }
    public Enumfailures getError() {
        return error;
    }

    public void setError(Enumfailures error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}