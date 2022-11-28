package com.lt.dom.otcReq;

import com.lt.dom.OctResp.PhotoResp;

import java.util.List;


public class PassActivePojo {


    private String type ;//digital tickets or PDF tickets

    private String code ;//digital tickets or PDF tickets
    private String name;


    private String cvc;
    private PhotoResp front;
    private PhotoResp back;


    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    private String id_card;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public PhotoResp getFront() {
        return front;
    }

    public void setFront(PhotoResp front) {
        this.front = front;
    }

    public PhotoResp getBack() {
        return back;
    }

    public void setBack(PhotoResp back) {
        this.back = back;
    }
}
