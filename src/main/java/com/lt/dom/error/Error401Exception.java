package com.lt.dom.error;

import com.lt.dom.otcenum.Enumfailures;

@SuppressWarnings("serial")
public class Error401Exception extends RuntimeException {

    private Enumfailures error;
    private String message;


    public Error401Exception(Enumfailures object, String message) {

        this.error = object;
        this.message = message;

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