package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumSeatTier;
import com.lt.dom.otcenum.EnumSeatType;

import javax.validation.constraints.NotNull;

public class ZoneReq {



    @NotNull
    private int row;//	The seat's row
    @NotNull
    private int column;//	The seat's column

    private String name;//	The displayable seat name
    @NotNull
    private EnumSeatType type;//
    private EnumSeatTier seatTier;//	The tier a seat is targeted at.

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
