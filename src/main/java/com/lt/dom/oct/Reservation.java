package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Reservation {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;
    private EnumProductType productType;
    
@Column(unique=true) 
private String code;

    private String additional_info;
    @NotNull
    private Long user;
    private boolean paymentSplit;
    private String paymentSplitCode;
    private String paymentMethods_json;
    private EnumPayChannel paymentMethod;
    private String note;

    private Integer hold_time_seconds;


    private LocalDateTime paied_at;

    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Integer getHold_time_seconds() {
        return hold_time_seconds;
    }

    public void setHold_time_seconds(Integer hold_time_seconds) {
        this.hold_time_seconds = hold_time_seconds;
    }

    @NotNull
    private EnumFulfillmentType followupPaid;
    private EnumValidateWay setValidate_way;
    private EnumValidationStatus validationStatus;


    private Boolean asap;


    private long deliveryDetails;
    private long pickupDetails;
    private long dineInDetails;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Transient
    List<RoomStay> roomStays; // 表示库存 多久的库存。



    @NotNull
    EnumBookingType type;

    public EnumBookingType getType() {
        return type;
    }

    public void setType(EnumBookingType type) {
        this.type = type;
    }

    //  List<Customer> customers;
  @Transient
    Product product ;

    @Transient
    List<Traveler> travelers;

    @Transient
    List<ComponentRight> componentRights;

    @Transient
    List<RatePlan> ratePlans;

    @Transient
    List<RoyaltyRule> royaltyRules;

    public List<RoyaltyRule> getRoyaltyRules() {
        return royaltyRules;
    }

    public void setRoyaltyRules(List<RoyaltyRule> royaltyRules) {
        this.royaltyRules = royaltyRules;
    }

    public List<RatePlan> getRatePlans() {
        return ratePlans;
    }

    public void setRatePlans(List<RatePlan> ratePlans) {
        this.ratePlans = ratePlans;
    }

    @Transient
    private Voucher voucher;


    @Transient
    List<BookAnswer> answers;
    private Long productId;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }




    @NotNull
    private EnumBookingStatus status;

    public EnumBookingStatus getStatus() {
        return status;
    }

    public void setStatus(EnumBookingStatus status) {
        this.status = status;
    }
    /*    status	The order's current status:
    Pending
            Submitted
    Fulfilled
            Errored
    RefundInProgress
            Refunded
    PartialyRefunded
            Cancelled
    Abandonded*/

    @Transient
    private List<EnumPaymentOption> paymentOptions;

    private int total;//	The order's current total price, included all items, fees, and taxes.
    private int subtotal;//	The order's current total price of all active items.
    private boolean paid;//	The order's current value of all active tenders.
    private int savings;//	The order's current savings value for all active discounts and waived fees.
    private String token;//	A GUID identifier for the Order.


    @Transient
    List<BookingProduct> products;
    @Transient
    List<BookingPayment> payments;

    public List<BookingProduct> getProducts() {
        return products;
    }

    public void setProducts(List<BookingProduct> products) {
        this.products = products;
    }


    @Transient
    List<Component> components;

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }




    private int amount;
    //integer	Represents a total amount of order items (sum of item.amount)

    //Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private int  items_discount_amount;//
    //integer	Represents total amount of the discount applied to order line items

    //Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private int  total_discount_amount;//
   // integer	Summarize all discounts applied to the order including discounts applied to particular order line items and discounts applied to the whole cart.

   // Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private int  total_amount;//

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getItems_discount_amount() {
        return items_discount_amount;
    }

    public void setItems_discount_amount(int items_discount_amount) {
        this.items_discount_amount = items_discount_amount;
    }

    public int getTotal_discount_amount() {
        return total_discount_amount;
    }

    public void setTotal_discount_amount(int total_discount_amount) {
        this.total_discount_amount = total_discount_amount;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public void setProductType(EnumProductType productType) {
        this.productType = productType;
    }

    public EnumProductType getProductType() {
        return productType;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }



    private long supplier;

    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }


    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getUser() {
        return user;
    }

    public void setPaied_at(LocalDateTime paied_at) {
        this.paied_at = paied_at;
    }

    public LocalDateTime getPaied_at() {
        return paied_at;
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

    public void setPaymentMethods_json(String paymentMethods_json) {
        this.paymentMethods_json = paymentMethods_json;
    }

    public String getPaymentMethods_json() {
        return paymentMethods_json;
    }

    public void setPaymentMethod(EnumPayChannel paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public EnumPayChannel getPaymentMethod() {
        return paymentMethod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setFollowupPaid(EnumFulfillmentType followupPaid) {
        this.followupPaid = followupPaid;
    }

    public EnumFulfillmentType getFollowupPaid() {
        return followupPaid;
    }

    public void setSetValidate_way(EnumValidateWay setValidate_way) {
        this.setValidate_way = setValidate_way;
    }

    public EnumValidateWay getSetValidate_way() {
        return setValidate_way;
    }

    public void setValidationStatus(EnumValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public EnumValidationStatus getValidationStatus() {
        return validationStatus;
    }
}
