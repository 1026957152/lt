package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.AgentResp;
import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.oct.Agent;
import com.lt.dom.otcenum.EnumAgentBilling;
import com.lt.dom.otcenum.EnumAgentStatus;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierType;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentEditReq {
    private EntityModel basicEdit;

    public void setBasicEdit(EntityModel basicEdit) {
        this.basicEdit = basicEdit;
    }

    public EntityModel getBasicEdit() {
        return basicEdit;
    }

    public static class EditReq {

        private Map parameterList;
        private Long agent;


        public Map getParameterList() {
            return parameterList;
        }

        public void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        private String desc;

        List<IdentifierReq> contacts;
        private EnumAgentBilling billing;

        public List<IdentifierReq> getContacts() {
            return contacts;
        }

        public void setContacts(List<IdentifierReq> contacts) {
            this.contacts = contacts;
        }

        List<ProductReq> products;

        public List<ProductReq> getProducts() {
            return products;
        }

        public void setProducts(List<ProductReq> products) {
            this.products = products;
        }



        private PhotoResp logo;
        private String registered_address;
        private String uniformSocialCreditCode;
        private String registered_name;
        private String contact_detail;
        private String about;




        private String partner_id;
        private String authorization_code;



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

        public static EditReq from(Agent region) {

            EditReq agentEditReq = new EditReq();
            agentEditReq.setDesc(region.getDesc());
            agentEditReq.setBilling(region.getBilling());
            agentEditReq.setName(region.getName());
            agentEditReq.setPartner_id(region.getPartnerId());

            agentEditReq.setAgent(region.getAgent());

            agentEditReq.setAuthorization_code(region.getAuthorizationCode());

            if(region.getContact()!= null){
                agentEditReq.setContacts(region.getContact().getIdentifiers().stream().map(e->{
                    IdentifierReq identifierReq = new IdentifierReq();
                    identifierReq.setId(e.getLinkId());
                    identifierReq.setType(e.getType());
                    return identifierReq;
                }).collect(Collectors.toList()));

            }else{
                System.out.println("ddddddddddddddddd"+"没有哦contact");
            }
            return agentEditReq;
        }


        public Long getAgent() {
            return agent;
        }

        public void setAgent(Long agent) {
            this.agent = agent;
        }
    }


    private AgentResp info;


    public void setInfo(AgentResp info) {
        this.info = info;
    }

    public AgentResp getInfo() {
        return info;
    }

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

}
