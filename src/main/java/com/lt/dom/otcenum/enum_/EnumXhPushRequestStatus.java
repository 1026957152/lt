package com.lt.dom.otcenum.enum_;

public enum EnumXhPushRequestStatus {
    Created(1),
    Rejected(2),
    Approved(3),
    Loaned(3);


    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    EnumXhPushRequestStatus(Integer id){
        this.id = id;
    }
}
