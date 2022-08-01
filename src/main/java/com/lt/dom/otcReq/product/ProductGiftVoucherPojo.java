package com.lt.dom.otcReq.product;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.otcenum.EnumProductType;

import java.util.List;

public class ProductGiftVoucherPojo {

    private String name;
    private String source_id;
    private String Uniquecode;
    private EnumProductType type ;//Museums, Attractions or Hop on Hop off tours

    public EnumProductType getType() {
        return type;
    }

    public void setType(EnumProductType type) {
        this.type = type;
    }

    List<AttributeResp> attributes;

    public List<AttributeResp> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeResp> attributes) {
        this.attributes = attributes;
    }













    private String briefdescription;//:"Gift cards October",
    private String longdescription;
    private String valueType; //
    private int fixedAmount;

    private long specificProductId;
    private int specificProductPrice;


    private long specificCategoryId;
    private int specificCategoryPrice;

    private String expiryType;//  Fixed number of days        or   Fixed date

    private boolean Includealltaxesfees;

}
