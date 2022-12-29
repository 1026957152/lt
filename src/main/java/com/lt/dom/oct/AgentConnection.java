package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.otcenum.EnumAgentBilling;
import com.lt.dom.otcenum.EnumAgentStatus;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class AgentConnection extends Base {




    @JsonBackReference
    @OneToMany(mappedBy= "agentConnection",
            cascade = CascadeType.ALL)  // orphanRemoval = true
    private List<AgentProduct> products;

    @NotNull
    private Long supplier;


    @NotNull
    private Long agent;

    public static List List(List<AgentConnection> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getName()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
    }
    public List<AgentProduct> getProducts() {
        return products;
    }

    public void setProducts(List<AgentProduct> products) {
        this.products = products;
    }

    public boolean isReview() {
        return review;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    private EnumAgentBilling billing;

    public EnumAgentBilling getBilling() {
        return billing;
    }

    public void setBilling(EnumAgentBilling billing) {
        this.billing = billing;
    }

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
/*    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumBussinessType business_type;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumSupplierType type;*/


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "id")
    private Address location;

    private String locationName;
    private boolean review;


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

    private String baseURL;

    private String partnerId;
    private String authorizationCode;


    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partner_id) {
        this.partnerId = partner_id;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorization_code) {
        this.authorizationCode = authorization_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact", referencedColumnName = "id")
    private Contact contact;


    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
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
    private EnumAgentStatus status; //省



    public EnumAgentStatus getStatus() {
        return status;
    }

    public void setStatus(EnumAgentStatus status) {
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

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setAgent(Long agent) {
        this.agent = agent;
    }

    public Long getAgent() {
        return agent;
    }
}
