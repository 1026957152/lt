package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumProductPricingType;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;
import com.lt.dom.otcenum.EnumValidatorType;
import com.lt.dom.vo.ByHour;
import com.lt.dom.vo.ByItem;
import com.lt.dom.vo.ByPerson;
import com.lt.dom.vo.Fixed;

public class RedemPojo {
    private long quipment;
    private long user;
    private EnumValidatorType type; //指定人工，机器, 所有人工

    public long getQuipment() {
        return quipment;
    }

    public void setQuipment(long quipment) {
        this.quipment = quipment;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public EnumValidatorType getType() {
        return type;
    }

    public void setType(EnumValidatorType type) {
        this.type = type;
    }


    private String  redeemptionTyppe; //核销票据  // 旅行消费券，      畅游消费券， 剧院消费券， 清爽观影券

    private Traveler  traveler;

    public String getRedeemptionTyppe() {
        return redeemptionTyppe;
    }

    public void setRedeemptionTyppe(String redeemptionTyppe) {
        this.redeemptionTyppe = redeemptionTyppe;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }
//旅行团，五个人，旅游团，

    public class Traveler {

        private String name;///

        private String idNo;


        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }



}
