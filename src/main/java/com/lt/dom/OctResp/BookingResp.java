package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResp  {



    List<RoomStay> roomStays; // 表示库存 多久的库存。



  Product product ;


    List<TravelerResp> travelers;


    List<ComponentRight> componentRights;


    List<RatePlan> ratePlans;


    List<RoyaltyRule> royaltyRules;
    

private String code;
    private EnumProductType productType;
    private String productCode;
    private String note;
    private List<DocumentResp> documents;
    private EntityModel<AssetResp> asset;
    private int traveler_number;
    private List<EntityModel<PaymentMethodResp>> paymentMethods;
    private EnumBookingType type;
    private String type_text;
    private EnumValidateWay validate_way;
    private EnumValidationStatus validationStatus;

    public static BookingResp toResp(Reservation booking, List<Traveler> travelers, List<Document> documents) {

       // Product product = pair.getValue1();
       // Tour tour = pair.getValue2();
        BookingResp reservationResp = new BookingResp();

        reservationResp.setAmount(booking.getAmount());
        reservationResp.setCode(booking.getCode());
        reservationResp.setStatus(booking.getStatus());

        reservationResp.setTotal_amount(booking.getTotal_amount());
        reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

        reservationResp.setProductType(booking.getProductType());

        reservationResp.setDocuments(DocumentResp.Listfrom(documents));
        reservationResp.setTravelers(TravelerResp.Listfrom(travelers));
      //  reservationResp.setProductCode(product.getCode());
       // reservationResp.setNote(tour.getTour_name());
        //reservationResp.setNote(tour.getTour_name_long());
        return reservationResp;

    }

    public static BookingResp totoResp(Pair<Reservation, Product> with) {

        Reservation booking = with.getValue0();
        Product product = with.getValue1();

        BookingResp reservationResp = new BookingResp();

        reservationResp.setAmount(booking.getAmount());
        reservationResp.setCode(booking.getCode());
        reservationResp.setStatus(booking.getStatus());

        reservationResp.setTotal_amount(booking.getTotal_amount());
        reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

        reservationResp.setProductType(booking.getProductType());
        reservationResp.setProductCode(product.getCode());

        //reservationResp.setNote(tour.getTour_name_long());
        return reservationResp;

    }

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

    private int total;//	The order's current total price, included all items, fees, and taxes.
    private int subtotal;//	The order's current total price of all active items.
    private boolean paid;//	The order's current value of all active tenders.
    private int savings;//	The order's current savings value for all active discounts and waived fees.
    private String token;//	A GUID identifier for the Order.



    List<BookingProduct> products;

    List<BookingPayment> payments;

    public List<BookingProduct> getProducts() {
        return products;
    }

    public void setProducts(List<BookingProduct> products) {
        this.products = products;
    }



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





    public static BookingResp toResp_LIST(Pair<Reservation, List<BookingTypeTowhoVo>> pair) {

        List<BookingTypeTowhoVo> bookingTypeTowhoVolIST = pair.getValue1();
        BookingTypeTowhoVo bookingTypeTowhoVo = bookingTypeTowhoVolIST.get(0);

        if (bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Product)) {
            Reservation booking = pair.getValue0();
            Product product = bookingTypeTowhoVo.getProduct();

            BookingResp reservationResp = new BookingResp();
            reservationResp.setValidate_way(booking.getSetValidate_way());
            reservationResp.setValidationStatus(booking.getValidationStatus());
            reservationResp.setAmount(booking.getAmount());
            reservationResp.setCode(booking.getCode());
            reservationResp.setStatus(booking.getStatus());

            reservationResp.setTotal_amount(booking.getTotal_amount());
            reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

            reservationResp.setProductType(booking.getProductType());
            reservationResp.setProductCode(product.getCode());
            reservationResp.setType(booking.getType());
            reservationResp.setType_text(booking.getType().toString());
            reservationResp.setNote(booking.getNote());
            //reservationResp.setNote(tour.getTour_name_long());
            return reservationResp;
        }
        if (bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Voucher)) {
            Reservation booking = pair.getValue0();
            Campaign campaign = bookingTypeTowhoVo.getCampaign();

            BookingResp reservationResp = new BookingResp();

            reservationResp.setAmount(booking.getAmount());
            reservationResp.setCode(booking.getCode());
            reservationResp.setStatus(booking.getStatus());

            reservationResp.setTotal_amount(booking.getTotal_amount());
            reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

            reservationResp.setProductType(booking.getProductType());
/*            reservationResp.setProductCode(product.getCode());
            reservationResp.setNote(tour.getTour_name());*/
            //reservationResp.setNote(tour.getTour_name_long());
            return reservationResp;
        }

        return null;
    }



    public static BookingResp toResp(Pair<Reservation, BookingTypeTowhoVo> pair) {

        BookingTypeTowhoVo bookingTypeTowhoVo = pair.getValue1();

        if (bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Product)) {
            Reservation booking = pair.getValue0();
            Product product = pair.getValue1().getProduct();

            BookingResp reservationResp = new BookingResp();
            reservationResp.setValidate_way(booking.getSetValidate_way());
            reservationResp.setValidationStatus(booking.getValidationStatus());
            reservationResp.setAmount(booking.getAmount());
            reservationResp.setCode(booking.getCode());
            reservationResp.setStatus(booking.getStatus());

            reservationResp.setTotal_amount(booking.getTotal_amount());
            reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

            reservationResp.setProductType(booking.getProductType());
            reservationResp.setProductCode(product.getCode());
            reservationResp.setType(booking.getType());
            reservationResp.setType_text(booking.getType().toString());
             reservationResp.setNote(booking.getNote());
            //reservationResp.setNote(tour.getTour_name_long());
            return reservationResp;
        }
        if (bookingTypeTowhoVo.getToWhoTyp().equals(EnumBookingOjbectType.Voucher)) {
            Reservation booking = pair.getValue0();
            Campaign campaign = bookingTypeTowhoVo.getCampaign();

            BookingResp reservationResp = new BookingResp();

            reservationResp.setAmount(booking.getAmount());
            reservationResp.setCode(booking.getCode());
            reservationResp.setStatus(booking.getStatus());

            reservationResp.setTotal_amount(booking.getTotal_amount());
            reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

            reservationResp.setProductType(booking.getProductType());
/*            reservationResp.setProductCode(product.getCode());
            reservationResp.setNote(tour.getTour_name());*/
            //reservationResp.setNote(tour.getTour_name_long());
            return reservationResp;
        }

        return null;
    }

    public static BookingResp toResp(Triplet<Reservation,BookingTypeTowhoVo,List<Traveler>> pair) {

        BookingResp resp = toResp(Pair.with(pair.getValue0(),pair.getValue1()));
        List<Traveler> travelers = pair.getValue2();
        ;
        resp.setTraveler_number(travelers.size());
        resp.setTravelers(TravelerResp.Listfrom(travelers));

        return resp;
    }

    public static BookingResp toResp(Quartet<Reservation,BookingTypeTowhoVo,List<Traveler>,List<Document>> pair,Asset asset) {

        BookingResp resp = toResp(Triplet.with(pair.getValue0(),pair.getValue1(),pair.getValue2()));
        List<Document> travelers = pair.getValue3();

        resp.setDocuments(DocumentResp.Listfrom(travelers));
        resp.setAsset(AssetResp.from(asset));


        return resp;
    }
    public static BookingResp toResp(Quartet<Reservation,BookingTypeTowhoVo,List<Traveler>,List<Document>> pair) {

        BookingResp resp = toResp(Triplet.with(pair.getValue0(),pair.getValue1(),pair.getValue2()));
        List<Document> travelers = pair.getValue3();
        DocumentResp.Listfrom(travelers);
        resp.setDocuments(DocumentResp.Listfrom(travelers));



        return resp;
    }


    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setTravelers(List<TravelerResp> travelers) {
        this.travelers = travelers;
    }

    public List<TravelerResp> getTravelers() {
        return travelers;
    }

    public void setDocuments(List<DocumentResp> documents) {
        this.documents = documents;
    }

    public List<DocumentResp> getDocuments() {
        return documents;
    }

    public void setAsset(EntityModel<AssetResp> asset) {
        this.asset = asset;
    }

    public EntityModel<AssetResp> getAsset() {
        return asset;
    }

    public void setTraveler_number(int traveler_number) {
        this.traveler_number = traveler_number;
    }

    public int getTraveler_number() {
        return traveler_number;
    }



    public void setPaymentMethods(List<EntityModel<PaymentMethodResp>> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<EntityModel<PaymentMethodResp>> getPaymentMethods() {
        return paymentMethods;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setType(EnumBookingType type) {
        this.type = type;
    }

    public EnumBookingType getType() {
        return type;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setValidate_way(EnumValidateWay validate_way) {
        this.validate_way = validate_way;
    }

    public EnumValidateWay getValidate_way() {
        return validate_way;
    }

    public void setValidationStatus(EnumValidationStatus validationStatus) {

        this.validationStatus = validationStatus;
    }

    public EnumValidationStatus getValidationStatus() {
        return validationStatus;
    }
}
