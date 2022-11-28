package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.controllerOct.AssetRestController;
import com.lt.dom.oct.Asset;
import com.lt.dom.otcenum.EnumAssetType;
import org.javatuples.Pair;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.swing.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetResp  {



    private EntityModel<Item> qr;
    private EntityModel<Item> barcode;

    public EntityModel<Item> getQr() {
        return qr;
    }

    public void setQr(EntityModel<Item> qr) {
        this.qr = qr;
    }

    public EntityModel<Item> getBarcode() {
        return barcode;
    }

    public void setBarcode(EntityModel<Item> barcode) {
        this.barcode = barcode;
    }

    public static EntityModel from( List<Asset> assets) {


        Map map  = assets.stream().map(asset->{
            EnumAssetType type = asset.getType();

            Item item = new Item();
            item.setId(asset.getIdId());
            item.setUrl(asset.getUrl());
            item.setType(asset.getType());
            item.setType_text(asset.getType().toString());

            EntityModel entityModel = EntityModel.of(item);
            try {
                entityModel.add(linkTo(methodOn(AssetRestController.class).downloadAsset(asset.getId())).withRel("download_url"));

                entityModel.add(linkTo(methodOn(AssetRestController.class).getAsset(asset.getId())).withRel("download___url"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Map.entry(type.name,entityModel);

        }).collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));




        return EntityModel.of(map);
    }

    public static List toList( List<Asset> assets) {


        List list  = assets.stream().map(asset->{


            Item item = new Item();
            item.setId(asset.getId()+"");

            item.setType(asset.getType());
            item.setType_text(asset.getType().toString());

            EntityModel entityModel = EntityModel.of(item);
            try {
                String url = linkTo(methodOn(AssetRestController.class).downloadAsset(asset.getId())).withRel("regenerate").getHref();

                item.setUrl(url);

                entityModel.add(linkTo(methodOn(AssetRestController.class).downloadAsset(asset.getId())).withRel("download_url"));
                entityModel.add(linkTo(methodOn(AssetRestController.class).regenerate(asset.getId())).withRel("regenerate"));
                entityModel.add(linkTo(methodOn(AssetRestController.class).getAsset(asset.getId())).withRel("download___url"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return entityModel;

        }).collect(Collectors.toList());

        return list;
    }

    public static Map toMap( List<Asset> assets) {


        Map map  = assets.stream().map(asset->{
            EnumAssetType type = asset.getType();

            Item item = new Item();
            item.setId(asset.getIdId());
            item.setUrl(asset.getUrl());
            item.setType(asset.getType());
            item.setType_text(asset.getType().toString());

            EntityModel entityModel = EntityModel.of(item);
            try {
                entityModel.add(linkTo(methodOn(AssetRestController.class).downloadAsset(asset.getId())).withRel("download_url"));
                entityModel.add(linkTo(methodOn(AssetRestController.class).regenerate(asset.getId())).withRel("regenerate"));
                String url = linkTo(methodOn(AssetRestController.class).downloadAsset(asset.getId())).withRel("regenerate").getHref();

                item.setUrl(url);
                entityModel.add(linkTo(methodOn(AssetRestController.class).getAsset(asset.getId())).withRel("download___url"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Map.entry(type.name,entityModel);

        }).collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));



        return map;
    }
    public static EntityModel<AssetResp> from(Asset asset) {
        AssetResp assetResp = new AssetResp();
        Item item = new Item();
        item.setId(asset.getIdId());
        item.setUrl(asset.getUrl());

        EntityModel entityModel = EntityModel.of(item);
        try {
            entityModel.add(linkTo(methodOn(AssetRestController.class).downloadAsset(asset.getId())).withRel("download_url"));

            entityModel.add(linkTo(methodOn(AssetRestController.class).getAsset(asset.getId())).withRel("download___url"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assetResp.setQr(entityModel);
        return EntityModel.of(assetResp);
    }
/*    "qr":{
        "id":"U2FsdGVkX1+VwsADlREDxcAsQVY7l9Byt6cEX2AKBqJmBZvW0pvxGg1UZlsYikCo9nSxUxNXKQ928Ra0el20OJhqi9XyYo3Rv1KTUabg3fgkCBrrPfQVZsYomE6/Yz/KCxK90Ro3o1Cs4fKDmtnnog==",
                "url":"https://dl.voucherify.io/api/v1/assets/qr/U2FsdGVkX1%2BVwsADlREDxcAsQVY7l9Byt6cEX2AKBqJmBZvW0pvxGg1UZlsYikCo9nSxUxNXKQ928Ra0el20OJhqi9XyYo3Rv1KTUabg3fgkCBrrPfQVZsYomE6%2FYz%2FKCxK90Ro3o1Cs4fKDmtnnog%3D%3D"
    },
            "barcode":{
        "id":"U2FsdGVkX1+pl1abyxraI3S1WSBmsA+U6tkJiTXlEdCBitA9D4W5R46rkMvirKA3CE0tYcSdAgOxQQYD6z8fvs7aG6NSVeq5K7rhNvrGXP+OO+yfA03tgBNcVU89vhu8iyhI58NmzciUIZSfEPdy3w==",
                "url":*/

    public static class Item {
        private String id;
        private String url;
        private EnumAssetType type;
        private String type_text;

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

        public EnumAssetType getType() {
            return type;
        }

        public void setType(EnumAssetType type) {
            this.type = type;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public String getType_text() {
            return type_text;
        }
    }

}
