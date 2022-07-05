package com.lt.dom.controllerOct;

import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.domain.TouristAttraction;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.oct.ComponentRightVounch;
import com.lt.dom.oct.Voucher;
import com.lt.dom.repository.ComponentRightVounchRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.RedeemServiceImpl;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class RedeemRestController {

/*
    @GetMapping(value = "/{Redeem_ID}", produces = "application/json")
    public TouristAttraction getBook(@PathVariable int APP_ID, @PathVariable int COUPON_TMPL_ID) {
        return null;
    }
    @GetMapping(value = "/{APP_ID}/{COUPON_TMPL_ID}", produces = "application/json")
    public TouristAttraction listCouponTemplate(@PathVariable int APP_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        return null;
    }
*/

    @Autowired
    private ComponentRightVounchRepository componentRightVounchRepository;


    @Autowired
    private VoucherRepository voucherRepository;


    @Autowired
    private RedeemServiceImpl redeemService;


    //TODO 这里有点问题
    @GetMapping(value = "/vonchors/{VOUNCHOR_ID}/redeem", produces = "application/json")
    public ComponentRightVounch 获得可验证的(Authentication authentication, @PathVariable long VOUNCHOR_ID) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Voucher> voucher = voucherRepository.findById(VOUNCHOR_ID);


        if(voucher.isPresent()){
            try {
                return redeemService.redeemVounchor(userDetails,voucher.get());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }

    @PostMapping(value = "/componet_right_vounchs/{COMPONENT_RIGHT_ID}/redeem", produces = "application/json")
    public ComponentRightVounch redeemComponentRightVounch(@PathVariable long COMPONENT_RIGHT_ID,CouponTemplatePojo pojo) {

        Optional<ComponentRightVounch> componentRightVounch = componentRightVounchRepository.findById(COMPONENT_RIGHT_ID);


        if(componentRightVounch.isPresent()){
            try {
                return redeemService.redeemCompo(componentRightVounch.get());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }


    //TODO 这里有点问题
    @PostMapping(value = "/vonchors/{VOUNCHOR_ID}/redeem", produces = "application/json")
    public ComponentRightVounch redeemVonchor(Authentication authentication, @PathVariable long VOUNCHOR_ID) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Voucher> voucher = voucherRepository.findById(VOUNCHOR_ID);


        if(voucher.isPresent()){
            try {
                return redeemService.redeemVounchor(userDetails,voucher.get());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }


/*
    @PutMapping(value = "/{APP_ID}/coupon_templates/{COUPON_TMPL_ID}", produces = "application/json")
    public TouristAttraction updateBook(@PathVariable int APP_ID, Map metadata) {
        return null;
    }

    @DeleteMapping(value = "/coupon_templates", produces = "application/json")
    public TouristAttraction createCouponTemplate(@PathVariable int id) {
        return null;
    }
*/

}