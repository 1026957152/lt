package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.JwtUtils;
import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.PublicationEntry;
import com.lt.dom.oct.Voucher;
import com.lt.dom.otcReq.VoucherResp;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import com.lt.dom.requestvo.PublishTowhoVo;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicationResp {



    private String source_id;


    private List<VoucherResp> vouchers;
    private Map<String,String> metadata;
    private EnumPublicationObjectType type;
    private String campaign;
    private String campaignCode;
    private String customer;
    private LocalDateTime published_at;
    private VoucherResp voucher;
    private boolean paied;
    private long charge;


    public static List<PublicationResp> from(List<PublicationEntry> publicationResps) {
        return publicationResps.stream().map(x->{

            return new PublicationResp();//PublicationResp.from(x);

        }).collect(Collectors.toList());

    }

    public static PublicationResp from(Quintet<PublicationEntry, Voucher, Campaign,List<Asset>, PublishTowhoVo> p) {
        PublicationEntry publicationEntry = p.getValue0();
        Voucher  voucher= p.getValue1();
        Campaign  campaign= p.getValue2();
        List<Asset> assets= p.getValue3();
        PublishTowhoVo publishTowhoVo = p.getValue4();

        PublicationResp publicationResp = new PublicationResp();

        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.customer)){
            publicationResp.setCustomer(publishTowhoVo.getUser().getRealName());
        }
        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.business)){
            publicationResp.setCustomer(publishTowhoVo.getSupplier().getName());
        }
        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.traveler)){
            publicationResp.setCustomer(publishTowhoVo.getTraveler().getName());
        }
        publicationResp.setType(publicationEntry.getToWhoType());
        publicationResp.setCampaign(campaign.getName());
        publicationResp.setCampaignCode(campaign.getCode());
        publicationResp.setVouchers(Arrays.asList(VoucherResp.from(Triplet.with(voucher,campaign,assets), Optional.empty())));
        return publicationResp;
    }

    public static PublicationResp from(Quartet<PublicationEntry, Voucher, Campaign,List<Asset>> p) {
        PublicationEntry publicationEntry = p.getValue0();
        Voucher  voucher= p.getValue1();
        Campaign  campaign= p.getValue2();
        List<Asset> assets= p.getValue3();


        PublicationResp publicationResp = new PublicationResp();
/*
        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.customer)){
            publicationResp.setCustomer(publishTowhoVo.getUser().getRealName());
        }
        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.business)){
            publicationResp.setCustomer(publishTowhoVo.getSupplier().getName());
        }
        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.traveler)){
            publicationResp.setCustomer(publishTowhoVo.getTraveler().getName());
        }*/
        publicationResp.setType(publicationEntry.getToWhoType());
        publicationResp.setCampaign(campaign.getName());
        publicationResp.setCampaignCode(campaign.getCode());
        publicationResp.setVouchers(Arrays.asList(VoucherResp.from(Triplet.with(voucher,campaign,assets),Optional.empty())));
        return publicationResp;
    }
    public static PublicationResp sigleFrom(Quartet< List<PublicationEntry>, Voucher, Campaign,List<Asset>> p) {
        List<PublicationEntry> publicationEntryList = p.getValue0();
        PublicationEntry publicationEntry = publicationEntryList.get(0);
        Voucher  voucher= p.getValue1();
        Campaign  campaign= p.getValue2();
        List<Asset> assets= p.getValue3();


        PublicationResp publicationResp = new PublicationResp();
/*
        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.customer)){
            publicationResp.setCustomer(publishTowhoVo.getUser().getRealName());
        }
        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.business)){
            publicationResp.setCustomer(publishTowhoVo.getSupplier().getName());
        }
        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.traveler)){
            publicationResp.setCustomer(publishTowhoVo.getTraveler().getName());
        }*/
        publicationResp.setType(publicationEntry.getToWhoType());
        publicationResp.setCampaign(campaign.getName());
        publicationResp.setCampaignCode(campaign.getCode());
        publicationResp.setPaied(publicationEntry.getPaied());
        publicationResp.setCharge(publicationEntry.getCharge());
        publicationResp.setVoucher(VoucherResp.from(Triplet.with(voucher,campaign,assets),Optional.empty()));
        publicationResp.setPublished_at(publicationEntry.getPublished_at());



        return publicationResp;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
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

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaignCode(String campaignCode) {
        this.campaignCode = campaignCode;
    }

    public String getCampaignCode() {
        return campaignCode;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    public void setPublished_at(LocalDateTime published_at) {
        this.published_at = published_at;
    }

    public LocalDateTime getPublished_at() {
        return published_at;
    }

    public void setVoucher(VoucherResp voucher) {
        this.voucher = voucher;
    }

    public VoucherResp getVoucher() {
        return voucher;
    }

    public void setPaied(boolean paied) {
        this.paied = paied;
    }

    public boolean getPaied() {
        return paied;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    public long getCharge() {
        return charge;
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
