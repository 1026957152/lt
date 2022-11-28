package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumCardType;

import javax.validation.constraints.NotNull;
import java.util.List;


public class PassCreatePojo {


    @NotNull
    private EnumCardType type ;//digital tickets or PDF tickets
    @NotNull
    private Long product;

    @NotNull
    private Long user ;//digital tickets or PDF tickets

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }




    List<Long> rights;

    public EnumCardType getType() {
        return type;
    }

    public void setType(EnumCardType type) {
        this.type = type;
    }

    public List<Long> getRights() {
        return rights;
    }

    public void setRights(List<Long> rights) {
        this.rights = rights;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }
}
