package com.lt.dom.oct;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BusStopPlaceKey implements Serializable {

    @Column(name = "place_id")
    Long placeId;

    @Column(name = "stop_id")
    Long stopId;

    public BusStopPlaceKey(long placeId, long stopId) {
        this.placeId = placeId;
        this.stopId = stopId;
    }

    public BusStopPlaceKey() {

    }
}