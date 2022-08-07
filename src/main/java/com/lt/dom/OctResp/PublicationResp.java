package com.lt.dom.OctResp;


import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.PublicationEntry;
import com.lt.dom.oct.Voucher;
import com.lt.dom.otcReq.VoucherResp;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PublicationResp {



    private String source_id;

    private long campaign_id;

    private long customer_id;

    private List<VoucherResp> vouchers;
    private Map<String,String> metadata;
    private EnumPublicationObjectType type;


    public static List<PublicationResp> from(List<PublicationEntry> publicationResps) {
        return publicationResps.stream().map(x->{

            return new PublicationResp();//PublicationResp.from(x);

        }).collect(Collectors.toList());

    }

    public static PublicationResp from(Quartet<PublicationEntry, Voucher, Campaign,List<Asset>> p) {
        PublicationEntry publicationEntry = p.getValue0();
        Voucher  voucher= p.getValue1();
        Campaign  campaign= p.getValue2();
        List<Asset> assets= p.getValue3();

        PublicationResp publicationResp = new PublicationResp();
        publicationResp.setCustomer_id(publicationEntry.getToWho());
        publicationResp.setType(publicationEntry.getToWhoType());
        publicationResp.setCampaign_id(publicationEntry.getCampaign_id());

        publicationResp.setVouchers(Arrays.asList(VoucherResp.from(Triplet.with(voucher,campaign,assets))));
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

    public List<VoucherResp> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<VoucherResp> vouchers) {
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
