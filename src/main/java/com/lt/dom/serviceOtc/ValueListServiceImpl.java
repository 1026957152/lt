package com.lt.dom.serviceOtc;


import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import com.lt.dom.otcReq.ValueListItemReq;
import com.lt.dom.otcReq.ValueListReq;
import com.lt.dom.repository.ValueListItemRepository;
import com.lt.dom.repository.ValueListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValueListServiceImpl {

    @Autowired
    private ValueListItemRepository valueListItemRepository;

    @Autowired
    private ValueListRepository valueListRepository;

    @Autowired
    private IdGenServiceImpl idGenService;


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
            valueListItem.setValue_list(x.getValue_list());
            valueListItem.setValue(x.getValue());
            return valueListItem;
        }).collect(Collectors.toList());

        valueListItems = valueListItemRepository.saveAll(valueListItems);
        return valueListItems;
    }
}
