package com.lt.dom.otcReq;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RedemByCryptoCodePojo {

    private String crypto_code;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCrypto_code() {
        return crypto_code;
    }

    public void setCrypto_code(String crypto_code) {
        this.crypto_code = crypto_code;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
