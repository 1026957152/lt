package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.oct.PricingType;
import com.lt.dom.otcReq.product.ProductGiftVoucherPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.vo.ByHour;
import com.lt.dom.vo.ByItem;
import com.lt.dom.vo.ByPerson;
import com.lt.dom.vo.Fixed;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductEditComponentTabPojo {


    @Size(max = 50)
    private String name;
    private String source_id;

    private EnumAvailabilityType availability;


    @Valid
    private List<ProductPojo.Price> prices;
    @Valid
    private List<ProductPojo.Royalty> royalties;



    public List<ProductPojo.Price> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductPojo.Price> prices) {
        this.prices = prices;
    }




    List<AttributeResp> attributes;


    public List<AttributeResp> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeResp> attributes) {
        this.attributes = attributes;
    }





    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public List<ProductPojo.Royalty> getRoyalties() {
        return royalties;
    }

    public void setRoyalties(List<ProductPojo.Royalty> royalties) {
        this.royalties = royalties;
    }
}
