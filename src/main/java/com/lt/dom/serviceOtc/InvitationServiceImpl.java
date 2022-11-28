package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.InvitationEmployeePojo;
import com.lt.dom.otcReq.InvitationPartnerAcceptReq;
import com.lt.dom.otcReq.InvitationPartnerPojo;
import com.lt.dom.otcenum.EnumInvitateRequestType;
import com.lt.dom.otcenum.EnumInvitationStatus;
import com.lt.dom.otcenum.EnumInvitationType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.InvitationsRepository;
import com.lt.dom.repository.SupplierRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvitationServiceImpl {


    @Autowired
    private InvitationsRepository invitationsRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private RegisterServiceImpl registerService;
    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    private PartnerServiceImpl partnerService;

    public Invitation invitePartner(Optional<Supplier> supplier, InvitationPartnerPojo employerPojo) {




        List<Invitation> invitationList = invitationsRepository.findAllByStatus(EnumInvitationStatus.pending);
        if(invitationList.isEmpty()){
            Invitation componentRight = new Invitation();
            componentRight.setInfo(JSONObject.valueToString(employerPojo));

            componentRight.setSupplier(supplier.get().getId());
            componentRight.setType(EnumInvitationType.Partner);
            componentRight.setStatus(EnumInvitationStatus.pending);
            componentRight = invitationsRepository.save(componentRight);



            return componentRight;
        }else{
            return invitationList.get(0);
        }

    }



    public Invitation invitePartner(Supplier supplier, InvitationPartnerPojo employerPojo) {


        String uuid = UUID.randomUUID().toString();

        Optional<Supplier> optionalSupplier = supplierRepository.findByName(employerPojo.getCompanyName());

        if(optionalSupplier.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到被受邀请 公司");
        }
        List<Invitation> invitationList = invitationsRepository.findAllBySupplierAndPartnerName(supplier.getId(),employerPojo.getCompanyName());
        if(invitationList.isEmpty()) {
            Invitation componentRight = new Invitation();
            componentRight.setInfo(JSONObject.valueToString(employerPojo));
            componentRight.setCode(idGenService.nextId(""));
            componentRight.setSupplier(supplier.getId());
            componentRight.setType(EnumInvitationType.Partner);
            componentRight.setInvitateRequestType(EnumInvitateRequestType.Invitation);
            componentRight.setStatus(EnumInvitationStatus.pending);


            componentRight.setGroupId(uuid);



            if(optionalSupplier.isPresent()){
                Supplier supplier_partner = optionalSupplier.get();

                Invitation invitation = new Invitation();
                invitation.setInfo(JSONObject.valueToString(employerPojo));

                invitation.setSupplier(supplier_partner.getId());
                invitation.setType(EnumInvitationType.Partner);
                invitation.setStatus(EnumInvitationStatus.pending);
                invitation.setInvitateRequestType(EnumInvitateRequestType.Request);
                invitation.setGroupId(uuid);
                invitation.setPartner(supplier.getId());
                invitation = invitationsRepository.save(invitation);

                componentRight.setPartner(supplier_partner.getId());

            }
            componentRight = invitationsRepository.save(componentRight);

            return componentRight;

        }else{
            return invitationList.get(0);
        }

    }


    public Invitation inviteEmployee(Optional<Supplier> supplier, InvitationEmployeePojo employerPojo) {


        Invitation componentRight = new Invitation();
        componentRight.setInfo(JSONObject.valueToString(employerPojo));

        componentRight.setSupplier(supplier.get().getId());
        componentRight.setType(EnumInvitationType.Employee);
        componentRight.setStatus(EnumInvitationStatus.pending);
        componentRight = invitationsRepository.save(componentRight);

        return componentRight;
    }




    @Transactional
    public Invitation accept(Invitation invitation) {
        invitation.setStatus(EnumInvitationStatus.accepted);
        invitation = invitationsRepository.save(invitation);

        if(invitation.getType().equals(EnumInvitationType.Partner)){

          //  InvitationPartnerPojo invitationPartnerPojo = (InvitationPartnerPojo)JSONObject.stringToValue(invitation.getInfo());


            partnerService.createPartner(invitation.getSupplier(),invitation.getPartner());


/*
            registerService.register(invitationPartnerPojo.getPhone(),
                    invitationPartnerPojo.getFirstName(),
                    invitationPartnerPojo.getLastName(),
                    invitationPartnerPojo.getCompanyName());
*/
            return invitation;

        }
        if(invitation.getType().equals(EnumInvitationType.Employee)){

            InvitationEmployeePojo invitationPartnerPojo = (InvitationEmployeePojo)JSONObject.stringToValue(invitation.getInfo());



            registerService.registerEmployee(invitationPartnerPojo.getPhone(),
                    invitationPartnerPojo.getFirstName(),
                    invitationPartnerPojo.getLastName(),
                    invitationPartnerPojo.getSupplierId());
            return invitation;
        }

        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }



}
