package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumSeatTier;
import com.lt.dom.otcenum.EnumSeatType;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class Zone extends Base{



    private Long seatingLayoutId;//	The displayable seat name

    public Long getSeatingLayoutId() {
        return seatingLayoutId;
    }

    public void setSeatingLayoutId(Long seatingLayoutId) {
        this.seatingLayoutId = seatingLayoutId;
    }


    @Transient
    private List<ZoneRow> rows;//	The seat's row

    private String name;//	The displayable seat name


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
