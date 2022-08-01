package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ValidatorPojo;
import com.lt.dom.otcenum.EnumValidatorType;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ValidatorServiceImpl {

    @Autowired
    private WriteoffEquipServiceImpl writeoffEquipService;

    @Autowired
    private AccessValidatorRepository accessValidatorRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;


    @Autowired
    private ValidatorGroupRepository validatorGroupRepository;

    @Autowired
    private ValidatorRepository validatorRepository;




    public Validator newValidator(ValidatorPojo pojo) {


        Optional<Equipment> equipmentOptioanl = equipmentRepository.findById(pojo.getQuipment());

        if(equipmentOptioanl.isEmpty())
                throw new RuntimeException();


        if(!Arrays.asList(EnumValidatorType.特定的人员,EnumValidatorType.特定机器).contains(pojo.getType())){
            throw new RuntimeException();
        }

        Validator validator = new Validator();
        validator.setEquipmentId(equipmentOptioanl.get().getId());


        if(pojo.getType().equals(EnumValidatorType.特定的人员)){
            validator.setUserId(pojo.getUser());
            validator.setType(pojo.getType());

        }

        if(pojo.getType().equals(EnumValidatorType.特定机器)){

            validator.setEquipmentId(pojo.getQuipment());
            validator.setType(pojo.getType());

        }

        validator= validatorRepository.save(validator);

        Validator finalValidator = validator;
        List<AccessValidator> accessValidators = pojo.getExtents().stream().map(x->{
            AccessValidator accessValidator = new AccessValidator();
            accessValidator.setExtend(x);
            accessValidator.setValidatorId(finalValidator.getId());
            return accessValidator;
        }).collect(Collectors.toList());


        accessValidatorRepository.saveAll(accessValidators);


        return validator;

    }




    public void push(List<Validator> validators, Voucher value0) {

        validators.stream().forEach(x->{
            if(x.getType().equals(EnumValidatorType.特定机器)){
            //    value0.getUser();
                User user = new User();
                //writeoffEquipService.mqtt(x.getName(),user.getPhone());
            };
            if(x.getType().equals(EnumValidatorType.一组机器)){
               // value0.getUser();
                User user = new User();
               // writeoffEquipService.mqtt(x.getName(),user.getPhone());
            };

            if(x.getType().equals(EnumValidatorType.角色)){

              //  value0.
               // writeoffEquipService.mqtt(x.getName(),user.getPhone());
            };
        });
    }

}