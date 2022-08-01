package com.lt.dom.error;

@SuppressWarnings("serial")
public class voucher_disabledException extends RuntimeException {

    private long id;
    private String object;
    public voucher_disabledException(long id, String object) {
        this.id = id;
        this.object = object;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}