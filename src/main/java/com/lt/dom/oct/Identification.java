package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumFeatureTag;
import com.lt.dom.otcenum.EnumIdentificationType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Identification extends Base{



    private EnumIdentificationType type;
    private String document_back_file_id; //name String 身份证姓名
    private String document_front_file_id; //name String 身份证姓名


    private String name; //name String 身份证姓名
    private String gender;//gender String 性别
    private String race; //名族
    private String side;//front/back 表示身份证的人像面/国徽面
    private String birthday;//birthday String 生日
    private String address;//address String 地址
    private String idCardNumber; //idCardNumber String 身份证号码
    private String issuedBy;//身份证签发机关
    private String validDate;//身份证有效期限 validDate String 有效日期
    // private String 人脸比对分数;
    //  private String 人脸图片;


    public EnumIdentificationType getType() {
        return type;
    }

    public void setType(EnumIdentificationType type) {
        this.type = type;
    }

    public String getDocument_back_file_id() {
        return document_back_file_id;
    }

    public void setDocument_back_file_id(String document_back_file_id) {
        this.document_back_file_id = document_back_file_id;
    }

    public String getDocument_front_file_id() {
        return document_front_file_id;
    }

    public void setDocument_front_file_id(String document_front_file_id) {
        this.document_front_file_id = document_front_file_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }
}
