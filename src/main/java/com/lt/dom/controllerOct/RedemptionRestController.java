package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Voucher_has_no_permistion_redeemException;
import com.lt.dom.error.voucher_disabledException;
import com.lt.dom.error.voucher_not_publishException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RedemBycodePojo;
import com.lt.dom.otcReq.RedemPojo;
import com.lt.dom.otcReq.ReviewReq;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.serviceOtc.RedeemServiceImpl;
import com.lt.dom.serviceOtc.RedemptionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class RedemptionRestController {

    @Autowired
    private VoucherRepository voucherRepository;


    @Autowired
    private RedemptionServiceImpl redemptionService;

    @Autowired
    private RedemptionRepository redemptionRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;


    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CampaignRepository campaignRepository;


    @Autowired
    private RedeemServiceImpl redeemService;
    @Autowired
    private EmployeeRepository employeeRepository;


    @PostMapping(value = "/redemptions/{REDEMPTION_ID}", produces = "application/json")
    public EntityModel<Redemption> getRedemption(@PathVariable long REDEMPTION_ID) {


        Optional<Redemption> validatorOptional = redemptionRepository.findById(REDEMPTION_ID);

        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(REDEMPTION_ID,"找不到核销");

        }


        EntityModel<Redemption> entityModel = EntityModel.of(validatorOptional.get());

        return entityModel;


    }

    @PostMapping(value = "/redemptions/{REDEMPTION_ID}/rollback", produces = "application/json")
    public List<Redemption> redemption(@PathVariable long REDEMPTION_ID) {


        Optional<Redemption> validatorOptional = redemptionRepository.findById(REDEMPTION_ID);

        if(validatorOptional.isPresent()){
            try {
                return redemptionService.rollback(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }





    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/redemption_entries", produces = "application/json")
    public PagedModel pageRedemptionEntry(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<RedemptionEntryResp>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){

            Page<RedemptionEntry> clainQuotas = redemptionEntryRepository.findAllBySupplier(optionalSupplier.get().getId(), PageRequest.of(0,10));


            return assembler.toModel(RedemptionResp.pageFrom(clainQuotas));
        }

        throw new RuntimeException();
    }


    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/redemption_entries/summary", produces = "application/json")
    public ResponseEntity<SummarySupplierRedemptionResp> getRedemptionEntrySummary(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){

            List<RedemptionEntry> clainQuotas = redemptionEntryRepository.findAllBySupplier(optionalSupplier.get().getId());



            SummarySupplierRedemptionResp summarySupplierRedemptionResp =SummarySupplierRedemptionResp.from(optionalSupplier.get(),clainQuotas);
            return ResponseEntity.ok(summarySupplierRedemptionResp);
        }

        throw new RuntimeException();
    }






    //TODO 这里有点问题
    @PostMapping(value = "/redemptions", produces = "application/json")
    public EntityModel<RedemptionResp> redeemVonchorBycode(Authentication authentication,@RequestBody RedemBycodePojo pojo) {



   //     Authentication authentication =  authenticationFacade.getAuthentication();

      //  UserDetails user_de = (UserDetails)authentication.getPrincipal();
     //   Optional<User> optionalUser = userRepository.findByPhone(user_de.getUsername());
        Optional<User> optionalUser = userRepository.findByPhone("13468801684");


        if(optionalUser.isEmpty()) {

            throw  new BookNotFoundException(pojo.getCode(),"找不到登录用户");

        }
        User user = optionalUser.get();
        Optional<Employee> optional = employeeRepository.findByUserId(user.getId());

        Optional<Supplier> optionalSupplier = supplierRepository.findById(optional.get().getSuplierId());

        // Asset asset = assetRepository.findById();
        Optional<Voucher> voucher = voucherRepository.findByCode(pojo.getCode());
        if(voucher.isEmpty()) {
            throw  new BookNotFoundException(pojo.getCode(),"找不到消费券");


        }
            if(!voucher.get().isActive()){
                throw new voucher_disabledException(voucher.get().getId(),"");
            }
            if(!voucher.get().getPublished()){
                throw new voucher_not_publishException(voucher.get().getId(),pojo.getCode()+"券换没有被认领","券换没有被认领");
            }
            Optional<Campaign> optionalCampaign = campaignRepository.findById(voucher.get().getCampaign());
            boolean v = redeemService.是否符合验证(optionalCampaign.get(),new Supplier());
            if(v){
                Triplet<RedemptionEntry,Redemption, PublishTowhoVo> pair= redeemService.bulkRedeemVounchor(voucher.get(),optionalCampaign.get(),optionalSupplier.get());
                return RedemptionResp.from(pair);
            }else{

                throw new Voucher_has_no_permistion_redeemException(voucher.get().getId(),"Foo Not Found","该商户无法核销该券");
            }




    }



















    @PostMapping(value = "/redemptions/{REDEMPTION_ID}/reviews", produces = "application/json")
    public Review createReviews(@PathVariable long REDEMPTION_ID,@RequestBody ReviewReq pojo) {


        Optional<Redemption> validatorOptional = redemptionRepository.findById(REDEMPTION_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(REDEMPTION_ID,"找不到核销");
        }
        Redemption redemption = validatorOptional.get();
        Review review = new Review();
        review.setCharge(redemption.getCode());
        review.setCreated_at(LocalDateTime.now());
        review.setOpen(true);
        review = reviewRepository.save(review);


        return review;

    }




}