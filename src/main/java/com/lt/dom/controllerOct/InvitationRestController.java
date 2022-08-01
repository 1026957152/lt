package com.lt.dom.controllerOct;

import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Invitation;
import com.lt.dom.repository.InvitationsRepository;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.InvitationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/invitations")
public class InvitationRestController {




    @Autowired
    private InvitationsRepository invitationsRepository;


    @Autowired
    private InvitationServiceImpl invitationService;




    @PostMapping(value = "/{INVITATION_ID}/accept", produces = "application/json")
    public ResponseEntity<Invitation> 接受邀请(@PathVariable long INVITATION_ID, @RequestBody CouponTemplatePojo  pojo) {

      //  Example<Invitation> example = Example.of(Invitation.from("Fred", "Bloggs", null));
        //Optional<Passenger> actual = repository.findOne(example);

      //  Invitation invitations = invitationsRepository.findBy(example,ff->ff.first()).get();

        Optional<Invitation> optionalInvitation = invitationsRepository.findById(INVITATION_ID);
        if(optionalInvitation.isPresent()){

            Invitation invitation = invitationService.accept(optionalInvitation.get());


            return ResponseEntity.ok(invitation);
        }else{
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }

    }



}