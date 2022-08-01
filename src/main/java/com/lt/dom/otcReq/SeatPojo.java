package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumSeatType;

public class SeatPojo {


    private int row;//	The seat's row
    private int column;//	The seat's column
    private int seatName;//	The displayable seat name
    private EnumSeatType type;//
    private String seatTier;//	The tier a seat is targeted at.

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
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
}
