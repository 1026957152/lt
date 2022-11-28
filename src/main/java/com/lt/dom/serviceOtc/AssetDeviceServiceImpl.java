package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumValidatorType;
import com.lt.dom.repository.ComponentVounchRepository;
import com.lt.dom.vo.DeviceScanValidatorVo;
import com.lt.dom.vo.ValidatedByTypeVo;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetDeviceServiceImpl {


    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private ComponentRightServiceImpl componentRightService;


/*
    public Pair<Supplier,User,ParkingLot> 通过设备获得管理者(Device departures) {


        return null;
    }
    public Pair<Supplier,ParkingLot> 通过设备获得管理者(Device departures) {


        return null;
    }*/

    public Triplet<Supplier,TouristAttraction,ParkingLot> 通过设备获得管理者(Device departures) {


        return null;
    }


    //    _门票，消费券，权益，一卡通。
    public List<ComponentVounch> 通过设备获得管理者(User user) {

        List<ComponentVounch> componentVounchList = componentVounchRepository.findAllByUser(user.getId());
        return componentVounchList;
    }





    // 这张门票 只有一项权益， 只能玩一次。
    // TODO   _门票，消费券，权益，一卡通。
    public DeviceScanValidatorVo 扫设备(User user, List<Long> triplet来自设备,
                                     List<ComponentVounch> componentRights来自user) {






   //     Supplier supplier = triplet来自设备.getValue0();


       // List<ComponentRight> passes = listListListTriplet.getValue0();


        List<ComponentVounch> componentVounchList = componentRights来自user.stream()  // TODO 找到了这里的 权限
                .filter(e->triplet来自设备.contains(e.getComponentRight())).collect(Collectors.toList());


        if(componentVounchList.size() > 0){

          //  ComponentVounch pass = componentVounchList.get();

            ValidatedByTypeVo vo = new ValidatedByTypeVo();
            vo.setValidatorType(EnumValidatorType.特定机器);

            componentRightService.redeem(componentVounchList.stream().map(e->{
                return Triplet.with(vo,user,e);
            }).collect(Collectors.toList()));

/*          if(pass.getType() == 一卡通){

            }

            if(pass.getType() == 一张门票){

            }*/
            DeviceScanValidatorVo deviceScanValidatorVo = new DeviceScanValidatorVo();
            deviceScanValidatorVo.setPass(true);
/*          deviceScanValidatorVo.setRequire_confirm(false);
            deviceScanValidatorVo.setType("成人票");
            deviceScanValidatorVo.setSubType("儿童票");*/
            return deviceScanValidatorVo;
            //todO 找到了 一卡通
        }else{

            DeviceScanValidatorVo deviceScanValidatorVo = new DeviceScanValidatorVo();
            deviceScanValidatorVo.setPass(false);
            return deviceScanValidatorVo;

        }



    }





    public List<Calendar> batch_assets_authorized(Product product) {

        List<BookingRule> bookingRules =null;// bookingRuleRepository.findByProductId(product.getId());
        BookingRule bookingRule = bookingRules.get(0);
        List<LocalDate> localDates =null;// getDatesBetweenUsingJava9(bookingRule.getBookings_from(),bookingRule.getBookings_to());


        return null;
    }
}
