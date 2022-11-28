package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import java.time.LocalDate;
//https://www.zuora.com/developer/api-reference/?_ga=2.81241876.28207374.1666457102-461931621.1664039613&_gl=1*1xfj59h*_ga*NDYxOTMxNjIxLjE2NjQwMzk2MTM.*_ga_MY8CQ650DH*MTY2NjQ2MjI2OS4yNC4xLjE2NjY0NjU5MDUuMC4wLjA.#operation/Object_POSTProductRatePlanCharge

@Entity
public class PartnerShareRatePlan extends Base{


    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="rate_plan", nullable=false)
    private RatePlan ratePlan;


    private long product;
    @Enumerated(EnumType.STRING)
    private EnumRatePlaneCommissionType commissionType;
    private long supplier;
    private String code;

    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    private String name;
    private String description;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;
    private long partner;
    private long partnerSupplier;


    public RatePlan getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(RatePlan ratePlan) {
        this.ratePlan = ratePlan;
    }

    public void setPartner(long partner) {
        this.partner = partner;
    }

    public long getPartner() {
        return partner;
    }

    public void setPartnerSupplier(long partnerSupplier) {
        this.partnerSupplier = partnerSupplier;
    }

    public long getPartnerSupplier() {
        return partnerSupplier;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getProduct() {
        return product;
    }
}
