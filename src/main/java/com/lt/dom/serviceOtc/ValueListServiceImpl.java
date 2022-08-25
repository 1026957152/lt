package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ClainRedeemAssignmentReq;
import com.lt.dom.otcReq.ValueListItemReq;
import com.lt.dom.otcReq.ValueListReq;
import com.lt.dom.otcenum.EnumValueListItemType;
import com.lt.dom.otcenum.Enumfailures;
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



    public ValueList createValueList(ValueListReq pojo) {
        ValueList valueList = new ValueList();
        valueList.setCode(idGenService.valueListCode());
        valueList.setCreated(LocalDateTime.now());
        valueList.setAlias(pojo.getAlias());
        valueList.setName(pojo.getName());
        valueList.setItem_type(pojo.getItem_type());

        valueListRepository.save(valueList);
        return valueList;
    }


    public List<ValueListItem> addValueListItem(ValueList valueList,List<ValueListItemReq> aa ) {

        List<ValueListItem>  valueListItems =  aa.stream().map(x->{
            ValueListItem valueListItem = new ValueListItem();

            valueListItem.setCreated(LocalDateTime.now());
            valueListItem.setMetadata(x.getMetadata());
            valueListItem.setValueList(x.getValue_list());
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
}
