package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumTourBookingStatus;
import com.lt.dom.state.ApplicationReviewStates;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity
public class TourBooking extends Base {
    @Column(name = "when_")

    private final Date when
            ;


    @Enumerated(EnumType.STRING)
    private EnumProductType productType;
    
//##@Column(unique=true) 
private String code;
   // private long campaign;

    //@NotNull
    private Long owner;
    private ApplicationReviewStates test_Status;

    public TourBooking(Date when, ApplicationReviewStates peerReview) {
        this.test_Status = peerReview;
        this.when = when;
    }

    public TourBooking() {

        this.when = new Date();

    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    private String additional_info;
    private boolean reviewing;

    private LocalDateTime created_at;

    //@NotNull
    private String additional_info_guide_name;
    //@NotNull
    private String additional_info_guide_id;
    //@NotNull
    private String additional_info_guide_phone;
    //@NotNull
    private String additional_info_tour_line_info;
    //@NotNull
    private String additional_info_tour_title;
    //@NotNull
    private String additional_info_tour_code;
    
    
    
    private LocalDateTime additional_info_tour_starts_at;
    private LocalDateTime additional_info_tour_ends_at;
    private String note;

    public boolean isReviewing() {
        return reviewing;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }



    @Transient
    List<RoomStay> roomStays; // 表示库存 多久的库存。



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

    private EnumTourBookingStatus status;

    public EnumTourBookingStatus getStatus() {
        return status;
    }

    public void setStatus(EnumTourBookingStatus status) {
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

 /*   public long getCampaign() {
        return campaign;
    }


    public void setCampaign(long campaign) {
        this.campaign = campaign;
    }*/
    private long redeemer;

    public long getRedeemer() {
        return redeemer;
    }

    public void setRedeemer(long redeemer) {
        this.redeemer = redeemer;
    }


    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setReviewing(boolean reviewing) {
        this.reviewing = reviewing;
    }

    public boolean getReviewing() {
        return reviewing;
    }

    public void setAdditional_info_guide_name(String additional_info_guide_name) {
        this.additional_info_guide_name = additional_info_guide_name;
    }

    public String getAdditional_info_guide_name() {
        return additional_info_guide_name;
    }

    public void setAdditional_info_guide_id(String additional_info_guide_id) {
        this.additional_info_guide_id = additional_info_guide_id;
    }

    public String getAdditional_info_guide_id() {
        return additional_info_guide_id;
    }

    public void setAdditional_info_guide_phone(String additional_info_guide_phone) {
        this.additional_info_guide_phone = additional_info_guide_phone;
    }

    public String getAdditional_info_guide_phone() {
        return additional_info_guide_phone;
    }

    public void setAdditional_info_tour_line_info(String additional_info_tour_line_info) {
        this.additional_info_tour_line_info = additional_info_tour_line_info;
    }

    public String getAdditional_info_tour_line_info() {
        return additional_info_tour_line_info;
    }

    public void setAdditional_info_tour_title(String additional_info_tour_title) {
        this.additional_info_tour_title = additional_info_tour_title;
    }

    public String getAdditional_info_tour_title() {
        return additional_info_tour_title;
    }

    public void setAdditional_info_tour_code(String additional_info_tour_code) {
        this.additional_info_tour_code = additional_info_tour_code;
    }

    public String getAdditional_info_tour_code() {
        return additional_info_tour_code;
    }

    public void setAdditional_info_tour_starts_at(LocalDateTime additional_info_tour_starts_at) {
        this.additional_info_tour_starts_at = additional_info_tour_starts_at;
    }

    public LocalDateTime getAdditional_info_tour_starts_at() {
        return additional_info_tour_starts_at;
    }

    public void setAdditional_info_tour_ends_at(LocalDateTime additional_info_tour_ends_at) {
        this.additional_info_tour_ends_at = additional_info_tour_ends_at;
    }

    public LocalDateTime getAdditional_info_tour_ends_at() {
        return additional_info_tour_ends_at;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTest_Status(ApplicationReviewStates test_status) {
        this.test_Status = test_status;
    }

    public ApplicationReviewStates getTest_Status() {
        return test_Status;
    }
}
