package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.controllerOct.AssetRestController;
import com.lt.dom.oct.Asset;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WxPayOrderQueryResultResp {



    private boolean paied;

    public boolean isPaied() {
        return paied;
    }

    public void setPaied(boolean paied) {
        this.paied = paied;
    }
}
