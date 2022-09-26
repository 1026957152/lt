package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ClainRedeemAssignmentReq;
import com.lt.dom.otcReq.ValueListEditReq;
import com.lt.dom.otcReq.ValueListItemReq;
import com.lt.dom.otcReq.ValueListReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.RuleAssignmentRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.ValueListItemRepository;
import com.lt.dom.repository.ValueListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ValueListServiceImpl {

    @Autowired
    private ValueListItemRepository valueListItemRepository;

    @Autowired
    private ValueListRepository valueListRepository;

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private RuleAssignmentRepository ruleAssignmentRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    public ValueList createValueListWithAdd(ValueListReq pojo) {


        ValueList valueList =createValueList(pojo);

        List<ValueListItem> optionalValueListItem = valueListItemRepository.findAllByValueList(valueList.getId());

        if(optionalValueListItem.stream().filter(e->pojo.getItem_ids().contains(e.getValue())).findAny().isPresent()){
            throw new ExistException(Enumfailures.general_exists_error,"已经存在该对象了");
        }



        List<ValueListItem> valueListItem = addValueListItem(valueList, pojo.getItem_ids().stream().map(e->{

            ValueListItemReq valueListItemReq = new ValueListItemReq();
            valueListItemReq.setValue(e.toString());
            valueListItemReq.setValue_list(valueList.getId());
            return valueListItemReq;
        }).collect(Collectors.toList()));

        return valueList;
    }



    public ValueList createValueList(ValueListReq pojo) {
        ValueList valueList = new ValueList();
        valueList.setCode(idGenService.valueListCode());
        valueList.setCreated(LocalDateTime.now());
        valueList.setAlias(pojo.getAlias());
        valueList.setName(pojo.getName());

        valueList.setItem_type(pojo.getItem_type());
        valueList.setType(pojo.getType());
        if(pojo.getType().equals(EnumValueListType.Vendor_groups)){
            valueList.setItem_type(EnumValueListItemType.supplier_id);
            valueList.setLogical_type(EnumLogicalType.exclusion);
            valueList.setItem_value_type(pojo.getItem_value_type());
        }
        if(pojo.getType().equals(EnumValueListType.High_Quality_Product_recommendation)){
            valueList.setItem_type(EnumValueListItemType.product);
            valueList.setLogical_type(EnumLogicalType.inclusion);
            valueList.setItem_value_type(pojo.getItem_value_type());
        }

        valueListRepository.save(valueList);
        return valueList;
    }


    public List<ValueListItem> addValueListItem(ValueList valueList,List<ValueListItemReq> aa ) {

        List<ValueListItem>  valueListItems =  aa.stream().map(x->{
            ValueListItem valueListItem = new ValueListItem();
            valueListItem.setValueList(valueList.getId());
            valueListItem.setCreated(LocalDateTime.now());
            valueListItem.setMetadata(x.getMetadata());
         //   valueListItem.setValueList(x.getValue_list());
            valueListItem.setValue(x.getValue());
            return valueListItem;
        }).collect(Collectors.toList());

        valueListItems = valueListItemRepository.saveAll(valueListItems);
        return valueListItems;
    }

    public List<ValueListItem> addClainRedeem(Campaign campaign, ClainRedeemAssignmentReq clainQuotaReq) {


        Optional<Supplier>  supplier = supplierRepository.findById(Long.valueOf(clainQuotaReq.getValue()));

        if(supplier.isEmpty()){
            throw new BookNotFoundException("找不到公司","找不到公司");
        }

        Optional<RuleAssignment> ruleAssignmentOptional = ruleAssignmentRepository.findById(campaign.getId());


        ValueList valueList = null;
        if(ruleAssignmentOptional.isEmpty()){
            RuleAssignment ruleAssignment = new RuleAssignment();
            ruleAssignment.setCampaign(campaign.getId());
            ruleAssignment.setClain_redeem(1l);
            ruleAssignment = ruleAssignmentRepository.save(ruleAssignment);

            ValueListReq valueListReq = new ValueListReq();
            valueListReq.setAlias("");
            valueListReq.setItem_type(EnumValueListItemType.customer_id);
            valueListReq.setName("");
            valueList = createValueList(valueListReq);

        }else{
            RuleAssignment ruleAssignment = ruleAssignmentOptional.get();
            valueList = valueListRepository.findById(ruleAssignment.getClain_redeem()).get();
        }


        Optional<ValueListItem> optionalValueListItem = valueListItemRepository.findByValueListAndValue(valueList.getId(),clainQuotaReq.getValue());

        if(optionalValueListItem.isPresent()){
            throw new ExistException(Enumfailures.general_exists_error,"已经存在该对象了");
        }
        ValueListItemReq valueListItemReq = new ValueListItemReq();
        valueListItemReq.setValue(clainQuotaReq.getValue());
        valueListItemReq.setValue_list(valueList.getId());

        List<ValueListItem> valueListItem = addValueListItem(valueList, Arrays.asList(valueListItemReq));

        return valueListItem;

    }

    public ValueList edit(ValueList valueList, ValueListEditReq pojo) {

        List<ValueListItem> optionalValueListItem = valueListItemRepository.findAllByValueList(valueList.getId());

        if(optionalValueListItem.stream().filter(e->pojo.getItem_ids().contains(e.getValue())).findAny().isPresent()){
            throw new ExistException(Enumfailures.general_exists_error,"已经存在该对象了");
        }



        List<ValueListItem> valueListItem = addValueListItem(valueList, pojo.getItem_ids().stream().map(e->{

            ValueListItemReq valueListItemReq = new ValueListItemReq();
            valueListItemReq.setValue(e.toString());
            valueListItemReq.setValue_list(valueList.getId());
            return valueListItemReq;
        }).collect(Collectors.toList()));

        return valueList;

    }









    public void setupData(EnumValueListDefault enumValueListDefault) {


        ValueListReq valueListReq = new ValueListReq();

        valueListReq.setName(EnumValueListDefault.High_Quality_Product_recommendation.name());
        valueListReq.setAlias(EnumValueListDefault.High_Quality_Product_recommendation.name());
        valueListReq.setItem_type(EnumValueListItemType.customer_id);
        valueListReq.setType(EnumValueListType.High_Quality_Product_recommendation);
        valueListReq.setItem_value_type(EnumValueType.int_);
        ValueList valueList = createValueList(valueListReq);



        valueListReq = new ValueListReq();
        valueListReq.setName(EnumValueListDefault.city_pass_right_recommendation.name());
        valueListReq.setAlias(EnumValueListDefault.city_pass_right_recommendation.name());
        valueListReq.setItem_type(EnumValueListItemType.customer_id);
        valueListReq.setType(EnumValueListType.city_pass_right_recommendation);
        valueListReq.setItem_value_type(EnumValueType.int_);

        valueList = createValueList(valueListReq);



    }


    public List<ValueListItem> getByName(EnumValueListDefault high_quality_product_recommendation) {

        Optional<ValueList> valueListItem = valueListRepository.findByName(high_quality_product_recommendation.name());
      if(valueListItem.isPresent()){

return           valueListItemRepository.findAllByValueList(valueListItem.get().getId());

      }
      throw new BookNotFoundException(Enumfailures.not_found,"high_quality_product_recommendation"+high_quality_product_recommendation.name());
    }



    public List<ValueListItem> addItmeByListName(EnumValueListDefault high_quality_product_recommendation,ValueListItemReq valueListItemReq) {

        Optional<ValueList> valueListOptional = valueListRepository.findByName(high_quality_product_recommendation.name());
        if(valueListOptional.isEmpty()){
            throw  new BookNotFoundException(Enumfailures.not_found,"找不到组");

        }
        ValueList valueList = valueListOptional.get();

        return addValueListItem(valueList,Arrays.asList(valueListItemReq));

    }
}
