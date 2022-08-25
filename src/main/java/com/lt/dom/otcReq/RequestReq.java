package com.lt.dom.otcReq;


import com.lt.dom.otcenum.EnumRequestType;
import com.lt.dom.requestvo.VoucherBatch;

import javax.persistence.Transient;
import java.util.List;

public class RequestReq {  // 这个就是机器了啊

    private String titel;


    private EnumRequestType type;

    private VoucherBatch voucherBatch;

    private long tourBooking;

    public long getTourBooking() {
        return tourBooking;
    }

    public void setTourBooking(long tourBooking) {
        this.tourBooking = tourBooking;
    }

    //  private TourBookingPojo tourBooking;

    public VoucherBatch getVoucherBatch() {
        return voucherBatch;
    }

    public void setVoucherBatch(VoucherBatch voucherBatch) {
        this.voucherBatch = voucherBatch;
    }



    public EnumRequestType getType() {
        return type;
    }

    public void setType(EnumRequestType type) {
        this.type = type;
    }

    public List<Long> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<Long> reviewers) {
        this.reviewers = reviewers;
    }

    public List<Long> getTeam_reviewers() {
        return team_reviewers;
    }

    public void setTeam_reviewers(List<Long> team_reviewers) {
        this.team_reviewers = team_reviewers;
    }

    private List<Long> reviewers;

    private List<Long> team_reviewers;

}
