package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.AvailabilityCalendarVO;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct/market")
public class MarketRestController {


    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

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
    @GetMapping(value = "/products/{PRODUCT_ID}", produces = "application/json")
    public EntityModel<ProductResp> getProduct(@PathVariable long PRODUCT_ID) {


        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo user = authenticationFacade.getUserVo(authentication);


        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = validatorOptional.get();


        List<Component> componentRightPage= componentRepository.findAllByProduct(product.getId());


        ProductResp productResp = null;
        if(product.getType().equals(EnumProductType.Daytour)){

            Optional<Tour> tour = tourRepository.findByProduct(product.getId());

            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByTourId(tour.get().getId());

            List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));
            productResp = ProductResp.dayTourFrom(product,tour.get(),campaigns);


        }else{
            productResp = ProductResp.from(product);
            if(product.getType().equals(EnumProductType.Attraction)){
               Optional<Attraction> attractionOptional = attractionRepository.findById(product.getTypeTo());


                if(attractionOptional.isPresent()){

                    productResp.setAttraction(AttractionResp.from(attractionOptional.get()));
                }


            }






            List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());



            PricingType pricingType = pricingTypes.get(0);
            productResp.setDefault_price(PricingTypeResp.from(pricingType));

            productResp.setPriceTypes(pricingTypes.stream().map(e->{

                PricingTypeResp pricingTypeResp = PricingTypeResp.from(e);

                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

             //   entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));




        }

       List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());




        ProductResp.AvailabilityTab availability =new ProductResp.AvailabilityTab();//availabilityService.form(bookingRuleList);

        availability.setParameter(Map.of("range_type_list",EnumAvailabilityRangetype.from()));

        EntityModel entityModel_a = EntityModel.of(availability);


        productResp.setAvailability(entityModel_a);

        List<AvailabilityCalendarVO> availabilityCalendarVOList = availabilityService.getAvailability_(product,bookingRuleList, LocalDate.now().plusDays(10));

        productResp.setBookingAvailability(availabilityCalendarVOList);


        //   entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(product.getId(),null)).withRel("createBooking"));


        EntityModel entityModel = EntityModel.of(productResp);
        productResp.setComponents(componentRightPage.stream().map(e->{
            return ComponentResp.from(e);
        }).collect(Collectors.toList()));


        String note = "游客预约系统在线预约时需要提供出行人的身份证西南西，并需要在进馆时出具对应身份证件用于验证，请确保录入信息真实有效。一码君行将通过加密等方式保护您录入的身份证件信息。";

        productResp.setBooking_note(note);
        productResp.setTraveller_term(note);



        List<RatePlan> ratePlanList = ratePlanRepository.findAllByProduct(product.getId());

        // productResp.withRateplans(ratePlanList);


        productResp.setRatePlans(ratePlanList.stream().map(e->{

            RatePlanResp resp = RatePlanResp.from(e);
            EntityModel entityModel_ = EntityModel.of(resp);
            entityModel_.add(linkTo(methodOn(SubscriptionRestController.class).createSubscription(e.getId(), null)).withRel("subscribe").expand());

            return entityModel_;
        }).collect(Collectors.toList()));


        entityModel.add(linkTo(methodOn(MarketRestController.class).getProduct_componentRights(product.getId(),null,null)).withRel("getcomponentRightList"));

        entityModel.add(linkTo(methodOn(MarketRestController.class).Page_createProduct(product.getId())).withRel("Page_subscribe"));

        entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(product.getSupplierId())).withRel("getSupplier"));

        entityModel.add(linkTo(methodOn(MarketRestController.class).getProduct(product.getId())).withSelfRel());

/*

        entityModel.add(linkTo(methodOn(SubscriptionRestController.class)
                .createSubscription(user.getSupplier().getId(),product.getId(),null)).withRel("subscribe"));
*/

        return entityModel;


    }




    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}/component_rights", produces = "application/json")
    public PagedModel<ComponentResp> getProduct_componentRights(@PathVariable long PRODUCT_ID,Pageable pageable,PagedResourcesAssembler<EntityModel<ComponentResp>> assembler) {

        Optional<Product> productOptional = productRepository.findById(PRODUCT_ID);

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
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
    @GetMapping(value = "/products/page", produces = "application/json")
    public EntityModel Page_getProductList() {


        EntityModel entityModel = EntityModel.of(Map.of(

                "product_type_list", Arrays.stream(EnumProductType.values()).map(x->{


                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    EntityModel entityModel1 =EntityModel.of(enumResp);
/*                    if(x.equals(EnumProductType.Attraction)){
                        entityModel1.add(linkTo(methodOn(MarketRestController.class).Page_createProduct_By_Type(supplier.getId(),EnumProductType.Attraction)).withRel("Page_create"));
                    }
                    if(x.equals(EnumProductType.Pass)){
                        entityModel1.add(linkTo(methodOn(MarketRestController.class).Page_createProduct_By_Type(supplier.getId(),EnumProductType.Pass)).withRel("Page_create"));
                    }
                    if(x.equals(EnumProductType.Daytour)){
                        entityModel1.add(linkTo(methodOn(MarketRestController.class).Page_createProduct_By_Type(supplier.getId(),EnumProductType.Daytour)).withRel("Page_create"));
                    }*/
                    return entityModel1;
                }).collect(Collectors.toList()),



                "status_list", Arrays.stream(EnumProductStatus.values()).map(x->{
                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
        ));





        entityModel.add(linkTo(methodOn(MarketRestController.class).getProductList(null,null)).withRel("getProductList"));




        return entityModel;
    }





    @GetMapping(value = "/Page_market", produces = "application/json")
    public EntityModel<Media> Page_market() {


        Map map = Map.of("status_list", EnumBulkIssuanceCardRequestStatus.values());

        //  Map map = Map.of("status_list", EnumBulkIssuanceCardRequestStatus.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(MarketRestController.class).getProductList(null,null)).withRel("getProductList"));


        return entityModel;

    }


    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products", produces = "application/json")
    public PagedModel getProductList( Pageable pageable, PagedResourcesAssembler<EntityModel<ProductResp>> assembler) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo user = authenticationFacade.getUserVo(authentication);


        Page<Product> productPage= productRepository.findAll(pageable);

        Map<Long,Supplier> suppliers = supplierRepository
                .findAllById(productPage.stream().map(e->e.getSupplierId()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

          return  assembler.toModel(productPage.map(x->{

              EntityModel entityModel= EntityModel.of(ProductResp.from(Pair.with(x,suppliers.get(x.getSupplierId()))));
        entityModel.add(linkTo(methodOn(MarketRestController.class).Page_createProduct(x.getId())).withRel("Page_subscible"));


              entityModel.add(linkTo(methodOn(MarketRestController.class).getProduct(x.getId())).withSelfRel());

     /*         entityModel.add(linkTo(methodOn(MarketRestController.class)
                      .createSubscription(user.getSupplier().getId(),x.getId(),null)).withRel("subscribe"));

*/
                      return entityModel;
                }));


    }










    @Operation(summary = "1、查询权益")
    @GetMapping(value = "/supliers/{SUPPLIER_ID}/component_rights_from_product", produces = "application/json")
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







    @GetMapping(value = "/supplier/{SUPPLIER_ID}/products/page_create", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createProduct(@PathVariable long SUPPLIER_ID) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }
        Supplier supplier = supplierOptional.get();

        List<Campaign> campaignList = campaignRepository.findAll();


        List<ComponentRight> componentRightList = componentRightRepository.findAllBySupplier(supplier.getId());


        List<Attraction> attractionList = attractionRepository.findAllBySupplier(supplier.getId());


        List<RoyaltyRule> royaltyRuleList = royaltyRuleRepository.findAllByComponentRightIn(componentRightList.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<RoyaltyRule>> royaltyRuleListMap =  royaltyRuleList.stream().collect(Collectors.groupingBy(e->e.getComponentRight()));

    //    validateService.newValidator(type, componentRightList,attractionList);




        EntityModel entityModel = EntityModel.of(Map.of(
                "attraction_list", AttractionResp.EnumList(attractionList),
                "range_type_list",EnumAvailabilityRangetype.from(),

                "component_right_list",ComponentRight.List(componentRightList,supplier,royaltyRuleListMap),
                "daytour_days_list", EnumDaytourDays.from(),
                "product_type_list",EnumProductType.from(productService.getSupportProductType()),
                "price_type_list", EnumProductPricingType.from(),
                "campaign_list", Campaign.EnumList(campaignList),
                "payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance))));

        entityModel.add(linkTo(methodOn(MarketRestController.class).getComponentRightList(supplier.getId(),null,null)).withRel("getComponentRightList"));

       // 基本信息价格权益支付与退款订票规则


        return entityModel;
    }












/*

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/products/{PRODUCT_ID}/subscriptions", produces = "application/json")
    public ResponseEntity<Subscription> createSubscription(@PathVariable long SUPPLIER_ID,
                                                           @PathVariable Long PRODUCT_ID,
                                                           @RequestBody SubscriptionResp subscriptionResp) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);


        if (optionalSupplier.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到哦");
        }

        Supplier supplier = optionalSupplier.get();

        Optional<Product> productOptional = productRepository.findById(PRODUCT_ID);
        Product product = productOptional.get();


        Optional<RatePlan> optionalRatePlan = ratePlanRepository.findById(subscriptionResp.getRatePlan());
        if(optionalRatePlan.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到哦价格计划");

        }
*/
/*        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(subscriptionResp.getComponentRightId());

        ComponentRight componentRight = optionalComponentRight.get();*//*

        RatePlan ratePlan = optionalRatePlan.get();


        Subscription subscription = subscriptionService.create(supplier, ratePlan,product,subscriptionResp);



        SupplierResp supplierResp = SupplierResp.from(supplier, Arrays.asList());
        supplierResp.add(linkTo(methodOn(SupplierRestController.class).createSupplier(null)).withSelfRel());
        supplierResp.add(linkTo(methodOn(SupplierRestController.class).updateSupplier(supplier.getId(),null)).withRel("update_url"));

        supplierResp.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("add_product_url"));
        supplierResp.add(linkTo(methodOn(FileUploadController.class).upload(null)).withRel("upload_file_url"));



        return ResponseEntity.ok(subscription);


    }
*/


}