package com.lt.dom.error;

import com.lt.dom.otcenum.Enumfailures;

@SuppressWarnings("serial")
public class BadJwtException extends RuntimeException {

    private Enumfailures error;
    private String object;
    public BadJwtException(Enumfailures id, String object) {
        this.error = id;
        this.object = object;
    }

    public Enumfailures getError() {
        return error;
    }

    public void setError(Enumfailures error) {
        this.error = error;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}