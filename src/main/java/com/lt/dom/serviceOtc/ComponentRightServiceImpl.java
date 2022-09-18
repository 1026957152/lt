package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AccessValidatorPojo;
import com.lt.dom.otcReq.ComponentRightPojo;
import com.lt.dom.otcReq.ProductPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComponentRightServiceImpl {
    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private AccessValidatorRepository accessValidatorRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentVounchRepository componentVounchRepository;
    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;


    @Autowired
    private ProductRepository productRepository;


    public ComponentRight createComponentRight(Supplier supplier, ComponentRightPojo pojo) {


        ComponentRight componentRight = new ComponentRight();

        componentRight.setSupplier(supplier.getId());
        componentRight.setCode(idGenService.componentRightCode());
        componentRight.setName(pojo.getName());
        componentRight.setLong_desc(pojo.getLong_desc());
        componentRight.setName_long(pojo.getName_long());
        componentRight.setPrivate_(pojo.getPrivate_());
        componentRight.setDuration(pojo.getDuration());
        componentRight.setStatus(EnumComponentStatus.Created);
        if(componentRight.getDuration().equals(EnumDuration.repeating)){
            componentRight.setQuantity(pojo.getQuantity());
        }
        if(componentRight.getDuration().equals(EnumDuration.once)){
            componentRight.setQuantity(1l);
        }
        if(componentRight.getDuration().equals(EnumDuration.forever)){
            componentRight.setQuantity(Long.MAX_VALUE);
        }
        componentRight = componentRightRepository.save(componentRight);


/*
        ComponentRight finalComponentRight = componentRight;
        List<AccessValidator> componentRights = pojo.getValidators().stream().map(x->{
            AccessValidator accessValidator = new AccessValidator();
            accessValidator.setValidatorId(x.getId());
            accessValidator.setExtend(EnumValidatorRedemExtent.特定权益券);
            accessValidator.setRalativeId(finalComponentRight.getId());
            return accessValidator;
        }).collect(Collectors.toList());

        accessValidatorRepository.saveAll(componentRights);
*/


        return componentRight;
    }

    public AccessValidator addAccessValidator(ComponentRight componentRight, AccessValidatorPojo pojo) {

        Optional<Validator_> validatorOptional = validatorRepository.findById(pojo.getValidatorId());




        AccessValidator probe = new AccessValidator();
        probe.setValidatorId(validatorOptional.get().getId());
        Example<AccessValidator> example = Example.of(probe);


        Optional<AccessValidator> optional = accessValidatorRepository.findOne(example);

        if(optional.isEmpty()){
            AccessValidator accessValidator = new AccessValidator();
            accessValidator.setValidatorId(validatorOptional.get().getId());
            accessValidator.setExtend(EnumValidatorRedemExtent.特定权益券);
            accessValidator.setRalativeId(componentRight.getId());
            accessValidator = accessValidatorRepository.save(accessValidator);
            return accessValidator;
        }

        throw new RuntimeException();
    }








    public List<ComponentVounch> createComponentVounch(Long id,List<Component> componentRightList, User user) {




        List<ComponentVounch> componentVounchList = componentRightList.stream().map(e->{
            ComponentVounch componentVounch = new ComponentVounch();
            componentVounch.setComponentRight(e.getComponentRightId());
            componentVounch.setComponent(e.getId());

            if(id != null){
                componentVounch.setReservation(id);
            }



            componentVounch.setCount(e.getUnit_off());
            componentVounch.setRedeemed_quantity(0l);
            componentVounch.setDuration(e.getDuration());
            componentVounch.setUser(user.getId());
            componentVounch.setSupplier(e.getSupplier());
            componentVounch.setStatus(EnumComponentVoucherStatus.NotRedeemed);
            return componentVounch;
        }).collect(Collectors.toList());



        componentVounchList = componentVounchRepository.saveAll(componentVounchList);


        return componentVounchList;
    }


    public List<ComponentVounch> createComponentVounch(Reservation reservation, User user) {


        Optional<Product> productOptional = productRepository.findById(reservation.getProductId());

        Product product = productOptional.get();

        product.getType().equals(EnumProductType.Attraction);

     //   Attraction attraction = attractionRepository.findBy()

        List<Component> componentRightList = componentRepository.findAllByProductId(reservation.getProductId());




        List<ComponentVounch> componentVounchList = createComponentVounch(reservation.getId(),componentRightList,user);

        return componentVounchList;
    }


    public void redeem(List<ComponentVounch> optionalPass) {

        List<ComponentVounch> componentVounchList = optionalPass.stream().map(e-> {
            if(e.getDuration().equals(EnumDuration.once)){
                e.setStatus(EnumComponentVoucherStatus.AlreadyRedeemed);

                e.setRedeemed_quantity(1l);
            }
            if(e.getDuration().equals(EnumDuration.repeating)){
                e.setRedeemed_quantity(e.getRedeemed_quantity()+1);
                if(e.getCount() == e.getRedeemed_quantity()){
                    e.setStatus(EnumComponentVoucherStatus.AlreadyRedeemed);
                }else{
                    e.setStatus(EnumComponentVoucherStatus.PartialyRedeemed);
                }

            }
            return e;

        }).collect(Collectors.toList());


        componentVounchRepository.saveAll(componentVounchList);
    }



    public void assingtoProduct(Product product ,List<ProductPojo.Royalty> royalties) {


        List<ComponentRight> componentRights = componentRightRepository.findAllById(royalties.stream().map(e->e.getComponent_right()).collect(Collectors.toList()));


        if((componentRights.size() == 0)  || (componentRights.size() != royalties.size())){
            throw new BookNotFoundException(Enumfailures.resource_not_found,royalties.stream().map(e->e.getComponent_right()).collect(Collectors.toList())+ "找不到权益，或权益为空");
        }



        Map<Long,ComponentRight> longStringMap = componentRights.stream().collect(Collectors.toMap(e->e.getId(), e->e));

        List<Component> list =  royalties.stream().map(royalty->{

            ComponentRight x = longStringMap.get(royalty.getComponent_right());

            Component component = new Component();
            component.setUnit_off(x.getQuantity());
            component.setDuration(x.getDuration());
            component.setComponentRight(x.getId());
            component.setProduct(product.getId());
            component.setSupplier(x.getSupplier());

            component.setValidate_way(royalty.getValidate_way());

            if(x.getSupplier().equals(product.getSupplierId())){
                component.setSource(EnumProductComponentSource.own);
            }else{
                component.setSource(EnumProductComponentSource.partner);
            }


            component.setRoyaltyPercent(royalty.getPercent());

            return component;
        }).collect(Collectors.toList());

        list = componentRepository.saveAll(list);


        royaltyRuleRepository.saveAll(list.stream().map(e->{
            RoyaltyRule royaltyRule = new RoyaltyRule();
            royaltyRule.setPercent(e.getRoyaltyPercent());
            royaltyRule.setComponentRight(e.getId());
            royaltyRule.setProductId(product.getId());
            royaltyRule.setSplitCode(product.getCode());
            return royaltyRule;
        }).collect(Collectors.toList()));

    }

    public  List<ComponentVounch> groupby(List<ComponentVounch> componentVounchList, Pass pass) {
         List<ComponentVounch> componentVounches = componentVounchList.stream().map(e->{
            e.setPass(pass.getId());
            return e;
        }).collect(Collectors.toList());
        return componentVounches;
    }

    public void bulkUpdata(List<ComponentVounch> componentVounchList) {
        componentVounchRepository.saveAll(componentVounchList);
    }
}
