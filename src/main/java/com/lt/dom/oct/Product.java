package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//https://bokun.dev/booking-api-restful/vU6sCfxwYdJWd1QAcLt12i/introduction-to-the-data-model-of-products/mGtiogVmyzywvDaZFK29b5
@Entity
public class Product {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;


    @NotNull
    private EnumAvailabilityType availability_type;

    private EnumPassexpiry passexpiry;


    private EnumBookingTypes types = EnumBookingTypes.PASS;

    @NotNull
    private Boolean free;
    private Boolean package_;


    public static List List(List<Product> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getName()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
    }
    public EnumAvailabilityType getAvailability_type() {
        return availability_type;
    }

    public void setAvailability_type(EnumAvailabilityType availability) {
        this.availability_type = availability;
    }

    private long scheduling;  //时间选择。
    
//##@Column(unique=true) 
private String code;

    @Size(min = 1,max = 50,message = "名称长度必须为1到10")
    private String name;
    @Size(min = 1,max = 100,message = "名称长度必须为1到100")
    private String name_long;
    private Long typeTo;
    private EnumProductStatus status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean acitve;
    private boolean shippable;


    @Transient
    private List<String> tags;
    private String paymentMethods_json;
    private Boolean refund;

    @Length(max = 2000)
    private String long_desc;
    private String note;
    private String refund_note;
    private EnumValidateWay validate_way;



    private Long default_price;
    private String shipping_rates_json;
    private Integer shipping_rate;

    public Long getDefault_price() {
        return default_price;
    }

    public void setDefault_price(Long default_price) {
        this.default_price = default_price;
    }

    public String getShipping_rates_json() {
        return shipping_rates_json;
    }

    public void setShipping_rates_json(String shipping_rates_json) {
        this.shipping_rates_json = shipping_rates_json;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTags_json() {
        return tags_json;
    }

    public void setTags_json(String tags_json) {
        this.tags_json = tags_json;
    }

    private String tags_json;



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

    public Long getTypeTo() {
        return typeTo;
    }

    public void setTypeTo(Long raletiveId) {
        this.typeTo = raletiveId;
    }

    public void setStatus(EnumProductStatus status) {
        this.status = status;
    }

    public EnumProductStatus getStatus() {
        return status;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setAcitve(boolean acitve) {
        this.acitve = acitve;
    }

    public boolean getAcitve() {
        return acitve;
    }

    public void setShippable(boolean shippable) {
        this.shippable = shippable;
    }

    public boolean getShippable() {
        return shippable;
    }

    public void setPaymentMethods_json(String paymentMethods_json) {
        this.paymentMethods_json = paymentMethods_json;
    }

    public String getPaymentMethods_json() {
        return paymentMethods_json;
    }

    public void setRefund(Boolean refund) {
        this.refund = refund;
    }

    public Boolean getRefund() {
        return refund;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getLong_desc() {
        return long_desc;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setRefund_note(String refund_note) {
        this.refund_note = refund_note;
    }

    public String getRefund_note() {
        return refund_note;
    }

    public void setValidate_way(EnumValidateWay validate_way) {
        this.validate_way = validate_way;
    }

    public EnumValidateWay getValidate_way() {
        return validate_way;
    }

    public void setShipping_rate(Integer shipping_rate) {
        this.shipping_rate = shipping_rate;
    }

    public Integer getShipping_rate() {
        return shipping_rate;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Boolean getFree() {
        return free;
    }

    public void setPackage_(Boolean package_) {
        this.package_ = package_;
    }

    public Boolean getPackage_() {
        return package_;
    }
}
