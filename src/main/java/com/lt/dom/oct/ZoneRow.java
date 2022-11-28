package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumSeatTier;
import com.lt.dom.otcenum.EnumSeatType;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class ZoneRow extends Base{



    @Transient
    private List<Seat> seats;//	The displayable seat name
    private Long zone;//	The displayable seat name

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    private String name;//	The displayable seat name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
