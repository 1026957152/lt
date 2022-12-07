package com.lt.dom.oct;

import com.lt.dom.otcenum.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Reservation extends Base{
    @Enumerated(EnumType.STRING)
    private EnumProductType productType;
    
//##@Column(unique=true) 
private String code;

    private String additional_info;

    private Long user;
    private boolean paymentSplit;
    private String paymentSplitCode;
    private String paymentMethods_json;
    @Enumerated(EnumType.STRING)
    private EnumPayChannel paymentMethod;


    @Enumerated(EnumType.STRING)
    private EnumPayment_behavior payment_behavior;
    private String trackingId;
    private Long agent;

    public EnumPayment_behavior getPayment_behavior() {
        return payment_behavior;
    }

    public void setPayment_behavior(EnumPayment_behavior payment_behavior) {
        this.payment_behavior = payment_behavior;
    }

    private String note;

    private Integer hold_time_seconds;


    private LocalDateTime paied_at;

    private LocalDateTime expiresAt;

    private String contact_fullName;
    private String contact_emailAddress;
    private String contact_phoneNumber;

    @Enumerated(EnumType.STRING)
    private EnumShippingAddressCollection shippingAddressCollection;

    @Length(max = 1000)
    private String shipingAddress_json;
    private Long shippingRate;
    private String log_buyer_name;
    private String log_buyer_phone;


    @Enumerated(EnumType.STRING)
    private EnumIdType log_buyer_id_ntype;
    private String log_buyer_id_number;
    private EnumPlatform platform;

    public String getContact_fullName() {
        return contact_fullName;
    }

    public void setContact_fullName(String contact_fullName) {
        this.contact_fullName = contact_fullName;
    }

    public String getContact_emailAddress() {
        return contact_emailAddress;
    }

    public void setContact_emailAddress(String contact_emailAddress) {
        this.contact_emailAddress = contact_emailAddress;
    }

    public String getContact_phoneNumber() {
        return contact_phoneNumber;
    }

    public void setContact_phoneNumber(String contact_phoneNumber) {
        this.contact_phoneNumber = contact_phoneNumber;
    }

    /*    contact
    The customer contact details. These values are set in the booking confirmation step.
            contact.fullName
    The full name of the guest
    contact.emailAddress
    The email of the guest
    contact.phoneNumber
    The phone number of the guest*/

    private LocalDateTime confirmedAt;
    private LocalDateTime cancelled_at;



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
    @Enumerated(EnumType.STRING)

    @NotNull
    private EnumFulfillment_behavior fulfillment_behavior;
    @Enumerated(EnumType.STRING)


    @NotNull
    private EnumValidateWay setValidate_way;
    @Enumerated(EnumType.STRING)

    private EnumValidationStatus validationStatus;


    private Boolean asap;


    private long deliveryDetails;
    private long pickupDetails;
    private long dineInDetails;



    @Transient
    List<RoomStay> roomStays; // 表示库存 多久的库存。


    @Enumerated(EnumType.STRING)

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



    @Enumerated(EnumType.STRING)

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

   // private int subtotal;//	The order's current total price of all active items.
  //  private int savings;//	The order's current savings value for all active discounts and waived fees.


    @Transient
    List<LineItem> products;
    @Transient
    List<BookingPayment> payments;

    public List<LineItem> getProducts() {
        return products;
    }

    public void setProducts(List<LineItem> products) {
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

    public void setFulfillment_behavior(EnumFulfillment_behavior followupPaid) {
        this.fulfillment_behavior = followupPaid;
    }

    public EnumFulfillment_behavior getFulfillment_behavior() {
        return fulfillment_behavior;
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


    public void setShippingAddressCollection(EnumShippingAddressCollection shippingAddressCollection) {
        this.shippingAddressCollection = shippingAddressCollection;
    }

    public EnumShippingAddressCollection getShippingAddressCollection() {
        return shippingAddressCollection;
    }

    public void setShipingAddress_json(String shipingAddress_json) {
        this.shipingAddress_json = shipingAddress_json;
    }

    public String getShipingAddress_json() {
        return shipingAddress_json;
    }

    public void setShippingRate(Long shippingRate) {
        this.shippingRate = shippingRate;
    }

    public Long getShippingRate() {
        return shippingRate;
    }

    public void setLog_buyer_name(String log_buyer_name) {
        this.log_buyer_name = log_buyer_name;
    }

    public String getLog_buyer_name() {
        return log_buyer_name;
    }

    public void setLog_buyer_phone(String log_buyer_phone) {
        this.log_buyer_phone = log_buyer_phone;
    }

    public String getLog_buyer_phone() {
        return log_buyer_phone;
    }

    public void setLog_buyer_id_ntype(EnumIdType log_buyer_id_ntype) {
        this.log_buyer_id_ntype = log_buyer_id_ntype;
    }

    public EnumIdType getLog_buyer_id_ntype() {
        return log_buyer_id_ntype;
    }

    public void setLog_buyer_id_number(String log_buyer_id_number) {
        this.log_buyer_id_number = log_buyer_id_number;
    }

    public String getLog_buyer_id_number() {
        return log_buyer_id_number;
    }

    public void setPlatform(EnumPlatform platform) {
        this.platform = platform;
    }

    public EnumPlatform getPlatform() {
        return platform;
    }

    public void setTrackingId(String tracking_id) {
        this.trackingId = tracking_id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setAgent(Long agent) {
        this.agent = agent;
    }

    public Long getAgent() {
        return agent;
    }
}
