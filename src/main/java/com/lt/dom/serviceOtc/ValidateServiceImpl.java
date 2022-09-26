package com.lt.dom.serviceOtc;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumComponentVoucherStatus;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumValidatorType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.vo.DeviceScanValidatorVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ValidateServiceImpl {

    @Autowired
    private WriteoffEquipServiceImpl writeoffEquipService;

    @Autowired
    private AccessValidatorRepository accessValidatorRepository;

    @Autowired
    private ComponentVounchValidatorRecordRepository componentVounchValidatorRecordRepository;


    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserRepository userRepository;


    //@Autowired
    private Queue queue;

    public void send(String order) {
        rabbitTemplate.convertAndSend(this.queue.getName(), order);
    }


    @Transactional
    public void newValidator(EnumProductType type, List<ComponentRight> componentRightList, List<Attraction> attractionList) {


        if(type.equals(EnumProductType.Attraction)){
            if(componentRightList.size() == 0 &&  attractionList.size() == 0){
                throw  new BookNotFoundException(Enumfailures.not_found,"找不到权益 和景区，请新建进去和 权益");
            }


            if(attractionList.size() == 0 ){
                throw  new BookNotFoundException(Enumfailures.not_found,"找不到景区，请新建景区");
            }
            if(componentRightList.size() == 0 ){
                throw  new BookNotFoundException(Enumfailures.not_found,"找不到权益 请新建权益");
            }

        }

        if(type.equals(EnumProductType.Pass)){

            if(componentRightList.size() == 0 ){
                throw  new BookNotFoundException(Enumfailures.not_found,"找不到权益 请新建权益");
            }

        }


    }




    public List<ComponentVounchValidatorRecord> push( List<Pair<ComponentVounch,User>> pairs) {

        List<Validator_> validator_list = validatorRepository.findAllByComponentRightIdIn(pairs.stream().map(e->e.getValue0().getComponentRight()).collect(Collectors.toList()));


        Map<Long,List<Validator_>> longListMap = validator_list.stream().collect(Collectors.groupingBy(e->e.getComponentRightId()));


        List<ComponentVounchValidatorRecord> componentVounchValidatorRecordList = pairs.stream().map(e->{

            ComponentVounch componentVounch = e.getValue0();
            User user = e.getValue1();

            List<Validator_> validator_s = longListMap.getOrDefault(componentVounch.getComponentRight(),Arrays.asList());


            ComponentVounchValidatorRecord componentVounchValidatorRecord = new ComponentVounchValidatorRecord();
            componentVounchValidatorRecord.setStatus(EnumComponentVoucherStatus.NotRedeemed);
            componentVounchValidatorRecord.setHolder_name(user.getRealName());
            componentVounchValidatorRecord.setHolder_id(user.getIdCard());


            validator_s.stream().forEach(x->{
                componentVounchValidatorRecord.setValidatorType(x.getType());

                if(x.getType().equals(EnumValidatorType.特定机器)){
                    componentVounchValidatorRecord.setDevice(x.getDevice());

                    //    value0.getUser();

                    //writeoffEquipService.mqtt(x.getName(),user.getPhone());
                };
                if(x.getType().equals(EnumValidatorType.一组机器)){
                    componentVounchValidatorRecord.setValidatorGroupId(x.getValidatorGroupId());

                    // value0.getUser();

                    // writeoffEquipService.mqtt(x.getName(),user.getPhone());
                };

                if(x.getType().equals(EnumValidatorType.角色)){
                    componentVounchValidatorRecord.setRole(x.getRole());

                    //  value0.
                    // writeoffEquipService.mqtt(x.getName(),user.getPhone());
                };


            });
            return componentVounchValidatorRecord;
        }).collect(Collectors.toList());


        return componentVounchValidatorRecordRepository.saveAll(componentVounchValidatorRecordList);
    }



    // 这张门票 只有一项权益， 只能玩一次。
    // TODO   _门票，消费券，权益，一卡通。
    public DeviceScanValidatorVo user扫文旅码(UserVo user,
                                          List<ComponentVounch> componentRights来自user) {


        List<Validator_> validator_s = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,user.getUser_id());

        if(validator_s.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"职工得 核销分配 对象 为空，请添加"+user.getPhone());
        }

        return 扫设备(user,validator_s,componentRights来自user);

    }


        // 这张门票 只有一项权益， 只能玩一次。
    // TODO   _门票，消费券，权益，一卡通。
    public DeviceScanValidatorVo 扫设备(UserVo uservO, List<Validator_> validator_s ,
                                     List<ComponentVounch> componentRights来自user) {


        User user =userRepository.findById(uservO.getUser_id()).get();
        List<Long> triplet来自设备 =
                validator_s.stream().map(e->e.getComponentRightId()).collect(Collectors.toList());

        List<ComponentVounch> componentVounchList = componentRights来自user.stream()  // TODO 找到了这里的 权限
                .filter(e->{

                 System.out.println("==========职工可用核销的 所有权益 "+triplet来自设备.toString());
                    System.out.println("==========用户有权益id"+e.getComponentRight());


                    return triplet来自设备.contains(e.getComponentRight());
                }).collect(Collectors.toList());


        if(componentVounchList.size() > 0){

            //  ComponentVounch pass = componentVounchList.get();

            componentRightService.redeem(componentVounchList.stream().map(e->{
                return Pair.with(user,e);
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
            throw new BookNotFoundException(Enumfailures.not_found,"该职工 与该 用户 无核销交际"+user.getPhone());

        }



    }
}