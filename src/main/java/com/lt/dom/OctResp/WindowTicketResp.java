package com.lt.dom.OctResp;

import com.lt.dom.oct.AssetAuthorized;
import com.lt.dom.oct.AssetDevice;
import com.lt.dom.otcenum.EnumUserType;

import javax.validation.constraints.NotNull;
import java.util.List;


public class WindowTicketResp {



    private List<ProductResp> products;

    public void setProducts(List<ProductResp> products) {
        this.products = products;
    }

    public List<ProductResp> getProducts() {
        return products;
    }
}
