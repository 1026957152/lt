package com.lt.dom.controllerOct;

import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.RealnameAuthsReq;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.IAuthenticationFacade;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class RealnameAuthRestController {



    @Autowired
    private RealNameAuthenticationServiceImpl realnameAuthsService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Operation(summary = "1、实名认证")
    @PostMapping(value = "/realname-auths/individual", produces = "application/json")
    public ResponseEntity postRealnameAuths( @RequestBody @Valid RealnameAuthsReq realnameAuthsReq) {

        Authentication authentication =  authenticationFacade.getAuthentication();

        UserDetails user_ = (UserDetails)authentication.getPrincipal();


        User user = realnameAuthsService.postWxRealnameAuths(user_.getUsername(),realnameAuthsReq);
        return ResponseEntity.ok(user_);

  /*      }else{
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

        }*/

    }

}