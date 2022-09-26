package com.lt.dom.OctResp;

import com.lt.dom.controllerOct.BarcodesController;
import com.lt.dom.oct.IntoOnecode;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.util.ZxingBarcodeGenerator;

import java.util.List;

import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class IntoOnecodeResp {



    private EnumAssetType type;

    private String code;
    private String url;

    private String username;
    private String code_base64;
    private String code_base64_src;
    private String code_note;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private List<ComponentVounchResp> componentVounch;

    public static IntoOnecodeResp from(User user, IntoOnecode intoOnecode) {
        IntoOnecodeResp intoOnecodeResp = new IntoOnecodeResp();
        intoOnecodeResp.setCode(intoOnecode.getIdId());
        intoOnecodeResp.setType(intoOnecode.getType());

        intoOnecodeResp.setUsername(user.getRealName());
        String link = null;
        try {
            link = linkTo(methodOn(BarcodesController.class).getZxingQRCode(intoOnecode.getIdId())).withRel("editProduct").getHref();
        } catch (Exception e) {
            e.printStackTrace();
        }


        intoOnecodeResp.setCode_note("请出示二维码，核验权益");
        intoOnecodeResp.setCode_base64_src(ZxingBarcodeGenerator.base64_png_src(intoOnecode.getIdId()));

        intoOnecodeResp.setUrl(link);
        return intoOnecodeResp;
    }


    public EnumAssetType getType() {
        return type;
    }

    public void setType(EnumAssetType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public  void setComponentVounch(List<ComponentVounchResp> componentVounch) {
        this.componentVounch = componentVounch;
    }

    public List<ComponentVounchResp> getComponentVounch() {
        return componentVounch;
    }

    public void setCode_base64(String code_base64) {
        this.code_base64 = code_base64;
    }

    public String getCode_base64() {
        return code_base64;
    }

    public void setCode_base64_src(String code_base64_src) {
        this.code_base64_src = code_base64_src;
    }

    public String getCode_base64_src() {
        return code_base64_src;
    }

    public void setCode_note(String code_note) {
        this.code_note = code_note;
    }

    public String getCode_note() {
        return code_note;
    }

    /*    "qr":{
        "id":"U2FsdGVkX1+VwsADlREDxcAsQVY7l9Byt6cEX2AKBqJmBZvW0pvxGg1UZlsYikCo9nSxUxNXKQ928Ra0el20OJhqi9XyYo3Rv1KTUabg3fgkCBrrPfQVZsYomE6/Yz/KCxK90Ro3o1Cs4fKDmtnnog==",
                "url":"https://dl.voucherify.io/api/v1/assets/qr/U2FsdGVkX1%2BVwsADlREDxcAsQVY7l9Byt6cEX2AKBqJmBZvW0pvxGg1UZlsYikCo9nSxUxNXKQ928Ra0el20OJhqi9XyYo3Rv1KTUabg3fgkCBrrPfQVZsYomE6%2FYz%2FKCxK90Ro3o1Cs4fKDmtnnog%3D%3D"
    },
            "barcode":{
        "id":"U2FsdGVkX1+pl1abyxraI3S1WSBmsA+U6tkJiTXlEdCBitA9D4W5R46rkMvirKA3CE0tYcSdAgOxQQYD6z8fvs7aG6NSVeq5K7rhNvrGXP+OO+yfA03tgBNcVU89vhu8iyhI58NmzciUIZSfEPdy3w==",
                "url":*/
}
