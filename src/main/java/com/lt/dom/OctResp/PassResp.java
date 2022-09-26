package com.lt.dom.OctResp;

import com.lt.dom.OctResp.home.FeatureResp;
import com.lt.dom.OctResp.home.HomeResp;
import com.lt.dom.oct.Pass;
import com.lt.dom.otcenum.EnumCardStatus;
import com.lt.dom.otcenum.EnumCardType;
import com.lt.dom.otcenum.EnumPassDorationUnit;
import com.lt.dom.otcenum.EnumPassShippingStatus;
import org.javatuples.Pair;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


public class PassResp {




    private EnumCardType type ;//digital tickets or PDF tickets

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public static PassResp from(Pass e) {

        PassResp passResp = new PassResp();
        passResp.setType(e.getType());
        passResp.setCode(e.getCode());

        return passResp;
    }


}
