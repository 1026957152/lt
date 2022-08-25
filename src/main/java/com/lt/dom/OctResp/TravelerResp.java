package com.lt.dom.OctResp;


import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Traveler;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;


public class TravelerResp {





    private String name;///

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String age;///

    private String idNo;///
    private String tel_home;///

    public static List<TravelerResp> Listfrom(List<Traveler> travelers) {
        return travelers.stream().map(x->{
            TravelerResp travelerResp = new TravelerResp();
       //     travelerResp.setAge(x.getAge());
            travelerResp.setName(DesensitizedUtil.chineseName(x.getName()));


            travelerResp.setIdNo(DesensitizedUtil.idCardNum(x.getIdNo(), 1, 2));
            travelerResp.setTel_home(DesensitizedUtil.mobilePhone(x.getTel_home()));
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


    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTel_home() {
        return tel_home;
    }

    public void setTel_home(String tel_home) {
        this.tel_home = tel_home;
    }
}
