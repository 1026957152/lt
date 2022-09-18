package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumFulfillmentType;
import com.lt.dom.otcenum.EnumValidateWay;
import com.lt.dom.otcenum.EnumValidationStatus;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComponentRightResolveServiceImpl {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PassServiceImpl passService;
    @Autowired
    private AssetServiceImpl assetService;

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ComponentRightServiceImpl componentRightService;

    public void resolve(Reservation reservation, EnumFulfillmentType followupPaid) {

        if(reservation.getSetValidate_way().equals(EnumValidateWay.none)){
            resolve_(reservation,followupPaid);
        }

        if(reservation.getSetValidate_way().equals(EnumValidateWay.offline_manual)){

        }
    }



    public void resolve_(Reservation reservation, EnumFulfillmentType followupPaid) {

        Optional<User> objectUser = userRepository.findById(reservation.getUser());


        switch (followupPaid){

            case Universal:{
                List<ComponentVounch> componentVounchList =componentRightService.createComponentVounch(reservation,objectUser.get());


                Map<Long,User> longUserMap = userRepository.findAllById(componentVounchList.stream().map(e->e.getUser()).collect(Collectors.toList()))
                        .stream().collect(Collectors.toMap(e->e.getId(),e->e));

               ;
                validatorService.push( componentVounchList.stream().map(e->{
                    User user = longUserMap.get(e.getUser());

                    return Pair.with(e,user);
                }).collect(Collectors.toList()));
            }
            break;
            case Create_pass:{
                List<ComponentVounch> componentVounchList = componentRightService.createComponentVounch(reservation,objectUser.get());

                Map<Long,User> longUserMap = userRepository.findAllById(componentVounchList.stream().map(e->e.getUser()).collect(Collectors.toList()))
                        .stream().collect(Collectors.toMap(e->e.getId(),e->e));

                validatorService.push( componentVounchList.stream().map(e->{
                    User user = longUserMap.get(e.getUser());

                    return Pair.with(e,user);
                }).collect(Collectors.toList()));

                Pass pass = passService.create_virtual(objectUser.get(),12);
                componentRightService.groupby(componentVounchList,pass);
                componentRightService.bulkUpdata(componentVounchList);
            }
            break;
            case Free_ticket:{
            }
        }
        assetService.newQr(reservation.getCode(),reservation.getId());


        reservation.setValidationStatus(EnumValidationStatus.ValidationSucceeded);

        bookingService.update(reservation);


    }


}
