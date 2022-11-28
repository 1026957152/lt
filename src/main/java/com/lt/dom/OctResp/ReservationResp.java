package com.lt.dom.OctResp;

import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumBookingStatus;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductType;

import java.util.List;



public class ReservationResp {



    List<RoomStay> roomStays; // 表示库存 多久的库存。



  Product product ;


    List<Traveler> travelers;


    List<ComponentRight> componentRights;


    List<RatePlan> ratePlans;


    List<RoyaltyRule> royaltyRules;
    

private String code;
    private EnumProductType productType;
    private String productCode;

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

    private List<Voucher> vouchers; //折扣券

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    List<BookAnswer> answers;
    private Long productId;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }




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


    private List<EnumPaymentOption> paymentOptions;

    private Integer total;//	The order's current total price, included all items, fees, and taxes.
    private Integer subtotal;//	The order's current total price of all active items.
    private Boolean paid;//	The order's current value of all active tenders.
    private Integer savings;//	The order's current savings value for all active discounts and waived fees.
    private String token;//	A GUID identifier for the Order.



    List<LineItem> products;

    List<BookingPayment> payments;

    public List<LineItem> getProducts() {
        return products;
    }

    public void setProducts(List<LineItem> products) {
        this.products = products;
    }



    List<Component> components;

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }




    private Integer amount;
    //Integereger	Represents a total amount of order items (sum of item.amount)

    //Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private Integer  items_discount_amount;//
    //Integereger	Represents total amount of the discount applied to order line items

    //Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private Integer  total_discount_amount;//
   // Integereger	Summarize all discounts applied to the order including discounts applied to particular order line items and discounts applied to the whole cart.

   // Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private Integer  total_amount;//

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getItems_discount_amount() {
        return items_discount_amount;
    }

    public void setItems_discount_amount(Integer items_discount_amount) {
        this.items_discount_amount = items_discount_amount;
    }

    public Integer getTotal_discount_amount() {
        return total_discount_amount;
    }

    public void setTotal_discount_amount(Integer total_discount_amount) {
        this.total_discount_amount = total_discount_amount;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setProductType(EnumProductType productType) {
        this.productType = productType;
    }

    public EnumProductType getProductType() {
        return productType;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }
}
