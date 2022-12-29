package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.error.Voucher_has_no_permistion_redeemException;
import com.lt.dom.error.voucher_not_publishException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.serviceOtc.product.BusTicketServiceImpl;
import com.lt.dom.serviceOtc.product.CityPassServiceImpl;
import com.lt.dom.serviceOtc.product.MultiTicketServiceImpl;
import com.lt.dom.serviceOtc.scanner.UserScanServiceImpl;
import com.lt.dom.vo.CodeWithLatLngVo;
import com.lt.dom.vo.PersonBean;
import com.lt.dom.vo.UserVo;
import com.lt.dom.vo.WrittenOffMerchantVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct/scanner")
public class ScanRestController {
    private static final Logger logger = LoggerFactory.getLogger(ScanRestController.class);

    @Autowired
    private UserScanServiceImpl userScanService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private CardholderRepository cardholderRepository;



    @Autowired
    private MultiTicketServiceImpl multiTicketService;
    @Autowired
    private BusTicketServiceImpl busTicketService;


    @Autowired
    private PassRepository passRepository;

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private CryptoServiceImpl cryptoService;




    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private CityPassServiceImpl cityPassService;



    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private ValidateServiceImpl validateService;




    @Autowired
    private IntoOnecodeServiceImpl intoOnecodeService;

    @Autowired
    private VoucherTicketRepository voucherTicketRepository;

    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private ClainQuotaServiceImpl clainQuotaService;



    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedeemServiceImpl redeemService;

    @Autowired
    private TourBookingRepository tourBookingRepository;


    //TODO 这里有点问题
    @PostMapping(value = "/validate", produces = "application/json")
    public Object validateVoucherByCode(@RequestBody CodeWithLatLngVo pojo_) {

        Authentication authentication = authenticationFacade.getAuthentication();
        UserVo userVo = authenticationFacade.getUserVo(authentication);


        Supplier supplier = userVo.getSupplier();



        String code_decode = cryptoService.decode(pojo_.getC());


        RedemBycodePojo.Code code = new RedemBycodePojo.Code();
        code.setCode(code_decode);


        EntityModel entityModel = userScanService.validate(code,userVo);
        if(entityModel != null){
            return entityModel;
        }




        Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findByCode(code_decode);


        EntityModel entityModel_city = cityPassService.validate(optionalVoucher.get(),userVo);
        if(entityModel_city != null){
            return entityModel_city;
        }




        throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到核销券_验证");
    }







/*



    //TODO 这里有点问题
    @PostMapping(value = "/validate_by_id_number", produces = "application/json")
    public Object validate_by_id_number(@RequestBody OnecodeScanPojo pojo___) {



        //  User user_文旅码用户 = intoOnecodeService.byCode(pojo___.getCode());


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userOv_景区检票员 = authenticationFacade.getUserVo(authentication);


        List<Cardholder> cardholderList = cardholderRepository.findByIdentity(pojo___.getId());


        RedemBycodePojo.Code redemBycodePojo = new RedemBycodePojo.Code();
        redemBycodePojo.setCode(cardholderList.get(0).getIdentity());
        EntityModel entityModel = cityPassService.validate_by_idnumber(redemBycodePojo,userOv_景区检票员);


        if(entityModel!= null){
            return entityModel;
        }


        throw new BookNotFoundException(Enumfailures.not_found,"找不到这个权益券 "+pojo___.getCode());



    }

    @PostMapping(value = "/crypt", produces = "application/json")
    public Object getCrypto(@RequestBody RedemBycodeFromBusScannerPojo code) {

        String code_st = cryptoService.encode(code.getCode());

        return code_st;

    }

    //TODO 这里有点问题
    @PostMapping(value = "/verify", produces = "application/json")
    public Object bus_ticket_validator(@RequestBody RedemBycodeFromBusScannerPojo code) {



        Optional<Device> deviceOptional = deviceRepository.findByCode(code.getDeviceCode());


        if(deviceOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }
        Device device = deviceOptional.get();

        if(!device.getType().equals(EnumDeviceType.BUS_QRCODE_READER)){
            throw new BookNotFoundException(Enumfailures.not_found,"不支持的设备");
        }


        String code_st = cryptoService.decode(code.getCode());


        if(!code_st.startsWith("tike_")) {

            throw new BookNotFoundException(Enumfailures.not_found,"不是券");

        }

        Optional<VoucherTicket> voucherTicketOptional = voucherTicketRepository.findByCode(code_st);
            if(voucherTicketOptional.isEmpty()){

                throw new BookNotFoundException(Enumfailures.not_found,"找不到券");
            }


        VoucherTicket voucherTicket = voucherTicketOptional.get();
        if(voucherTicket.getType().equals(EnumVoucherType.PASS)){

            Optional<Pass> optionalVoucher= passRepository.findById(voucherTicket.getRelateId());
            Pass pass = optionalVoucher.get();
            List<ComponentVounch> componentVounchList = validatorService.check(device.getId(),pass.getCode());


            RedemptionTryResp resp = new RedemptionTryResp();


            componentRightService.sentEntries(pass.getCode(),resp,componentVounchList);
            resp.setCrypto_code(pass.getCode());



            resp.setType_text(EnumRedeamptionType.PASS.toString());
            return resp;
        }



        throw new BookNotFoundException(Enumfailures.not_found,"不支持的 券");




    }


    //TODO 这里有点问题
    @PostMapping(value = "/quick_redeem", produces = "application/json")
    public Object quick_redeem_busticket(@RequestBody RedemBycodeFromBusScannerPojo pojo_) {



        Optional<Device> deviceOptional = deviceRepository.findByCode(pojo_.getDeviceCode());


        if(deviceOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }
        Device device = deviceOptional.get();

        if(!device.getType().equals(EnumDeviceType.BUS_QRCODE_READER)){
            throw new BookNotFoundException(Enumfailures.not_found,"不支持的设备");
        }

        String code_st = cryptoService.decode(pojo_.getCode());

        System.out.println("参数啊啊 {}"+pojo_.toString());



        CodeWithLatLngVo codeWithLatLngVo = new CodeWithLatLngVo();
*/
/*        codeWithLatLngVo.setC(code.getCode());
        codeWithLatLngVo.setCt(code.getLatitude());
        codeWithLatLngVo.setCg(code.getLongitude());*//*

        codeWithLatLngVo.setSt(pojo_.getLatitude());
        codeWithLatLngVo.setSg(pojo_.getLongitude());

        codeWithLatLngVo.setC(code_st);


        EntityModel entityModel =  busTicketService.redeem(codeWithLatLngVo,device);


        if(entityModel != null){
            return entityModel;
        }
        throw new BookNotFoundException(Enumfailures.not_found,"找不到核销券");


    }





    //TODO 这里有点问题 redemptions
    @PostMapping(value = "/crypto_redemptions", produces = "application/json")
    public Object redeemVonchor(@RequestBody RedemByCryptoCodePojo pojo___) {

        logger.debug("参数 {}", pojo___);

        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userOv = authenticationFacade.getUserVo(authentication);



        CodeWithLatLngVo codeWithLatLngVo =  new CodeWithLatLngVo();
        codeWithLatLngVo.setC(pojo___.getCode());


        codeWithLatLngVo.setC(pojo___.getCrypto_code());

        Supplier supplier = userOv.getSupplier();




        EntityModel entityModel = multiTicketService.redeem(codeWithLatLngVo,userOv);



        if(entityModel != null){
            return entityModel;
        }




        String code_de = cryptoService.decode(codeWithLatLngVo.getC());

        System.out.println("加密前"+codeWithLatLngVo.getC()+"解密后"+code_de);

        Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findByCode(code_de);

        if(optionalVoucher.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到核销券"+code_de);

        }

        codeWithLatLngVo.setC(code_de);
        entityModel = cityPassService.redeem(optionalVoucher.get(),userOv);
        if(entityModel != null){
            return entityModel;
        }







        throw new BookNotFoundException(Enumfailures.not_found,"找不到核销券"+pojo___.getCrypto_code());
    }

*/





}