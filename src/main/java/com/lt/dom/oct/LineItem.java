package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


//https://developers.google.com/ad-manager/api/reference/v202208/LineItemService.LineItem
@Entity
public class LineItem extends Base {

    private String code;


    private int cost;//	The unit cost of the product as a decimal.
    private int tax;//	The tax on the product as a decimal.
    private String name;//	The name of the product.

/*
    private String theatre;//	The name of the Theatre showing the movie (tickets only).
    private long theatreId;//	The Id of the Theatre showing the movie (tickets only).
    private String movie;//	The name of the movie (tickets only).
    private String showDateTime;//	The Date time of the movie showing in local theatre time (tickets only).
    private String showDateTimeUtc;//	The Date time of the movie showing in UTC (tickets only).
    private long performanceNumber;//	The showtimes performance number (tickets only).
    private long auditorium;//	The auditorium the showtime is in (tickets only).


    @Column(name="row_")
    private int row;//	The row of the currently selected seat (reserved-seating tickets only).
    @Column(name="column_")

    private int column;//	The column of the currently selected seat (reserved-seating tickets only).
    private String seatName;//	The displayable seat name of the currently selected seat (reserved-seating tickets only).
*/




    private String productCode;//-  (string) - The ID of the associated product object for this line item.
    private String sku_id;//        - sku_id (string) - The ID of the associated variant (sku) object for this line item.
    private Integer quantity;//          - quantity (integer) - A positive integer representing the number of instances of item that are included in this order line.
    private Integer unitPrice;//

  //  private int retail_price;//         - price (integer) - A positive integer representing the cost of an item.
    private Integer amount;//  - amount (integer) - quantity * price (you should provide it to retrieve discount_amount for a particular order item if the discount is applied only to this item learn more

    private EnumDiscountType discountType;
    private Integer discountPerUnit;

    private EnumTaxMode taxMode;
    private String taxCode;

    @Enumerated(EnumType.STRING)
    private EnumProductType productType;

    @Enumerated(EnumType.STRING)
    private EnumLineItemCostType costType;


  //  private Integer unitPrice;//


    private Float base_cost_price;
    private Float cost_price_inc_tax;
    private Float cost_price_ex_tax;
    private Float cost_price_tax;
    private Boolean checkin = false;
    private LocalDateTime checkin_at;

    public Float getBase_cost_price() {
        return base_cost_price;
    }

    public void setBase_cost_price(Float base_cost_price) {
        this.base_cost_price = base_cost_price;
    }

    public Float getCost_price_inc_tax() {
        return cost_price_inc_tax;
    }

    public void setCost_price_inc_tax(Float cost_price_inc_tax) {
        this.cost_price_inc_tax = cost_price_inc_tax;
    }

    public Float getCost_price_ex_tax() {
        return cost_price_ex_tax;
    }

    public void setCost_price_ex_tax(Float cost_price_ex_tax) {
        this.cost_price_ex_tax = cost_price_ex_tax;
    }

    public Float getCost_price_tax() {
        return cost_price_tax;
    }

    public void setCost_price_tax(Float cost_price_tax) {
        this.cost_price_tax = cost_price_tax;
    }

    /*            string
    The product’s cost price. This can be set using the Catalog API. (Float, Float-As-String, Integer) Read Only

    Example:
            0.0000
    cost_price_inc_tax
            string
    The product’s cost price including tax. (Float, Float-As-String, Integer) The cost of your products to you; this is never shown to customers, but can be used for accounting purposes. Read Only

    Example:
            0.0000
    cost_price_ex_tax
            string
    The products cost price excluding tax. (Float, Float-As-String, Integer) The cost of your products to you; this is never shown to customers, but can be used for accounting purposes. Read Only

    Example:
            0.0000
    weight
    number or string
    Weight of the product. (Float, Float-As-String, Integer)

    Example:
            1
    cost_price_tax
            string*/



    @Enumerated(EnumType.STRING)
    private EnumDeliveryFormats deliveryFormats;
    private String metadata;



    @OneToMany(mappedBy="lineItem",
            cascade = CascadeType.ALL)
    private List<Traveler> travelers;
    private EnumFulfillmentInstructionsType fulfillmentInstructionsType;
    private String remark;

    public EnumDeliveryFormats getDeliveryFormats() {
        return deliveryFormats;
    }

    public void setDeliveryFormats(EnumDeliveryFormats deliveryFormats) {
        this.deliveryFormats = deliveryFormats;
    }

    private long product;
    private long supplier;
    private String paymentMethods_json;
    private boolean paymentSplit;
    private String paymentSplitCode;
    private Long booking;
    private String title;

    private EnumBillingTriggerRule billingTriggerRule;

    public EnumDiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(EnumDiscountType discountType) {
        this.discountType = discountType;
    }

    public Integer getDiscountPerUnit() {
        return discountPerUnit;
    }

    public void setDiscountPerUnit(Integer discountPerUnit) {
        this.discountPerUnit = discountPerUnit;
    }

    public EnumTaxMode getTaxMode() {
        return taxMode;
    }

    public void setTaxMode(EnumTaxMode taxMode) {
        this.taxMode = taxMode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public EnumBillingTriggerRule getBillingTriggerRule() {
        return billingTriggerRule;
    }

    public void setBillingTriggerRule(EnumBillingTriggerRule billingTriggerRule) {
        this.billingTriggerRule = billingTriggerRule;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    private EnumFulfillment_behavior fulfillment_behavior;
    private boolean negotiated;
    private String referTraveler;


    @Enumerated(EnumType.STRING)
    private EnumProductBookingFullfullmentStatus fullfullmentStatus;



    @Enumerated(EnumType.STRING)
    private EnumLineItemStatus status;

    public EnumLineItemStatus getStatus() {
        return status;
    }

    public void setStatus(EnumLineItemStatus status) {
        this.status = status;
    }



    @Enumerated(EnumType.STRING)
    private EnumLineType lineType;
    private Long sku;

    public EnumProductBookingFullfullmentStatus getFullfullmentStatus() {
        return fullfullmentStatus;
    }

    public void setFullfullmentStatus(EnumProductBookingFullfullmentStatus fullfullmentStatus) {
        this.fullfullmentStatus = fullfullmentStatus;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String product_id) {
        this.productCode = product_id;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer price) {
        this.unitPrice = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setProductType(EnumProductType productType) {
        this.productType = productType;
    }

    public EnumProductType getProductType() {
        return productType;
    }



    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setPaymentMethods_json(String paymentMethods_json) {
        this.paymentMethods_json = paymentMethods_json;
    }

    public String getPaymentMethods_json() {
        return paymentMethods_json;
    }

    public void setPaymentSplit(boolean paymentSplit) {
        this.paymentSplit = paymentSplit;
    }

    public boolean getPaymentSplit() {
        return paymentSplit;
    }

    public void setPaymentSplitCode(String paymentSplitCode) {
        this.paymentSplitCode = paymentSplitCode;
    }

    public String getPaymentSplitCode() {
        return paymentSplitCode;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getProduct() {
        return product;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBooking(Long booking) {
        this.booking = booking;
    }

    public Long getBooking() {
        return booking;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFulfillment_behavior(EnumFulfillment_behavior fulfillment_behavior) {
        this.fulfillment_behavior = fulfillment_behavior;
    }

    public EnumFulfillment_behavior getFulfillment_behavior() {
        return fulfillment_behavior;
    }

    public void setNegotiated(boolean negotiated) {
        this.negotiated = negotiated;
    }

    public boolean isNegotiated() {
        return negotiated;
    }

    public void setReferTraveler(String referTraveler) {
        this.referTraveler = referTraveler;
    }

    public String getReferTraveler() {
        return referTraveler;
    }



    public void setLineType(EnumLineType lineType) {
        this.lineType = lineType;
    }

    public EnumLineType getLineType() {
        return lineType;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Long getSku() {
        return sku;
    }
    public static String OrderLable(List<LineItem> list) {
        String s = list.stream().map(e->e.getTitle()+"X"+e.getQuantity()).collect(Collectors.joining(";"));

        return s;
    }


    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getMetadata() {
        return metadata;
    }

    public <R> void setTravelers(List<Traveler> travelers) {
        this.travelers = travelers;
    }

    public List<Traveler> getTravelers() {
        return travelers;
    }

    public void setFulfillmentInstructionsType(EnumFulfillmentInstructionsType fulfillmentInstructionsType) {

        this.fulfillmentInstructionsType = fulfillmentInstructionsType;
    }



    public EnumFulfillmentInstructionsType getFulfillmentInstructionsType() {
        return fulfillmentInstructionsType;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCheckin(Boolean checkin) {
        this.checkin = checkin;
    }

    public Boolean isCheckin() {
        return checkin;
    }

    public void setCheckin_at(LocalDateTime checkin_at) {
        this.checkin_at = checkin_at;
    }

    public LocalDateTime getCheckin_at() {
        return checkin_at;
    }
}
