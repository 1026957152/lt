package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumCapabilities;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity

public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;

    private String code;//	string	The ID of the user
    private String password;//	string	The ID of the user

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String no) {
        this.code = no;
    }

    @Transient
    private PingxxUser pingxxUser;
    private String created_date;//	string	The date and time of when the user was created, in ISO 8601 format
    private String last_updated;//	string	The date and time of when the user was last updated, in ISO 8601 format
    private String first_name;//	string	The user’s first name
    private String last_name;//	string	The user’s last name

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    private String username;//	string	The user’s username*
    private String email;//	string	The user’s email address
    private String phone;//	string	The user’s phone number

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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


    public boolean isRealNameVerified() {
        return realNameVerified;
    }

    public void setRealNameVerified(boolean realNameVerified) {
        this.realNameVerified = realNameVerified;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    //favoriteTheatreId	The preferred theatre id of the loyalty account holder.
    private boolean areTermsAndConditionsAccepted;//	Whether or not the terms and conditions have been accepted.
    private String termsAndConditionsAcceptedVersion;//	The version of the terms and conditions that were accepted.
    private String acceptedTermsAndConditionsDate;//		The date when the terms and conditions were accepted.
    private int numberOfCardsIssued;//		The number of cards that are associated with the loyalty account.
    private boolean activationType;//		The type of activation of the loyalty account.
    //ASSO
    //WELC
    private Date activationDate;//		The date that the loyalty account was activiated.
    private boolean isEarn;//		Whether or not the loyalty account is earn enabled.
    private boolean isRedeem;//		Whether or not the loyalty account is redeem enabled.
    private Date enrollDate;//		The enrollment date of the loyalty account.
    private boolean receivesNotificationsViaEmail;//		Whether or not the loyalty account holder wants to receive notifications via email.
    private boolean receivesNotificationsViaMobile;//		Whether or not the loyalty account holder wants to receive notifications via text.
    private boolean receivesOffersViaEmail;//		Whether or not the loyalty account holder wants to receive offers via email.
    private boolean receivesOffersViaMobile;//		Whether or not the loyalty account holder wants to receive offers via text.
    private boolean receivesWeeklyEmails;//		Whether or not the loyalty account holder wants to receive email updates.





    private boolean enabled;
    private boolean tokenExpired;






    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
