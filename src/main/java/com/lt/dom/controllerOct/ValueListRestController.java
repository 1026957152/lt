package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ValueListResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import com.lt.dom.otcReq.ValueListItemReq;
import com.lt.dom.otcReq.ValueListReq;
import com.lt.dom.repository.ValueListItemRepository;
import com.lt.dom.repository.ValueListRepository;
import com.lt.dom.serviceOtc.ValueListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oct")
public class ValueListRestController {


    @Autowired
    private ValueListServiceImpl valueListService;


    @Autowired
    private ValueListItemRepository valueListItemRepository;

    @Autowired
    private ValueListRepository valueListRepository;

    @GetMapping(value = "/value_lists",produces = "application/json")
    public PagedModel listValueList(Pageable pageable, PagedResourcesAssembler<EntityModel<ValueListResp>> assembler) {

        Page<ValueList> user = valueListRepository.findAll(pageable);


        List<ValueListItem> itemList = valueListItemRepository.findAllByValueListIn(user.stream().map(x->x.getId()).collect(Collectors.toList()));
        Map<Long,List<ValueListItem>> stringListMap = itemList.stream().collect(Collectors.groupingBy(x->x.getValueList()));
        return assembler.toModel(user.map(x->{
            return ValueListResp.from(x,stringListMap.getOrDefault(x.getId(), Collections.EMPTY_LIST));
        }));


    }


    @PostMapping(value = "/value_lists",produces = "application/json")
    public EntityModel<ValueList> createUser(@RequestBody @Valid ValueListReq pojo) {

        ValueList user = valueListService.createValueList(pojo);
        EntityModel<ValueList> entityModel = EntityModel.of(user);
        return entityModel;
    }

    @PostMapping(value = "/value_lists/{VALUE_LIST_ID}/value_list_items",produces = "application/json")
    public CollectionModel<ValueListItem> createUser(@PathVariable long VALUE_LIST_ID,@RequestBody @Valid List<ValueListItemReq> pojo) {


        Optional<ValueList> optionalValueList = valueListRepository.findById(VALUE_LIST_ID);
        if(optionalValueList.isEmpty()){
            throw new BookNotFoundException(VALUE_LIST_ID,"找不到 Value list");
        }
        List<ValueListItem> user = valueListService.addValueListItem(optionalValueList.get(),pojo);
        CollectionModel<ValueListItem> entityModel = CollectionModel.of(user);
        return entityModel;
    }



}