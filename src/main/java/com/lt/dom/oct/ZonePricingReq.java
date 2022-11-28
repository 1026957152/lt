package com.lt.dom.oct;

import java.util.List;


public class ZonePricingReq {


    private Long pricingType;//	The displayable seat name

    public Long zone;


    public static ZonePricingReq from(Sku e) {
        ZonePricingReq zonePricingReq = new ZonePricingReq();
        zonePricingReq.setPricingType(e.getPricingType());
        zonePricingReq.setZone(e.getZone());
        return zonePricingReq;
    }

    public static ZonePricingReq from(Zone e, List<Sku> skus) {
        ZonePricingReq zonePricingReq = new ZonePricingReq();

        if(skus.isEmpty()){
            zonePricingReq.setPricingType(null);

        }else{
            zonePricingReq.setPricingType(skus.get(0).getPricingType());

        }

   //     zonePricingReq.setPricingType(zonePricings.stream().map(ee-> ee.getPricingType()).collect(Collectors.toList()));
        zonePricingReq.setZone(e.getId());
        return zonePricingReq;
    }

    public Long getPricingType() {
        return pricingType;
    }

    public void setPricingType(Long pricingType) {
        this.pricingType = pricingType;
    }

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

}
