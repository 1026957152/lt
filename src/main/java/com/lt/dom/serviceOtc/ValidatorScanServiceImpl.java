package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.oct.ValidatorParking.ParkingStatus;
import com.lt.dom.oct.ValidatorParking.VounchLicense;
import com.lt.dom.otcenum.EnumValidatorRedemExtent;
import com.lt.dom.repository.*;
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
    private VoucherRepository voucherRepository;

    @Autowired
    private AccessValidatorRepository accessValidatorRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private VounchLicenseRepository vounchLicenseRepository;


    @Autowired
    private ValidatorGroupRepository validatorGroupRepository;

    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    public List<ComponentVounch> 当前权益名单(ComponentRight componentRight) {


        Referral referral = new Referral();


        return new ArrayList<>();

    }

/*    public List<Validator> 当前停车设备(ComponentRight componentRight) throws Exception {

        AccessValidator accessValidator = componentRight.getAccessValidators().;

        if (accessValidator.getExtend() == "车牌摄像头") {
            return accessValidator.getValidators();
        }

        throw new Exception("aaa");

    }*/

    public List<ComponentRight> 找出当前验证者管理的权益(Validator_ validator) throws Exception {


        List<AccessValidator> validatorGroups = accessValidatorRepository.findByValidatorId(validator.getId());

        List<ComponentRight> componentRights = componentRightRepository.findAllById(validatorGroups
                .stream()
                .filter(x->x.getExtend().equals(EnumValidatorRedemExtent.特定权益券))
                .map(x->x.getRalativeId()).collect(Collectors.toList()));

        return componentRights;

    }



    private List<ComponentVounch> 找到当前权益(Validator_ validator, String license) throws Exception {


        VounchLicense vounchLicense = vounchLicenseRepository.findByLicense(license);

        ValidatorGroup validatorGroup = validator.getValidatorGroup();

        AccessValidator accessValidator = validatorGroup.getAccessValidator();


        ComponentRight componentRight = accessValidator.getComponentRight();

        List<ComponentVounch> componentVounches = componentVounchRepository.findByComponentRight(componentRight.getId());

      //  List<ComponentRightVounch> componentRightVounches = componentRight.getComponentRightVounches();


        List<ComponentVounch> componentVounchList = componentVounchRepository.findByVoucherId(vounchLicense.getVounchId());
       // List<ComponentRightVounch> componentRightVounchList = voucher.getComponentRightVounchList();

        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("model", ignoreCase());

        ComponentVounch probe = new ComponentVounch();
        probe.setComponentRight(componentRight.getId());
        probe.setVoucherId(vounchLicense.getVounchId());

        Example<ComponentVounch> example = Example.of(probe, modelMatcher);

        List<ComponentVounch> componentVounches1 = componentVounchRepository.findAll(example);



        return componentVounches1;


    }

    private List<ComponentVounch> 找到当前权益(Validator_ validator, Voucher voucher) throws Exception {


        ValidatorGroup validatorGroup = validator.getValidatorGroup();

        AccessValidator accessValidator = validatorGroup.getAccessValidator();


        ComponentRight componentRight = accessValidator.getComponentRight();

        List<ComponentVounch> componentVounches = componentVounchRepository.findByComponentRight(componentRight.getId());

        //  List<ComponentRightVounch> componentRightVounches = componentRight.getComponentRightVounches();


        List<ComponentVounch> componentVounchList = componentVounchRepository.findByVoucherId(voucher.getId());

        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("model", ignoreCase());

        ComponentVounch probe = new ComponentVounch();
        probe.setComponentRight(componentRight.getId());
        probe.setVoucherId(voucher.getId());

        Example<ComponentVounch> example = Example.of(probe, modelMatcher);

        List<ComponentVounch> componentVounches1 = componentVounchRepository.findAll(example);

        return componentVounches1;


    }



    public ComponentVounch 进入(Validator_ validator, String license) throws Exception {

        List<ComponentVounch> componentVounches = 找到当前权益(validator, license);
        ParkingStatus parkingStatus = new ParkingStatus();

        parkingStatus.setStatus("进入");
        parkingStatus.setComponentRightVounch(componentVounches.get(0));
        return new ComponentVounch();
    }

    public ComponentVounch 出去(Validator_ validator, String license) throws Exception {

        List<ComponentVounch> componentVounches = 找到当前权益(validator, license);

        componentVounches.get(0).setLimit(componentVounches.get(0).getLimit()-1);
        ComponentVounch componentVounch_ = componentVounchRepository.save(componentVounches.get(0));
        return componentVounch_;
    }










    public List<ComponentVounch> 当前核验者的权益券(Validator_ validator) {

        AccessValidator probeAcc = new AccessValidator();
        probeAcc.setValidatorId(validator.getId());
        Example<AccessValidator> validatorExample = Example.of(probeAcc);

        List<AccessValidator> accessValidators = accessValidatorRepository.findAll(validatorExample);
        return  accessValidators
                .stream().filter(x->x.getExtend().equals(EnumValidatorRedemExtent.特定权益券)).map(x->{
                    ComponentVounch probe = new ComponentVounch();
                    probe.setComponentRight(x.getRalativeId());
                    Example<ComponentVounch> example = Example.of(probe);

                    return componentVounchRepository.findAll(example);
                }).flatMap(List::stream).collect(Collectors.toList());

    }
}