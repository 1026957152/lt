package com.lt.dom.otcReq;

import com.lt.dom.oct.ComponentVounch;
import com.lt.dom.oct.Discount;
import com.lt.dom.otcenum.EnumVoucherType;
import com.lt.dom.oct.GiftVoucher;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


public class VoucherPojo {


    private long user;




    private String barcode_data;

    private String redemptionMethod;


    @Transient
    private List<ComponentVounch> componentVounchList;

    public List<ComponentVounch> getComponentRightVounchList() {
        return componentVounchList;
    }

    public void setComponentRightVounchList(List<ComponentVounch> componentVounchList) {
        this.componentVounchList = componentVounchList;
    }

    /*
    How the voucher can be redeemed. Possible values are:
    MANIFEST The guest name will be written down and they just need to show up
    DIGITAL The tickets/voucher must be scanned but can be on mobile
    PRINT The tickets/voucher must be printed and presented on arrival
*/


    private EnumVoucherType type;



    @Transient
    private Discount discount ;
    @Transient
    private GiftVoucher giftVoucher ;
    @Transient
    private ComponentVounch component ;


    private LocalDate start_date;
    private LocalDate expiration_date;

    private boolean active;

    @Transient
    private List assets;


    private int length;
    private String charset;
    private String prefix;
    private String postfix;
    private String pattern;


}
