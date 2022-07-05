package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;


@Entity

public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;

    private String no;//	string	The ID of the user

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @Transient
    private PingxxUser pingxxUser;
    private String created_date;//	string	The date and time of when the user was created, in ISO 8601 format
    private String last_updated;//	string	The date and time of when the user was last updated, in ISO 8601 format
    private String first_name;//	string	The user’s first name
    private String last_name;//	string	The user’s last name
    private String username;//	string	The user’s username*
    private String email;//	string	The user’s email address
    private String phone;//	string	The user’s phone number

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String dob;//	string	The user’s date of birth, in ISO 8601 format*


    private String gender;//	string	The user’s gender*
    private String supplier_id;;//	string	The company name*
    private String status;//	string	The user status
    private String wishlist;//	object	Object containing items added to the user’s wishlist

    @Transient
    private List<String> associated_venues;//	array	An array of venue IDs that this user is associated with**
    private boolean marketing_permission;//	boolean	Marketing permission for the venue group*

    @Transient
    private List<EnumCapabilities> capabilities;



    private boolean realNameVerified;

    private String realName ;
    private Integer idCardType ;
    private String idCard ;
}
