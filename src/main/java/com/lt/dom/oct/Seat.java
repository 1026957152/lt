package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumSeatTier;
import com.lt.dom.otcenum.EnumSeatType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Seat extends Base{

    private long seatingLayoutId;
    private long zone;

    public long getSeatingLayoutId() {
        return seatingLayoutId;
    }

    public void setSeatingLayoutId(long seatingLayoutId) {
        this.seatingLayoutId = seatingLayoutId;
    }

    private int row_;//	The seat's row
    private int column_;//	The seat's column
    private String seatName;//	The displayable seat name
    private EnumSeatType type;//
    private EnumSeatTier seatTier;//	The tier a seat is targeted at.



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

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public EnumSeatType getType() {
        return type;
    }

    public void setType(EnumSeatType type) {
        this.type = type;
    }

    public EnumSeatTier getSeatTier() {
        return seatTier;
    }

    public void setSeatTier(EnumSeatTier seatTier) {
        this.seatTier = seatTier;
    }

    public void setZone(long zone) {
        this.zone = zone;
    }

    public long getZone() {
        return zone;
    }
/*            [empty_string_when_non_seat]
    Regular
            Premiere*/

}
