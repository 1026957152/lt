package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.*;

@Entity
public class VerificationReport extends Base {


    @Enumerated(EnumType.STRING)
    private EnumVerificationCheckType type;

    @Enumerated(EnumType.STRING)
    private EnumVerificationReportid_number_type enumVerificationReportid_number_type;

    @Enumerated(EnumType.STRING)
    private EnumVerificationDocumentType_type documentType;

    @Enumerated(EnumType.STRING)


    private EnumVerificationStatus status;
    private String code;
    private String id_number;

    public EnumVerificationStatus getStatus() {
        return status;
    }

    public void setStatus(EnumVerificationStatus status) {
        this.status = status;
    }

    private Long user;


    public EnumVerificationCheckType getType() {
        return type;
    }

    public void setType(EnumVerificationCheckType type) {
        this.type = type;
    }

    public EnumVerificationReportid_number_type getEnumVerificationReportid_number_type() {
        return enumVerificationReportid_number_type;
    }

    public void setEnumVerificationReportid_number_type(EnumVerificationReportid_number_type enumVerificationReportid_number_type) {
        this.enumVerificationReportid_number_type = enumVerificationReportid_number_type;
    }

    public EnumVerificationDocumentType_type getDocumentType() {
        return documentType;
    }

    public void setDocumentType(EnumVerificationDocumentType_type documentType) {
        this.documentType = documentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_state() {
        return address_state;
    }

    public void setAddress_state(String address_state) {
        this.address_state = address_state;
    }

    public String getAddress_zip() {
        return address_zip;
    }

    public void setAddress_zip(String address_zip) {
        this.address_zip = address_zip;
    }

    public String getAddress_country() {
        return address_country;
    }

    public void setAddress_country(String address_country) {
        this.address_country = address_country;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getExpiration_date_month() {
        return expiration_date_month;
    }

    public void setExpiration_date_month(String expiration_date_month) {
        this.expiration_date_month = expiration_date_month;
    }

    public String getExpiration_date_day() {
        return expiration_date_day;
    }

    public void setExpiration_date_day(String expiration_date_day) {
        this.expiration_date_day = expiration_date_day;
    }

    public String getExpiration_date_year() {
        return expiration_date_year;
    }

    public void setExpiration_date_year(String expiration_date_year) {
        this.expiration_date_year = expiration_date_year;
    }

    public String getIssued_date_month() {
        return issued_date_month;
    }

    public void setIssued_date_month(String issued_date_month) {
        this.issued_date_month = issued_date_month;
    }

    public String getIssued_date_day() {
        return issued_date_day;
    }

    public void setIssued_date_day(String issued_date_day) {
        this.issued_date_day = issued_date_day;
    }

    public String getIssued_date_year() {
        return issued_date_year;
    }

    public void setIssued_date_year(String issued_date_year) {
        this.issued_date_year = issued_date_year;
    }

    public String getIssued_date() {
        return issued_date;
    }

    public void setIssued_date(String issued_date) {
        this.issued_date = issued_date;
    }

    public String getIssuing_country() {
        return issuing_country;
    }

    public void setIssuing_country(String issuing_country) {
        this.issuing_country = issuing_country;
    }

    private String name;
    private String address;

    private String address_line1;
    private String address_city;
    private String address_state;
    private String address_zip;
    private String address_country;


    private String expiration_date;
    private String expiration_date_month;
    private String expiration_date_day;
    private String expiration_date_year;


    private String issued_date_month;
    private String issued_date_day;
    private String issued_date_year;
    private String issued_date;
    private String issuing_country;

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getUser() {
        return user;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }
}
