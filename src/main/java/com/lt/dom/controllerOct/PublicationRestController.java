package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.PublicationResp;
import com.lt.dom.OctResp.RedemptionEntryResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PublicationPojo;
import com.lt.dom.otcReq.PublicationSearchPojo;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.DeviceService;
import com.lt.dom.serviceOtc.IAuthenticationFacade;
import com.lt.dom.serviceOtc.PublicationServiceImpl;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
    private VoucherRepository voucherRepository;

    @Autowired
    private DeviceService deviceService;

/*
    @Autowired
    private Parser uaParser;
*/

    @GetMapping(value = "/users/{USER_ID}/publications", produces = "application/json")
    public PagedModel pageUserPublicationResp(@PathVariable long USER_ID, @RequestBody PublicationSearchPojo pojo, Pageable pageable, PagedResourcesAssembler<EntityModel<PublicationResp>> assembler) {

        Optional<User> validatorOptional = userRepository.findById(USER_ID);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(USER_ID,"找不到user");

        }
            Page<PublicationEntry> publicationResps = publicationService.pagePublication(validatorOptional.get(),pojo,pageable);

            List<Campaign> campaigns = campaignRepository.findAllById(publicationResps.stream().map(x->x.getCampaign_id()).collect(Collectors.toList()));
            List<Voucher> vouchers = voucherRepository.findAllById(publicationResps.stream().map(x->x.getVoucherId()).collect(Collectors.toList()));
            List<Asset> assets = assetRepository.findAllBySourceIn(publicationResps.stream().map(x->x.getVoucherId()).collect(Collectors.toList()));
            Map<Long,Campaign> longCampaignMap = campaigns.stream().collect(Collectors.toMap(x->x.getId(), x->x));
            Map<Long,Voucher> longVoucherMap = vouchers.stream().collect(Collectors.toMap(x->x.getId(),x->x));
           Map<Long,List<Asset>> longListMap =
                    assets.stream().collect(Collectors.groupingBy(x->x.getSource()));


            return assembler.toModel(publicationResps.map(x->{
                Campaign campaign = longCampaignMap.get(x.getCampaign_id());
                Voucher voucher = longVoucherMap.get(x.getVoucherId());
                List<Asset> assetList = longListMap.get(x.getVoucherId());
                return EntityModel.of(PublicationResp.from(Quartet.with(x,voucher,campaign,assetList)));
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




    @PostMapping(value = "/campaigns/{CAMPAIGN_ID}/publications", produces = "application/json")
    public EntityModel<PublicationResp> createPublication(@PathVariable long CAMPAIGN_ID, @RequestBody @Valid PublicationPojo publicationPojo, final HttpServletRequest request) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        UserDetails user_ = (UserDetails)authentication.getPrincipal();

        Optional<User> user__ = userRepository.findByUsername(user_.getUsername());
        if(user__.isEmpty()){
            throw new BookNotFoundException("","找不到用户");
        }
        try {
            deviceService.verifyDevice(user__.get(), request);
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

        System.out.println("_______________________________"+user_);

        //publicationPojo.
        Optional<Campaign> validatorOptional = campaignRepository.findById(CAMPAIGN_ID);
        if(validatorOptional.isPresent()){

            if(isNull(publicationPojo.getType())){
                publicationPojo.setType(EnumPublicationObjectType.customer);
                Optional<User> user = userRepository.findByPhone(user_.getUsername());
                if(user.isEmpty()){
                    System.out.println("抛出异常");
                    throw new BookNotFoundException(user_.getUsername(),User.class.getSimpleName());
                }
                publicationPojo.setUser(user.get().getId());

            }else{
                if(publicationPojo.getType().equals(EnumPublicationObjectType.customer)){
                    Optional<User> user = userRepository.findById(publicationPojo.getUser());
                    if(user.isEmpty()){
                        System.out.println("抛出异常");
                        throw new BookNotFoundException(publicationPojo.getUser(),User.class.getSimpleName());
                    }
                }
            }

            Triplet<PublicationEntry, Voucher,Campaign> voucherPair = publicationService.CreatePublication(validatorOptional.get(),publicationPojo,session);
            List<Asset> assets = assetRepository.findAllBySource(voucherPair.getValue1().getId());

            return EntityModel.of(PublicationResp.from(Quartet.with(voucherPair.getValue0(),voucherPair.getValue1(),voucherPair.getValue2(),assets)));
        }else{
            System.out.println("抛出异常");
            throw new BookNotFoundException(CAMPAIGN_ID,Campaign.class.getSimpleName());
        }
    }

}