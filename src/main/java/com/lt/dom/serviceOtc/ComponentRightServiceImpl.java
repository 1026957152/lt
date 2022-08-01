package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AccessValidatorPojo;
import com.lt.dom.otcReq.ComponentRightPojo;
import com.lt.dom.otcenum.EnumValidatorRedemExtent;
import com.lt.dom.repository.AccessValidatorRepository;
import com.lt.dom.repository.ComponentRightRepository;
import com.lt.dom.repository.ValidatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComponentRightServiceImpl {
    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private AccessValidatorRepository accessValidatorRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;

    public ComponentRight createComponentRight(Supplier supplier, ComponentRightPojo pojo) {


        ComponentRight componentRight = new ComponentRight();

        componentRight.setSupplierId(supplier.getId());
        componentRight.setName(pojo.getName());
        componentRight = componentRightRepository.save(componentRight);


        ComponentRight finalComponentRight = componentRight;
        List<AccessValidator> componentRights = pojo.getValidators().stream().map(x->{
            AccessValidator accessValidator = new AccessValidator();
            accessValidator.setValidatorId(x.getId());
            accessValidator.setExtend(EnumValidatorRedemExtent.特定权益券);
            accessValidator.setRalativeId(finalComponentRight.getId());
            return accessValidator;
        }).collect(Collectors.toList());

        accessValidatorRepository.saveAll(componentRights);


        return componentRight;
    }

    public AccessValidator addAccessValidator(ComponentRight componentRight, AccessValidatorPojo pojo) {

        Optional<Validator> validatorOptional = validatorRepository.findById(pojo.getValidatorId());




        AccessValidator probe = new AccessValidator();
        probe.setValidatorId(validatorOptional.get().getId());
        Example<AccessValidator> example = Example.of(probe);


        Optional<AccessValidator> optional = accessValidatorRepository.findOne(example);

        if(optional.isEmpty()){
            AccessValidator accessValidator = new AccessValidator();
            accessValidator.setValidatorId(validatorOptional.get().getId());
            accessValidator.setExtend(EnumValidatorRedemExtent.特定权益券);
            accessValidator.setRalativeId(componentRight.getId());
            accessValidator = accessValidatorRepository.save(accessValidator);
            return accessValidator;
        }

        throw new RuntimeException();
    }


}
