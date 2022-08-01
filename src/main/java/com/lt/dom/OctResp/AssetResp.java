package com.lt.dom.OctResp;

import com.lt.dom.controllerOct.AssetRestController;
import com.lt.dom.oct.Asset;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class AssetResp extends RepresentationModel<AssetResp> {



    private Item qr;
    private Item barcode;

    public Item getQr() {
        return qr;
    }

    public void setQr(Item qr) {
        this.qr = qr;
    }

    public Item getBarcode() {
        return barcode;
    }

    public void setBarcode(Item barcode) {
        this.barcode = barcode;
    }

    public static AssetResp from(Asset asset) {
        AssetResp assetResp = new AssetResp();
        Item item = new Item();
        item.setId(asset.getIdId());
        item.setUrl(asset.getUrl());

        try {
            item.add(linkTo(methodOn(AssetRestController.class).getAsset(asset.getId())).withRel("download_url"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assetResp.setQr(item);
        return assetResp;
    }
/*    "qr":{
        "id":"U2FsdGVkX1+VwsADlREDxcAsQVY7l9Byt6cEX2AKBqJmBZvW0pvxGg1UZlsYikCo9nSxUxNXKQ928Ra0el20OJhqi9XyYo3Rv1KTUabg3fgkCBrrPfQVZsYomE6/Yz/KCxK90Ro3o1Cs4fKDmtnnog==",
                "url":"https://dl.voucherify.io/api/v1/assets/qr/U2FsdGVkX1%2BVwsADlREDxcAsQVY7l9Byt6cEX2AKBqJmBZvW0pvxGg1UZlsYikCo9nSxUxNXKQ928Ra0el20OJhqi9XyYo3Rv1KTUabg3fgkCBrrPfQVZsYomE6%2FYz%2FKCxK90Ro3o1Cs4fKDmtnnog%3D%3D"
    },
            "barcode":{
        "id":"U2FsdGVkX1+pl1abyxraI3S1WSBmsA+U6tkJiTXlEdCBitA9D4W5R46rkMvirKA3CE0tYcSdAgOxQQYD6z8fvs7aG6NSVeq5K7rhNvrGXP+OO+yfA03tgBNcVU89vhu8iyhI58NmzciUIZSfEPdy3w==",
                "url":*/

    public static class Item extends RepresentationModel<Item>{
        private String id;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
