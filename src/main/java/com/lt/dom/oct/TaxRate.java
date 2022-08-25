package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumTaxType;

public class TaxRate {
    private String code;
    private String display_name;
    private int DecimalPlaces;

    private ChargeTypeCodes chargeTypeCodes;
    private boolean inclusive;

    private Integer percentage;
    private EnumTaxType tax_type;

}
