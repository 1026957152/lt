package com.lt.dom.OctResp;

import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.Guide;
import com.lt.dom.oct.Scenario;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumVoucherType;
import org.javatuples.Pair;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import java.util.Map;
import java.util.Optional;



public class GuideResp {





    private long userId;
    private String rean_name;
    private String id_card;
    private String phone;
    
//##@Column(unique=true) 
private String code;


    public static Page<EntityModel<EnumLongIdResp>> pageMapToListEnumSimple(Page<Guide> campaignPageable, Map<Long, User> longUserMap) {
        return  campaignPageable.map(x->{
            EntityModel entityModel = EntityModel.of(enumSimple(Pair.with(x, longUserMap.get(x.getUserId()))));
            return entityModel;
        });
    }


    public static EnumLongIdResp enumSimple(Pair<Guide,User> triplet) {

        Guide campaign = triplet.getValue0();
        GuideResp campaignResp = new GuideResp();

        User user = triplet.getValue1();

        campaignResp.setRean_name(user.getRealName());

        campaignResp.setId_card(user.getIdCard());
        campaignResp.setCode(user.getCode());
        campaignResp.setPhone(user.getPhone());

        EnumLongIdResp enumLongIdResp = new EnumLongIdResp();
        enumLongIdResp.setId(campaign.getId());
        enumLongIdResp.setText(user.getRealName()+"_"+user.getIdCard());
        enumLongIdResp.setInfo(campaignResp);
        return enumLongIdResp;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setRean_name(String rean_name) {
        this.rean_name = rean_name;
    }

    public String getRean_name() {
        return rean_name;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getId_card() {
        return id_card;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
