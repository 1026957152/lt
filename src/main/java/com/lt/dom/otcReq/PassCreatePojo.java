package com.lt.dom.otcReq;

import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Product;

import javax.persistence.*;
import java.util.List;


public class PassCreatePojo {


    private String type ;//digital tickets or PDF tickets

    private String code ;//digital tickets or PDF tickets
    private Long productId;

    public String getCode() {
        return code;
    }
    private long user ;//digital tickets or PDF tickets

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public void setCode(String code) {
        this.code = code;
    }


    List<Long> rights;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Long> getRights() {
        return rights;
    }

    public void setRights(List<Long> rights) {
        this.rights = rights;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
