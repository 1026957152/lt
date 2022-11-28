package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BillRunReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class BillRunRestController {


    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BillRunRepository billRunRepository;
    @Autowired
    private BillServiceImpl billService;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private CampaignAssignToTourProductRepository campaignAssignToTourProductRepository;

    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private AvailabilityServiceImpl availabilityService;


    @Autowired
    private PricingTypeRepository pricingTypeRepository;


    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @Autowired
    private RatePlanRepository ratePlanRepository;



    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;




    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/billruns/{BILLRUN_ID}", produces = "application/json")
    public EntityModel<ProductResp> getBillrun(@PathVariable long BILLRUN_ID) {


        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo user = authenticationFacade.getUserVo(authentication);


        Optional<BillRun> validatorOptional = billRunRepository.findById(BILLRUN_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(BILLRUN_ID,"找不到产品");
        }

        BillRun product = validatorOptional.get();


        EntityModel entityModel = EntityModel.of(product);


        entityModel.add(linkTo(methodOn(BillRunRestController.class).getProduct_componentRights(product.getId(),null,null)).withRel("getcomponentRightList"));

        entityModel.add(linkTo(methodOn(BillRunRestController.class).Page_createBillrun(product.getId())).withRel("Page_subscribe"));

        entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(product.getSupplier())).withRel("getSupplier"));

        entityModel.add(linkTo(methodOn(BillRunRestController.class).getBillrun(product.getId())).withSelfRel());


        entityModel.add(linkTo(methodOn(BillRunRestController.class)
                .createBillrun(user.getSupplier().getId(),null)).withRel("subscribe"));

        return entityModel;


    }




    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/billruns/{BILLRUN_ID}/component_rights", produces = "application/json")
    public PagedModel<ComponentResp> getProduct_componentRights(@PathVariable long BILLRUN_ID,
                                                                @PageableDefault(sort = {"createdDate",
                                                                        "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,


                                                                PagedResourcesAssembler<EntityModel<ComponentResp>> assembler) {

        Optional<Product> productOptional = productRepository.findById(BILLRUN_ID);

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(BILLRUN_ID,"找不到产品");
        }

        Product product = productOptional.get();


       Page<Component> components= componentRepository.findAllByProduct(product.getId(),pageable);


        List<ComponentRight> componentRights= componentRightRepository.findAllById(components.stream().map(e->e.getProduct()).collect(Collectors.toList()));

        Map<Long,ComponentRight> componentRightMap = componentRights.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        return  assembler.toModel(components.map(x->{


            ComponentRight componentRight = componentRightMap.get(x.getComponentRightId());
            EntityModel entityModel= EntityModel.of(ComponentResp.from(x,componentRight));

         //   entityModel.add(linkTo(methodOn(ComponentRightRestController.class).delete(x.getId())).withRel("delete"));
            entityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentRighte(x.getProduct())).withSelfRel());


            return entityModel;
        }));




    }




    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/billruns/page", produces = "application/json")
    public EntityModel Page_getBillrunList() {


        EntityModel entityModel = EntityModel.of(Map.of(

                "product_type_list", Arrays.stream(EnumProductType.values()).map(x->{


                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    EntityModel entityModel1 =EntityModel.of(enumResp);

                    return entityModel1;
                }).collect(Collectors.toList()),



                "status_list", Arrays.stream(EnumProductStatus.values()).map(x->{
                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
        ));





        entityModel.add(linkTo(methodOn(BillRunRestController.class).getProductList(null,null)).withRel("getProductList"));




        return entityModel;
    }



    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/billruns", produces = "application/json")
    public PagedModel getProductList( Pageable pageable, PagedResourcesAssembler<EntityModel<ProductResp>> assembler) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo user = authenticationFacade.getUserVo(authentication);


        Page<BillRun> productPage= billRunRepository.findAll(pageable);

        Map<Long,Supplier> suppliers = supplierRepository
                .findAllById(productPage.stream().map(e->e.getSupplier()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

          return  assembler.toModel(productPage.map(x->{

              EntityModel entityModel= EntityModel.of(x);
        entityModel.add(linkTo(methodOn(BillRunRestController.class).Page_createBillrun(x.getId())).withRel("Page_subscible"));


              entityModel.add(linkTo(methodOn(BillRunRestController.class).getBillrun(x.getId())).withSelfRel());

              entityModel.add(linkTo(methodOn(BillRunRestController.class)
                      .createBillrun(user.getSupplier().getId(),null)).withRel("subscribe"));


                      return entityModel;
                }));


    }










    @Operation(summary = "1、查询权益")
    @GetMapping(value = "/billruns/{SUPPLIER_ID}/component_rights_from_product", produces = "application/json")
    public PagedModel getComponentRightList(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<ComponentRight>> assembler) {



        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = optionalSupplier.get();


        Page<ComponentRight> componentRights = componentRightRepository.findAllBySupplier(supplier.getId(),pageable);



        return assembler.toModel(componentRights.map(e->{

         //   ComponentRightResp.ComponentRightOption enumResp = new ComponentRightResp.ComponentRightOption();

            EnumProductComponentSource origin = null;
            if(supplier.getId() == supplier.getId()){
                origin = EnumProductComponentSource.own;
            }else{
                origin = EnumProductComponentSource.partner;
            }


            ComponentRightResp componentRightResp = ComponentRightResp.from(e,EnumValidateWay.EnumList(),origin);


            EntityModel entityModel = EntityModel.of(componentRightResp);



            return entityModel;
        }));
    }







    @GetMapping(value = "/supplier/{SUPPLIER_ID}/billruns/page_create", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createBillrun(@PathVariable long SUPPLIER_ID) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }
        Supplier supplier = supplierOptional.get();



        List<ComponentRight> componentRightList = componentRightRepository.findAllBySupplier(supplier.getId());



        List<RoyaltyRule> royaltyRuleList = royaltyRuleRepository.findAllByComponentRightIn(componentRightList.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<RoyaltyRule>> royaltyRuleListMap =  royaltyRuleList.stream().collect(Collectors.groupingBy(e->e.getComponentRight()));

    //    validateService.newValidator(type, componentRightList,attractionList);




        EntityModel entityModel = EntityModel.of(Map.of(
                "range_type_list",EnumAvailabilityRangetype.from(),
                "type_list",EnumTypesOfBillRuns.EnumList(),


                "component_right_list",ComponentRight.List(componentRightList,supplier,royaltyRuleListMap),
                "daytour_days_list", EnumDaytourDays.from(),
                "product_type_list",EnumProductType.from(productService.getSupportProductType()),
                "price_type_list", EnumProductPricingType.from(),
                "payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance))));

        entityModel.add(linkTo(methodOn(BillRunRestController.class).getComponentRightList(supplier.getId(),null,null)).withRel("getComponentRightList"));



        return entityModel;
    }








    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/billruns", produces = "application/json")
    public ResponseEntity<Void> createBillrun(@PathVariable long SUPPLIER_ID,
                                                           @RequestBody BillRunReq billRunReq) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);


        if (optionalSupplier.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到哦");
        }

        Supplier supplier = optionalSupplier.get();




        BillRun subscription = billService.create(supplier,billRunReq);





        return ResponseEntity.ok().build();


    }


}