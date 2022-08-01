package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.repository.ReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class ReferralServiceImpl {



    @Autowired
    ReferralRepository referralRepository;



    public void getUserAndProductByScene(String scene) {

        Referral user = new Referral();
        user.setCode(scene);
        Example<Referral> example = Example.of(user);
        Optional<Referral> referral = referralRepository.findOne(example);

        referral.get().getProductId();
        referral.get().getAgentId();
        referral.get().getUserId();

    }
}
