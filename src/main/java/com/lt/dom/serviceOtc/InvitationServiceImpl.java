package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.InvitationEmployeePojo;
import com.lt.dom.otcReq.InvitationPartnerPojo;
import com.lt.dom.otcenum.EnumInvitationStatus;
import com.lt.dom.otcenum.EnumInvitationType;
import com.lt.dom.repository.InvitationsRepository;
import com.lt.dom.repository.SupplierRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class InvitationServiceImpl {


    @Autowired
    private InvitationsRepository invitationsRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private RegisterServiceImpl registerService;

    public Invitation invitePartner(Optional<Supplier> supplier, InvitationPartnerPojo employerPojo) {


        Invitation componentRight = new Invitation();
        componentRight.setInfo(JSONObject.valueToString(employerPojo));

         componentRight.setSupplierId(supplier.get().getId());
         componentRight.setType(EnumInvitationType.Partner);
        componentRight.setStatus(EnumInvitationStatus.pending);
        componentRight = invitationsRepository.save(componentRight);
        return componentRight;
    }


    public Invitation inviteEmployee(Optional<Supplier> supplier, InvitationEmployeePojo employerPojo) {


        Invitation componentRight = new Invitation();
        componentRight.setInfo(JSONObject.valueToString(employerPojo));

        componentRight.setSupplierId(supplier.get().getId());
        componentRight.setType(EnumInvitationType.Employee);
        componentRight.setStatus(EnumInvitationStatus.pending);
        componentRight = invitationsRepository.save(componentRight);

        return componentRight;
    }




    public Invitation accept(Invitation invitation) {
        invitation.setStatus(EnumInvitationStatus.accepted);
        invitation = invitationsRepository.save(invitation);

        if(invitation.getType().equals(EnumInvitationType.Partner)){

            InvitationPartnerPojo invitationPartnerPojo = (InvitationPartnerPojo)JSONObject.stringToValue(invitation.getInfo());


            registerService.register(invitationPartnerPojo.getPhone(),
                    invitationPartnerPojo.getFirstName(),
                    invitationPartnerPojo.getLastName(),
                    invitationPartnerPojo.getCompanyName());

        }
        if(invitation.getType().equals(EnumInvitationType.Employee)){

            InvitationEmployeePojo invitationPartnerPojo = (InvitationEmployeePojo)JSONObject.stringToValue(invitation.getInfo());



            registerService.registerEmployee(invitationPartnerPojo.getPhone(),
                    invitationPartnerPojo.getFirstName(),
                    invitationPartnerPojo.getLastName(),
                    invitationPartnerPojo.getSupplierId());

        }

        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }
}
