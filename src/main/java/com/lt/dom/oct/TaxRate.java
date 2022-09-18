package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumTaxType;

import javax.persistence.Column;

public class TaxRate {
    
@Column(unique=true)
private String code;
    private String display_name;
    private int DecimalPlaces;

    private ChargeTypeCodes chargeTypeCodes;
    private boolean inclusive;

    private Integer percentage;
    private EnumTaxType tax_type;

}
