package com.lt.dom.oct;

import com.lt.dom.OctResp.EnumLongIdResp;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class SeatingLayout extends Base{

    private int theatreNumber;//	The requested Theatre Number.


    private int rows_;//	The total count of rows.
    private int columns_;//	The total count of columns.
    private Long theatre;
    private String code;
    private long supplier;

    public static List List(List<SeatingLayout> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getVersionNumber()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
    }

    public int getTheatreNumber() {
        return theatreNumber;
    }

    public void setTheatreNumber(int theatreNumber) {
        this.theatreNumber = theatreNumber;
    }

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

    @Transient
    private List<Seat> seats;//	The list of seats in the layout.
    //id	Differentiates between the various layouts an auditorium can have.
    private int versionNumber;//	The version of an auditorium layout. When a layout is changed, this will be incremented.


    public void setTheatre(Long theatre) {

        this.theatre = theatre;
    }

    public Long getTheatre() {
        return theatre;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }
}
