package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.CardholderResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PassCreatePojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassServiceImpl {
    @Autowired
    private AssetRepository assetRepository;


    @Autowired
    private PassRepository passRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private ComponentRightRepository componentRightRepository;



    @Autowired
    private ComponentRightServiceImpl componentRightService;


/*
    public  void setupData(User user) {

        Pass pass = new Pass();
        pass.setUser(user.getId());
        pass.setCode("code");
        passRepository.save(pass);
    }
*/


    public Pass create(User user, List<ComponentRight> componentRightList) {

        Optional<Pass> passOptional = passRepository.findByUser(user.getId());
        Pass pass = create_virtual(user,12);

        pass =  passOptional.get();

        return pass;
    }





    public Cardholder create(User user, CardholderResp cardholderResp) {

        Cardholder cardholder = new Cardholder();
        cardholder.setCreated_at(LocalDateTime.now());
        cardholder.setUser(user.getId());
        cardholder.setStatus(EnumCardholderStatus.DATE_ENQUIRY);
        cardholder.setName(cardholderResp.getName());
        cardholder.setPhoneNumber(cardholderResp.getPhoneNumber());
        cardholder.setType(cardholderResp.getType());
        return cardholder;
    }



    public Pass create_virtual(User user,Integer duration) {

        Optional<Pass> passOptional = passRepository.findByUser(user.getId());
        Pass pass = null;
        if(passOptional.isEmpty()){
            pass = new Pass();
            pass.setCode(idGenService.passCode());
            pass.setUser(user.getId());

            pass.setDuration(duration);
            pass.setType(EnumCardType.virtual);
            pass.setDurationUnit(EnumPassDorationUnit.months);
            pass.setDurationUnit(EnumPassDorationUnit.months);
            pass.setActive(false);
            pass.setStatus(EnumCardStatus.active);
            pass.setShipping_statis(EnumPassShippingStatus.delivered);

            if(pass.getDurationUnit().equals(EnumPassDorationUnit.months)){
                pass.setExpiringDate(LocalDateTime.now().plusMonths(pass.getDuration()));
            }
            if(pass.getDurationUnit().equals(EnumPassDorationUnit.days)){
                pass.setExpiringDate(LocalDateTime.now().plusDays(pass.getDuration()));
            }
            pass= passRepository.save(pass);
        }else{
            pass =  passOptional.get();
        }





        return pass;
    }

    public List<ComponentRight> fromValueList(List<Long> collect) {




        List<ComponentRight> componentRightList = componentRightRepository.findAllById(collect);
        return componentRightList;

    }
}
