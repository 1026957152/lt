package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumSeatType;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Seat {

    @Id
    private long id;
    private long seatingLayoutId;

    public long getSeatingLayoutId() {
        return seatingLayoutId;
    }

    public void setSeatingLayoutId(long seatingLayoutId) {
        this.seatingLayoutId = seatingLayoutId;
    }

    private int row_;//	The seat's row
    private int column_;//	The seat's column
    private int seatName;//	The displayable seat name
    private EnumSeatType type;//
    private String seatTier;//	The tier a seat is targeted at.

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRow_() {
        return row_;
    }

    public void setRow_(int row_) {
        this.row_ = row_;
    }

    public int getColumn_() {
        return column_;
    }

    public void setColumn_(int column_) {
        this.column_ = column_;
    }

    public int getSeatName() {
        return seatName;
    }

    public void setSeatName(int seatName) {
        this.seatName = seatName;
    }

    public EnumSeatType getType() {
        return type;
    }

    public void setType(EnumSeatType type) {
        this.type = type;
    }

    public String getSeatTier() {
        return seatTier;
    }

    public void setSeatTier(String seatTier) {
        this.seatTier = seatTier;
    }
    /*            [empty_string_when_non_seat]
    Regular
            Premiere*/

}
