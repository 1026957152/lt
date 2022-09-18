package com.lt.dom.OctResp;

import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumTourBookingStatus;
import com.lt.dom.requestvo.ProductSubVo;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


public class ReservationTourResp extends RepresentationModel<ReservationTourResp> {




  Product product ;


    List<TravelerResp> travelers;




private String code;
    private EnumProductType productType;
    private String productCode;
    private String note;
    private List<DocumentResp> documents;
    private String status_text;

    public static ReservationTourResp toResp(TourBooking booking, List<Traveler> travelers, List<Document> documents) {

       // Product product = pair.getValue1();
       // Tour tour = pair.getValue2();
        ReservationTourResp reservationResp = new ReservationTourResp();

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





    public static ReservationTourResp toResp(Triplet<TourBooking,Product, ProductSubVo> pair) {
        TourBooking booking = pair.getValue0();
        Product product = pair.getValue1();
        Tour tour = pair.getValue2().getTour();
        ReservationTourResp reservationResp = new ReservationTourResp();

        reservationResp.setAmount(booking.getAmount());
        reservationResp.setCode(booking.getCode());
        reservationResp.setStatus(booking.getStatus());

        reservationResp.setTotal_amount(booking.getTotal_amount());
        reservationResp.setTotal_discount_amount(booking.getTotal_discount_amount());

        reservationResp.setProductType(booking.getProductType());
        reservationResp.setProductCode(product.getCode());
        reservationResp.setNote(tour.getTour_name());
        reservationResp.setStatus_text(booking.getStatus().toString());
        //reservationResp.setNote(tour.getTour_name_long());
        return reservationResp;
    }

    public static ReservationTourResp toResp(Quartet<TourBooking,Product,ProductSubVo,List<Traveler>> pair) {

        ReservationTourResp resp = toResp(Triplet.with(pair.getValue0(),pair.getValue1(),pair.getValue2()));
        List<Traveler> travelers = pair.getValue3();
        ;

        resp.setTravelers(TravelerResp.Listfrom(travelers));

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





    private String title = "测试团名";//团名称： title;
    private String group_code= "测试团号";//团号： code;
    private int customer_count= 12;//游客数量： customer_count;
    private String starts_at= "测试开始日期";//线路开始：starts_at;
    private String ends_at= "测试结束始日期";//线路结束：ends_at;
    private String line_info= "测试线路信息";//线路信息：  line_info;
    private String guide_name= "测试导游姓名";//导游名字：guide_name
    private String guide_phone= "测试导游电话";//导游电话：guide_phone
    private String guide_id= "测试导游身份证";//导游身份证：guide_id
    private int campaign_count= 5;//优惠券类别数量：campaign_count
    private int redeemed_campaign_count= 1;//已核销券类别：redeemed_campaign_count


    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }
}
