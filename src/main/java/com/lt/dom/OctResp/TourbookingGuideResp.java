package com.lt.dom.OctResp;

import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumTourBookingStatus_;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class TourbookingGuideResp {



    List<TravelerResp> travelers;
    private String code;
    private String note;
    private EntityModel<AssetResp> asset;
    private String status_text;
    private String productType_text;







    private EnumTourBookingStatus_ status;
    private int traveler_count;
    private List<EntityModel> campaigns;
    private LocalDateTime create_at;

    public EnumTourBookingStatus_ getStatus() {
        return status;
    }

    public void setStatus(EnumTourBookingStatus_ status) {
        this.status = status;
    }


    private int total;//	The order's current total price, included all items, fees, and taxes.
    private int subtotal;//	The order's current total price of all active items.
    private boolean paid;//	The order's current value of all active tenders.
    private int savings;//	The order's current savings value for all active discounts and waived fees.
    private String token;//	A GUID identifier for the Order.





    private int  campaign_count;//


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }




    public static TourbookingGuideResp toResp(TourBooking booking,List<Traveler> travelers, List<EntityModel> campaigns) {
        TourbookingGuideResp tourbookingTourResp = new TourbookingGuideResp();
        tourbookingTourResp.setCode(booking.getCode());
        tourbookingTourResp.setStatus(booking.getStatus());
        tourbookingTourResp.setStatus_text(booking.getStatus().toString());
        tourbookingTourResp.setGuide_id(booking.getAdditional_info_guide_id());
        tourbookingTourResp.setGuide_phone(booking.getAdditional_info_guide_phone());
        tourbookingTourResp.setGuide_name(booking.getAdditional_info_guide_name());
        tourbookingTourResp.setTour_code(booking.getAdditional_info_tour_code());
        tourbookingTourResp.setTour_title(booking.getAdditional_info_tour_title());
        tourbookingTourResp.setTour_line_info(booking.getAdditional_info_tour_line_info());
        tourbookingTourResp.setTour_starts_at(booking.getAdditional_info_tour_starts_at());
        tourbookingTourResp.setTour_ends_at(booking.getAdditional_info_tour_ends_at());

        tourbookingTourResp.setTraveler_count(travelers.size());
        tourbookingTourResp.setCustomer_count(travelers.size());

        tourbookingTourResp.setTravelers(TravelerResp.Listfrom(travelers));
        tourbookingTourResp.setCreate_at(booking.getCreated_at());
        tourbookingTourResp.setNote(booking.getNote());

        tourbookingTourResp.setCampaigns(campaigns);
        return tourbookingTourResp;
    }

    CampaignResp campaignResp = new CampaignResp();

    public void setTraveler_count(int traveler_count) {
        this.traveler_count = traveler_count;
    }

    public int getTraveler_count() {
        return traveler_count;
    }

    public void setCampaigns(List<EntityModel> campaigns) {
        this.campaigns = campaigns;
    }

    public List<EntityModel> getCampaigns() {
        return campaigns;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }


    public static class CampaignResp_ {
        private int total_redeamed;
        private int total_claim;
        private String title;
        private String type;
        private String status_text;

       /* public static CampaignResp from(Campaign x) {
            CampaignResp campaignResp = new CampaignResp();
            campaignResp.setStatus_text("部分核销");
            campaignResp.setTotal_redeamed(10);
            campaignResp.setTotal_claim(9);
            campaignResp.setTitle(x.getName());
            return campaignResp;
        }*/

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public int getTotal_redeamed() {
            return total_redeamed;
        }

        public void setTotal_redeamed(int total_redeamed) {
            this.total_redeamed = total_redeamed;
        }

        public int getTotal_claim() {
            return total_claim;
        }

        public void setTotal_claim(int total_claim) {
            this.total_claim = total_claim;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


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


    private String tour_title = "测试团名";//团名称： title;
    private String tour_code = "测试团号";//团号： code;
    private int customer_count;//游客数量： customer_count;
    private LocalDateTime tour_starts_at;//线路开始：starts_at;
    private LocalDateTime tour_ends_at;//线路结束：ends_at;
    private String tour_line_info = "测试线路信息";//线路信息：  line_info;
    private String guide_name= "测试导游姓名";//导游名字：guide_name
    private String guide_phone= "测试导游电话";//导游电话：guide_phone
    private String guide_id= "测试导游身份证";//导游身份证：guide_id

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



}
