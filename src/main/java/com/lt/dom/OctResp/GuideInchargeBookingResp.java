package com.lt.dom.OctResp;

import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.controllerOct.PaymentRestController;
import com.lt.dom.oct.CampaignAssignToTourBooking;
import com.lt.dom.oct.GuideInchargeBooking;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.TourBooking;
import com.lt.dom.otcenum.EnumTourBookingStatus_;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class GuideInchargeBookingResp {




    private String tour_title;
    private String tour_code;
    private EnumTourBookingStatus_ booking_status;
    private String booking_status_text;
    private String tour_line_info;
    private String booking_code;
    private int campaign_count;
    private int travaler_count;
    private String owner;
    private String tourAgent;
    private LocalDateTime created_at;
    private String code;

    public static GuideInchargeBookingResp from(GuideInchargeBooking x, TourBooking tourBooking, List<CampaignAssignToTourBooking> campaignAssignToTourBookings, Supplier supplier) {
        GuideInchargeBookingResp guideInchargeBookingResp = new GuideInchargeBookingResp();

        ;

        guideInchargeBookingResp.setTour_title(tourBooking.getAdditional_info_tour_title());
        guideInchargeBookingResp.setTour_code(tourBooking.getAdditional_info_tour_code());
        guideInchargeBookingResp.setBooking_status(tourBooking.getStatus());
        guideInchargeBookingResp.setBooking_status_text(tourBooking.getStatus().toString());
        guideInchargeBookingResp.setTour_line_info(tourBooking.getAdditional_info_tour_line_info());
        guideInchargeBookingResp.setBooking_code(tourBooking.getCode());
        guideInchargeBookingResp.setTravaler_count(-1);
        guideInchargeBookingResp.setCampaign_count(campaignAssignToTourBookings.size());
        guideInchargeBookingResp.setTourAgent(supplier.getName()+"_"+supplier.getCode());
        guideInchargeBookingResp.setCreated_at(tourBooking.getCreated_at());
        guideInchargeBookingResp.setCode(tourBooking.getCode());


       // guideInchargeBookingResp.setGuideId(x.getGuideId());

        return guideInchargeBookingResp;
    }



    public void setTour_title(String tour_title) {
        this.tour_title = tour_title;
    }

    public String getTour_title() {
        return tour_title;
    }

    public void setTour_code(String tour_code) {
        this.tour_code = tour_code;
    }

    public String getTour_code() {
        return tour_code;
    }

    public void setBooking_status(EnumTourBookingStatus_ booking_status) {
        this.booking_status = booking_status;
    }

    public EnumTourBookingStatus_ getBooking_status() {
        return booking_status;
    }

    public void setBooking_status_text(String booking_status_text) {
        this.booking_status_text = booking_status_text;
    }

    public String getBooking_status_text() {
        return booking_status_text;
    }

    public void setTour_line_info(String tour_line_info) {
        this.tour_line_info = tour_line_info;
    }

    public String getTour_line_info() {
        return tour_line_info;
    }

    public void setBooking_code(String booking_code) {
        this.booking_code = booking_code;
    }

    public String getBooking_code() {
        return booking_code;
    }

    public void setCampaign_count(int campaign_count) {
        this.campaign_count = campaign_count;
    }

    public int getCampaign_count() {
        return campaign_count;
    }

    public void setTravaler_count(int travaler_count) {
        this.travaler_count = travaler_count;
    }

    public int getTravaler_count() {
        return travaler_count;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setTourAgent(String tourAgent) {
        this.tourAgent = tourAgent;
    }

    public String getTourAgent() {
        return tourAgent;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
