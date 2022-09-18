package com.lt.dom.error;

import com.lt.dom.otcenum.Enumfailures;

@SuppressWarnings("serial")
public class PaymentException extends RuntimeException {
    private Enumfailures error;

    private String message;
    private String detail;

    public PaymentException( Enumfailures error,String message) {
        this.message = message;
        this.error = error;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Enumfailures getError() {
        return error;
    }

    public void setError(Enumfailures error) {
        this.error = error;
    }
}