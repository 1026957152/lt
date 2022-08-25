package com.lt.dom.otcReq;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;


public class EmpowerGetPhoneReq extends RepresentationModel<EmpowerGetPhoneReq> {
  //  @NotEmpty
  //  private String encryptedData;
  //  @NotEmpty
   // private String iv;

/*    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }*/

    @NotEmpty
    private String code;


    private int type; //1 用户端 ， 2商户端

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
