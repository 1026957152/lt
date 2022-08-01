package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class MemberCertification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;







    private String name; //name String 身份证姓名
    private String gender;//gender String 性别
    private String race; //名族
    private String side;//front/back 表示身份证的人像面/国徽面
    private String birthday;//birthday String 生日
    private String address;//address String 地址
    private String idCardNumber; //idCardNumber String 身份证号码
    private String issuedBy;//身份证签发机关
    private String validDate;//身份证有效期限 validDate String 有效日期
    private String 人脸比对分数;
    private String 人脸图片;
    private LocalDate update_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String get人脸比对分数() {
        return 人脸比对分数;
    }

    public void set人脸比对分数(String 人脸比对分数) {
        this.人脸比对分数 = 人脸比对分数;
    }

    public String get人脸图片() {
        return 人脸图片;
    }

    public void set人脸图片(String 人脸图片) {
        this.人脸图片 = 人脸图片;
    }

    public void setUpdate_at(LocalDate update_at) {
        this.update_at = update_at;
    }

    public LocalDate getUpdate_at() {
        return update_at;
    }
}
