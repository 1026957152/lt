package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.PublicationResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Campaign_inactiveException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PublicationPojo;
import com.lt.dom.otcReq.PublicationSearchPojo;
import com.lt.dom.otcReq.VoucherResp;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.DeviceService;
import com.lt.dom.serviceOtc.IAuthenticationFacade;
import com.lt.dom.serviceOtc.PublicationServiceImpl;
import com.lt.dom.specification.VoucherQueryfieldsCriteria;
import com.lt.dom.specification.VoucherSpecification;
import com.lt.dom.util.HttpUtils;
import com.lt.dom.vo.VoucherPublicationPaymentVo;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//import ua_parser.Parser;
//import ua_parser.Client;




@RestController
@RequestMapping("/oct")
public class PublicationRestController {

    Logger logger = LoggerFactory.getLogger(PublicationRestController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PublicationServiceImpl publicationService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private AssetServiceImpl assetService;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PublicationEntryRepository publicationEntryRepository;

/*
    @Autowired
    private Parser uaParser;
*/





    @GetMapping(value = "/users/{USER_ID}/voucherList", produces = "application/json")
    public PagedModel getVoucherList(@PathVariable long USER_ID, @ModelAttribute PublicationSearchPojo pojo, Pageable pageable, PagedResourcesAssembler<EntityModel<PublicationResp>> assembler) {

        logger.debug(pojo.toString());


        System.out.println("参数 "+pojo.toString());
        Authentication authentication =  authenticationFacade.getAuthentication();



        User user =  authenticationFacade.getUser(authentication);
        VoucherQueryfieldsCriteria voucherQueryfieldsCriteria = new VoucherQueryfieldsCriteria();
        voucherQueryfieldsCriteria.setPublishTo(user.getId());
        voucherQueryfieldsCriteria.setStatus(pojo.getStatus());

        Specification<Voucher> specification = VoucherSpecification.toP(voucherQueryfieldsCriteria);

        Page<Voucher> voucherList = voucherRepository.findAll(specification,pageable);

        List<PublicationEntry> publicationResps = publicationEntryRepository.findAllByVoucherIn(voucherList.stream().map(e->e.getId()).collect(Collectors.toList()));


        List<Campaign> campaigns = campaignRepository.findAllById(publicationResps.stream().map(x->x.getCampaign()).collect(Collectors.toList()));
        List<Asset> assets = assetRepository.findAllByTypeAndIdIdIn(EnumAssetType.qr,voucherList.stream().map(x->x.getCode()).collect(Collectors.toList()));
        Map<Long,Campaign> longCampaignMap = campaigns.stream().collect(Collectors.toMap(x->x.getId(), x->x));
        Map<Long,List<PublicationEntry>> longPublicationEntryMap = publicationResps.stream().collect(Collectors.groupingBy(x->x.getVoucher()));
        Map<Long,List<Asset>> longListMap =
                assets.stream().collect(Collectors.groupingBy(x->x.getSource()));


        return assembler.toModel(voucherList.map(voucher->{
            Campaign campaign = longCampaignMap.get(voucher.getCampaign());

            List<Asset> assetList = longListMap.get(voucher.getId());

            List<PublicationEntry> x= longPublicationEntryMap.get(voucher.getId());
            EntityModel entityModel = EntityModel.of(PublicationResp.sigleFrom(Quartet.with(x,voucher,campaign,assetList)));
            PublicationEntry publicationEntry = x.get(0);
            if(publicationEntry.getPaied()){
                entityModel.add(linkTo(methodOn(PaymentRestController.class).refund(publicationEntry.getCharge(),null)).withRel("refund"));
            }

            entityModel.add(linkTo(methodOn(VoucherRestController.class).refresh(voucher.getId())).withRel("qr_refresh"));

            return entityModel;
        }));

    }






    @GetMapping(value = "/users/{USER_ID}/publications", produces = "application/json")
    public PagedModel pageUserPublicationResp(@PathVariable long USER_ID, @ModelAttribute PublicationSearchPojo pojo, Pageable pageable, PagedResourcesAssembler<EntityModel<PublicationResp>> assembler) {

        Authentication authentication =  authenticationFacade.getAuthentication();



        User user =  authenticationFacade.getUser(authentication);
/*        if(authentication == null){
            Optional<User> validatorOptional = userRepository.findByPhone("13468801684");
            if(validatorOptional.isEmpty()) {

                throw new BookNotFoundException(USER_ID,"找不到user");

            }
        }else{

        }*/



            Page<PublicationEntry> publicationResps = publicationService.pagePublication(user,pojo,pageable);

            List<Campaign> campaigns = campaignRepository.findAllById(publicationResps.stream().map(x->x.getCampaign()).collect(Collectors.toList()));
            List<Voucher> vouchers = voucherRepository.findAllById(publicationResps.stream().map(x->x.getVoucher()).collect(Collectors.toList()));
            List<Asset> assets = assetRepository.findAllByTypeAndIdIdIn(EnumAssetType.qr,vouchers.stream().map(x->x.getCode()).collect(Collectors.toList()));
            Map<Long,Campaign> longCampaignMap = campaigns.stream().collect(Collectors.toMap(x->x.getId(), x->x));
            Map<Long,Voucher> longVoucherMap = vouchers.stream().collect(Collectors.toMap(x->x.getId(),x->x));
           Map<Long,List<Asset>> longListMap =
                    assets.stream().collect(Collectors.groupingBy(x->x.getSource()));


            return assembler.toModel(publicationResps.map(x->{
                Campaign campaign = longCampaignMap.get(x.getCampaign());
                Voucher voucher = longVoucherMap.get(x.getVoucher());
                List<Asset> assetList = longListMap.get(x.getVoucher());

            //    EntityModel entityModel = EntityModel.of(VoucherResp.from(Quartet.with(x,voucher,campaign,assetList),Optional.empty()));


                EntityModel entityModel = EntityModel.of(PublicationResp.sigleFrom(Quartet.with(Arrays.asList(x),voucher,campaign,assetList)));

                if(x.getPaied()){
                    entityModel.add(linkTo(methodOn(PaymentRestController.class).refund(x.getCharge(),null)).withRel("refund"));
                }

                return entityModel;
            }));



    }


    @GetMapping(value = "/publications", produces = "application/json")
    public List<PublicationResp> listPublicationResp(@RequestParam PublicationSearchPojo pojo) {

        Optional<Product> validatorOptional = productRepository.findById(1l);
        if(validatorOptional.isPresent()){
            try {
                return null;//publicationService.listPublication(null,pojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }



    //自己领券



    //给别人发券
    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/publications", produces = "application/json")
    public EntityModel<PublicationResp> createPublication(@PathVariable long CAMPAIGN_ID, @RequestBody @Valid PublicationPojo publicationPojo, final HttpServletRequest request) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        User user =  authenticationFacade.getUser(authentication);
        String ip = HttpUtils.getRequestIP(request);

        if(!user.isRealNameVerified()){

        }



        publicationPojo.setUser(user.getId());
        publicationPojo.setType(EnumPublicationObjectType.customer);


        try {
            deviceService.verifyDevice(user, request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";

/*        Client c = uaParser.parse(request.getHeader("user-agent"));

        System.out.println(c.userAgent.family); // => "Mobile Safari"
        System.out.println(c.userAgent.major);  // => "5"
        System.out.println(c.userAgent.minor);  // => "1"

        System.out.println(c.os.family);        // => "iOS"
        System.out.println(c.os.major);         // => "5"
        System.out.println(c.os.minor);         // => "1"

        System.out.println(c.device.family);    // => "iPhone"*/


        Session session = new Session();
        session.setIp(ip);
/*
        session.setBrowser(c.userAgent.family);
        session.setPlatform(c.os.family);
        session.setDevice(c.device.family);
        session.setVersion(c.userAgent.major+"."+c.userAgent.minor+"."+c.userAgent.patch);
*/

        System.out.println("_______________________________"+user);


        Optional<Campaign> validatorOptional = campaignRepository.findById(CAMPAIGN_ID);
        if(validatorOptional.isEmpty()) {
            System.out.println("找不到消费活动");
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到消费活动");
        }
        Campaign campaign = validatorOptional.get();
        if(!validatorOptional.get().isActive()) {
            throw new Campaign_inactiveException(campaign.getId(),Campaign.class.getSimpleName(),"活动未开放");
        }

        long exists = publicationEntryRepository.countByToWhoTypeAndToWhoAndCampaign(EnumPublicationObjectType.customer,user.getId(),campaign.getId());

        if(exists>=campaign.getClain_limit()){
            throw new ExistException(Enumfailures.invalid_product,"领券超出限额");
        }


        PublishTowhoVo publishTowhoVo = new PublishTowhoVo();

            if(isNull(publicationPojo.getType())){
                publicationPojo.setType(EnumPublicationObjectType.customer);
                publicationPojo.setUser(user.getId());

                publishTowhoVo.setToWho(user.getId());
                publishTowhoVo.setToWhoTyp(EnumPublicationObjectType.customer);
                publishTowhoVo.setUser(user);
            }else{
                if(publicationPojo.getType().equals(EnumPublicationObjectType.customer)){
                    Optional<User> objectUser = userRepository.findById(publicationPojo.getUser());
                    if(objectUser.isEmpty()){
                        System.out.println("抛出异常");
                        throw new BookNotFoundException(publicationPojo.getUser(),User.class.getSimpleName());
                    }
                    publishTowhoVo.setToWhoTyp(EnumPublicationObjectType.customer);
                    publishTowhoVo.setUser(objectUser.get());
                    publishTowhoVo.setToWho(publishTowhoVo.getUser().getId());
                }
                if(publicationPojo.getType().equals(EnumPublicationObjectType.business)){
                    Optional<Supplier> optionalSupplier = supplierRepository.findById(publicationPojo.getSupplier());
                    if(optionalSupplier.isEmpty()){
                        System.out.println("抛出异常");
                        throw new BookNotFoundException(publicationPojo.getSupplier(),Supplier.class.getSimpleName());
                    }
                    publishTowhoVo.setToWhoTyp(EnumPublicationObjectType.business);
                    publishTowhoVo.setSupplier(optionalSupplier.get());
                    publishTowhoVo.setToWho(publishTowhoVo.getSupplier().getId());
                }
                if(publicationPojo.getType().equals(EnumPublicationObjectType.traveler)){
                    Optional<Traveler> optionalTraveler = travelerRepository.findById(publicationPojo.getTraveler());
                    if(optionalTraveler.isEmpty()){
                        System.out.println("抛出异常");
                        throw new BookNotFoundException(publicationPojo.getTraveler(),Traveler.class.getSimpleName());
                    }
                    publishTowhoVo.setToWhoTyp(EnumPublicationObjectType.traveler);
                    publishTowhoVo.setTraveler(optionalTraveler.get());
                    publishTowhoVo.setToWho(publishTowhoVo.getTraveler().getId());
                }
            }

            System.out.println("====发给其他人发给谁：============================="+publishTowhoVo.toString());

        VoucherPublicationPaymentVo voucherPublicationPaymentVo = new VoucherPublicationPaymentVo();

        voucherPublicationPaymentVo.setFree(true);

            Quartet<PublicationEntry, Voucher,Campaign,PublishTowhoVo> voucherPair = publicationService.CreatePublication(campaign,publicationPojo,session,publishTowhoVo, voucherPublicationPaymentVo);


            List<Asset> assets = assetService.getQr(voucherPair.getValue1().getCode());

            return EntityModel.of(PublicationResp.from(Quintet.with(voucherPair.getValue0(),voucherPair.getValue1(),voucherPair.getValue2(),assets,voucherPair.getValue3())));

    }

}