package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.BookingResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.User;
import com.lt.dom.oct.Voucher;
import com.lt.dom.otcReq.VoucherResp;
import com.lt.dom.repository.CampaignRepository;
import com.lt.dom.repository.UserRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.QrcodeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;

import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class VoucherRestController {
    private static final Logger logger = LoggerFactory.getLogger(RedemptionRestController.class);

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private CampaignRepository campaignRepository;


    @Autowired
    private AssetServiceImpl assetService;

    @Autowired
    QrcodeServiceImpl qrcodeService;

    @Operation(summary = "1、获得")
    @GetMapping(value = "/vouchers/{VOUCHER_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<VoucherResp>> getVoucher(@PathVariable long VOUCHER_ID) {


        Optional<Voucher> optionalVoucher = voucherRepository.findById(VOUCHER_ID);
        //Optional<Voucher> optionalVoucher = voucherRepository.findOne(example);

        if (optionalVoucher.isEmpty()) {
            throw new BookNotFoundException(VOUCHER_ID,"找不到优惠券");
        }

        Optional<Campaign> campaignOptional = campaignRepository.findById(optionalVoucher.get().getCampaign());

        List<Asset> assets = assetService.getQr(optionalVoucher.get().getCode());
            VoucherResp Voucher = VoucherResp.from(Triplet.with(optionalVoucher.get(),campaignOptional.get(),assets),Optional.empty());
            return ResponseEntity.ok(EntityModel.of(Voucher));
    }









    @Operation(summary = "1、获得")
    @GetMapping(value = "/vouchers/{VOUCHER_ID}/qr_refresh", produces = "application/json")
    public ResponseEntity<String> refresh(@PathVariable long VOUCHER_ID) {

        logger.debug("参数：",VOUCHER_ID);

        Optional<Voucher> optionalVoucher = voucherRepository.findById(VOUCHER_ID);

        if (optionalVoucher.isEmpty()) {
            throw new BookNotFoundException(VOUCHER_ID,"找不到优惠券");
        }
        Voucher voucher = optionalVoucher.get();
        String crypto = qrcodeService._encode(voucher.getCode());

        String 解密后的数据 = qrcodeService._decode(crypto);
        logger.debug("参数{} {} 加密{} 解密后的数据{}",VOUCHER_ID,voucher.getCode(),crypto,解密后的数据);
        return ResponseEntity.ok(crypto);
    }






    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/users/{USER_ID}/vouchers", produces = "application/json")
    public PagedModel listVoucher(@PathVariable long USER_ID, Pageable pageable , PagedResourcesAssembler<EntityModel<VoucherResp>> assembler) {


        Optional<User> optionalUser = userRepository.findById(USER_ID);

        if(optionalUser.isEmpty()){
            throw new BookNotFoundException(USER_ID,User.class.getSimpleName());

        }


        Page<Voucher> voucherPage = voucherRepository.findAll(pageable);


        return assembler.toModel(voucherPage.map(e->{
            VoucherResp Voucher = VoucherResp.from(e);
            return EntityModel.of(Voucher);
        }));


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


