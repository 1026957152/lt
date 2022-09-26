package com.lt.dom.OctResp;


import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Traveler;

import java.util.List;
import java.util.stream.Collectors;


public class TravelerResp {





    private String name;///

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String age;///

    private String id_card;///
    private String phone;///

    public static List<TravelerResp> Listfrom(List<Traveler> travelers) {
        return travelers.stream().map(x->{
            TravelerResp travelerResp = new TravelerResp();
       //     travelerResp.setAge(x.getAge());
            travelerResp.setName(DesensitizedUtil.chineseName(x.getName()));

            travelerResp.setId_card(DesensitizedUtil.idCardNum(x.getIdNo(), 1, 2));
            travelerResp.setPhone(DesensitizedUtil.mobilePhone(x.getTel_home()));
            return travelerResp;
        }).collect(Collectors.toList());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
