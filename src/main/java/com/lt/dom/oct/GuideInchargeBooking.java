package com.lt.dom.oct;

import javax.persistence.*;


@Entity
public class GuideInchargeBooking {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;



    private long guideId;


    private long booking;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
