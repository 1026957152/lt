package com.lt.dom.oct;

import javax.persistence.*;
@Entity
public class ParkingLot {


        @Version
        private Integer version;

        @GeneratedValue(strategy = GenerationType.IDENTITY)

        @Id
        private long id;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
