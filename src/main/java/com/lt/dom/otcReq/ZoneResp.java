package com.lt.dom.otcReq;

import com.lt.dom.oct.Zone;
import com.lt.dom.otcenum.EnumSeatTier;
import com.lt.dom.otcenum.EnumSeatType;

import javax.validation.constraints.NotNull;

public class ZoneResp {


    private Long id;

    public static ZoneResp from(Zone x) {
        ZoneResp zoneResp = new ZoneResp();
        zoneResp.setId(x.getId());
        zoneResp.setName(x.getName());
        return zoneResp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;//	The displayable seat name

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
