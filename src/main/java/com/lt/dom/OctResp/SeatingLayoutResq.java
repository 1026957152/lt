package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.SeatingLayout;
import com.lt.dom.otcReq.SeatPojo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class
SeatingLayoutResq extends BaseResp {




    @NotNull
    @JsonProperty("rows")
    private Integer rows_;//	The total count of rows.
    @NotNull
    @JsonProperty("columns")
    private Integer columns_;//	The total count of columns.
    private List zones;

    public static SeatingLayoutResq from(SeatingLayout supplier) {
        SeatingLayoutResq seatingLayoutResq = new SeatingLayoutResq();
        seatingLayoutResq.setColumns_(supplier.getColumns_());
        seatingLayoutResq.setRows_(supplier.getRows_());
        seatingLayoutResq.setVersionNumber(supplier.getVersionNumber());

        seatingLayoutResq.setCreatedDate(supplier.getCreatedDate());
        seatingLayoutResq.setModifiedDate(supplier.getModifiedDate());
        return seatingLayoutResq;
    }

    public Integer getRows_() {
        return rows_;
    }

    public void setRows_(Integer rows) {
        this.rows_ = rows;
    }

    public Integer getColumns_() {
        return columns_;
    }

    public void setColumns_(Integer columns) {
        this.columns_ = columns;
    }

    public List<SeatResp> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatResp> seats) {
        this.seats = seats;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }


    @Valid
    @Size(min = 0,max = 1000)
    private List<SeatResp> seats;//	The list of seats in the layout.
    //id	Differentiates between the various layouts an auditorium can have.
    @NotNull
    private Integer versionNumber;//	The version of an auditorium layout. When a layout is changed, this will be incremented.


    public <R> void setZones(List zones) {
        this.zones = zones;
    }

    public List getZones() {
        return zones;
    }
}
