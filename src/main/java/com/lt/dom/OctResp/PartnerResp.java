package com.lt.dom.OctResp;


import com.lt.dom.oct.Partner;
import com.lt.dom.oct.Supplier;

import java.util.List;


public class PartnerResp extends BaseResp {



    private String partnerCode;
    private String partner;
    private String code;
    private List ratePlans;

    public static PartnerResp from(Partner e, Supplier supplier1) {
        PartnerResp partnerResp = new PartnerResp();
        partnerResp.setCode(e.getCode());
        partnerResp.setPartner(supplier1.getName());
        partnerResp.setPartnerCode(supplier1.getCode());
        partnerResp.setCreatedDate(e.getCreatedDate());
        partnerResp.setModifiedDate(e.getModifiedDate());

        return partnerResp;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartner() {
        return partner;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public <R> void setRatePlans(List ratePlans) {
        this.ratePlans = ratePlans;
    }

    public List getRatePlans() {
        return ratePlans;
    }
}
