package com.lt.dom.otcenum;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

public enum EnumReferralType {

    qr("qr"),
    barcode("barcode");

    EnumReferralType(String barcode) {

    }

}
