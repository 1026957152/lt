package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumFulfillment_behavior;
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

    @Autowired
    private BookingProductRepository bookingProductRepository;




    public void resolve(Reservation reservation, EnumFulfillment_behavior followupPaid) {

      //  if(reservation.getSetValidate_way().equals(EnumValidateWay.none)){
            resolve_(reservation,followupPaid);
       // }

        if(reservation.getSetValidate_way().equals(EnumValidateWay.offline_manual)){

        }
    }



    public void resolve_(Reservation reservation, EnumFulfillment_behavior followupPaid) {


        System.out.println("-------------看看fulfillment ----------- 不知道是在哪里啊啊啊啊 "+followupPaid);

        Optional<User> objectUser = userRepository.findById(reservation.getUser());


        switch (followupPaid){

            case Universal:{

                System.out.println("-------------看看fulfillment --看看一般的完工啊啊啊 "+followupPaid);





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

                System.out.println("-------------看看fulfillment --新建 主人卡的完工 "+followupPaid);

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




    }
    public void resolve_with_manual_validate(Reservation reservation, EnumFulfillment_behavior followupPaid) {

        resolve_(reservation,followupPaid);

        reservation.setValidationStatus(EnumValidationStatus.ValidationSucceeded);

        bookingService.update(reservation);

    }

}
