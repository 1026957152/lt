package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.CommentResp;
import com.lt.dom.OctResp.InvoiceResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CommentReq;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.CommentServiceImpl;
import com.lt.dom.serviceOtc.HistoryServiceImpl;
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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class CommentRestController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SubscriptionServiceImpl subscriptionService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private RatePlanRepository ratePlanRepository;

    @Autowired
    private HistoryServiceImpl historyService;

    @GetMapping(value = "/comments/{INVOICE_ID}", produces = "application/json")
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
        entityModel.add(linkTo(methodOn(CommentRestController.class).listInvoices(subscription.getId(),null,null)).withRel("listItem"));

        return entityModel;

    }


    @GetMapping(value = "/products/{PRODUCT_ID}/comments", produces = "application/json")
    public PagedModel listInvoices(@PathVariable long PRODUCT_ID,Pageable pageable, PagedResourcesAssembler<EntityModel<Subscription>> assembler) {
        Optional<Product> optionalRatePlan = productRepository.findById(PRODUCT_ID);

        if(optionalRatePlan.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"没找到 产品");

        }

        Page<Comment> validatorOptional = commentRepository.findAll(pageable);


        return assembler.toModel(validatorOptional.map(e->{

            CommentResp invoiceResp = CommentResp.from(e);

            EntityModel entityModel = EntityModel.of(invoiceResp);
            entityModel.add(linkTo(methodOn(CommentRestController.class).getInvoice(e.getId())).withSelfRel());
           // entityModel.add(linkTo(methodOn(CommentRestController.class).listItem(e.getId(),null,null)).withRel("listUsage"));
            return entityModel;
        }));

    }






    @PostMapping(value = "/products/{PRODUCT_ID}/comments", produces = "application/json")
    public ResponseEntity<EntityModel> createComment(@PathVariable long PRODUCT_ID,
                                                     @RequestBody @Valid CommentReq subscriptionResp) {
        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        Supplier supplier = userVo.getSupplier();

        Optional<Product> optionalRatePlan = productRepository.findById(PRODUCT_ID);

        if(optionalRatePlan.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"没找到 产品");

        }
        Product product = optionalRatePlan.get();

        Comment subscription = commentService.create(product, subscriptionResp);



       // SubscriptionResp supplierResp = SubscriptionResp.from(subscription);
        EntityModel entityModel = EntityModel.of(subscription);


        return ResponseEntity.ok(entityModel);


    }



    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/comments/{PRODUCT_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long PRODUCT_ID) {

        ratePlanRepository.deleteById(PRODUCT_ID);

        return ResponseEntity.ok().build();
    }




}