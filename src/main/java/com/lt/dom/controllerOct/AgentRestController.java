package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AgentEditReq;
import com.lt.dom.otcReq.AgentReq;
import com.lt.dom.otcReq.IdentifierReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AgentServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.PaymentServiceImpl;
import com.lt.dom.serviceOtc.PriceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//https://www.postman.com/opamcurators/workspace/open-access-third/example/1501710-e695127d-5c31-4f88-901e-ef4e2a376086
@RestController
@RequestMapping("/oct")
public class AgentRestController {
    @Autowired
    private ThirdPartyProductRepository thirdPartyProductRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AgentProductRepository agentProductRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceServiceImpl priceService;


    @Autowired
    private LineItemRepository lineItemRepository;
    @Autowired
    private PaymentServiceImpl paymentService;


    @Autowired
    private AgentServiceImpl agentService;


    @Autowired
    private FileStorageServiceImpl fileStorageService;


/*
    @GetMapping(value = "/agent/{SUPPLIER_ID}/agents/listThirdParty", produces = "application/json")
    public EntityModel<Media> Page_listThirdParty(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Map map = Map.of("status_list", EnumVoucherStatus.values());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(AgentRestController.class).listThirdParty(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }*/



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/Page_agent", produces = "application/json")
    public EntityModel<Media> Page_agent(@PathVariable Long SUPPLIER_ID) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("status_list", EnumAgentStatus.values());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(AgentRestController.class).listAgent(supplier.getId(),null,null)).withRel("list"));

        entityModel.add(linkTo(methodOn(AgentRestController.class).createAgent(supplier.getId(),null)).withRel("create"));

        return entityModel;

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/agents", produces = "application/json")
    public PagedModel listAgent(@PathVariable Long SUPPLIER_ID,

                                     @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,

                                     PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();



        Page<Agent> bookingRuleList = agentRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            AgentResp agentResp = AgentResp.from(e);

  /*          LocationResp locationResp = new LocationResp();
            locationResp.setAddress("山西省榆阳区阜石路");
            movieResp.setAddress(locationResp);*/
            EntityModel entityModel = EntityModel.of(agentResp);
            entityModel.add(linkTo(methodOn(AgentRestController.class).agentEdit(e.getId())).withSelfRel());

            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/agents", produces = "application/json")
    public EntityModel<Agent> createAgent(@PathVariable Long SUPPLIER_ID,@RequestBody @Valid AgentReq tripReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Optional<Supplier> agentOptional = supplierRepository.findById(tripReq.getAgent());
        if(agentOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier agent = agentOptional.get();



        Agent region = agentService.create(supplier,agent,tripReq);


        EntityModel entityModel = EntityModel.of(region);

        return entityModel;

    }


    @PutMapping(value = "/agents/{Museum_ID}", produces = "application/json")
    public EntityModel<Agent> update(@PathVariable long Museum_ID , @RequestBody @Valid AgentEditReq.EditReq regionReq) {

        Optional<Agent> supplierOptional = agentRepository.findById(Museum_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Agent supplier = supplierOptional.get();


        Optional<Supplier> agentOptional = supplierRepository.findById(regionReq.getAgent());
        if(agentOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier agent = agentOptional.get();



        Agent region = agentService.update(supplier,agent,regionReq);


        EntityModel entityModel = EntityModel.of(region);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/agents/Page_createMuseum", produces = "application/json")
    public EntityModel<Museum> Page_createMuseum(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(AgentRestController.class).createAgent(supplier.getId(),null)).withRel("createMuseum"));


        return entityModel;

    }



    @GetMapping(value = "/agents/{Museum_ID}", produces = "application/json")
    public EntityModel getThirdParty(@PathVariable long Museum_ID) {

        Optional<Agent> validatorOptional = agentRepository.findById(Museum_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }


        Agent region = validatorOptional.get();
        AgentResp thirdPartyResp = AgentResp.simpleFrom(region);

        thirdPartyResp.setProducts(region.getProducts().stream().map(e->{

            return e;

        }).collect(Collectors.toList()));

/*        thirdPartyResp.setProducts(region.getProducts().stream().map(e->{

            ProductResp productResp = ProductResp.basefrom(e.getProduct());
            EntityModel entityModel = EntityModel.of(productResp);
            entityModel.add(linkTo(methodOn(AgentRestController.class).delete(e.getId().getThirdPartyId(),e.getId().getProductId())).withRel("delete"));


            return entityModel;

        }).collect(Collectors.toList()));*/
        thirdPartyResp.setPhoto(fileStorageService.loadDocumentWithDefault(EnumDocumentType.region_photo,region.getCode()));




        EntityModel entityModel = EntityModel.of(thirdPartyResp);
        entityModel.add(linkTo(methodOn(AgentRestController.class).getThirdParty(region.getId())).withSelfRel());
       // entityModel.add(linkTo(methodOn(AgentRestController.class).createProduct(region.getId(),null)).withRel("addProduct"));
        entityModel.add(linkTo(methodOn(AgentRestController.class).update(region.getId(),null)).withRel("edit"));


        return entityModel;

    }




    @GetMapping(value = "/agents/{Museum_ID}/edit", produces = "application/json")
    public EntityModel agentEdit(@PathVariable long Museum_ID) {

        Optional<Agent> validatorOptional = agentRepository.findById(Museum_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }


        Agent region = validatorOptional.get();

        AgentEditReq agentEditReq = new  AgentEditReq();

        AgentEditReq.EditReq thirdPartyResp = AgentEditReq.EditReq.from(region);


        EntityModel entityModel_eidt = EntityModel.of(thirdPartyResp);
        // entityModel.add(linkTo(methodOn(AgentRestController.class).createProduct(region.getId(),null)).withRel("addProduct"));
        entityModel_eidt.add(linkTo(methodOn(AgentRestController.class).update(region.getId(),null)).withRel("edit"));

        agentEditReq.setBasicEdit(entityModel_eidt);

        AgentResp agentResp = AgentResp.from(region);
        Optional<Supplier> supplier = supplierRepository.findById(region.getAgent());
        agentResp.setAgent(SupplierResp.simpleFrom(supplier.get()));

        agentEditReq.setInfo(agentResp);

        List<Product> productList = productRepository.findAll();

        thirdPartyResp.setParameterList(Map.of(
                "identify_type_list",EnumIdentifiersType.List(),
                "agent_bill_type_list",EnumAgentBilling.List(),
                "product_list",priceService.getProductSkus(productList)
        ));
        thirdPartyResp.setProducts(region.getProducts().stream().map(e->{
            AgentEditReq.ProductReq req = new AgentEditReq.ProductReq();
            req.setId(e.getId().getProductId());
            req.setNet(e.getNet());
            req.setSku(e.getSku());

            return req;

        }).collect(Collectors.toList()));


        if(region.getContact() != null){
            thirdPartyResp.setContacts(region.getContact().getIdentifiers().stream().map(e->{
                IdentifierReq identifierReq = new IdentifierReq();
                identifierReq.setType(e.getType());
                identifierReq.setId(e.getLinkId());
                return identifierReq;

            }).collect(Collectors.toList()));
        }else{
            thirdPartyResp.setContacts(Arrays.asList());
        }


/*        thirdPartyResp.setProducts(region.getProducts().stream().map(e->{

            ProductResp productResp = ProductResp.basefrom(e.getProduct());
            EntityModel entityModel = EntityModel.of(productResp);
            entityModel.add(linkTo(methodOn(AgentRestController.class).delete(e.getId().getThirdPartyId(),e.getId().getProductId())).withRel("delete"));


            return entityModel;

        }).collect(Collectors.toList()));*/
        thirdPartyResp.setLogo(fileStorageService.loadDocumentWithDefault(EnumDocumentType.agent_logo,region.getCode()));




        EntityModel entityModel = EntityModel.of(agentEditReq);
        entityModel.add(linkTo(methodOn(AgentRestController.class).agentEdit(region.getId())).withSelfRel());
        // entityModel.add(linkTo(methodOn(AgentRestController.class).createProduct(region.getId(),null)).withRel("addProduct"));


        return entityModel;

    }









/*

    @PostMapping(value = "/agents/{SUPPLIER_ID}/exhibit", produces = "application/json")
    public EntityModel<ExhibitionReq> createPlace(@PathVariable long SUPPLIER_ID , @RequestBody @Valid ExhibitionReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Exhibition place = thirdervice.createExhibit(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }*/


/*
    @PostMapping(value = "/agents/{SUPPLIER_ID}/products", produces = "application/json")
    public EntityModel<ThirdPartyProduct> createProduct(@PathVariable long SUPPLIER_ID , @RequestBody @Valid List<BusStopReq> movieReq) {

        Optional<ThirdParty> supplierOptional = regionRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        ThirdParty supplier = supplierOptional.get();


        ThirdParty place = agentService.createThirdPartyProduct(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }
*/









/*

    @PostMapping(value = "/agents/{SUPPLIER_ID}/exhibit", produces = "application/json")
    public EntityModel<ExhibitionReq> createPlace(@PathVariable long SUPPLIER_ID , @RequestBody @Valid CollectionItemReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Artwork place = thirdervice.createCollectionItem(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }
*/




    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/agents/{SUPPLIER_ID}/products/{Movie_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long SUPPLIER_ID , @PathVariable long Movie_ID) {


        ThirdPartyProductKey thirdPartyProductKey = new ThirdPartyProductKey(SUPPLIER_ID,Movie_ID);

        Optional<ThirdPartyProduct> supplierOptional = thirdPartyProductRepository.findById(thirdPartyProductKey);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        ThirdPartyProduct supplier = supplierOptional.get();

        thirdPartyProductRepository.delete(supplier);

        return ResponseEntity.ok().build();
    }
    @Operation(summary = "4、删除Product对象")
    @GetMapping(value = "/agents/{SUPPLIER_ID}/products/{Movie_ID}", produces = "application/json")
    public EntityModel get(@PathVariable long SUPPLIER_ID , @PathVariable long Movie_ID) {


        ThirdPartyProductKey thirdPartyProductKey = new ThirdPartyProductKey(SUPPLIER_ID,Movie_ID);

        Optional<ThirdPartyProduct> supplierOptional = thirdPartyProductRepository.findById(thirdPartyProductKey);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        ThirdPartyProduct supplier = supplierOptional.get();



        EntityModel entityModel = EntityModel.of(supplier);
        return entityModel;
    }




    @GetMapping(value = "/agents/{SUPPLIER_ID}/Page_listAgentProduct", produces = "application/json")
    public EntityModel<Media> Page_listAgentProduct(@PathVariable long SUPPLIER_ID ) {


        Optional<Agent> supplierOptional = agentRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Agent supplier = supplierOptional.get();



        Map map = Map.of("status_list", EnumVoucherStatus.values());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(AgentRestController.class).listProduct(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }


    @GetMapping(value = "/agents/{AGENT_ID}/products", produces = "application/json")
    public PagedModel listProduct(@PathVariable long AGENT_ID ,
                                   @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,

                                   PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {

        Optional<Agent> validatorOptional = agentRepository.findById(AGENT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }


        Agent region = validatorOptional.get();


        Page<AgentProduct> bookingRuleList = agentProductRepository.findAllByAgent(region,pageable);

        return assembler.toModel(bookingRuleList.map(x->{

            EntityModel entityModel = EntityModel.of(x);
       //     entityModel.add(linkTo(methodOn(BookingRestController.class).getBookingEdit(x.getId())).withSelfRel());

       //     entityModel.add(linkTo(methodOn(BookingRestController.class).addTravelers(x.getId(),null)).withRel("addTraveler"));
            return entityModel;


        }));

    }





    @GetMapping(value = "/agents/{AGENT_ID}/bookings", produces = "application/json")
    public PagedModel listBookings(@PathVariable long AGENT_ID ,
                                   @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,

                                   PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {

        Optional<Agent> validatorOptional = agentRepository.findById(AGENT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }


        Agent region = validatorOptional.get();


        Page<Reservation> bookingRuleList = reservationRepository.findAllByAgent(region.getId(),pageable);

        return assembler.toModel(bookingRuleList.map(x->{


            List<LineItem> lineItemList = lineItemRepository.findAllByBooking(x.getId());

            BookingResp bookingResp = BookingResp.toResp_LIST(Pair.with(x, lineItemList));

            Optional<Payment> optionalPayment = paymentService.getByBooking(x);

            if(optionalPayment.isPresent()){
                Payment payment = optionalPayment.get();



                PaymentResp paymentResp = PaymentResp.from(payment);

                List<EntityModel<PaymentMethodResp>>  entityModelList = paymentService.getPaymentMethods(payment);

                paymentResp.setMethods(entityModelList);

                EntityModel entityModel_payment= EntityModel.of(paymentResp);
                entityModel_payment.add(linkTo(methodOn(BookingRestController.class).refund(payment.getId(),null)).withRel("refund"));

                bookingResp.setPayment(entityModel_payment);
            }

            EntityModel entityModel = EntityModel.of(bookingResp);
            entityModel.add(linkTo(methodOn(BookingRestController.class).getBookingEdit(x.getId())).withSelfRel());

            entityModel.add(linkTo(methodOn(BookingRestController.class).addTravelers(x.getId(),null)).withRel("addTraveler"));
            return entityModel;


        }));

    }



}