package com.lt.dom.OctResp;

import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.controllerOct.PaymentRestController;
import com.lt.dom.oct.GuideInchargeBooking;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class GuideInchargeBookingResp extends RepresentationModel<GuideInchargeBookingResp> {





    private long guideId;


    private long booking;

    public static GuideInchargeBookingResp from(GuideInchargeBooking x) {
        GuideInchargeBookingResp guideInchargeBookingResp = new GuideInchargeBookingResp();

        guideInchargeBookingResp.setBooking(x.getBooking());
        guideInchargeBookingResp.setGuideId(x.getGuideId());
        guideInchargeBookingResp.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(x.getId(),null)).withRel("be_guide_url"));
        guideInchargeBookingResp.add(linkTo(methodOn(BookingRestController.class).getReservation(x.getBooking())).withRel("booking_url"));

        return guideInchargeBookingResp;
    }


    public long getGuideId() {
        return guideId;
    }

    public void setGuideId(long guide_id) {
        this.guideId = guide_id;
    }

    public long getBooking() {
        return booking;
    }

    public void setBooking(long booking) {
        this.booking = booking;
    }
}
