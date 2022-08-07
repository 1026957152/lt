package com.lt.dom.error;

@SuppressWarnings("serial")
public class Voucher_has_no_permistion_redeemException extends RuntimeException {

    private long id;
    private String object;
    private String detail;
    public Voucher_has_no_permistion_redeemException(long id, String object, String detail) {
        this.id = id;
        this.object = object;
        this.detail = detail;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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
}