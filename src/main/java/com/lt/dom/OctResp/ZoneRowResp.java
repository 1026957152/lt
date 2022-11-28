package com.lt.dom.OctResp;

import com.lt.dom.oct.Base;
import com.lt.dom.oct.Seat;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class ZoneRowResp extends Base {



    private String name;//	The displayable seat name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
