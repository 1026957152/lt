package com.lt.dom.otcenum;

public enum EnumSupplierVerifiedStatus {
    verified(0),
    unverified(-1),



    ;


    int id;
    EnumSupplierVerifiedStatus(int id) {

        this.id = id;
    }
    public int id(){
        return id;
    }
}
