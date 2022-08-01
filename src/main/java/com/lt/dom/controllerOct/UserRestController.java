package com.lt.dom.controllerOct;

import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Invitation;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.RealnameAuthsReq;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.RealnameAuthsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/otc/users")
public class UserRestController {


    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private RealnameAuthsServiceImpl realnameAuthsService;


    @Autowired
    private UserRepository userRepository;

    @GetMapping( produces = "application/json")
    public User get(@RequestParam String  phone) {

        User user = new User();
        user.setPhone(phone);
        Example<User> example = Example.of(user);

        Optional<User> optionalUser = userRepository.findOne(example);

        if(optionalUser.isPresent()){
            return user;
        }else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

        }

    }





    @PostMapping( produces = "application/json")
    public User createUser(@RequestBody UserPojo pojo) {
        User componentRight = new User();

        componentRight.setPhone(pojo.getPhone());
        componentRight.setNo(idGenService.nextId("user"));
        componentRight = userRepository.save(componentRight);
        return componentRight;
    }





    @Operation(summary = "1、实名认证")
    @PostMapping(value = "/{USER_ID}/realname-auths/individual", produces = "application/json")
    public ResponseEntity<User> postRealnameAuths(@PathVariable long USER_ID, @RequestBody RealnameAuthsReq realnameAuthsReq) {

        Optional<User> optionalUser = userRepository.findById(USER_ID);

        if(optionalUser.isPresent()){
            User user = realnameAuthsService.postRealnameAuths(optionalUser.get(),realnameAuthsReq);
            return ResponseEntity.ok(user);

        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }


}