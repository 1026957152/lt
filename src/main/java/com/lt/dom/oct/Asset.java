package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Asset {


    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private EnumAssetType type;
    private String idId;
    private String url;
    private long source;

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public EnumAssetType getType() {
        return type;
    }

    public void setType(EnumAssetType type) {
        this.type = type;
    }

    public String getIdId() {
        return idId;
    }

    public void setIdId(String idId) {
        this.idId = idId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /*    "qr":{
        "id":"U2FsdGVkX1+VwsADlREDxcAsQVY7l9Byt6cEX2AKBqJmBZvW0pvxGg1UZlsYikCo9nSxUxNXKQ928Ra0el20OJhqi9XyYo3Rv1KTUabg3fgkCBrrPfQVZsYomE6/Yz/KCxK90Ro3o1Cs4fKDmtnnog==",
                "url":"https://dl.voucherify.io/api/v1/assets/qr/U2FsdGVkX1%2BVwsADlREDxcAsQVY7l9Byt6cEX2AKBqJmBZvW0pvxGg1UZlsYikCo9nSxUxNXKQ928Ra0el20OJhqi9XyYo3Rv1KTUabg3fgkCBrrPfQVZsYomE6%2FYz%2FKCxK90Ro3o1Cs4fKDmtnnog%3D%3D"
    },
            "barcode":{
        "id":"U2FsdGVkX1+pl1abyxraI3S1WSBmsA+U6tkJiTXlEdCBitA9D4W5R46rkMvirKA3CE0tYcSdAgOxQQYD6z8fvs7aG6NSVeq5K7rhNvrGXP+OO+yfA03tgBNcVU89vhu8iyhI58NmzciUIZSfEPdy3w==",
                "url":*/
}
