package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.ProductServiceImpl;
import com.lt.dom.serviceOtc.ValidateServiceImpl;
import com.lt.dom.vo.AvailabilityVO;
import com.lt.dom.vo.BookingRuleVO;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ProductRestController {


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
    private TempDocumentRepository tempDocumentRepository;
    @Autowired
    private FileStorageServiceImpl fileStorageService;



    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private AvailabilityServiceImpl availabilityService;


    @Autowired
    private PricingTypeRepository pricingTypeRepository;


    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ValidateServiceImpl validateService;
    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;


    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}/Page_editProduct", produces = "application/json")
    public EntityModel<ProductEditResp> Page_editProduct(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = validatorOptional.get();


        List<Component> componentRightPage= componentRepository.findAllByProductId(product.getId());


        ProductEditResp productResp = null;
        if(product.getType().equals(EnumProductType.Daytour)){

            Optional<Tour> tour = tourRepository.findByProduct(product.getId());

            List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByTourId(tour.get().getId());

            List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));
            productResp = ProductEditResp.dayTourFrom(product,tour.get(),campaigns);


        }else{
            productResp = ProductEditResp.from(product);
            if(product.getType().equals(EnumProductType.Attraction)){
                Optional<Attraction> attractionOptional = attractionRepository.findById(product.getTypeTo());


                if(attractionOptional.isPresent()){

                    productResp.setAttraction(attractionOptional.get());
                }


            }






            List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());



            PricingType pricingType = pricingTypes.get(0);
            productResp.setPriceType(PricingTypeResp.from(pricingType));

            productResp.setPriceTypes(pricingTypes.stream().map(e->{

                PricingTypeResp pricingTypeResp = PricingTypeResp.from(e);

                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

                entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));




        }
        Optional<Supplier> supplierOptional= supplierRepository.findById(product.getSupplierId());
        Supplier supplier = supplierOptional.get();


        List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());

        List<ComponentRight> componentRightList = componentRightRepository.findAllBySupplier(supplier.getId());

        List<RoyaltyRule> royaltyRuleList = royaltyRuleRepository.findAllByComponentRightIn(componentRightList.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<RoyaltyRule>> royaltyRuleListMap =  royaltyRuleList.stream().collect(Collectors.groupingBy(e->e.getComponentRight()));

        List<Attraction> attractionList = attractionRepository.findAllBySupplier(supplier.getId());






        productResp.setParameterList(
                Map.of(
                        "attraction_list", AttractionResp.EnumList(attractionList),
                        "range_type_list",EnumAvailabilityRangetype.from(),
                        "component_right_list",ComponentRight.List(componentRightList,supplier,royaltyRuleListMap),
                        "daytour_days_list", EnumDaytourDays.from(),
                        "product_type_list",EnumProductType.from(productService.getSupportProductType()),
                        "price_type_list", EnumProductPricingType.from(),
                        "payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance)))
        );


        ProductResp.Availability availability =availabilityService.form(bookingRuleList);
        EntityModel entityModel_a = EntityModel.of(availability);
        entityModel_a.add(linkTo(methodOn(ProductRestController.class).addBookingRule(product.getId(),null)).withRel("update"));
        productResp.setAvailabilityTab(entityModel_a);


        ProductResp.ComponentTap componentTap = ProductResp.ComponentTap.from(componentRightPage);
        EntityModel entityModel_ComponentTap = EntityModel.of(componentTap);
        entityModel_ComponentTap.add(linkTo(methodOn(ProductRestController.class).addComponentRight(product.getId(),null)).withRel("update"));
        productResp.setComponentTab(entityModel_ComponentTap);





        List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());



        PricingType pricingType = pricingTypes.get(0);
        productResp.setPriceType(PricingTypeResp.from(pricingType));

        productResp.setPriceTypes(pricingTypes.stream().map(e->{

            PricingTypeResp pricingTypeResp = PricingTypeResp.from(e);

            EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

            entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));

            return entityModel1;
        }).collect(Collectors.toList()));




        EntityModel entityModel = EntityModel.of(productResp);
        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct_componentRights(product.getId(),null,null)).withRel("getcomponentRightList"));

        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(product.getId())).withSelfRel());

        entityModel.add(linkTo(methodOn(ProductRestController.class).editProduct(product.getId(),null)).withRel("edit"));

        return entityModel;


    }




    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}", produces = "application/json")
    public EntityModel<ProductResp> getProduct(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = validatorOptional.get();


        List<Component> componentRightPage= componentRepository.findAllByProductId(product.getId());


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

                    productResp.setAttraction(attractionOptional.get());
                }


            }






            List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());



            PricingType pricingType = pricingTypes.get(0);
            productResp.setDefault_price(PricingTypeResp.from(pricingType));

            productResp.setPriceTypes(pricingTypes.stream().map(e->{

                PricingTypeResp pricingTypeResp = PricingTypeResp.from(e);

                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

                entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));




        }

       List<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId());




        ProductResp.Availability availability =availabilityService.form(bookingRuleList);

        availability.setParameter(Map.of("range_type_list",EnumAvailabilityRangetype.from()));

        EntityModel entityModel_a = EntityModel.of(availability);


        entityModel_a.add(linkTo(methodOn(ProductRestController.class).addBookingRule(product.getId(),null)).withRel("update"));

        productResp.setAvailability(entityModel_a);

        List<AvailabilityVO> availabilityVOList = availabilityService.getAvailability_(product,bookingRuleList, LocalDate.now().plusDays(10));

        productResp.setBookingAvailability(availabilityVOList);


        //   entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(product.getId(),null)).withRel("createBooking"));


        EntityModel entityModel = EntityModel.of(productResp);
        productResp.setComponents(componentRightPage.stream().map(e->{
            return ComponentResp.from(e);
        }).collect(Collectors.toList()));


        String note = "游客预约系统在线预约时需要提供出行人的身份证西南西，并需要在进馆时出具对应身份证件用于验证，请确保录入信息真实有效。一码君行将通过加密等方式保护您录入的身份证件信息。";

        productResp.setBooking_note(note);
        productResp.setTraveller_term(note);


        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct_componentRights(product.getId(),null,null)).withRel("getcomponentRightList"));

        entityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(product.getId())).withRel("Page_createBooking"));

        entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(product.getSupplierId())).withRel("getSupplier"));

        entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(product.getId())).withSelfRel());

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


       Page<Component> components= componentRepository.findAllByProductId(product.getId(),pageable);


        List<ComponentRight> componentRights= componentRightRepository.findAllById(components.stream().map(e->e.getProductId()).collect(Collectors.toList()));

        Map<Long,ComponentRight> componentRightMap = componentRights.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        return  assembler.toModel(components.map(x->{


            ComponentRight componentRight = componentRightMap.get(x.getComponentRightId());
            EntityModel entityModel= EntityModel.of(ComponentResp.from(x,componentRight));

         //   entityModel.add(linkTo(methodOn(ComponentRightRestController.class).delete(x.getId())).withRel("delete"));
            entityModel.add(linkTo(methodOn(ComponentRightRestController.class).getComponentRighte(x.getProductId())).withSelfRel());


            return entityModel;
        }));




    }




    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/products/page", produces = "application/json")
    public EntityModel Page_getProductList(@PathVariable long SUPPLIER_ID) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }
        Supplier supplier = supplierOptional.get();


        EntityModel entityModel = EntityModel.of(Map.of(

                "product_type_list", Arrays.stream(EnumProductType.values()).map(x->{


                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    EntityModel entityModel1 =EntityModel.of(enumResp);
                    if(x.equals(EnumProductType.Attraction)){
                        entityModel1.add(linkTo(methodOn(ProductRestController.class).Page_createProduct_By_Type(supplier.getId(),EnumProductType.Attraction)).withRel("Page_create"));
                    }
                    if(x.equals(EnumProductType.Pass)){
                        entityModel1.add(linkTo(methodOn(ProductRestController.class).Page_createProduct_By_Type(supplier.getId(),EnumProductType.Pass)).withRel("Page_create"));
                    }
                    if(x.equals(EnumProductType.Daytour)){
                        entityModel1.add(linkTo(methodOn(ProductRestController.class).Page_createProduct_By_Type(supplier.getId(),EnumProductType.Daytour)).withRel("Page_create"));
                    }
                    return entityModel1;
                }).collect(Collectors.toList()),



                "status_list", Arrays.stream(EnumProductStatus.values()).map(x->{



                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
        ));





        entityModel.add(linkTo(methodOn(ProductRestController.class).getProductList(supplier.getId(),null,null)).withRel("getProductList"));




        return entityModel;
    }



    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/products", produces = "application/json")
    public PagedModel getProductList(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<ProductResp>> assembler) {

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException("找不到供应商","找不到供应商");
        }



          return  assembler.toModel(productService.listProduct(validatorOptional.get(),pageable).map(x->{

              EntityModel entityModel= EntityModel.of(ProductResp.from(Pair.with(x,validatorOptional.get())));
              entityModel.add(linkTo(methodOn(ProductRestController.class).delete(x.getId())).withRel("delete"));
              entityModel.add(linkTo(methodOn(ProductRestController.class).Page_editProduct(x.getId())).withRel("Page_edit"));


              entityModel.add(linkTo(methodOn(ProductRestController.class).editProduct(x.getId(),null)).withRel("edit"));
              entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(x.getId())).withSelfRel());



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






    @GetMapping(value = "/supplier/{SUPPLIER_ID}/products/page_create_by_type/{type}", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createProduct_By_Type(@PathVariable long SUPPLIER_ID, @PathVariable EnumProductType type) {


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


        validateService.newValidator(type,componentRightList,attractionList);



        EnumResp enumResp = EnumResp.of(type,type.toString());

        EntityModel entityModel = EntityModel.of(Map.of(
                "type",enumResp,
                "attraction_list", AttractionResp.EnumList(attractionList),
                "range_type_list",EnumAvailabilityRangetype.from(),

                "component_right_list",ComponentRight.List(componentRightList,supplier,royaltyRuleListMap),
                "daytour_days_list", EnumDaytourDays.from(),
                "product_type_list",EnumProductType.from(productService.getSupportProductType()),
                "price_type_list", EnumProductPricingType.from(),
                "campaign_list", Campaign.EnumList(campaignList),
                "payment_method_list",   EnumPayChannel.from(Arrays.asList(EnumPayChannel.wx,EnumPayChannel.balance))));

        entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct_for_attraction(supplier.getId(),null)).withRel("create"));
        entityModel.add(linkTo(methodOn(ProductRestController.class).getComponentRightList(supplier.getId(),null,null)).withRel("getComponentRightList"));

        // 基本信息价格权益支付与退款订票规则


        return entityModel;
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

        entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct_for_attraction(supplier.getId(),null)).withRel("create"));
        entityModel.add(linkTo(methodOn(ProductRestController.class).getComponentRightList(supplier.getId(),null,null)).withRel("getComponentRightList"));

       // 基本信息价格权益支付与退款订票规则


        return entityModel;
    }






    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/supplier/{SUPPLIER_ID}/products", produces = "application/json")
    public ResponseEntity<ProductResp> createProduct(@PathVariable long SUPPLIER_ID,@RequestBody @Valid ProductPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }

        Supplier supplier = supplierOptional.get();


        if(pojo.getType().equals(EnumProductType.Daytour)){

            List<Campaign> campaignList = Arrays.asList();

            if(pojo.getDay_tour().getCampaigns().size()< 1){
                throw new BookNotFoundException(Enumfailures.resource_not_found,pojo.getDay_tour().getCampaigns()+ "必须包含活动");

            }

            campaignList = campaignRepository.findAllById(new HashSet(pojo.getDay_tour().getCampaigns()));
            if(campaignList.size() != pojo.getDay_tour().getCampaigns().size()){

                throw new BookNotFoundException("",pojo.getDay_tour().getCampaigns()+ "有不存在的 campaigns");
            }


            Pair<Product,Supplier> product=  productService.createProduct(supplier,pojo,campaignList);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getValue0().getId())
                    .toUri();
            return ResponseEntity.created(uri)
                    .body(ProductResp.from(product));
        }




 /*       List<Campaign> campaignList = Arrays.asList();
        if(pojo.getType().equals(EnumProductType.Daytour)){

        }*/




        if(pojo.getType().equals(EnumProductType.Attraction)){
            Optional<Attraction> attractionOptional = attractionRepository.findById(pojo.getAttraction().getId());
            if(attractionOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"找不到景区");
            }
        }






        if(pojo.getType().equals(EnumProductType.Pass)){

            List<ComponentRight> campaignList = componentRightRepository.findAllById(pojo.getPass().getRoyalties().stream().map(e->e.getComponent_right()).collect(Collectors.toList()));
            if(campaignList.size() != pojo.getPass().getRoyalties().size()){
                throw new BookNotFoundException("",pojo.getPass().getRoyalties().stream().map(e->e.getComponent_right()).collect(Collectors.toList())+ "有不存在的 campaigns");
            }

            Pair<Product,Supplier> product=  productService.createPass(supplier,pojo,campaignList);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getValue0().getId())
                    .toUri();
            return ResponseEntity.created(uri)
                    .body(ProductResp.from(product));


        }






        Pair<Product,Supplier> product=  productService.createProduct(supplier,pojo,Arrays.asList());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getValue0().getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }





    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/suppler/{SUPPLIER_ID}/products_atraction", produces = "application/json")
    public ResponseEntity<ProductResp> createProduct_for_attraction(@PathVariable long SUPPLIER_ID,@RequestBody @Valid ProductPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }

        Supplier supplier = validatorOptional.get();


        if(pojo.getType().equals(EnumProductType.Attraction)){
            Optional<Attraction> attractionOptional = attractionRepository.findById(pojo.getAttraction().getId());
            if(attractionOptional.isEmpty()){
                throw new BookNotFoundException(Enumfailures.not_found,"找不到景区");
            }
        }



        List<ComponentRight> componentRights = componentRightRepository.findAllById(pojo.getRoyalties().stream().map(e->e.getComponent_right()).collect(Collectors.toList()));

        if((componentRights.size() == 0)  || (componentRights.size() != pojo.getRoyalties().size())){
            throw new BookNotFoundException(Enumfailures.resource_not_found,pojo.getRoyalties().stream().map(e->e.getComponent_right()).collect(Collectors.toList())+ "找不到权益，或权益为空");
        }



        Pair<Product,Supplier> productSupplierPair=  productService.createProduct(supplier,pojo,Arrays.asList());
        Product product = productSupplierPair.getValue0();

        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(pojo.getImages().stream().map(e->e.getImage()).distinct().collect(Collectors.toList()));


        if(tempDocuments.size() != 0){
            fileStorageService.saveFromTempDocument(product.getCode(),EnumDocumentType.product_thumb,tempDocuments.get(0));

        }


        List<Document> documents = fileStorageService.saveFromTempDocumentCode(
                product.getCode(),tempDocuments.stream().map(e->{
                    return Pair.with(EnumDocumentType.product_photos,e);
                }).collect(Collectors.toList()));


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }




/*

    @Operation(summary = "1、查询Product对象列表")
    @GetMapping(value = "/products/{PRODUCT_ID}/page", produces = "application/json")
    public EntityModel Page_editProduct(@PathVariable long PRODUCT_ID) {
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();
        List<Campaign> campaigns = campaignRepository.findAll();
        List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByProduct(product.getId());

        List<Long> hasList = campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList());
        EntityModel entityModel = EntityModel.of(Map.of("product_type_list", Arrays.asList(EnumProductType.Daytour).stream().map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    enumResp.setSelected(product.getType().equals(x));
                    return enumResp;
                }).collect(Collectors.toList()),
                "product",ProductResp.from(product),
                "campaign_list", campaigns.stream().map(x->{
                    EnumLongIdResp enumResp = new EnumLongIdResp();
                    enumResp.setId(x.getId());
                    enumResp.setText(x.getName());
                    enumResp.setSelected(hasList.contains(x.getId()));
                    return enumResp;
                }).collect(Collectors.toList())
        ));

        entityModel.add(linkTo(methodOn(ProductRestController.class).editProduct(product.getId(),null)).withRel("editProduct"));
        return entityModel;
    }*/




    @PutMapping(value = "/products/{PRODUCT_ID}", produces = "application/json")
    public ResponseEntity<ProductResp> editProduct(@PathVariable long PRODUCT_ID,@RequestBody @Valid ProductEditPojo pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(PRODUCT_ID,Supplier.class.getSimpleName());
        }
        Product product = validatorOptional.get();

        List<Campaign> campaigns = campaignRepository.findAllById(new HashSet(pojo.getCampaigns()));
        if(campaigns.size() != pojo.getCampaigns().size()){

            throw new BookNotFoundException("",pojo.getCampaigns()+ "有不存在的 campaigns");
        }


        Optional<Supplier> optionalSupplier = supplierRepository.findById(product.getSupplierId());
        Supplier supplier = optionalSupplier.get();

        Pair<Product,Supplier> product_pair=  productService.editProduct(product,supplier,pojo,campaigns);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getValue0().getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(ProductResp.from(product));


    }
    @Operation(summary = "3、更改Product对象")
    @PutMapping(value = "/___suppler/{SUPPLIER_ID}/products/{PRODUCT_ID}", produces = "application/json")
    public ProductResp updateComponentRight(@PathVariable int SUPPLIER_ID,@PathVariable int PRODUCT_ID, Map metadata) {
        return null;
    }


    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/products/{PRODUCT_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long PRODUCT_ID) {

        productRepository.deleteById(PRODUCT_ID);

        return ResponseEntity.ok().build();
    }











    @GetMapping(value = "/products/{PRODUCT_ID}/booking-rules/page", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createBookingRule(@PathVariable long PRODUCT_ID) {


        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = validatorOptional.get();


        List<PricingType> pricingTypes = pricingTypeRepository.findByProductId(product.getId());




        Map map = Map.of(

                "range_type_list",EnumAvailabilityRangetype.from(),

                "price_type_list", pricingTypes.stream().map(e->{

                    EntityModel pricingTypeEntityModel = EntityModel.of(e);
                    //         pricingTypeEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(e.getId())).withRel("Page_createBooking"));


                    return pricingTypeEntityModel;

                }).collect(Collectors.toList())

                //  "status_list", EnumTourBookingStatus_.from()
        );


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(DocumentRestController.class).idScan(null)).withRel("idcardOcr"));

        entityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(product.getId(),null)).withRel("booking"));

        return entityModel;
    }


    @Operation(summary = "5、增加订票规则")
    @PostMapping(value = "/products/{PRODUCT_ID}/booking-rules", produces = "application/json")
    public List<BookingRule> addBookingRule(@PathVariable long PRODUCT_ID, @RequestBody List<BookingRulePojoFuck> pojo) {
        Optional<Product> productOptional = productRepository.findById(PRODUCT_ID);

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = productOptional.get();


        List<BookingRuleVO> bookingRuleVOList  = productService.addBookingRuleWithValidate(product,pojo);

        List<BookingRule> bookingRule  = productService.addBookingRule(product,bookingRuleVOList);


        return bookingRule;
    }














    public class addComponentRightPojo{
        private long componentRightId;
        private EnumProductComponentSource sourceType; // own, partner

        private Long supplier; //结算账号

        public Long getSupplier() {
            return supplier;
        }

        public void setSupplier(Long supplier) {
            this.supplier = supplier;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        private long recipient; //结算账号
        private Long quantity; //结算账号

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }

        public EnumProductComponentSource getSourceType() {
            return sourceType;
        }

        public void setSourceType(EnumProductComponentSource sourceType) {
            this.sourceType = sourceType;
        }

        private String note;

        public long getComponentRightId() {
            return componentRightId;
        }

        public void setComponentRightId(long componentRightId) {
            this.componentRightId = componentRightId;
        }


        List<RoyaltyRulePojo> royaltyRules;

        public List<RoyaltyRulePojo> getRoyaltyRules() {
            return royaltyRules;
        }

        public void setRoyaltyRules(List<RoyaltyRulePojo> royaltyRules) {
            this.royaltyRules = royaltyRules;
        }

        public long getRecipient() {
            return recipient;
        }

        public void setRecipient(long recipient) {
            this.recipient = recipient;
        }
    }






    @Operation(summary = "6、增加权益")
    @PostMapping(value = "/products/{PRODUCT_ID}/components", produces = "application/json")
    public Component addComponentRight(@PathVariable long PRODUCT_ID, addComponentRightPojo pojo) {
        Optional<Product> optionalProduct = productRepository.findById(PRODUCT_ID);
        if(optionalProduct.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Product product = optionalProduct.get();

        Component component  = productService.addComponent(product,pojo);

        return component;
    }







    @Operation(summary = "7、对产品权益增加分容规则")
    @PostMapping(value = "products/{PRODUCT_ID}/componet_rights/{COMPONENT_RIGHTS_ID}/royalty_rules", produces = "application/json")
    public RoyaltyRule addRoyaltyRuleToComponentRight(@PathVariable int PRODUCT_ID,@PathVariable int COMPONENT_RIGHTS_ID, RoyaltyRulePojo pojo) throws Exception {
        Optional<ComponentRight> optionalProduct = productService.getByComponentRightForProduct(PRODUCT_ID,COMPONENT_RIGHTS_ID);
        RoyaltyRule bookingRule  = productService.addRoyaltyRuleToComponentRight(optionalProduct.get(),pojo);
        return bookingRule;
    }




    @Operation(summary = "8、对产品增加问题")
    @PostMapping(value = "products/{PRODUCT_ID}/questions", produces = "application/json")
    public BookingQuestion addQuestion(@PathVariable int PRODUCT_ID, BookingQuestionPojo pojo) throws Exception {
        Optional<Product> optionalProduct = productService.getByProductId(PRODUCT_ID);
        BookingQuestion bookingRule  = productService.addBookingQuestion(optionalProduct.get(),pojo);
        return bookingRule;
    }

    @Operation(summary = "9、获取 Question 列表 GET")
    @GetMapping(value = "products/{PRODUCT_ID}/questions", produces = "application/json")
    public List<BookingQuestion> getQuestion(@PathVariable int PRODUCT_ID) throws Exception {
        Optional<Product> optionalProduct = productService.getByProductId(PRODUCT_ID);
        List<BookingQuestion> bookingQuestion  = productService.listQuestions(optionalProduct.get());
        return bookingQuestion;
    }




    @Operation(summary = "10、为产品添加评论")
    @PostMapping(value = "products/{PRODUCT_ID}/comments", produces = "application/json")
    public Comment addComments(@PathVariable int PRODUCT_ID, CommentPojo commentPojo) throws Exception {
        Optional<Product> optionalProduct = productService.getByProductId(PRODUCT_ID);
        Comment bookingQuestion  = productService.addComment(optionalProduct.get(),commentPojo);
        return bookingQuestion;
    }





    @Operation(summary = "10、为产品添加评论")
    @PostMapping(value = "products/{PRODUCT_ID}/campaigns", produces = "application/json")
    public Comment addCampaigns(@PathVariable long PRODUCT_ID, CommentPojo commentPojo)  {
        Optional<Product> optionalProduct = productRepository.findById(PRODUCT_ID);
        if(optionalProduct.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }
        Comment bookingQuestion  = productService.addComment(optionalProduct.get(),commentPojo);
        return bookingQuestion;
    }



}