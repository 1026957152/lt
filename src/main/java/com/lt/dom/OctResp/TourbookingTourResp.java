package com.lt.dom.OctResp;

import com.github.binarywang.wxpay.bean.businesscircle.PaidResult;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumTourBookingStatus_;
import com.lt.dom.vo.FlowButtonVo;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;


public class TourbookingTourResp{




  Product product ;


    List<TravelerResp> travelers;


    private String code;
    private EnumProductType productType;
    private String productCode;
    private String note;
    private List<DocumentResp> documents;
    private EntityModel<AssetResp> asset;
    private String status_text;
    private String productType_text;
    private int traveler_number;
    private List publication_entrys;
    private boolean reviewing;
    private List<DocumentResp.DocumentGroup> documentGroups;
    private String tour_Code;
    private List<ReviewResp> reviews;
    private FlowButtonVo flowButton;

    public static TourbookingTourResp toResp(TourBooking booking, List<Traveler> travelers, List<Document> documents) {

       // Product product = pair.getValue1();
       // Tour tour = pair.getValue2();
        TourbookingTourResp reservationResp = new TourbookingTourResp();


        reservationResp.setCode(booking.getCode());
        reservationResp.setStatus(booking.getStatus());


        reservationResp.setProductType(booking.getProductType());

        reservationResp.setDocuments(DocumentResp.Listfrom(documents));
        reservationResp.setTravelers(TravelerResp.Listfrom(travelers));
      //  reservationResp.setProductCode(product.getCode());
       // reservationResp.setNote(tour.getTour_name());
        //reservationResp.setNote(tour.getTour_name_long());
        return reservationResp;

    }





    List<BookAnswer> answers;




    private EnumTourBookingStatus_ status;

    public EnumTourBookingStatus_ getStatus() {
        return status;
    }

    public void setStatus(EnumTourBookingStatus_ status) {
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




    List<BookingPayment> payments;







    //integer	Represents a total amount of order items (sum of item.amount)

    //Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 is written as 10000.
    private int  campaign_count;//


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



    public static TourbookingTourResp toResp(Pair<TourBooking,List<CampaignAssignToTourBookingResp>> pair) {
        TourBooking booking = pair.getValue0();
        List<CampaignAssignToTourBookingResp> campaignAssignToTourBookings = pair.getValue1();

        TourbookingTourResp tourbookingTourResp = new TourbookingTourResp();


        tourbookingTourResp.setCode(booking.getCode());
        tourbookingTourResp.setStatus(booking.getStatus());
        tourbookingTourResp.setStatus_text(booking.getStatus().toString());

        tourbookingTourResp.setProductType(booking.getProductType());
        tourbookingTourResp.setProductType_text(booking.getProductType().toString());

        tourbookingTourResp.setNote(booking.getAdditional_info_guide_name());
        tourbookingTourResp.setReviewing(booking.getReviewing());

        tourbookingTourResp.setGuide_id(booking.getAdditional_info_guide_id());
        tourbookingTourResp.setGuide_phone(booking.getAdditional_info_guide_phone());
        tourbookingTourResp.setGuide_name(booking.getAdditional_info_guide_name());
        tourbookingTourResp.setTour_Code(booking.getAdditional_info_tour_code());
        tourbookingTourResp.setTour_title(booking.getAdditional_info_tour_title());
        tourbookingTourResp.setTour_line_info(booking.getAdditional_info_tour_line_info());
        tourbookingTourResp.setTour_starts_at(booking.getAdditional_info_tour_starts_at());
        tourbookingTourResp.setTour_ends_at(booking.getAdditional_info_tour_ends_at());
        tourbookingTourResp.setCampaign_count(campaignAssignToTourBookings.size());

        //reservationResp.setNote(tour.getTour_name_long());
        return tourbookingTourResp;
    }



    public static TourbookingTourResp toResp(Triplet<TourBooking,Product,Tour> pair) {
        TourBooking booking = pair.getValue0();
        Product product = pair.getValue1();
        Tour tour = pair.getValue2();
        TourbookingTourResp tourbookingTourResp = new TourbookingTourResp();


        tourbookingTourResp.setCode(booking.getCode());
        tourbookingTourResp.setStatus(booking.getStatus());
        tourbookingTourResp.setStatus_text(booking.getStatus().toString());

        tourbookingTourResp.setProductType(booking.getProductType());
        tourbookingTourResp.setProductType_text(booking.getProductType().toString());
        tourbookingTourResp.setProductCode(product.getCode());
        tourbookingTourResp.setNote(tour.getTour_name());
        tourbookingTourResp.setReviewing(booking.getReviewing());

        tourbookingTourResp.setGuide_id(booking.getAdditional_info_guide_id());
        tourbookingTourResp.setGuide_phone(booking.getAdditional_info_guide_phone());
        tourbookingTourResp.setGuide_name(booking.getAdditional_info_guide_name());
        tourbookingTourResp.setTour_Code(booking.getAdditional_info_tour_code());
        tourbookingTourResp.setTour_title(booking.getAdditional_info_tour_title());
        tourbookingTourResp.setTour_line_info(booking.getAdditional_info_tour_line_info());
        tourbookingTourResp.setTour_starts_at(booking.getAdditional_info_tour_starts_at());
        tourbookingTourResp.setTour_ends_at(booking.getAdditional_info_tour_ends_at());
        //reservationResp.setNote(tour.getTour_name_long());
        return tourbookingTourResp;
    }

    public static TourbookingTourResp toResp(Quartet<TourBooking,Product,Tour,List<Traveler>> pair) {
        TourBooking tourBooking = pair.getValue0();
        Product product = pair.getValue1();
        Tour tour = pair.getValue2();
        List<Traveler> travelers = pair.getValue3();
        TourbookingTourResp resp = toResp(Triplet.with(tourBooking,product,tour));

        resp.setTravelers(TravelerResp.Listfrom(travelers));
        resp.setTraveler_count(travelers.size());
        return resp;
    }

    public static TourbookingTourResp toResp(Quintet<TourBooking,Product,Tour,List<Traveler>,List<Document>> pair,Asset asset) {

        TourbookingTourResp resp = toResp(Quartet.with(pair.getValue0(),pair.getValue1(),pair.getValue2(),pair.getValue3()));
        List<Document> travelers = pair.getValue4();
        DocumentResp.Listfrom(travelers);

        resp.setAsset(AssetResp.from(asset));


        return resp;
    }

    public static TourbookingTourResp toResp(Quintet<TourBooking,Product,Tour,List<Traveler>,List<Document>> pair) {

        TourbookingTourResp resp = toResp(Quartet.with(pair.getValue0(),pair.getValue1(),pair.getValue2(),pair.getValue3()));
        List<Document> travelers = pair.getValue4();
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

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setProductType_text(String productType_text) {
        this.productType_text = productType_text;
    }

    public String getProductType_text() {
        return productType_text;
    }

    public void setTraveler_count(int traveler_number) {
        this.traveler_number = traveler_number;
    }

    public int getTraveler_number() {
        return traveler_number;
    }

    public void setPublication_entrys(List publication_entrys) {
        this.publication_entrys = publication_entrys;
    }

    public List getPublication_entrys() {
        return publication_entrys;
    }

    public void setReviewing(boolean reviewing) {
        this.reviewing = reviewing;
    }

    public boolean getReviewing() {
        return reviewing;
    }




    private String tour_title = "测试团名";//团名称： title;
    private String tour_code = "测试团号";//团号： code;
    private int customer_count= 12;//游客数量： customer_count;
    private LocalDateTime tour_starts_at;//线路开始：starts_at;
    private LocalDateTime tour_ends_at;//线路结束：ends_at;
    private String tour_line_info = "测试线路信息";//线路信息：  line_info;
    private String guide_name= "测试导游姓名";//导游名字：guide_name
    private String guide_phone= "测试导游电话";//导游电话：guide_phone
    private String guide_id= "测试导游身份证";//导游身份证：guide_id
  //  private int campaign_count= 5;//优惠券类别数量：campaign_count
    private int redeemed_campaign_count= 1;//已核销券类别：redeemed_campaign_count

    public int getCampaign_count() {
        return campaign_count;
    }

    public void setCampaign_count(int campaign_count) {
        this.campaign_count = campaign_count;
    }

    public String getTour_title() {
        return tour_title;
    }

    public void setTour_title(String tour_title) {
        this.tour_title = tour_title;
    }

    public String getTour_code() {
        return tour_code;
    }

    public void setTour_code(String tour_code) {
        this.tour_code = tour_code;
    }

    public int getCustomer_count() {
        return customer_count;
    }

    public void setCustomer_count(int customer_count) {
        this.customer_count = customer_count;
    }

    public LocalDateTime getTour_starts_at() {
        return tour_starts_at;
    }

    public void setTour_starts_at(LocalDateTime tour_starts_at) {
        this.tour_starts_at = tour_starts_at;
    }

    public LocalDateTime getTour_ends_at() {
        return tour_ends_at;
    }

    public void setTour_ends_at(LocalDateTime tour_ends_at) {
        this.tour_ends_at = tour_ends_at;
    }

    public String getTour_line_info() {
        return tour_line_info;
    }

    public void setTour_line_info(String tour_line_info) {
        this.tour_line_info = tour_line_info;
    }

    public String getGuide_name() {
        return guide_name;
    }

    public void setGuide_name(String guide_name) {
        this.guide_name = guide_name;
    }

    public String getGuide_phone() {
        return guide_phone;
    }

    public void setGuide_phone(String guide_phone) {
        this.guide_phone = guide_phone;
    }

    public String getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(String guide_id) {
        this.guide_id = guide_id;
    }

    public int getRedeemed_campaign_count() {
        return redeemed_campaign_count;
    }

    public void setRedeemed_campaign_count(int redeemed_campaign_count) {
        this.redeemed_campaign_count = redeemed_campaign_count;
    }

    public void setDocumentGroups(List<DocumentResp.DocumentGroup> documentGroups) {
        this.documentGroups = documentGroups;
    }

    public List<DocumentResp.DocumentGroup> getDocumentGroups() {
        return documentGroups;
    }

    public void setTour_Code(String tour_code) {
        this.tour_Code = tour_code;
    }

    public String getTour_Code() {
        return tour_Code;
    }

    public void setReviews(List<ReviewResp> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewResp> getReviews() {
        return reviews;
    }

    public void setFlowButton(FlowButtonVo flowButton) {
        this.flowButton = flowButton;
    }

    public FlowButtonVo getFlowButton() {
        return flowButton;
    }
}
