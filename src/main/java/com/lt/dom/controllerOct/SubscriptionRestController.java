package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.SupplierResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Subscription;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcReq.SubscriptionResp;
import com.lt.dom.otcReq.SupplierPojo;
import com.lt.dom.otcenum.EnumSupplierStatus;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.SubscriptionServiceImpl;
import com.lt.dom.vo.AvailabilityVO;
import com.lt.dom.vo.SupplierPojoVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class SubscriptionRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @Autowired
    private SupplierRepository supplierRepository;


    @GetMapping(value = "/subscriptions", produces = "application/json")
    public PagedModel listAvailability(Pageable pageable, PagedResourcesAssembler<EntityModel<Subscription>> assembler) {

        Page<Subscription> validatorOptional = subscriptionRepository.findAll(pageable);


        return assembler.toModel(validatorOptional.map(e->{
            EntityModel entityModel = EntityModel.of(e);
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

}