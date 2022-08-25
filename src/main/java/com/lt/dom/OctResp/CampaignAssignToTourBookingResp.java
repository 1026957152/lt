package com.lt.dom.OctResp;

import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumCampaignToTourBookingStatus;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumTourBookingStatus_;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Optional;


public class CampaignAssignToTourBookingResp {




    List<TravelerResp> travelers;


    private String code;

    private String note;
    private EntityModel<AssetResp> asset;
    private String status_text;
    private int traveler_count;
    private List publication_entrys;

    private int unredeemed_count;
    private int redeemed_count;
    private String campaign_title;
    private String campaign_code;
    private String campaign_voucher_type;
    private int campaign_amount_off;





    private EnumCampaignToTourBookingStatus status;

    public static CampaignAssignToTourBookingResp from(CampaignAssignToTourBooking x, Optional<Asset> optional) {
        CampaignAssignToTourBookingResp resp = new CampaignAssignToTourBookingResp();
        resp.setCode(x.getCode());
        if(optional.isPresent()){
            resp.setAsset(AssetResp.from(optional.get()));
        }

        return resp;
    }
    public static CampaignAssignToTourBookingResp from(CampaignAssignToTourBooking x,Campaign campaign, Optional<Asset> optional,List<Traveler> travelers) {
        CampaignAssignToTourBookingResp resp = new CampaignAssignToTourBookingResp();
        resp.setCode(x.getCode());
        if(optional.isPresent()){
            resp.setAsset(AssetResp.from(optional.get()));
        }

        resp.setStatus_text(x.getStatus().toString());
        resp.setStatus(x.getStatus());
        resp.setRedeemed_count(x.getRedeemed_count());
        resp.setUnredeemed_count(x.getUnredeemed_count());
        resp.setCampaign_title(campaign.getName());
        resp.setCampaign_code(campaign.getCode());
        resp.setCampaign_amount_off(campaign.getAmount_off());

        resp.setCampaign_voucher_type(campaign.getVouchertype().toString());

        resp.setCode(x.getCode());
        return resp;
    }

  /*  public static CampaignAssignToTourBookingResp from(Campaign campaign) {

        CampaignAssignToTourBookingResp resp = new CampaignAssignToTourBookingResp();


        resp.setCode(x.getCode());
        if(optional.isPresent()){
            resp.setAsset(AssetResp.from(optional.get()));
        }

        resp.setStatus_text("部分核销");
        resp.setRedeemed_count(x.getRedeemed_count());
        resp.setUnredeemed_count(x.getUnredeemed_count());
        resp.setCampaign_title(campaign.getName());
        resp.setCampaign_code(campaign.getCode());
        resp.setCampaign_amount_off(campaign.getAmount_off());

        resp.setCampaign_voucher_type(campaign.getVouchertype().toString());

        resp.setCode(x.getCode());
        return resp;
    }*/

    public EnumCampaignToTourBookingStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCampaignToTourBookingStatus status) {
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






/*    public static CampaignAssignToTourBookingResp toResp(Quartet<CampaignAssignToTourBooking,TourBooking,Product,Tour> pair) {
        CampaignAssignToTourBooking campaignAssignToTourBooking = pair.getValue0();
        TourBooking booking = pair.getValue1();
        Product product = pair.getValue2();
        Tour tour = pair.getValue3();
        CampaignAssignToTourBookingResp tourbookingTourResp = new CampaignAssignToTourBookingResp();


        tourbookingTourResp.setCode(booking.getCode());
        tourbookingTourResp.setStatus(campaignAssignToTourBooking.getStatus());
        tourbookingTourResp.setStatus_text(booking.getStatus().toString());


        tourbookingTourResp.setNote(tour.getTour_name());

        return tourbookingTourResp;
    }*/

/*
    public static CampaignAssignToTourBookingResp toResp(Quartet<TourBooking,Product,Tour,List<Traveler>> pair) {

        CampaignAssignToTourBookingResp resp = toResp(Triplet.with(pair.getValue0(),pair.getValue1(),pair.getValue2()));
        List<Traveler> travelers = pair.getValue3();
        ;

        resp.setTravelers(TravelerResp.Listfrom(travelers));
        resp.setTraveler_count(travelers.size());
        return resp;
    }
*/

/*    public static CampaignAssignToTourBookingResp toResp(Quintet<TourBooking,Product,Tour,List<Traveler>,List<Document>> pair, Asset asset) {

        CampaignAssignToTourBookingResp resp = toResp(Quartet.with(pair.getValue0(),pair.getValue1(),pair.getValue2(),pair.getValue3()));
        List<Document> travelers = pair.getValue4();
        DocumentResp.Listfrom(travelers);
     //   resp.setDocuments(DocumentResp.Listfrom(travelers));
        resp.setAsset(AssetResp.from(asset));


        return resp;
    }*/

/*    public static CampaignAssignToTourBookingResp toResp(Septet<CampaignAssignToTourBooking,TourBooking,Product,Tour,List<Traveler>,List<Document>> pair) {

        CampaignAssignToTourBookingResp resp = toResp(Quintet.with(pair.getValue0(),pair.getValue1(),pair.getValue2(),pair.getValue3(),pair.getValue4()));
        List<Document> travelers = pair.getValue5();
        DocumentResp.Listfrom(travelers);

        return resp;
    }*/
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


    public void setTraveler_count(int traveler_count) {
        this.traveler_count = traveler_count;
    }

    public int getTraveler_count() {
        return traveler_count;
    }

    public void setPublication_entrys(List publication_entrys) {
        this.publication_entrys = publication_entrys;
    }

    public List getPublication_entrys() {
        return publication_entrys;
    }


    public void setUnredeemed_count(int unredeemed_count) {
        this.unredeemed_count = unredeemed_count;
    }

    public int getUnredeemed_count() {
        return unredeemed_count;
    }

    public void setRedeemed_count(int redeemed_count) {
        this.redeemed_count = redeemed_count;
    }

    public int getRedeemed_count() {
        return redeemed_count;
    }

    public void setCampaign_title(String campaign_title) {
        this.campaign_title = campaign_title;
    }

    public String getCampaign_title() {
        return campaign_title;
    }

    public void setCampaign_code(String campaign_code) {
        this.campaign_code = campaign_code;
    }

    public String getCampaign_code() {
        return campaign_code;
    }

    public void setCampaign_voucher_type(String campaign_voucher_type) {
        this.campaign_voucher_type = campaign_voucher_type;
    }

    public String getCampaign_voucher_type() {
        return campaign_voucher_type;
    }

    public void setCampaign_amount_off(int campaign_amount_off) {
        this.campaign_amount_off = campaign_amount_off;
    }

    public int getCampaign_amount_off() {
        return campaign_amount_off;
    }
}
