package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.RatePlanReq;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.SubscriptionReq;
import com.lt.dom.OctResp.SubscriptionResp;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.SubscriptionServiceImpl;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class SubscriptionRestController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @Autowired
    private PartnerRepository partnerRepository;


    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private RatePlanRepository ratePlanRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping(value = "/subscriptions/{SUBSCRIPTION_ID}", produces = "application/json")
    public EntityModel getOne(@PathVariable long SUBSCRIPTION_ID) {

        Optional<Subscription> validatorOptional = subscriptionRepository.findById(SUBSCRIPTION_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");
        }
        Subscription subscription = validatorOptional.get();
        SubscriptionResp resp = SubscriptionResp.from(subscription);

        Optional<RatePlan> ratePlanList = ratePlanRepository.findById(subscription.getRatePlan());

        if(ratePlanList.isEmpty()){
            SubscriptionResp.withRateplans(resp,Arrays.asList());
        }else{
            SubscriptionResp.withRateplans(resp,Arrays.asList(ratePlanList.get()));
        }

        EntityModel entityModel = EntityModel.of(resp);
        entityModel.add(linkTo(methodOn(SubscriptionRestController.class).listUsages(subscription.getId(),null,null)).withRel("listUsage"));

        return entityModel;

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/invoices/Page_listSubscription", produces = "application/json")
    public EntityModel<Media> Page_listSubscription(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of();

        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(SubscriptionRestController.class).listSubscription(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/subscriptions", produces = "application/json")
    public PagedModel listSubscription(@PathVariable long SUPPLIER_ID,Pageable pageable, PagedResourcesAssembler<EntityModel<Subscription>> assembler) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Page<Subscription> validatorOptional = subscriptionRepository.findAll(pageable);


        return assembler.toModel(validatorOptional.map(e->{

            SubscriptionResp subscriptionResp = SubscriptionResp.from(e);

            EntityModel entityModel = EntityModel.of(subscriptionResp);
            entityModel.add(linkTo(methodOn(SubscriptionRestController.class).getOne(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(SubscriptionRestController.class).listUsages(e.getId(),null,null)).withRel("listUsage"));
            return entityModel;
        }));

    }


    @GetMapping(value = "/subscriptions/{PRODUCT_ID}/booking-rules", produces = "application/json")
    public Page<BookingRule> getbookingRuleList(@PathVariable long PRODUCT_ID, Pageable pageable) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");
        }
        Product product = validatorOptional.get();
        Page<BookingRule> bookingRuleList = bookingRuleRepository.findByProduct(product.getId(),pageable);
        return bookingRuleList;

    }



/*

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/subscriptions", produces = "application/json")
    public ResponseEntity<Subscription> createSubscription(@PathVariable long SUPPLIER_ID,@RequestBody SubscriptionResp subscriptionResp) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);


        if (optionalSupplier.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到哦");
        }

        Supplier supplier = optionalSupplier.get();



            Subscription subscription = subscriptionService.create(supplier, subscriptionResp);



            SupplierResp supplierResp = SupplierResp.from(supplier, Arrays.asList());
            supplierResp.add(linkTo(methodOn(SupplierRestController.class).createSupplier(null)).withSelfRel());
            supplierResp.add(linkTo(methodOn(SupplierRestController.class).updateSupplier(supplier.getId(),null)).withRel("update_url"));

            supplierResp.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("add_product_url"));
            supplierResp.add(linkTo(methodOn(FileUploadController.class).upload(null)).withRel("upload_file_url"));

            return ResponseEntity.ok(subscription);


    }



*/




    @PostMapping(value = "/rate_plans/{RATE_PLAN_ID}/subscriptions", produces = "application/json")
    public ResponseEntity<EntityModel> createSubscription(@PathVariable long RATE_PLAN_ID,
                                                           @RequestBody @Valid SubscriptionReq subscriptionResp) {
        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        Supplier supplier = userVo.getSupplier();

        Optional<RatePlan> optionalRatePlan = ratePlanRepository.findById(RATE_PLAN_ID);


        if (optionalRatePlan.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到哦");
        }

        RatePlan ratePlan = optionalRatePlan.get();
        //RateP//lan ratePlan = optionalSupplier.get();

        Optional<Product> productOptional = productRepository.findById(ratePlan.getProduct());

      //  Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(subscriptionResp.getComponentRightId());

      //  ComponentRight componentRight = optionalComponentRight.get();



        Subscription subscription = subscriptionService.create(supplier, ratePlan,productOptional.get(),subscriptionResp);



       // SubscriptionResp supplierResp = SubscriptionResp.from(subscription);
        EntityModel entityModel = EntityModel.of(subscription);
      //  entityModel.add(linkTo(methodOn(SupplierRestController.class).createSupplier(null)).withSelfRel());
      //  entityModel.add(linkTo(methodOn(SupplierRestController.class).updateSupplier(supplier.getId(),null)).withRel("update_url"));

    //    entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("add_product_url"));
    //    entityModel.add(linkTo(methodOn(FileUploadController.class).upload(null)).withRel("upload_file_url"));



        return ResponseEntity.ok(entityModel);


    }



    @PostMapping(value = "/products/{PRODUCT_ID}/rate_plans", produces = "application/json")
    public ResponseEntity createRatePlan(@PathVariable long PRODUCT_ID,@RequestBody RatePlanReq ratePlanReq) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = validatorOptional.get();
/*        Optional<RatePlan> optionalRatePlan = ratePlanRepository.findById(subscriptionResp.getRatePlan());
        Optional<ComponentRight> optionalComponentRight = componentRightRepository.findById(subscriptionResp.getComponentRightId());

        ComponentRight componentRight = optionalComponentRight.get();
        RatePlan ratePlan = optionalRatePlan.get();*/


        List<Partner> partner = partnerRepository.findAllBySupplier(product.getSupplierId());


        RatePlan subscription = subscriptionService.createRateplan(product, ratePlanReq,partner);



        EntityModel entityModel = EntityModel.of(subscription);

      //  SupplierResp supplierResp = SupplierResp.from(supplier, Arrays.asList());
        entityModel.add(linkTo(methodOn(SupplierRestController.class).createSupplier(null)).withSelfRel());
    //    entityModel.add(linkTo(methodOn(SupplierRestController.class).updateSupplier(supplier.getId(),null)).withRel("update_url"));

    //    entityModel.add(linkTo(methodOn(ProductRestController.class).createProduct(supplier.getId(),null)).withRel("add_product_url"));
        entityModel.add(linkTo(methodOn(FileUploadController.class).upload(null)).withRel("upload_file_url"));



        return ResponseEntity.ok(entityModel);


    }




    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/rate_plans/{PRODUCT_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long PRODUCT_ID) {

        ratePlanRepository.deleteById(PRODUCT_ID);

        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/products/{PRODUCT_ID}/rate_plans", produces = "application/json")
    public PagedModel listRatePlans(@PathVariable long PRODUCT_ID,Pageable pageable, PagedResourcesAssembler<EntityModel<Subscription>> assembler) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(PRODUCT_ID,"找不到产品");
        }

        Product product = validatorOptional.get();
        Page<RatePlan> ratePlanPage = ratePlanRepository.findAllByProduct(product.getId(),pageable);


        return assembler.toModel(ratePlanPage.map(e->{
            EntityModel entityModel = EntityModel.of(e);
            entityModel.add(linkTo(methodOn(SubscriptionRestController.class).createSubscription(e.getId(),null)).withRel("subscrible"));


            return entityModel;
        }));

    }
    @GetMapping(value = "/subscriptions/{PRODUCT_ID}/usages", produces = "application/json")
    public PagedModel listUsages(@PathVariable long PRODUCT_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<Usage>> assembler) {

        Optional<Subscription> validatorOptional = subscriptionRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");
        }
        Subscription product = validatorOptional.get();

        Page<Usage> bookingRuleList = usageRepository.findAllBySubscription(product.getId(),pageable);
        return assembler.toModel(bookingRuleList.map(E->{
            EntityModel entityModel = EntityModel.of(E);

            return entityModel;

        }));

    }




}