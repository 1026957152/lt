package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.InvoiceResp;
import com.lt.dom.credit.EnumActionType;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.InvoiceReq;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.HistoryServiceImpl;
import com.lt.dom.serviceOtc.InvoiceServiceImpl;
import com.lt.dom.serviceOtc.SubscriptionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class InvoiceRestController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private RatePlanRepository ratePlanRepository;

    @Autowired
    private HistoryServiceImpl historyService;


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/invoices", produces = "application/json")
    public EntityModel create(@PathVariable long SUPPLIER_ID, @RequestBody @Valid InvoiceReq invoiceResp) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Invoice subscription = invoiceService.create(supplier, EnumActionType.invoice_created,invoiceResp);
        InvoiceResp resp = InvoiceResp.from(subscription);

        resp.withRateplans(subscription);
        resp.withHistory(historyService.list(subscription.getId()));
        EntityModel entityModel = EntityModel.of(resp);
        entityModel.add(linkTo(methodOn(InvoiceRestController.class).listItem(subscription.getId(),null,null)).withRel("listItem"));

        return entityModel;

    }
    @GetMapping(value = "/invoices/{INVOICE_ID}", produces = "application/json")
    public EntityModel getInvoice(@PathVariable long INVOICE_ID) {

        Optional<Invoice> validatorOptional = invoiceRepository.findById(INVOICE_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");
        }
        Invoice subscription = validatorOptional.get();
        InvoiceResp resp = InvoiceResp.from(subscription);


        resp.withRateplans(subscription);
        resp.withHistory(historyService.list(subscription.getId()));
        EntityModel entityModel = EntityModel.of(resp);
        entityModel.add(linkTo(methodOn(InvoiceRestController.class).listItem(subscription.getId(),null,null)).withRel("listItem"));

        return entityModel;

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/invoices/Page_listInvoice", produces = "application/json")
    public EntityModel<Media> Page_listInvoice(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of();

        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(InvoiceRestController.class).listInvoices(supplier.getId(),null,null)).withRel("list"));

        entityModel.add(linkTo(methodOn(InvoiceRestController.class).Trial(supplier.getId(),null)).withRel("trial"));

        entityModel.add(linkTo(methodOn(InvoiceRestController.class).run(supplier.getId(),null)).withRel("run"));


        return entityModel;

    }
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/invoices", produces = "application/json")
    public PagedModel listInvoices(@PathVariable long SUPPLIER_ID,Pageable pageable, PagedResourcesAssembler<EntityModel<Subscription>> assembler) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Page<Invoice> validatorOptional = invoiceRepository.findAll(pageable);


        return assembler.toModel(validatorOptional.map(e->{

            InvoiceResp invoiceResp = InvoiceResp.from(e);

            EntityModel entityModel = EntityModel.of(invoiceResp);
            entityModel.add(linkTo(methodOn(InvoiceRestController.class).getInvoice(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(InvoiceRestController.class).listItem(e.getId(),null,null)).withRel("listUsage"));
            return entityModel;
        }));

    }






/*



    @PostMapping(value = "/rate_plans/{RATE_PLAN_ID}/invoices", produces = "application/json")
    public ResponseEntity<EntityModel> createSubscription(@PathVariable long RATE_PLAN_ID,
                                                           @RequestBody SubscriptionResp subscriptionResp) {
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
*/



    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/invoices/{PRODUCT_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long PRODUCT_ID) {

        ratePlanRepository.deleteById(PRODUCT_ID);

        return ResponseEntity.ok().build();
    }

/*
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
            entityModel.add(linkTo(methodOn(InvoiceRestController.class).createSubscription(e.getId(),null)).withRel("subscrible"));


            return entityModel;
        }));

    }*/
    @GetMapping(value = "/invoices/{INVOICE_ID}/items", produces = "application/json")
    public PagedModel listItem(@PathVariable long INVOICE_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<Usage>> assembler) {

        Optional<Subscription> validatorOptional = subscriptionRepository.findById(INVOICE_ID);
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






    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/invoices/trial", produces = "application/json")
    public EntityModel Trial(@PathVariable long SUPPLIER_ID, @RequestBody @Valid InvoiceReq invoiceResp) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        InvoiceResp subscription = invoiceService.Trial(supplier, LocalDateTime.now().minusDays(1));

        EntityModel entityModel = EntityModel.of(subscription);
        entityModel.add(linkTo(methodOn(InvoiceRestController.class).Trial(supplier.getId(),null)).withRel("trial"));

        return entityModel;

    }






    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/invoices/run", produces = "application/json")
    public EntityModel run(@PathVariable long SUPPLIER_ID, @RequestBody @Valid InvoiceReq invoiceResp) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();
        Invoice subscription = invoiceService.processing(supplier, LocalDateTime.now().minusDays(1));

        EntityModel entityModel = EntityModel.of(subscription);
        entityModel.add(linkTo(methodOn(InvoiceRestController.class).Trial(supplier.getId(),null)).withRel("trial"));

        return entityModel;

    }


}