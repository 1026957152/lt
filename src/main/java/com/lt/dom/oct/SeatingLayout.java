package com.lt.dom.oct;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;


@Entity
public class SeatingLayout {

    @Id
    private long id;
    private int theatreNumber;//	The requested Theatre Number.


    private int rows;//	The total count of rows.
    private int columns;//	The total count of columns.

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTheatreNumber() {
        return theatreNumber;
    }

    public void setTheatreNumber(int theatreNumber) {
        this.theatreNumber = theatreNumber;
    }

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

    @Transient
    private List<Seat> seats;//	The list of seats in the layout.
    //id	Differentiates between the various layouts an auditorium can have.
    private int versionNumber;//	The version of an auditorium layout. When a layout is changed, this will be incremented.



}
