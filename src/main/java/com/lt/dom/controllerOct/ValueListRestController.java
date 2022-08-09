package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.EmployeeResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.User;
import com.lt.dom.oct.ValueList;
import com.lt.dom.oct.ValueListItem;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.otcReq.ValueListItemReq;
import com.lt.dom.otcReq.ValueListReq;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.ValueListItemRepository;
import com.lt.dom.repository.ValueListRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.ValueListServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
    public PagedModel<ValueList> listValueList(Pageable pageable, PagedResourcesAssembler<EntityModel<ValueList>> assembler) {

        Page<ValueList> user = valueListRepository.findAll(pageable);

        return assembler.toModel(user.map(x->{
            return ValueListResp.from(user);
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