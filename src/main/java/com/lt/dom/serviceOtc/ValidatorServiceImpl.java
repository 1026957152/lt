package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.ValidatorEditResp;
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
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private ProductRepository productRepository;



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
    public List<Validator_> updateUserValidator(User device, List<Validator_> validatorOptional, ValidatorEditResp pojoList) {






/*
        validatorRepository.deleteAll(validatorOptional.stream()
                        .filter(e->!pojoList.getIds().contains(e.getId())).collect(Collectors.toList()));
*/

        List<Validator_> validator_list = validatorRepository.saveAll(validatorOptional.stream().map(e->{

            if(pojoList.getIds().contains(e.getId())){
                e.setActive(true);
            }else{
                e.setActive(false);
            }

//            e.setId(null);
            return e;
        }).collect(Collectors.toList()));

        return validator_list;
    }

    @Transactional
    public List<Validator_> newValidatorForDevice(Device device, List<ComponentRight> pojoList) {


        validatorRepository.deleteAllByDevice(device.getId());

        List<Validator_> validator_list = validatorRepository.saveAll(pojoList.stream().map(e->{
            Validator_ validator = new Validator_();
            validator.setDevice(device.getId());
            validator.setType(EnumValidatorType.特定机器);
            validator.setActive(false);
            validator.setComponentRightId(e.getId());
            return validator;
        }).collect(Collectors.toList()));

        return validator_list;
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
                    validator.setActive(false);
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
                    validator.setActive(false);
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

    public List<ComponentVounch> check(Long user_id, Long supplier, String code) {


        List<Validator_> validator_s = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,user_id);

        if(validator_s.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"职工得 核销分配 对象 为空，请添加"+user_id);
        }


        List<Long> triplet来自设备 = validator_s.stream().map(e->e.getComponentRightId()).collect(Collectors.toList());


        List<ComponentVounch> components = componentVounchRepository.findAllByReference(code);

        if(components.size() ==0){
            throw new BookNotFoundException(Enumfailures.not_found,"该券 无可核销得 权益"+code);
        }

        List<Product> productList = productRepository.findAllBySupplierId(supplier);
        List<Long> longList_公司允许的 = productList.stream().map(e->e.getId()).collect(Collectors.toList());



        List<ComponentVounch> componentVounchList = components.stream()  // TODO 找到了这里的 权限
                .filter(e->{

                    return switch (e.getType()){
                        case Burdle -> longList_公司允许的.contains(e.getOriginalProduct());

                        case Right -> triplet来自设备.contains(e.getComponentRight());

                        default -> false;
                    };




                }).collect(Collectors.toList());





        return componentVounchList;
    }



    public List<ComponentVounch> check(Long device_id,  String code) {


        List<Validator_> validator_s = validatorRepository.findAllByTypeAndDevice(EnumValidatorType.特定机器,device_id);

        if(validator_s.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"职工得 核销分配 对象 为空，请添加"+device_id);
        }


        List<Long> triplet来自设备 = validator_s.stream().map(e->e.getComponentRightId()).collect(Collectors.toList());


        List<ComponentVounch> components = componentVounchRepository.findAllByReference(code);

        if(components.size() ==0){
            throw new BookNotFoundException(Enumfailures.not_found,"该券 无可核销得 权益"+code);
        }



        List<ComponentVounch> componentVounchList = components.stream()  // TODO 找到了这里的 权限
                .filter(e->{

                    return switch (e.getType()){
                        case Burdle -> false;

                        case Right -> triplet来自设备.contains(e.getComponentRight());

                        default -> false;
                    };




                }).collect(Collectors.toList());





        return componentVounchList;
    }
}