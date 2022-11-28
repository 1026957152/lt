package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.SeatingLayoutResq;
import com.lt.dom.oct.Seat;
import com.lt.dom.oct.SeatingLayout;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;



public class
SeatingLayoutReq {




    @NotNull
    @JsonProperty("rows")
    private Integer rows_;//	The total count of rows.
    @NotNull
    @JsonProperty("columns")
    private Integer columns_;//	The total count of columns.




    public int getRows_() {
        return rows_;
    }

    public void setRows_(int rows) {
        this.rows_ = rows;
    }

    public int getColumns_() {
        return columns_;
    }

    public void setColumns_(int columns) {
        this.columns_ = columns;
    }

    public List<SeatPojo> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatPojo> seats) {
        this.seats = seats;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }


    @Valid
    @Size(min = 0,max = 1000)
    private List<SeatPojo> seats;//	The list of seats in the layout.
    //id	Differentiates between the various layouts an auditorium can have.
    @NotNull
    private Integer versionNumber;//	The version of an auditorium layout. When a layout is changed, this will be incremented.



}
