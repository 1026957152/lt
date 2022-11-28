package com.lt.dom.otcenum;

public enum EnumSupplierVerifiedStatus {

    completed(-1),
    rejected(-1),
    pending(-1),
    none(-1),

    ;


    int id;
    EnumSupplierVerifiedStatus(int id) {

        this.id = id;
    }
    public int id(){
        return id;
    }
}
