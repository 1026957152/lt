package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.repository.ComponentRightVounchRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.repository.ValidatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;


@Service
public class RedeemServiceImpl {

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private ComponentRightVounchRepository componentRightVounchRepository;

    public ComponentRightVounch redeemCompo(ComponentRightVounch componentRightVounch1){


        Referral referral = new Referral();

        return null;


    }




    private List<ComponentRightVounch> 找到当前权益(Validator validator, Voucher voucher) throws Exception {


        ValidatorGroup validatorGroup = validator.getValidatorGroup();

        AccessValidator accessValidator = validatorGroup.getAccessValidator();


        ComponentRight componentRight = accessValidator.getComponentRight();



        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("model", ignoreCase());

        ComponentRightVounch probe = new ComponentRightVounch();
        probe.setComponentRightId(componentRight.getId());
        probe.setVoucherId(voucher.getId());

        Example<ComponentRightVounch> example = Example.of(probe, modelMatcher);

        List<ComponentRightVounch> componentRightVounches1 = componentRightVounchRepository.findAll(example);


        if(componentRightVounches1.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }


        return componentRightVounches1;


    }


    public ComponentRightVounch redeemVounchor(UserDetails userDetails,Voucher voucher){


        List<Validator> validator = validatorRepository.findAllByUserId(userDetails.getUsername());


        Referral referral = new Referral();

        return null;


    }
}
