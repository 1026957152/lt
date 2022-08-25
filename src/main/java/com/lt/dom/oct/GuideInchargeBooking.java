package com.lt.dom.oct;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class GuideInchargeBooking {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;



    @NotNull
    private Long guideId;

    @NotNull
    private Long booking;

    @NotNull
    private Long agency;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getGuideId() {
        return guideId;
    }

    public void setGuideId(Long guide_id) {
        this.guideId = guide_id;
    }

    public Long getBooking() {
        return booking;
    }

    public void setBooking(Long booking) {
        this.booking = booking;
    }

    public void setAgency(Long agency) {
        this.agency = agency;
    }

    public Long getAgency() {
        return agency;
    }
}
