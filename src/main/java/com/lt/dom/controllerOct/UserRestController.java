package com.lt.dom.controllerOct;

import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Invitation;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.UserPojo;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
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
/*





    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();
        return componentRight;
    }


    @GetMapping(value = "/{APP_ID}/{COUPON_TMPL_ID}", produces = "application/json")
    public List<ComponentRight> listCouponTemplate(@PathVariable int APP_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        return null;
    }




    @PutMapping(value = "/{APP_ID}/component_right/{COMPONENT_RIGHT_ID}", produces = "application/json")
    public ComponentRight updateComponentRight(@PathVariable int APP_ID, Map  metadata) {
        return null;
    }

    @DeleteMapping(value = "/coupon_templates", produces = "application/json")
    public ComponentRight createCouponTemplate(@PathVariable String id) {
        return null;
    }*/

}