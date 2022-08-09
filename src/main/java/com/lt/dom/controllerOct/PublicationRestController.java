package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.PublicationResp;
import com.lt.dom.OctResp.RedemptionEntryResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Campaign_inactiveException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PublicationPojo;
import com.lt.dom.otcReq.PublicationSearchPojo;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.DeviceService;
import com.lt.dom.serviceOtc.IAuthenticationFacade;
import com.lt.dom.serviceOtc.PublicationServiceImpl;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
//import ua_parser.Parser;
//import ua_parser.Client;




@RestController
@RequestMapping("/oct")
public class PublicationRestController {


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


/*
    @Autowired
    private Parser uaParser;
*/

    @GetMapping(value = "/users/{USER_ID}/publications", produces = "application/json")
    public PagedModel pageUserPublicationResp(@PathVariable long USER_ID, @ModelAttribute PublicationSearchPojo pojo, Pageable pageable, PagedResourcesAssembler<EntityModel<PublicationResp>> assembler) {

        Authentication authentication =  authenticationFacade.getAuthentication();


        User user = null;
        if(authentication == null){
            Optional<User> validatorOptional = userRepository.findByPhone("13468801684");
            if(validatorOptional.isEmpty()) {

                throw new BookNotFoundException(USER_ID,"找不到user");

            }
        }else{
            user =  authenticationFacade.getUser(authentication);
        }



            Page<PublicationEntry> publicationResps = publicationService.pagePublication(user,pojo,pageable);

            List<Campaign> campaigns = campaignRepository.findAllById(publicationResps.stream().map(x->x.getCampaign_id()).collect(Collectors.toList()));
            List<Voucher> vouchers = voucherRepository.findAllById(publicationResps.stream().map(x->x.getVoucherId()).collect(Collectors.toList()));
            List<Asset> assets = assetRepository.findAllByTypeAndIdIdIn(EnumAssetType.qr,vouchers.stream().map(x->x.getCode()).collect(Collectors.toList()));
            Map<Long,Campaign> longCampaignMap = campaigns.stream().collect(Collectors.toMap(x->x.getId(), x->x));
            Map<Long,Voucher> longVoucherMap = vouchers.stream().collect(Collectors.toMap(x->x.getId(),x->x));
           Map<Long,List<Asset>> longListMap =
                    assets.stream().collect(Collectors.groupingBy(x->x.getSource()));


            return assembler.toModel(publicationResps.map(x->{
                Campaign campaign = longCampaignMap.get(x.getCampaign_id());
                Voucher voucher = longVoucherMap.get(x.getVoucherId());
                List<Asset> assetList = longListMap.get(x.getVoucherId());
                return EntityModel.of(PublicationResp.sigleFrom(Quartet.with(x,voucher,campaign,assetList)));
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
            throw new BookNotFoundException(CAMPAIGN_ID,Campaign.class.getSimpleName());

        }
        Campaign campaign = validatorOptional.get();
        if(!validatorOptional.get().isActive()) {
            throw new Campaign_inactiveException(campaign.getId(),Campaign.class.getSimpleName(),"活动未开放");
        }


        PublishTowhoVo publishTowhoVo = new PublishTowhoVo();

            if(isNull(publicationPojo.getType())){
                publicationPojo.setType(EnumPublicationObjectType.customer);
                publicationPojo.setUser(user.getId());
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
                }
                if(publicationPojo.getType().equals(EnumPublicationObjectType.business)){
                    Optional<Supplier> optionalSupplier = supplierRepository.findById(publicationPojo.getSupplier());
                    if(optionalSupplier.isEmpty()){
                        System.out.println("抛出异常");
                        throw new BookNotFoundException(publicationPojo.getSupplier(),Supplier.class.getSimpleName());
                    }
                    publishTowhoVo.setToWhoTyp(EnumPublicationObjectType.business);
                    publishTowhoVo.setSupplier(optionalSupplier.get());
                }
                if(publicationPojo.getType().equals(EnumPublicationObjectType.traveler)){
                    Optional<Traveler> optionalTraveler = travelerRepository.findById(publicationPojo.getTraveler());
                    if(optionalTraveler.isEmpty()){
                        System.out.println("抛出异常");
                        throw new BookNotFoundException(publicationPojo.getTraveler(),Traveler.class.getSimpleName());
                    }
                    publishTowhoVo.setToWhoTyp(EnumPublicationObjectType.traveler);
                    publishTowhoVo.setTraveler(optionalTraveler.get());
                }
            }




            Quartet<PublicationEntry, Voucher,Campaign,PublishTowhoVo> voucherPair = publicationService.CreatePublication(validatorOptional.get(),publicationPojo,session,publishTowhoVo);



            List<Asset> assets = assetService.getQr(voucherPair.getValue1().getCode());

            return EntityModel.of(PublicationResp.from(Quintet.with(voucherPair.getValue0(),voucherPair.getValue1(),voucherPair.getValue2(),assets,voucherPair.getValue3())));

    }

}