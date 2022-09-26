package com.lt.dom.serviceOtc;

import com.google.gson.Gson;
import com.lt.dom.controllerOct.PassengerRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ReferralPojo;
import com.lt.dom.otcenum.EnumReferralType;
import com.lt.dom.repository.ReferralRepository;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class ReferralServiceImpl {



    @Autowired
    ReferralRepository referralRepository;



    public Referral getUserAndProductByScene(String scene) {

        Referral user = new Referral();
        user.setCode(scene);
        Example<Referral> example = Example.of(user);
        Optional<Referral> referral = referralRepository.findOne(example);

        referral.get().getProductId();
        referral.get().getAgentId();
        referral.get().getUserId();

        return referral.get();

    }


    public Referral create(Long user, EnumReferralType fill_up_passager_info, ReferralPojo pojo) {



        Referral referral = new Referral();
        referral.setUser(user);
        referral.setCode(UUID.randomUUID().toString());

        String path = String.format(fill_up_passager_info.getPath(),referral.getCode());
        referral.setPath(path);

        referral.setType(fill_up_passager_info);
        referral.setLong_value(pojo.getLong_value());
        referral.setString_value(pojo.getString_value());
        referral.setMeta_data(pojo.getMeta_data());
        referral = referralRepository.save(referral);
        return referral;
    }

    public Referral create(User user, EnumReferralType fill_up_passager_info, ReferralPojo pojo) {

        List<Referral> referrals = referralRepository.findByUserAndType(user.getId(),fill_up_passager_info);

        if(referrals.isEmpty()){
            return create(user.getId(),fill_up_passager_info,pojo);
        }else{
            return referrals.get(0);
        }

    }
    public Referral create(UserVo user, EnumReferralType fill_up_passager_info, String message, ReferralPojo pojo) {
        List<Referral> referrals = referralRepository.findByUserAndType(user.getUser_id(),fill_up_passager_info);
        Referral referral =null;
        pojo.setMeta_data(new Gson().toJson(Map.of("message",message)));

        if(referrals.isEmpty()){
            referral =  create(user.getUser_id(),fill_up_passager_info,pojo);

        }else{
            referral =  referrals.get(0);
        }

/*        if(referral.getType().equals(EnumReferralType.fill_up_passager_info)){
            EntityModel entityModel = EntityModel.of(referral);

            entityModel.add(linkTo(methodOn(PassengerRestController.class).createPassenger(referral.getUser(),null)).withSelfRel());
            return entityModel;
        }*/
        return referral;
    }
}
