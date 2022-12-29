package com.lt.dom.OctResp;


import com.lt.dom.oct.AgentConnection;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.EnumAgentStatus;
import com.lt.dom.otcenum.EnumThirdParty;

import java.time.LocalDate;
import java.util.List;

public class AgentConnectionResp extends BaseResp{



    private String name;
    private PhotoResp photo;
    private String code;

    private EnumThirdParty platform;
    private String description;
    private List products;




    private EnumAgentStatus status;


    private String baseURL;

    private String partner_id;
    private String authorization_code;
    private String platform_text;
    private String status_text;



    private Integer total_bookings;//	Total number of bookings associated with the agent
    private Integer recent_bookings;//	Number of recent bookings associated with the agent
    private LocalDate last_booked;//	Date the last booking made by the agent was created. YYYY-MM-DD.
    private SupplierResp agent;
    private SupplierResp supplier;

    public static AgentConnectionResp toSupplier(AgentConnection e, Supplier supplier) {


        AgentConnectionResp agentResp = new AgentConnectionResp();
        agentResp.setName(supplier.getName());
        agentResp.setCode(supplier.getCode());
        agentResp.setStatus(e.getStatus());
        agentResp.setStatus_text(e.getStatus().toString());
        agentResp.setCreatedDate(e.getCreatedDate());
        agentResp.setModifiedDate(e.getModifiedDate());

        return agentResp;
    }


    public Integer getTotal_bookings() {
        return total_bookings;
    }

    public void setTotal_bookings(Integer total_bookings) {
        this.total_bookings = total_bookings;
    }

    public Integer getRecent_bookings() {
        return recent_bookings;
    }

    public void setRecent_bookings(Integer recent_bookings) {
        this.recent_bookings = recent_bookings;
    }

    public LocalDate getLast_booked() {
        return last_booked;
    }

    public void setLast_booked(LocalDate last_booked) {
        this.last_booked = last_booked;
    }

    public static AgentConnectionResp simpleFrom(AgentConnection region) {
        AgentConnectionResp resp = new AgentConnectionResp();
        resp.setName(region.getName());
        resp.setCode(region.getCode());
        resp.setDescription(region.getDesc());
        return resp;

    }
    public static AgentConnectionResp from(AgentConnection region) {
        AgentConnectionResp resp = new AgentConnectionResp();
        resp.setName(region.getName());
        resp.setCode(region.getCode());
/*        resp.setPlatform(region.getPlatform());
        resp.setPlatform_text(region.getPlatform().toString());*/
        resp.setDescription(region.getDesc());
        resp.setStatus(region.getStatus());
        resp.setStatus_text(region.getStatus().toString());

        resp.setPartner_id(region.getPartnerId());
        resp.setAuthorization_code(region.getAuthorizationCode());

        resp.setCreatedDate(region.getCreatedDate());
        resp.setModifiedDate(region.getModifiedDate());

        return resp;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhotoResp getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoResp photo) {
        this.photo = photo;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public EnumThirdParty getPlatform() {
        return platform;
    }

    public void setPlatform(EnumThirdParty platform) {
        this.platform = platform;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public <R> void setProducts(List products) {
        this.products = products;
    }

    public List getProducts() {
        return products;
    }

    public EnumAgentStatus getStatus() {
        return status;
    }

    public void setStatus(EnumAgentStatus status) {
        this.status = status;
    }

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

    public void setPlatform_text(String platform_text) {
        this.platform_text = platform_text;
    }

    public String getPlatform_text() {
        return platform_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setAgent(SupplierResp agent) {
        this.agent = agent;
    }

    public SupplierResp getAgent() {
        return agent;
    }

    public void setSupplier(SupplierResp supplier) {
        this.supplier = supplier;
    }

    public SupplierResp getSupplier() {
        return supplier;
    }
}
