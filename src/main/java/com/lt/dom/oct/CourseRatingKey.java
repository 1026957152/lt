package com.lt.dom.oct;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CourseRatingKey implements Serializable {

    @Column(name = "route_id")
    Long routeId;

    @Column(name = "stop_id")
    Long stopId;

    public CourseRatingKey(long id, long id1) {
        this.routeId = id;
        this.stopId = id1;
    }

    public CourseRatingKey() {

    }
}