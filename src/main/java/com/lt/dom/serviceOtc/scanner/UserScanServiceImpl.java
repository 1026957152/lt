package com.lt.dom.serviceOtc.scanner;


import com.lt.dom.OctResp.RedemptionTryResp;
import com.lt.dom.controllerOct.RedemptionRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RedemBycodePojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.*;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserScanServiceImpl {
    Logger logger = LoggerFactory.getLogger(UserScanServiceImpl.class);



    @Autowired
    private MovieProductRepository movieProductRepository;
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ComponentVounchRepository componentVounchRepository;




    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ValidatorRepository validatorRepository;
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;


    @Autowired
    private RightRedemptionEntryRepository rightRedemptionEntryRepository;

    @Autowired
    private RedemptionServiceImpl redemptionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherTicketRepository voucherTicketRepository;
    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private AvailabilityServiceImpl availabilityService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;



    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private TheatreRepository theatreRepository;


    private static final EnumProductType productType = EnumProductType.Multi_Ticket;
    private static final EnumLineType lineType = EnumLineType.Multi_Ticket;
    private static final EnumVoucherType voucherType = EnumVoucherType.Multi_Ticket;




    public EntityModel validate(RedemBycodePojo.Code code, UserVo userVo) {



        if(!code.getCode().startsWith("use_")) {

            return null;
        }
            Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findByCode(code.getCode());


            if(optionalVoucher.isEmpty()) {
                throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到门票"+code.getCode());


            }
            VoucherTicket voucher =optionalVoucher.get();
            if(!voucher.isActive()){
                throw new BookNotFoundException(Enumfailures.voucher_not_active,"该券状态不活跃");
            }


        List<Validator_> validator_s = validatorRepository.findAllByTypeAndUser(EnumValidatorType.特定的人员,userVo.getUser_id());

        if(validator_s.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"职工得 核销分配 对象 为空，请添加"+userVo.getPhone());
        }
        List<Long> triplet来自设备 =
                validator_s.stream().map(e->e.getComponentRightId()).collect(Collectors.toList());


        List<ComponentVounch> components = componentVounchRepository.findAllByReference(voucher.getCode());

        if(components.size() ==0){
            throw new BookNotFoundException(Enumfailures.not_found,"该券 无可核销得 权益"+voucher.getCode());
        }


        List<ComponentVounch> componentVounchList = components.stream()  // TODO 找到了这里的 权限
           //     .filter(e-> triplet来自设备.contains(e.getComponentRight()))
                .filter(e-> e.getType().equals(EnumComponentVoucherType.Right))
                .map(e->{
                        Optional<ComponentRight> componentRight = componentRightRepository.findById(e.getComponentRight());
                        e.setName(componentRight.get().getName());
                    return e;
                }).collect(Collectors.toList());



        List<Product> productList = productRepository.findAllBySupplierId(userVo.getSupplier().getId());

        List<Long> longList_公司允许的 = productList.stream().map(e->e.getId()).collect(Collectors.toList());



        List<ComponentVounch> burdle_vouchers = components.stream()  // TODO 找到了这里的 权限
                .filter(e-> e.getType().equals(EnumComponentVoucherType.Burdle))
                .filter(e->longList_公司允许的.contains(e.getOriginalProduct()))
                .map(e->{
                    return e;
                }).collect(Collectors.toList());


        componentVounchList.addAll(burdle_vouchers);




        RedemptionTryResp resp = new RedemptionTryResp();
        resp.setType_text(EnumRedeamptionType.VOUCHER.toString());


        RedemptionTryResp.PhotoId photoId = new RedemptionTryResp.PhotoId();

        photoId.setRealname(true);

        photoId.setFaceImage(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setDateOfbirth("ddd");

        photoId.setSelfie(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setDocument_front(fileStorageService.loadDocumentWithDefault(EnumDocumentType.card_cover,voucher.getCode()));

        photoId.setName("ddd");

        resp.setPhotoId(photoId);


        resp.setEntries(componentVounchList.stream()
                .filter(e->!(e.getStatus().equals(EnumComponentVoucherStatus.AlreadyRedeemed)))
                .map(e->{
            RedemptionTryResp.RedemptionEntryResp redemptionEntryResp = new RedemptionTryResp.RedemptionEntryResp();



            redemptionEntryResp.setLable(e.getName());
            redemptionEntryResp.setLimit(e.getLimit().intValue());
            redemptionEntryResp.setRemaining(e.getTry_().intValue());

                //    redemptionEntryResp.setRemaining(Long.valueOf(e.getLimit()-e.getRedeemed_quantity()).intValue());
            redemptionEntryResp.setCheck_in(triplet来自设备.contains(e.getComponentRight()));
            return redemptionEntryResp;

        })
                .sorted(Comparator.comparing(RedemptionTryResp.RedemptionEntryResp::isCheck_in))
                .collect(Collectors.toList()));



        resp.setCrypto_code(voucher.getCode());

        EntityModel redemptionEntryEntityModel =  EntityModel.of(resp);


        redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchor(null)).withRel("redeemByCryptoCode"));
        redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).redeemVonchorByCryptoCode(null)).withRel("redeem"));

        return redemptionEntryEntityModel;



    }



    public EntityModel redeem(CodeWithLatLngVo code, UserVo userVo) {


        logger.debug("核销联票类型");

        Optional<User> user = userRepository.findById(userVo.getUser_id());


        if(!code.getC().startsWith("tike")) {

            return null;
        }
        Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findByCode(code.getC());


        if(optionalVoucher.isEmpty()) {
            throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到门票"+code.getC());
        }
        VoucherTicket voucher =optionalVoucher.get();


        if(voucher.getStatus().equals(EnumVoucherStatus.Redeemed)){
            throw new ExistException(Enumfailures.invalid_voucher,"该券已核销,无法再次核销");
        }


        if(!voucher.isActive()){
            throw new BookNotFoundException(Enumfailures.voucher_not_active,"该券状态不活跃");
        }




        List<ComponentVounch> componentVounchList = validatorService.check(userVo.getUser_id(), userVo.getSupplier().getId(),voucher.getCode());




        logger.debug("核销联票类型 多少"+componentVounchList.size());
        ValidatedByTypeVo verifier核销人员 = new ValidatedByTypeVo();
        verifier核销人员.setValidatorType(EnumValidatorType.特定的人员);

        verifier核销人员.setUser(user.get());

        User traveler用户 = userRepository.findById(voucher.getUser()).get();

        RedemptionForObjectVo redemptionForObjectVo = new RedemptionForObjectVo();
        redemptionForObjectVo.setRelatedObjectId(voucher.getId());
        redemptionForObjectVo.setRelatedObjectType(EnumRelatedObjectType.voucher);
        redemptionForObjectVo.setRelatedObjectCode(voucher.getCode());
        redemptionForObjectVo.setRelatedObject_subType(voucher.getType().name());
        redemptionForObjectVo.setRelatedObject_subType(voucher.getLable());


        RedemptionForCustomerVo redemptionForCustomerVo = new RedemptionForCustomerVo();
        redemptionForCustomerVo.setId(traveler用户.getId());
        redemptionForCustomerVo.setRealName(traveler用户.getRealName());
        redemptionForCustomerVo.setCode(traveler用户.getCode());


        Pair<Redemption,List<RightRedemptionEntry>> redemptionListPair= redemptionService.redeemRight(redemptionForObjectVo,verifier核销人员,
                Optional.of(redemptionForCustomerVo),componentVounchList);

        Redemption redemption = redemptionListPair.getValue0();
        List<RightRedemptionEntry> redemptionEntryList = redemptionListPair.getValue1();

        redemptionEntryList = rightRedemptionEntryRepository.saveAll(redemptionEntryList);




        List<ComponentVounch> component_for_update_voucher = componentVounchRepository.findAllByReference(voucher.getCode());

        if(component_for_update_voucher
                .stream().filter(e->e.getStatus().equals(EnumComponentVoucherStatus.PartialyRedeemed))
                .findAny().isPresent()){
            voucher.setStatus(EnumVoucherStatus.PartiallyRedeemed);
        };
        if(component_for_update_voucher
                .stream().filter(e->!e.getStatus().equals(EnumComponentVoucherStatus.AlreadyRedeemed))
                .findAny().isEmpty()){
            voucher.setStatus(EnumVoucherStatus.Redeemed);
        };

        voucherTicketRepository.save(voucher);



        EntityModel redemptionEntryEntityModel =  EntityModel.of(Map.of("dd",redemptionEntryList));


        return redemptionEntryEntityModel;



    }


}
