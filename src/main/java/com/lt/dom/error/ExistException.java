package com.lt.dom.error;

@SuppressWarnings("serial")
public class ExistException extends RuntimeException {

    private long id;
    private String error;
    public ExistException(long id) {
        this.id = id;
    }

    public ExistException(String s) {
        this.error =s;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}