package com.lt.dom.otcenum;

public enum EnumNote {
    account_opening_license("account_opening_license","原件扫描件或 复印件需加盖公章"),



    ;

    String note;

    EnumNote(String barcode,String note) {

        this.note = note;
    }
}
