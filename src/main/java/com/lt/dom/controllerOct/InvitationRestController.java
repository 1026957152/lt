package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.InvitationResp;
import com.lt.dom.OctResp.MovieResp;
import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.InvitationPartnerPojo;
import com.lt.dom.otcenum.EnumBulkIssuanceCardRequestStatus;
import com.lt.dom.otcenum.EnumInvitationStatus;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.InvitationsRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.InvitationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class InvitationRestController {




    @Autowired
    private InvitationsRepository invitationsRepository;


    @Autowired
    private InvitationServiceImpl invitationService;
    @Autowired
    private SupplierRepository supplierRepository;



    @Operation(summary = "4、邀请成为")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/invitations", produces = "application/json")
    public Invitation 邀请合作(@PathVariable long SUPPLIER_ID, @RequestBody InvitationPartnerPojo pojo) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);
        if(optionalSupplier.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到供应商");

        }
            Supplier supplier = optionalSupplier.get();
            Invitation componentRight = invitationService.invitePartner(supplier,pojo);
            return componentRight;



    }



    @PostMapping(value = "/invitations/{INVITATION_ID}/accept", produces = "application/json")
    public ResponseEntity<Invitation> 接受邀请(@PathVariable long INVITATION_ID, @RequestBody CouponTemplatePojo  pojo) {


        Optional<Invitation> optionalInvitation = invitationsRepository.findById(INVITATION_ID);
        if(optionalInvitation.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到");
        }
        Invitation invitation = optionalInvitation.get();


        if(!invitation.getStatus().equals(EnumInvitationStatus.pending)) {

            throw new BookNotFoundException(Enumfailures.not_found,"状态不符合要求");
        }
            invitation = invitationService.accept(optionalInvitation.get());


            return ResponseEntity.ok(invitation);


    }















    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/invitations/Page_createInvitation", produces = "application/json")
    public EntityModel<Media> Page_createInvitation(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("status_list", EnumBulkIssuanceCardRequestStatus.values());

        //  Map map = Map.of("status_list", EnumBulkIssuanceCardRequestStatus.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(InvitationRestController.class).邀请合作(supplier.getId(),null)).withRel("create"));
        entityModel.add(linkTo(methodOn(InvitationRestController.class).getInvitationList(supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(InvitationRestController.class).Page_createInvitation(supplier.getId())).withSelfRel());


        return entityModel;

    }




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/invitations", produces = "application/json")
    public PagedModel getInvitationList(@PathVariable long SUPPLIER_ID , Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Page<Invitation> bookingRuleList = invitationsRepository.findAllBySupplier(supplier.getId(),pageable);


        List<Supplier> suppliers = supplierRepository.findAllById(bookingRuleList.map(e->e.getPartner()));

        Map<Long,Supplier> supplierMap = suppliers.stream().collect(Collectors.toMap(e->e.getId(),e->e));

        return assembler.toModel(bookingRuleList.map(e->{


            //Invitation movieResp = MovieResp.of(e);

            Supplier supplier1 = supplierMap.get(e.getPartner());
            InvitationResp invitationResp = InvitationResp.from(e,supplier1);
            EntityModel entityModel = EntityModel.of(invitationResp);
       //     entityModel.add(linkTo(methodOn(InvitationRestController.class).接受邀请(e.getId(),null)).withSelfRel());
            entityModel.add(linkTo(methodOn(InvitationRestController.class).接受邀请(e.getId(),null)).withRel("accept"));


            return entityModel;
        }));

    }



}