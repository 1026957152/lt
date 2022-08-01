package com.lt.dom.OctResp;


import com.lt.dom.oct.PublicationEntry;
import com.lt.dom.otcenum.EnumPublicationObjectType;

import java.util.List;
import java.util.Map;

public class PublicationResp {



    private String source_id;

    private long campaign_id;

    private long customer_id;

    private List<String> vouchers;
    private Map<String,String> metadata;
    private EnumPublicationObjectType type;

    public static PublicationResp from(PublicationEntry publicationEntry) {
        PublicationResp publicationResp = new PublicationResp();
        publicationResp.setCustomer_id(publicationEntry.getToWho());
        publicationResp.setType(publicationEntry.getToWhoType());
        publicationResp.setCampaign_id(publicationEntry.getCampaign_id());
        return publicationResp;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public long getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(long campaign_id) {
        this.campaign_id = campaign_id;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public List<String> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<String> vouchers) {
        this.vouchers = vouchers;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void setType(EnumPublicationObjectType type) {
        this.type = type;
    }

    public EnumPublicationObjectType getType() {
        return type;
    }
/*       "customer_id":"cust_lDnTN0zZfoXJDdgZRV0DzDP6",
               "channel":"API",
               "metadata":{
        "test":true,
                "provider":"Shop Admin"
    },
            "vouchers":[
            "pNAtHYW8",
            "8tOtXQQa"
            ]*/

}
