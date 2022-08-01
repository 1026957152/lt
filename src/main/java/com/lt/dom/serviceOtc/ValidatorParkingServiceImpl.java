package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.oct.ValidatorParking.ParkingStatus;
import com.lt.dom.repository.ComponentVounchRepository;

import java.util.ArrayList;
import java.util.List;

public class ValidatorParkingServiceImpl {



    private ComponentVounchRepository componentVounchRepository;

    public List<ComponentVounch> 当前权益名单(ComponentRight componentRight) {


        Referral referral = new Referral();


        return new ArrayList<>();

    }

    public List<Validator> 当前停车设备(ComponentRight componentRight) throws Exception {

        AccessValidator accessValidator = null;//componentRight.getAccessValidator();
/*

        if (accessValidator.getExtend() == "车牌摄像头") {
            ValidatorGroup validatorGroup = accessValidator.getValidatorGroup();
            return validatorGroup.getValidators();

        }
*/


        throw new Exception("aaa");

    }


    private ComponentVounch 找到当前权益(Validator validator, String license) throws Exception {


        ValidatorGroup validatorGroup = validator.getValidatorGroup();

        AccessValidator accessValidator = validatorGroup.getAccessValidator();


        ComponentRight componentRight = accessValidator.getComponentRight();

        List<ComponentVounch> componentVounches = 当前权益名单(componentRight);

        return new ComponentVounch();


    }

    public ComponentVounch 进入(Validator validator, String license) throws Exception {

        ComponentVounch componentVounch = 找到当前权益(validator, license);
        ParkingStatus parkingStatus = new ParkingStatus();

        parkingStatus.setStatus("进入");
        parkingStatus.setComponentRightVounch(componentVounch);
        return new ComponentVounch();
    }

    public ComponentVounch 出去(Validator validator, String license) throws Exception {

        ComponentVounch componentVounch = 找到当前权益(validator, license);

        componentVounch.setCount(componentVounch.getCount()-1);
        componentVounch = componentVounchRepository.save(componentVounch);
        return componentVounch;
    }


}