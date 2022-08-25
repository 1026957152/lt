package com.lt.dom.error;

import com.lt.dom.otcenum.Enumfailures;

@SuppressWarnings("serial")
public class ExistException extends RuntimeException {

    private long id;
    private Enumfailures error ;
    private String message ;
    public ExistException(Enumfailures e,String message) {
        this.error =e;
        this.message =message;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}