package com.lt.dom.OctResp;

import com.lt.dom.oct.Seat;
import com.lt.dom.otcenum.EnumSeatTier;
import com.lt.dom.otcenum.EnumSeatType;

import javax.validation.constraints.NotNull;

public class SeatResp {



    @NotNull
    private int row;//	The seat's row
    @NotNull
    private int column;//	The seat's column

    private String seatName;//	The displayable seat name
    @NotNull
    private EnumSeatType type;//
    private EnumSeatTier seatTier;//	The tier a seat is targeted at.

    public static SeatResp from(Seat x) {
        SeatResp seatResp = new SeatResp();

        seatResp.setSeatName(x.getSeatName());
        seatResp.setColumn(x.getColumn_());
        seatResp.setRow(x.getRow_());
        seatResp.setType(x.getType());
        seatResp.setSeatTier(x.getSeatTier());

        return seatResp;
    }

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
}
