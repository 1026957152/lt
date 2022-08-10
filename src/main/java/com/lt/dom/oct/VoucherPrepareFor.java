package com.lt.dom.oct;

import javax.persistence.Version;

public class VoucherPrepareFor {
    @Version
    private Integer version;
    private Campaign campaign;
    private String type; // for Product , For Supplier, for A
}
