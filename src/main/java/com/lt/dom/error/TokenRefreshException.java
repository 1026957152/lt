package com.lt.dom.error;

@SuppressWarnings("serial")
public class TokenRefreshException extends RuntimeException {

    private long id;
    private String error;
    public TokenRefreshException(long id) {
        this.id = id;
    }

    public TokenRefreshException(String s) {
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