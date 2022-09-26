package com.lt.dom.serviceOtc;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ComponentRightValidatorUpdatePojo;
import com.lt.dom.otcenum.EnumComponentVoucherStatus;
import com.lt.dom.otcenum.EnumValidatorType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ValidatorServiceImpl {

    @Autowired
    private WriteoffEquipServiceImpl writeoffEquipService;

    @Autowired
    private AccessValidatorRepository accessValidatorRepository;

    @Autowired
    private ComponentVounchValidatorRecordRepository componentVounchValidatorRecordRepository;


    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //@Autowired
    private Queue queue;

    public void send(String order) {
        rabbitTemplate.convertAndSend(this.queue.getName(), order);
    }


    @Transactional
    public List<Validator_> newValidator(ComponentRight componentRight, List<ComponentRightValidatorUpdatePojo> pojoList) {


        validatorRepository.deleteAllByComponentRightId(componentRight.getId());

        List<Validator_> validator_list_result = pojoList.stream().map(pojo ->{

            List<Validator_> validator_list = new ArrayList<>();
            if(!Arrays.asList(EnumValidatorType.特定的人员,EnumValidatorType.特定机器).contains(pojo.getType())){
                throw new BookNotFoundException(Enumfailures.not_found,"找不到 核验类型");
            }


            if(pojo.getType().equals(EnumValidatorType.特定机器)){

                List<Device> equipmentOptioanl = deviceRepository.findAllById(pojo.getItem_ids());

                if(equipmentOptioanl.isEmpty()){
                    throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
                }



                validator_list = validatorRepository.saveAll(equipmentOptioanl.stream().map(e->{
                    Validator_ validator = new Validator_();
                    validator.setDevice(e.getId());
                    validator.setType(pojo.getType());
                    validator.setComponentRightId(componentRight.getId());
                    return validator;
                }).collect(Collectors.toList()));
            }







            if(pojo.getType().equals(EnumValidatorType.特定的人员)){
                List<User> equipmentOptioanl = userRepository.findAllById(pojo.getItem_ids());

                if(equipmentOptioanl.isEmpty()){
                    throw new BookNotFoundException(Enumfailures.not_found,"找不到人员");
                }



                validator_list = validatorRepository.saveAll(equipmentOptioanl.stream().map(e->{
                    Validator_ validator = new Validator_();
                    validator.setUser(e.getId());
                    validator.setType(pojo.getType());
                    validator.setComponentRightId(componentRight.getId());
                    return validator;
                }).collect(Collectors.toList()));

            }





/*        Validator_ finalValidator = validator;
        List<AccessValidator> accessValidators = pojo.getExtents().stream().map(x->{
            AccessValidator accessValidator = new AccessValidator();
            accessValidator.setExtend(x);
            accessValidator.setValidatorId(finalValidator.getId());
            return accessValidator;
        }).collect(Collectors.toList());


        accessValidatorRepository.saveAll(accessValidators);*/


            return validator_list;
        }).flatMap(List::stream).collect(Collectors.toList());

        return validator_list_result;
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

}