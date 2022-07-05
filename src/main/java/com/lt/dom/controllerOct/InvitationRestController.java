package com.lt.dom.controllerOct;

import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Invitation;
import com.lt.dom.repository.InvitationsRepository;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/invitations")
public class InvitationRestController {




    @Autowired
    private InvitationsRepository invitationsRepository;




    @PostMapping(value = "/{INVITATION_ID}/accept", produces = "application/json")
    public Invitation 接受邀请(@PathVariable String SUPPLIER_ID,@RequestBody CouponTemplatePojo  pojo) {

        Example<Invitation> example = Example.of(Invitation.from("Fred", "Bloggs", null));
        //Optional<Passenger> actual = repository.findOne(example);

        Invitation invitations = invitationsRepository.findBy(example,ff->ff.first()).get();


        return invitations;
    }



}