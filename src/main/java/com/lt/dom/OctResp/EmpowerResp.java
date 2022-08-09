package com.lt.dom.OctResp;

import com.lt.dom.controllerOct.AssetRestController;
import com.lt.dom.oct.Asset;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class EmpowerResp extends RepresentationModel<EmpowerResp> {



    private String phone;
    private String cryptoPhone;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }


    public void setCryptoPhone(String cryptoPhone) {
        this.cryptoPhone = cryptoPhone;
    }

    public String getCryptoPhone() {
        return cryptoPhone;
    }
}
