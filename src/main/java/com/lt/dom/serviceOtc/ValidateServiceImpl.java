package com.lt.dom.serviceOtc;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumComponentVoucherStatus;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumValidatorType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
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
    private DeviceRepository deviceRepository;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
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

}