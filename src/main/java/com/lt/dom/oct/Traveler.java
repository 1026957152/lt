package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumIdType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Traveler extends Base{

    private String referSku;



    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="lineItem", nullable=false)
    private LineItem lineItem;

    private String title;



    private String type;///


         //   你好
    private String Email;
          //  士大夫
    private String Twitter;
           //
    private String Country;



    private String Phone;
    private String  Website;//
    private String Facebook;
           // Twitter
    private String LinkedIn;





    private String wherehear;//	Where did the customer hear about us (doesn't have to be pre-configured)//
    private String fax;//	Fax number
    private String tel_work;//	Tel work / day
    private String tel_mobile;//	Tel mobile
    private String tel_sms;//	Tel sms
    private String contact_note;//	Contact note (e.g. don't call before 8pm)
    private String diet;//	Dietary requirements
    private String medical;//	Medical conditions
    private String nok_name;//	Emergency contact name
    private String nok_relationship;//	Emergency contact relationship
    private String nok_tel;//	Emergency contact telephone number
    private String nok_contact;//	Emergency contact other note (can be multi-line)
    private String agent_customer_ref;//	A travel agent reference number / ID for this customer, e.g. perhaps the ID for this customer in their own system
    private long booking;

    @Enumerated(EnumType.STRING)
    private EnumIdType idType;

    public void setBooking(long booking) {
        this.booking = booking;
    }

    public long getBooking() {
        return booking;
    }



    @NotEmpty
    private String name;///
    private String age;///
    private String family_name;///
    private String given_name;///
    @NotEmpty
    private String idNo;///

    @NotEmpty
    private String tel_home;///

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTel_home() {
        return tel_home;
    }

    public void setTel_home(String tel_home) {
        this.tel_home = tel_home;
    }

    public void setReferSku(String referSku) {
        this.referSku = referSku;
    }

    public String getReferSku() {
        return referSku;
    }

    public void setIdType(EnumIdType idType) {
        this.idType = idType;
    }

    public EnumIdType getIdType() {
        return idType;
    }

    public void setLineItem(LineItem lineItem) {
        this.lineItem = lineItem;
    }

    public LineItem getLineItem() {
        return lineItem;
    }
}
