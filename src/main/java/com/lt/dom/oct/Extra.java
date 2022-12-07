package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.EnumLongIdResp;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.mediatype.alps.Ext;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;
import java.util.stream.Collectors;



@Entity
public class Extra extends Base{



    private String code;
    @JsonProperty("internalName")
    private String internalName;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("reference")
    private String reference;
    private long supplier;
    private String slug;
    private Float price_Net;
    private Float price_Original;
    private Float price_Retail;


    public static List List(List<Extra> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getTitle()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public RestrictionsDTO getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(RestrictionsDTO restrictions) {
        this.restrictions = restrictions;
    }

    @Transient
    @JsonProperty("restrictions")
    private RestrictionsDTO restrictions;

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setPrice_Net(Float price_net) {
        this.price_Net = price_net;
    }

    public Float getPrice_Net() {
        return price_Net;
    }

    public void setPrice_Original(Float price_original) {
        this.price_Original = price_original;
    }

    public Float getPrice_Original() {
        return price_Original;
    }

    public void setPrice_Retail(Float price_retail) {

        this.price_Retail = price_retail;
    }

    public Float getPrice_Retail() {
        return price_Retail;
    }


    @NoArgsConstructor
    @Data
    public static class RestrictionsDTO {
        @JsonProperty("minQuantity")
        private Integer minQuantity;
        @JsonProperty("maxQuantity")
        private Integer maxQuantity;
    }
}
