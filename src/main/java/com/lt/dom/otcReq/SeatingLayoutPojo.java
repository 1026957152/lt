package com.lt.dom.otcReq;

import com.lt.dom.oct.Seat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;



public class SeatingLayoutPojo {



    private int rows;//	The total count of rows.
    private int columns;//	The total count of columns.

    private List<Seat> seats;//	The list of seats in the layout.
    private int versionNumber;//	The version of an auditorium layout. When a layout is changed, this will be incremented.
    private Long theatre;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Long getTheatre() {
        return theatre;
    }

    public void setTheatre(Long theatre) {
        this.theatre = theatre;
    }
}
