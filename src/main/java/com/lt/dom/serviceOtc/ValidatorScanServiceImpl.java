package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.oct.ValidatorParking.ParkingStatus;
import com.lt.dom.oct.ValidatorParking.VounchLicense;
import com.lt.dom.repository.ComponentRightRepository;
import com.lt.dom.repository.ComponentRightVounchRepository;
import com.lt.dom.repository.ValidatorGroupRepository;
import com.lt.dom.repository.VounchLicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;


@Service
public class ValidatorScanServiceImpl {


    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private VounchLicenseRepository vounchLicenseRepository;


    @Autowired
    private ValidatorGroupRepository validatorGroupRepository;

    @Autowired
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

    public List<ComponentRight> 找出当前验证者管理的权益(Validator validator) throws Exception {


        List<ValidatorGroup> validatorGroups = validatorGroupRepository.findByValidatorId(validator.getId());

        List<ComponentRight> componentRights = componentRightRepository.findAllById(validatorGroups.stream().map(x->x.getId()).collect(Collectors.toList()));

        return componentRights;

    }



    private List<ComponentRightVounch> 找到当前权益(Validator validator, String license) throws Exception {


        VounchLicense vounchLicense = vounchLicenseRepository.findByLicense(license);

        ValidatorGroup validatorGroup = validator.getValidatorGroup();

        AccessValidator accessValidator = validatorGroup.getAccessValidator();


        ComponentRight componentRight = accessValidator.getComponentRight();

        List<ComponentRightVounch> componentRightVounches = componentRightVounchRepository.findByComponentRightId(componentRight.getId());

      //  List<ComponentRightVounch> componentRightVounches = componentRight.getComponentRightVounches();


        List<ComponentRightVounch> componentRightVounchList = componentRightVounchRepository.findByVoucherId(vounchLicense.getVounchId());
       // List<ComponentRightVounch> componentRightVounchList = voucher.getComponentRightVounchList();

        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("model", ignoreCase());

        ComponentRightVounch probe = new ComponentRightVounch();
        probe.setComponentRightId(componentRight.getId());
        probe.setVoucherId(vounchLicense.getVounchId());

        Example<ComponentRightVounch> example = Example.of(probe, modelMatcher);

        List<ComponentRightVounch> componentRightVounches1 = componentRightVounchRepository.findAll(example);



        return componentRightVounches1;


    }

    private List<ComponentRightVounch> 找到当前权益(Validator validator, Voucher voucher) throws Exception {


        ValidatorGroup validatorGroup = validator.getValidatorGroup();

        AccessValidator accessValidator = validatorGroup.getAccessValidator();


        ComponentRight componentRight = accessValidator.getComponentRight();

        List<ComponentRightVounch> componentRightVounches = componentRightVounchRepository.findByComponentRightId(componentRight.getId());

        //  List<ComponentRightVounch> componentRightVounches = componentRight.getComponentRightVounches();


        List<ComponentRightVounch> componentRightVounchList = componentRightVounchRepository.findByVoucherId(voucher.getId());

        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("model", ignoreCase());

        ComponentRightVounch probe = new ComponentRightVounch();
        probe.setComponentRightId(componentRight.getId());
        probe.setVoucherId(voucher.getId());

        Example<ComponentRightVounch> example = Example.of(probe, modelMatcher);

        List<ComponentRightVounch> componentRightVounches1 = componentRightVounchRepository.findAll(example);



        return componentRightVounches1;


    }



    public ComponentRightVounch 进入(Validator validator, String license) throws Exception {

        List<ComponentRightVounch> componentRightVounch = 找到当前权益(validator, license);
        ParkingStatus parkingStatus = new ParkingStatus();

        parkingStatus.setStatus("进入");
        parkingStatus.setComponentRightVounch(componentRightVounch.get(0));
        return new ComponentRightVounch();
    }

    public ComponentRightVounch 出去(Validator validator, String license) throws Exception {

        List<ComponentRightVounch> componentRightVounch = 找到当前权益(validator, license);

        componentRightVounch.get(0).setCount(componentRightVounch.get(0).getCount()-1);
        ComponentRightVounch componentRightVounch_ = componentRightVounchRepository.save(componentRightVounch.get(0));
        return componentRightVounch_;
    }


    public List<ComponentRightVounch> 当前核验者的权益券(Validator validator) {
        return null;
    }
}