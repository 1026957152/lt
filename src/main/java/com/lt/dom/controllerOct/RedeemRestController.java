package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.RedemptionResp;
import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.voucher_disabledException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RedemPojo;
import com.lt.dom.repository.CampaignRepository;
import com.lt.dom.repository.ComponentVounchRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.RedeemServiceImpl;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private CampaignRepository campaignRepository;


    @Autowired
    private VoucherRepository voucherRepository;


    @Autowired
    private RedeemServiceImpl redeemService;


    //TODO 这里有点问题
    @GetMapping(value = "/vouchers/{VOUNCHOR_ID}/redeem", produces = "application/json")
    public ResponseEntity<Voucher> 获得可验证的(Authentication authentication, @PathVariable long VOUNCHOR_ID) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Voucher> optionalVoucher = voucherRepository.findById(VOUNCHOR_ID);


        if(optionalVoucher.isPresent()){
            try {
             // ComponentVounch voucher = redeemService.redemOnceVounchor(userDetails,optionalVoucher.get());
                return ResponseEntity.ok(optionalVoucher.get());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new BookNotFoundException(VOUNCHOR_ID,Voucher.class.getSimpleName());

    }

    @PostMapping(value = "/componet_right_vounchs/{COMPONENT_RIGHT_ID}/redeem", produces = "application/json")
    public ComponentVounch redeemComponentRightVounch(@PathVariable long COMPONENT_RIGHT_ID, CouponTemplatePojo pojo) {

        Optional<ComponentVounch> componentRightVounch = componentVounchRepository.findById(COMPONENT_RIGHT_ID);


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
    @PostMapping(value = "/vouchers/{VOUNCHOR_ID}/redemption", produces = "application/json")
    public ResponseEntity<RedemptionResp> redeemVonchor(Authentication authentication, @PathVariable long VOUNCHOR_ID, @RequestBody RedemPojo pojo) {

    //    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

     //   userDetails.getAuthorities().
        Optional<Voucher> voucher = voucherRepository.findById(VOUNCHOR_ID);
/*
        if(userDetails.getAuthorities().contains("")){

        }*/

        if(voucher.isPresent()){

            if(!voucher.get().isActive()){
                throw new voucher_disabledException(voucher.get().getId(),"");

            }
/*            Supplier employee = userDetails.getSupplier();

            if(!(voucher.get().getValidatorSupplier() == employee.getId())){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
            }*/
            try {

                Optional<Campaign> optionalCampaign = campaignRepository.findById(voucher.get().getCampaign());

                boolean v = redeemService.是否符合验证(optionalCampaign.get(),new Supplier());
                if(v){
                    Pair<RedemptionEntry,Redemption> pair= redeemService.bulkRedeemVounchor(voucher.get(),optionalCampaign.get(),pojo,new Supplier());
                    return ResponseEntity.ok(RedemptionResp.from(pair));
                }else{
                    System.out.println("抛出异常");
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
                }





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