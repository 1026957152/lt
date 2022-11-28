package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.PartnerResp;
import com.lt.dom.OctResp.RatePlanResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumBulkIssuanceCardRequestStatus;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.PartnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class PartnerRestController {


    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PartnerShareRatePlanRepository partnerShareRatePlanRepository;

    @Autowired
    private RatePlanRepository ratePlanRepository;

    @Autowired
    private ProductRepository productRepository;




    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerServiceImpl partnerService;





    @GetMapping(value = "/partners/{PARTNER_ID}/component_rights", produces = "application/json")
    public List<ComponentRight> listComponents(@PathVariable long PARTNER_ID) {


        Optional<Partner> optionalPartner = partnerRepository.findById(PARTNER_ID);

        if(optionalPartner.isPresent()){
            try {
                return partnerService.listComponentRights(optionalPartner.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/partners/Page_getPartnerList", produces = "application/json")
    public EntityModel<Media> Page_getPartnerList(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("status_list", EnumBulkIssuanceCardRequestStatus.values());

        //  Map map = Map.of("status_list", EnumBulkIssuanceCardRequestStatus.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(PartnerRestController.class).getPartnerList(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/partners", produces = "application/json")
    public PagedModel getPartnerList(@PathVariable long SUPPLIER_ID ,
                                     @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,


                                     PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Page<Partner> bookingRuleList = partnerRepository.findAllBySupplier(supplier.getId(),pageable);

        Map<Long,Supplier> supplierMap = supplierRepository.findAllById(bookingRuleList.map(e->e.getPartner())).stream().collect(Collectors.toMap(e->e.getId(),e->e));
        return assembler.toModel(bookingRuleList.map(e->{
            Supplier supplier1 = supplierMap.get(e.getPartner());


            PartnerResp movieResp = PartnerResp.from(e,supplier1);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(PartnerRestController.class).getPartner(e.getId())).withSelfRel());


            return entityModel;
        }));

    }





    @GetMapping(value = "/partners/{PARTNER_ID}", produces = "application/json")
    public EntityModel getPartner(@PathVariable long PARTNER_ID ) {

        Optional<Partner> optionalPartner = partnerRepository.findById(PARTNER_ID);

        if(optionalPartner.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到合作详情");

        }

        Partner partner = optionalPartner.get();

        Supplier supplier = supplierRepository.findById(partner.getPartner()).get();



            PartnerResp movieResp = PartnerResp.from(partner,supplier);


            List<PartnerShareRatePlan> partnerShareRatePlans = partnerShareRatePlanRepository.findAll();

        List<RatePlan> ratePlans = partnerShareRatePlans.stream().map(e->e.getRatePlan()).collect(Collectors.toList());


        Map<Long,Product> productMap = productRepository.findAllById(partnerShareRatePlans.stream().map(e->e.getProduct()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),E->E));

        movieResp.setRatePlans(ratePlans.stream().map(e->{

            Product product = productMap.get(e.getProduct());
            RatePlanResp resp = RatePlanResp.from(e);

            EntityModel entityModel = EntityModel.of(resp);

            resp.withProduct(product);

            entityModel.add(linkTo(methodOn(SubscriptionRestController.class).createSubscription(e.getId(),null)).withRel("subscribe"));


            return entityModel;

        }).collect(Collectors.toList()));


            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(PartnerRestController.class).getPartner(partner.getId())).withSelfRel());


            return entityModel;

    }
}