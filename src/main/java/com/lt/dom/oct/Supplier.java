package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierStatus;
import com.lt.dom.otcenum.EnumSupplierType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Supplier extends Base {

    private String registered_address;
    private String uniformSocialCreditCode;
    private String registered_name;
    private String contact_detail;
    private String about;
    private String slug;

    public String getRegistered_address() {
        return registered_address;
    }

    public void setRegistered_address(String registered_address) {
        this.registered_address = registered_address;
    }

    public String getUniformSocialCreditCode() {
        return uniformSocialCreditCode;
    }

    public void setUniformSocialCreditCode(String uniformSocialCreditCode) {
        this.uniformSocialCreditCode = uniformSocialCreditCode;
    }

    public String getRegistered_name() {
        return registered_name;
    }

    public void setRegistered_name(String registered_name) {
        this.registered_name = registered_name;
    }

    public String getContact_detail() {
        return contact_detail;
    }

    public void setContact_detail(String contact_detail) {
        this.contact_detail = contact_detail;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Length(max = 2000)
    @Column(name = "desc_")
    private String desc;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumBussinessType business_type;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumSupplierType type;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "id")
    private Address location;

    private String locationName;
    private boolean review;

    public EnumBussinessType getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(EnumBussinessType business_type) {
        this.business_type = business_type;
    }



    @Transient
    private PingxxUser pingxxUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settleAccount", referencedColumnName = "id")
    private SettleAccount settleAccount;

    public SettleAccount getSettleAccount() {
        return settleAccount;
    }

    public void setSettleAccount(SettleAccount settleAccount) {
        this.settleAccount = settleAccount;
    }

    @NotNull
    
//##@Column(unique=true) 
private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotEmpty
    private String name;


    @JsonProperty("endpoint")
    private String endpoint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact", referencedColumnName = "id")
    private Contact contact;


    @Transient
    private Supplier parant;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setType(EnumSupplierType type) {
        this.type = type;
    }

    public EnumSupplierType getType() {
        return type;
    }


    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setReview(boolean review) {
        this.review = review;
    }

    public boolean getReview() {
        return review;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public static class ContactDTO {
        @JsonProperty("website")
        private String website;
        @JsonProperty("email")
        private String email;
        @JsonProperty("telephone")
        private String telephone;
        @JsonProperty("address")
        private String address;
    }











/*    supplier_id	Supplier ID
    channel_id	Channel ID
    account_id	Account ID
    name	Display name for the suplier
    code	Code for the supplier
    home_url	Website homepage url
    logo_url	Logo image url
    contact_name	Name of the contact (person) at the supplier
    email_bookings	Email address for sending booking information
    email_accounts	Email address for accounts
    address	Postal address (multiline)
    short_desc	Short description
    long_desc	Long description
    why_desc	Why us?
    bonding_desc	Bonding / financial protection
    certification	e.g. for activity companies could be industry association memberships
    cancel_policy	Cancellation policy
    terms_and_conditions	Terms and conditions for booking*/

    private long total_redeemed;
    private long total_failed;
    private long total_succeeded;
    private long total_rolled_back;
    private long total_rollback_failed;
    private long total_rollback_succeeded;

    public long getTotal_redeemed() {
        return total_redeemed;
    }

    public void setTotal_redeemed(long total_redeemed) {
        this.total_redeemed = total_redeemed;
    }

    public long getTotal_failed() {
        return total_failed;
    }

    public void setTotal_failed(long total_failed) {
        this.total_failed = total_failed;
    }

    public long getTotal_succeeded() {
        return total_succeeded;
    }

    public void setTotal_succeeded(long total_succeeded) {
        this.total_succeeded = total_succeeded;
    }

    public long getTotal_rolled_back() {
        return total_rolled_back;
    }

    public void setTotal_rolled_back(long total_rolled_back) {
        this.total_rolled_back = total_rolled_back;
    }

    public long getTotal_rollback_failed() {
        return total_rollback_failed;
    }

    public void setTotal_rollback_failed(long total_rollback_failed) {
        this.total_rollback_failed = total_rollback_failed;
    }

    public long getTotal_rollback_succeeded() {
        return total_rollback_succeeded;
    }

    public void setTotal_rollback_succeeded(long total_rollback_succeeded) {
        this.total_rollback_succeeded = total_rollback_succeeded;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }





    private Double latitude;
    private Double longitude;
    private String street; // 街道
    private String postal_code; // 邮编
    private String locality; // 县/区
    private String region; // 市
    private String state; //省

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumSupplierStatus status; //省



    public EnumSupplierStatus getStatus() {
        return status;
    }

    public void setStatus(EnumSupplierStatus status) {
        this.status = status;
    }

    private String country; // 国家
/*                    "street": "121 7th Street",
                            "postal_code": "94103",
                            "locality": "San Francisco",
                            "region": "California",
                            "state": "CA",
                            "country": "US"*/

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
