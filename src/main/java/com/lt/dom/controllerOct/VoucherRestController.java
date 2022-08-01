package com.lt.dom.controllerOct;

import com.lt.dom.oct.Voucher;
import com.lt.dom.otcReq.VoucherResp;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.ValidatorScanServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class VoucherRestController {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ValidatorScanServiceImpl validatorScanService;



    @Autowired
    private VonchorServiceImpl vonchorService;


    @Operation(summary = "1、获得")
    @GetMapping(value = "/vouchers/{VOUCHER_ID}", produces = "application/json")
    public ResponseEntity<VoucherResp> getVoucher(@PathVariable long VOUCHER_ID) {
   // public ResponseEntity<VoucherResp> getVoucher(@PathVariable String CODE) {
/*        System.out.println("getVouchergetVouchergetVouchergetVoucher"+CODE);
        System.out.println("getVouchergetVouchergetVouchergetVoucher"+CODE);
        System.out.println("getVouchergetVouchergetVouchergetVoucher"+CODE);
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("campaign")
                .withIgnorePaths("relateId")
                .withIgnorePaths("relateId")
                .withIgnorePaths("start_date")
                .withIgnorePaths("active")
                .withIgnorePaths("published")
                .withIgnorePaths("redeemed_quantity")
                .withIgnorePaths("expiration_date");*/


             //   .withMatcher("model", ignoreCase());

/*        Voucher probe = new Voucher();
        probe.setCode(CODE);
        Example<Voucher> example = Example.of(probe,modelMatcher);*/

        Optional<Voucher> optionalVoucher = voucherRepository.findById(VOUCHER_ID);
        //Optional<Voucher> optionalVoucher = voucherRepository.findOne(example);

        if (optionalVoucher.isPresent()) {
            VoucherResp Voucher = vonchorService.getVoucher(optionalVoucher.get());
            return ResponseEntity.ok(Voucher);
        } else {
           // System.out.println("ResponseStatusException"+CODE);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }

    }





/*        Optional<Product> optionalProduct = productRepository.findById(pojo.getProductId());

        if(optionalProduct.isPresent()){
            return ResponseEntity.ok(booking);
        }

        System.out.println("抛出异常");

    }


    @GetMapping(value = "/{VOUCHER_ID}/componet_right_vounchs", produces = "application/json")
    public List<ComponentVounch> listComponentRightVounch(@PathVariable long VOUCHER_ID) {

        Optional<Voucher> validatorOptional = voucherRepository.findById(VOUCHER_ID);
        if(validatorOptional.isPresent()){
            try {
                return vonchorService.get票的权益(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }
/*
    @PostMapping(value = "/coupon_templates", produces = "application/json")
    public ComponentRight createCouponTemplate(@RequestBody CouponTemplatePojo  pojo) {
        ComponentRight componentRight = new ComponentRight();

        componentRight.setName(pojo.getName());
        componentRight.setNote(idGenService.nextId("Coupon_"));
        return componentRight;
    }






    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();
        return componentRight;
    }
*/

/*

    @GetMapping(value = " /{VALIDATOR_ID}/component_rights", produces = "application/json")
    public List<ComponentRight> listComponentRight(@PathVariable long VALIDATOR_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
        if(validatorOptional.isPresent()){
            try {
                return validatorScanService.找出当前验证者管理的权益(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }


    @GetMapping(value = " /{VALIDATOR_ID}/component_right_vonchors", produces = "application/json")
    public List<ComponentRightVounch> listComponentRightVounch(@PathVariable long VALIDATOR_ID) {

        Optional<Validator> validatorOptional = validatorRepository.findById(VALIDATOR_ID);
        if(validatorOptional.isPresent()){
            try {
                return validatorScanService.当前核验者的权益券(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }


*/


/*
    @PutMapping(value = "/{APP_ID}/component_right/{COMPONENT_RIGHT_ID}", produces = "application/json")
    public ComponentRight updateComponentRight(@PathVariable int APP_ID, Map  metadata) {
        return null;
    }

    @DeleteMapping(value = "/coupon_templates", produces = "application/json")
    public ComponentRight createCouponTemplate(@PathVariable String id) {
        return null;
    }
*/

}