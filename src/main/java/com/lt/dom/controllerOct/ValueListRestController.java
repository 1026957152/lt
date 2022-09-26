package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.OctResp.ValueListResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ValueListEditReq;
import com.lt.dom.otcReq.ValueListItemReq;
import com.lt.dom.otcReq.ValueListReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.serviceOtc.ValueListServiceImpl;
import com.lt.dom.state.ApplicationReviewEvents;
import com.lt.dom.state.ApplicationReviewStates;
import com.lt.dom.vo.VoucherPublicationPaymentVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ValueListRestController {


    @Autowired
    private ValueListServiceImpl valueListService;


    @Autowired
    private ValueListItemRepository valueListItemRepository;

    @Autowired
    private ValueListRepository valueListRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ComponentRightRepository componentRightRepository;
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private StateMachineFactory<ApplicationReviewStates, ApplicationReviewEvents> stateMachineFactory;


/*
    @Autowired
    private StateMachinePersister<ApplicationReviewStates, ApplicationReviewEvents, String > persister;
    @Autowired
    private StateMachineFactory<ApplicationReviewStates, ApplicationReviewEvents> stateMachineFactory;
*/



/*    private StateMachine<EmployeeState, EmployeeEvent> build(Long employeeId){
        Optional<Employee> byId = employeeRepository.findById(employeeId);
        Employee employee = byId.get();
        StateMachine<EmployeeState, EmployeeEvent> stateMachine = stateMachineFactory.getStateMachine(Long.toString(employee.getId()));
        stateMachine.stop();
        stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<>() {
                @Override
                public void preStateChange(State<EmployeeState, EmployeeEvent> state, Message<EmployeeEvent> message, Transition<EmployeeState, EmployeeEvent> transition, StateMachine<EmployeeState, EmployeeEvent> stateMachine, StateMachine<EmployeeState, EmployeeEvent> stateMachine1) {
                    Optional.ofNullable(message).ifPresent(msg -> {
                        Long employeeId = Long.class.cast(msg.getHeaders().getOrDefault("EMPLOYEE_ID", -1));
                        Employee employee = employeeRepository.getById(employeeId);
                        employee.setState(state.getId());
                        employeeRepository.save(employee);
                    });
                }
            });
            sma.resetStateMachine(new DefaultStateMachineContext<>(EmployeeState.valueOf(employee.getState().name()), null, null,  null));
        });
        stateMachine.start();
        return stateMachine;
    }*/

/*

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/value_lists",produces = "application/json")
    public PagedModel<ValueListResp> getValueListList(@PathVariable long VALUE_LIST_ID) {

        Optional<ValueList> optionalValueList = valueListRepository.findAll();
        if(optionalValueList.isEmpty()){
            throw new BookNotFoundException(VALUE_LIST_ID,"找不到 Value list");
        }
        ValueList valueList = optionalValueList.get();
        List<ValueListItem> valueListItems = valueListItemRepository.findAllByValueList(valueList.getId());

        ValueListResp valueListResp = ValueListResp.from(valueList,valueListItems);

        EntityModel<ValueListResp> entityModel = EntityModel.of(valueListResp);




        return entityModel;
    }

*/




    @GetMapping(value = "/value_lists/{VALUE_LIST_ID}",produces = "application/json")
    public EntityModel<ValueListResp> getValueList(@PathVariable long VALUE_LIST_ID) {

        String orderIdKey = Long.toString(VALUE_LIST_ID);
        StateMachine<ApplicationReviewStates, ApplicationReviewEvents> stateMachine = stateMachineFactory.getStateMachine(orderIdKey);

        stateMachine.startReactively();





       // stateMachineFactory.getStateMachine();
     //   StateMachine<ApplicationReviewStates, ApplicationReviewEvents> firstStateMachine = stateMachineFactory.getStateMachine();

        //  stateMachine.startReactively();

/*

        try {
            persister.persist(firstStateMachine,firstStateMachine.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
*/


        Optional<ValueList> optionalValueList = valueListRepository.findById(VALUE_LIST_ID);
        if(optionalValueList.isEmpty()){
            throw new BookNotFoundException(VALUE_LIST_ID,"找不到 Value list");
        }
        ValueList valueList = optionalValueList.get();
        List<ValueListItem> valueListItems = valueListItemRepository.findAllByValueList(valueList.getId());

        ValueListResp valueListResp = ValueListResp.from(valueList,valueListItems);

        EntityModel<ValueListResp> entityModel = EntityModel.of(valueListResp);




        return entityModel;
    }






    @GetMapping(value = "/value_lists/page_getValueListList",produces = "application/json")
    public EntityModel Page_getValueListList() {

        EntityModel entityModel = EntityModel.of(Map.of("value_list_type_list", Arrays.stream(EnumValueListType.values()).map(x->{

                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
        ));
        entityModel.add(linkTo(methodOn(ValueListRestController.class).createValueList(null)).withRel("createValueList"));

        return entityModel;
    }


    @GetMapping(value = "/value_lists",produces = "application/json")
    public PagedModel getValueListList(Pageable pageable, PagedResourcesAssembler<EntityModel<ValueListResp>> assembler) {

        Page<ValueList> valueLists = valueListRepository.findAll(pageable);


        List<ValueListItem> itemList = valueListItemRepository.findAllByValueListIn(valueLists.stream().map(x->x.getId()).collect(Collectors.toList()));
        Map<Long,List<ValueListItem>> stringListMap = itemList.stream().collect(Collectors.groupingBy(x->x.getValueList()));
        return assembler.toModel(valueLists.map(x->{



            EntityModel<ValueListResp> entityModel = EntityModel.of(ValueListResp.from(x,stringListMap.getOrDefault(x.getId(), Collections.EMPTY_LIST)));

            entityModel.add(linkTo(methodOn(ValueListRestController.class).getValueList(x.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(ValueListRestController.class).editValueList(x.getId(),null)).withRel("editValueList"));
            entityModel.add(linkTo(methodOn(ValueListRestController.class).Page_editValueList(x.getId())).withRel("Page_editValueList"));
            entityModel.add(linkTo(methodOn(ValueListRestController.class).delete(x.getId())).withRel("deleteValueList"));

            entityModel.add(linkTo(methodOn(ValueListRestController.class).createValueListItem(x.getId(),null)).withRel("addValueListItem"));

            return entityModel;
        }));


    }




/*
    @Autowired
    private StateMachine<ApplicationReviewStates, ApplicationReviewEvents> stateMachine;

*/






    @GetMapping(value = "/value_lists/getValueListByType/{type}",produces = "application/json")
    public CollectionModel Page_getValueListByType( @PathVariable EnumValueListType type) {




        List<ValueList> valueList = valueListRepository.findAllByType(type);

        if(valueList.isEmpty()){

            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(ValueList.class);
            //Resources<Object> resources = new Resources<>(Arrays.asList(wrapper));

            CollectionModel entityModel  = CollectionModel.of(Arrays.asList(wrapper));
            entityModel.add(linkTo(methodOn(ValueListRestController.class).Page_createValueList_getItems(type)).withSelfRel());

            return entityModel;
/*

            if (!page.isEmpty()) {
                return assembler.toModel(page);
            } else {
                // toEmptyModel renders the _embedded field (with an empty array inside)
                return (PagedModel<EntityModel<T>>) assembler.toEmptyModel(page, TenantSubscriptionResponseDto.class);
            }
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(ValueList.class);
            wrapper.add(linkTo(methodOn(ValueListRestController.class).Page_createValueList_getItems(type)).withSelfRel());
*/

        }else{
            CollectionModel entityModel  = CollectionModel.of(ValueList.List(valueList));

            entityModel.add(linkTo(methodOn(ValueListRestController.class).Page_createValueList_getItems(type)).withSelfRel());

            return entityModel;
        }







    }


    @GetMapping(value = "/value_lists/page_createValueList/{type}",produces = "application/json")
    public CollectionModel Page_createValueList_getItems( @PathVariable EnumValueListType type) {






        CollectionModel entityModel = null;

        if(type.equals(EnumValueListType.city_pass_right_recommendation)){


            List<ComponentRight> componentRightMap =componentRightRepository.findAll();

            entityModel = CollectionModel.of( ComponentRight.List(componentRightMap));
        }

        if(type.equals(EnumValueListType.Vendor_groups)){
            List<ValueList> valueList = valueListRepository.findAllByType(EnumValueListType.Vendor_groups);


            Set<Long> valueListItemList = valueListItemRepository
                    .findAllByValueListIn(valueList.stream().map(e->e.getId()).collect(Collectors.toList()))
                    .stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toSet());


            List<Supplier> suppliers = supplierRepository.findAll();
            List<EnumLongIdResp> sup_enumResps = suppliers.stream().map(supplier->{
                EnumLongIdResp enumResp_sup = new EnumLongIdResp();
                enumResp_sup.setId(supplier.getId());
                enumResp_sup.setText(supplier.getName()+"_"+supplier.getCode());
                enumResp_sup.setSelected(!valueListItemList.contains(enumResp_sup.getId()));
                return enumResp_sup;
            }).collect(Collectors.toList());

            entityModel = CollectionModel.of(sup_enumResps);
        }
        if(type.equals(EnumValueListType.High_Quality_Product_recommendation)){

            List<Product> productList = productRepository.findAll();

            if(productList.isEmpty()){
                EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
                EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(EnumLongIdResp.class);
                //Resources<Object> resources = new Resources<>(Arrays.asList(wrapper));

                entityModel  = CollectionModel.of(Arrays.asList(wrapper));
            }else{
                entityModel = CollectionModel.of(Product.List(productList));

            }

        }





        entityModel.add(linkTo(methodOn(ValueListRestController.class).Page_createValueList_getItems(type)).withSelfRel());

        return entityModel;
    }


    @GetMapping(value = "/value_lists/page_createValueList",produces = "application/json")
    public EntityModel Page_createValueList() {
     //   stateMachine.sendEvent(ApplicationReviewEvents.APPROVE)

/*        stateMachine.stopReactively();

        stateMachine.stop();
        stateMachine.sendEvent(ApplicationReviewEvents.REJECT);*/
      //  ApplicationReviewStates.REJECTED, stateMachine.getState().getId()

      //  ApplicationReviewStates.REJECTED = stateMachine.getState().getId();




        List<ValueList> valueList = valueListRepository.findAllByType(EnumValueListType.Vendor_groups);


        Set<Long> valueListItemList = valueListItemRepository
                .findAllByValueListIn(valueList.stream().map(e->e.getId()).collect(Collectors.toList()))
                .stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toSet());


        List<Supplier> suppliers = supplierRepository.findAll();
        List<EnumLongIdResp> sup_enumResps = suppliers.stream().map(supplier->{
            EnumLongIdResp enumResp_sup = new EnumLongIdResp();
            enumResp_sup.setId(supplier.getId());
            enumResp_sup.setText(supplier.getName()+"_"+supplier.getCode());
            enumResp_sup.setSelected(!valueListItemList.contains(enumResp_sup.getId()));
            return enumResp_sup;
        }).collect(Collectors.toList());

        List<ComponentRight> componentRightMap =componentRightRepository.findAll();


        EntityModel entityModel = EntityModel.of(Map.of(

                "value_list_type_list", Arrays.stream(EnumValueListType.values()).map(x->{

                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());

                    EntityModel entityModel_enum = EntityModel.of(enumResp);
                    entityModel_enum.add(linkTo(methodOn(ValueListRestController.class).Page_createValueList_getItems(x)).withRel("getItemList"));

                    return entityModel_enum;
                }).collect(Collectors.toList())
        ));

        entityModel.add(linkTo(methodOn(ValueListRestController.class).createValueList(null)).withRel("createValueList"));

        return entityModel;
    }



    @PostMapping(value = "/value_lists",produces = "application/json")
    public EntityModel<ValueList> createValueList(@RequestBody @Valid ValueListReq pojo) {

        ValueList user = valueListService.createValueListWithAdd(pojo);
        EntityModel<ValueList> entityModel = EntityModel.of(user);
        return entityModel;
    }














    @GetMapping(value = "/value_lists/{VALUE_LIST_ID}/Page_editValueList",produces = "application/json")
    public EntityModel Page_editValueList(@PathVariable long VALUE_LIST_ID) {

        Optional<ValueList> optionalValueList = valueListRepository.findById(VALUE_LIST_ID);
        if(optionalValueList.isEmpty()){
            throw new BookNotFoundException(VALUE_LIST_ID,"找不到 Value list");
        }
        ValueList valueList = optionalValueList.get();


        List<ValueListItem> valueListItems = valueListItemRepository.findAllByValueList(valueList.getId());

/*


        List<ValueList> valueList = valueListRepository.findAllByType(EnumValueListType.Vendor_groups);

*/
        Set<Long> valueListItemList = valueListItems.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toSet());

/*

       List<Supplier> supplierList =  supplierRepository.findAll();


      //  List<Supplier> suppliers = supplierRepository.findAllById(valueListItems.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toSet()));



        List<EnumLongIdResp> sup_enumResps = supplierList.stream().map(supplier->{
            EnumLongIdResp enumResp_sup = new EnumLongIdResp();
            enumResp_sup.setId(supplier.getId());
            enumResp_sup.setText(supplier.getName()+"_"+supplier.getCode());
            enumResp_sup.setSelected(valueListItemList.contains(enumResp_sup.getId()));
            return enumResp_sup;
        }).collect(Collectors.toList());

        List<Long> publicationList  = sup_enumResps.stream().filter(x->x.getSelected()).map(x->x.getId()).collect(Collectors.toList());
*/


/*
        List<Integer> publicationList = IntStream.range(0, sup_enumResps.size()).mapToObj(x->{
                    EnumLongIdResp voucher=     sup_enumResps.get(x);

                   if( voucher.getSelected())
                       return voucher.getId();
                    else
                        return -1;

        }).filter(x->x!=-1) .collect(Collectors.toList());
*/



        EntityModel entityModel = EntityModel.of(Map.of(

                "info", ValueListReq.from(valueList,valueListItems),
              //  "item_list",sup_enumResps,
                "item_selected_index", valueListItemList,
                "value_list_type_list", Arrays.asList(valueList.getType()).stream().map(x->{
                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());

                    EntityModel entityModel_enum = EntityModel.of(enumResp);
                    entityModel_enum.add(linkTo(methodOn(ValueListRestController.class).Page_createValueList_getItems(x)).withRel("getItemList"));


                    return entityModel_enum;
                }).collect(Collectors.toList())
        ));
        entityModel.add(linkTo(methodOn(ValueListRestController.class).editValueList(valueList.getId(),null)).withRel("editValueList"));

        return entityModel;
    }





    @PutMapping(value = "/value_lists/{VALUE_LIST_ID}",produces = "application/json")
    public EntityModel<ValueListResp> editValueList(@PathVariable long VALUE_LIST_ID,@RequestBody @Valid ValueListEditReq pojo) {


        Optional<ValueList> optionalValueList = valueListRepository.findById(VALUE_LIST_ID);
        if(optionalValueList.isEmpty()){
            throw new BookNotFoundException(VALUE_LIST_ID,"找不到 Value list");
        }
        ValueList valueList = optionalValueList.get();

        ValueList user = valueListService.edit(valueList,pojo);


        List<ValueListItem> valueListItems = valueListItemRepository.findAllByValueList(valueList.getId());

        ValueListResp valueListResp = ValueListResp.from(valueList,valueListItems);

        EntityModel<ValueListResp> entityModel = EntityModel.of(valueListResp);



        return entityModel;
    }














    @PostMapping(value = "/value_lists/{VALUE_LIST_ID}/value_list_items",produces = "application/json")
    public CollectionModel<ValueListItem> createValueListItem(@PathVariable long VALUE_LIST_ID,@RequestBody @Valid List<ValueListItemReq> pojo) {


        Optional<ValueList> optionalValueList = valueListRepository.findById(VALUE_LIST_ID);
        if(optionalValueList.isEmpty()){
            throw new BookNotFoundException(VALUE_LIST_ID,"找不到 Value list");
        }
        List<ValueListItem> user = valueListService.addValueListItem(optionalValueList.get(),pojo);
        CollectionModel<ValueListItem> entityModel = CollectionModel.of(user);
        return entityModel;
    }





    @DeleteMapping(value = "/value_lists/{VALUE_LIST_ID}", produces = "application/json")
    public ResponseEntity delete(@PathVariable long VALUE_LIST_ID) {
        Optional<ValueList> optionalValueList = valueListRepository.findById(VALUE_LIST_ID);
        if(optionalValueList.isEmpty()){
            throw new BookNotFoundException(VALUE_LIST_ID,"找不到 Value list");
        }
        ValueList valueList = optionalValueList.get();
        valueListRepository.delete(valueList);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

}