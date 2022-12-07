package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.OctResp.BalanceResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.OctResp.RequestResp;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.*;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentReq {


    public static class ProductReq {

        private Long id;
        private Float net;
        private Float retail;
        private Long sku;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Float getNet() {
            return net;
        }

        public void setNet(Float net) {
            this.net = net;
        }

        public Float getRetail() {
            return retail;
        }

        public void setRetail(Float retail) {
            this.retail = retail;
        }

        public Long getSku() {
            return sku;
        }

        public void setSku(Long sku) {
            this.sku = sku;
        }
    }
    private String desc;

    List<IdentifierReq> contacts;
    private EnumAgentBilling billing;

    public List<IdentifierReq> getContacts() {
        return contacts;
    }



    List<ProductReq> products;

    public List<ProductReq> getProducts() {
        return products;
    }

    public void setProducts(List<ProductReq> products) {
        this.products = products;
    }

    private EnumAgentStatus status;
    private String status_text;
    private String location;

    private PhotoResp logo;
    private String registered_address;
    private String uniformSocialCreditCode;
    private String registered_name;
    private String contact_detail;
    private String about;




    private String baseURL;
    private Long agent;

    public Long getAgent() {
        return agent;
    }

    public void setAgent(Long agent) {
        this.agent = agent;
    }

    private String partner_id;
    private String authorization_code;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getAuthorization_code() {
        return authorization_code;
    }

    public void setAuthorization_code(String authorization_code) {
        this.authorization_code = authorization_code;
    }

    private SettleAccount settleAccount;
    private List<EntityModel<AssetResp>> assets;
    private EnumSupplierType type;
    private EnumBussinessType business_type;


    public SettleAccount getSettleAccount() {
        return settleAccount;
    }

    public void setSettleAccount(SettleAccount settleAccount) {
        this.settleAccount = settleAccount;
    }

    

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public List<EntityModel<AssetResp>> getAssets() {
        return assets;
    }

    public void setAssets(List<EntityModel<AssetResp>> assets) {
        this.assets = assets;
    }

    public void setType(EnumSupplierType type) {
        this.type = type;
    }

    public EnumSupplierType getType() {
        return type;
    }

    public void setBusiness_type(EnumBussinessType business_type) {
        this.business_type = business_type;
    }

    public EnumBussinessType getBusiness_type() {
        return business_type;
    }


    public void setStatus(EnumAgentStatus status) {
        this.status = status;
    }

    public EnumAgentStatus getStatus() {
        return status;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }


    public void setLogo(PhotoResp logo) {
        this.logo = logo;
    }

    public PhotoResp getLogo() {
        return logo;
    }

    public void setRegistered_address(String registered_address) {
        this.registered_address = registered_address;
    }

    public String getRegistered_address() {
        return registered_address;
    }

    public void setUniformSocialCreditCode(String uniformSocialCreditCode) {
        this.uniformSocialCreditCode = uniformSocialCreditCode;
    }

    public String getUniformSocialCreditCode() {
        return uniformSocialCreditCode;
    }

    public void setRegistered_name(String registered_name) {
        this.registered_name = registered_name;
    }

    public String getRegistered_name() {
        return registered_name;
    }

    public void setContact_detail(String contact_detail) {
        this.contact_detail = contact_detail;
    }

    public String getContact_detail() {
        return contact_detail;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
    }


    public EnumAgentBilling getBilling() {
        return billing;
    }

    public void setBilling(EnumAgentBilling billing) {
        this.billing = billing;
    }
}
