package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ReferralPojo;
import com.lt.dom.repository.ReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;



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

    public Referral create(ReferralPojo pojo) {

        Referral referral = new Referral();
        referral.setCode(UUID.randomUUID().toString());
        referral.setType(pojo.getType());
        referral.setLong_value(pojo.getLong_value());
        referral.setString_value(pojo.getString_value());
        referral.setMeta_data(pojo.getMeta_data());
        referral = referralRepository.save(referral);
        return referral;
    }
}
