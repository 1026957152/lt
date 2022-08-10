package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumAvailabilityType;
import com.lt.dom.otcenum.EnumProductPricingType;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.Enum___Redemption;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Product {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;


    private EnumAvailabilityType availability;

    private long scheduling;  //时间选择。
    private String code;

    @Size(min = 1,max = 50,message = "名称长度必须为1到10")
    private String name;
    @Size(min = 1,max = 100,message = "名称长度必须为1到100")
    private String name_long;
    private Long raletiveId;


    //只有但一票 才有 核销 之说。

    //    redeem tickets using a barcode or reference number. It's not enabled by default and not available or intended for resellers.
    // 景区闲逛票，  景区炸鸡票，景区天文馆票


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String royaltyId;
    private long supplierId;

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }


    @Transient
    List<RoyaltyRule> royaltyRules;


    @Transient
    List<ComponentRight> componentRights;


    @Transient
    List<Component> components;

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<ComponentRight> getComponentRights() {
        return componentRights;
    }

    public void setComponentRights(List<ComponentRight> componentRights) {
        this.componentRights = componentRights;
    }

    @Transient
    private EnumProductPricingType productType; // Packages（套票） ,or Passes（通票）  or Tickit 单一票

   // private String Type ;//Museums, Attractions or Hop on Hop off tours
    @Transient            // walking tours, day trips
    private Enum___Redemption redemption;
    @Transient
    List<Feature> features;
    @Transient
    List<RatePlan> ratePlans;
    @Transient
    TouristAttraction touristAttraction;
    @Transient
    private PolicyInfo policyInfo;
    @Transient
    private Offer offer;
    @Transient
    private PricingType pricingType;

    @Transient
    private Supplier supplier;


    @Transient
    private List<campaign营销> campaign营销s;
    @Transient
    private RoyaltyTemplate royaltyTemplate;  //每卖一次产品，就会产生若干个  Royalty

    @Transient
    private RoyaltyRule royaltyRule;

    public RoyaltyRule getRoyaltyRule() {
        return royaltyRule;
    }

    public void setRoyaltyRule(RoyaltyRule royaltyRule) {
        this.royaltyRule = royaltyRule;
    }

    public RoyaltyTemplate getRoyaltyTemplate() {
        return royaltyTemplate;
    }

    public void setRoyaltyTemplate(RoyaltyTemplate royaltyTemplate) {
        this.royaltyTemplate = royaltyTemplate;
    }





    private EnumProductType type;

    public EnumProductType getType() {
        return type;
    }

    public void setType(EnumProductType type) {
        this.type = type;
    }


    @Transient
    private ProductTheatre theatre;
    @Transient
    private ProductAttraction attraction;

    @Transient
    private  Voucher voucher;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getName_long() {
        return name_long;
    }

    public Long getRaletiveId() {
        return raletiveId;
    }

    public void setRaletiveId(Long raletiveId) {
        this.raletiveId = raletiveId;
    }
}
