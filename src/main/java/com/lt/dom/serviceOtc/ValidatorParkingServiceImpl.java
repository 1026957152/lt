package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.oct.ValidatorParking.ParkingStatus;
import com.lt.dom.repository.ComponentRightVounchRepository;

import java.util.ArrayList;
import java.util.List;

public class ValidatorParkingServiceImpl {



    private ComponentRightVounchRepository componentRightVounchRepository;

    public List<ComponentRightVounch> 当前权益名单(ComponentRight componentRight) {


        Referral referral = new Referral();


        return new ArrayList<>();

    }

    public List<Validator> 当前停车设备(ComponentRight componentRight) throws Exception {

        AccessValidator accessValidator = componentRight.getAccessValidator();

        if (accessValidator.getType() == "车牌摄像头") {
            ValidatorGroup validatorGroup = accessValidator.getValidatorGroup();
            return validatorGroup.getValidators();

        }


        throw new Exception("aaa");

    }


    private ComponentRightVounch 找到当前权益(Validator validator, String license) throws Exception {


        ValidatorGroup validatorGroup = validator.getValidatorGroup();

        AccessValidator accessValidator = validatorGroup.getAccessValidator();


        ComponentRight componentRight = accessValidator.getComponentRight();

        List<ComponentRightVounch> componentRightVounches = 当前权益名单(componentRight);

        return new ComponentRightVounch();


    }

    public ComponentRightVounch 进入(Validator validator, String license) throws Exception {

        ComponentRightVounch componentRightVounch = 找到当前权益(validator, license);
        ParkingStatus parkingStatus = new ParkingStatus();

        parkingStatus.setStatus("进入");
        parkingStatus.setComponentRightVounch(componentRightVounch);
        return new ComponentRightVounch();
    }

    public ComponentRightVounch 出去(Validator validator, String license) throws Exception {

        ComponentRightVounch componentRightVounch = 找到当前权益(validator, license);

        componentRightVounch.setCount(componentRightVounch.getCount()-1);
        componentRightVounch = componentRightVounchRepository.save(componentRightVounch);
        return componentRightVounch;
    }


}