package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.MerchantsSettledReq;
import com.lt.dom.otcReq.RequestEditReq;
import com.lt.dom.otcReq.RequestReq;
import com.lt.dom.otcReq.ReviewReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumRequestType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class RequestFuckRestController {
    @Autowired
    private ReviewerRepository reviewerRepository;


    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ApplyForApprovalServiceImpl applyForApprovalService;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    private TourBookingRepository tourBookingRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private MarchertSettledServiceImpl marchertSettledService;



    @GetMapping(value = "/requests/{REQUEST_ID}/approvers", produces = "application/json")
    public Request listAvailability(@PathVariable long REQUEST_ID) {

        Optional<Request> validatorOptional = requestRepository.findById(REQUEST_ID);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(REQUEST_ID,"找不到请求");
        }

        return validatorOptional.get();

    }


    @GetMapping(value = "/requests/{REQUEST_ID}/reviews", produces = "application/json")
    public List<Review> getReviews(@PathVariable long REQUEST_ID) {

        Optional<Request> validatorOptional = requestRepository.findById(REQUEST_ID);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(REQUEST_ID,"找不到请求");
        }

        List<Review> reviews = reviewRepository.findAll();

        return reviews;

    }











    @Operation(summary = "1、申请认证")
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/applications", produces = "application/json")
    public Request applyCertification(@PathVariable long SUPPLIER_ID, @RequestBody @Valid RequestReq pojo) {


        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){
            Request settleAccount = applyForApprovalService.apply(optionalSupplier.get(),pojo);

            return settleAccount;
        }else{
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");
        }


    }


    @GetMapping(value = "/requests/{REQUEST_ID}", produces = "application/json")
    public EntityModel<RequestResp> getRequest(@PathVariable long REQUEST_ID) {


        Authentication authentication = authenticationFacade.getAuthentication();

        Optional<Request> optionalSupplier = requestRepository.findById(REQUEST_ID);


        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(REQUEST_ID,"找不到申请");
        }
        Request request =optionalSupplier.get();



        List<Reviewer> reviewers = reviewerRepository.findAllByRequest(request.getId());





        RequestResp requestResp = null;//
        if(request.getType().equals(EnumRequestType.Merchants_settled)){


            MerchantsSettledReq merchantsSettledReq = MerchantsSettledReqResp.fromJsonString(request.getAdditional_info());

/*            Map<EnumDocumentType,TempDocument> enumDocumentTypeTempDocumentMap = fileStorageService.loadTempDocument(Arrays.asList(
                    Pair.with(EnumDocumentType.business_license,merchantsSettledReq.getBussiness_license()),
                    Pair.with(EnumDocumentType.license,merchantsSettledReq.getLicense_image()),
                    Pair.with(EnumDocumentType.liability_insurance,merchantsSettledReq.getLiability_insurance_image()),
                    Pair.with(EnumDocumentType.license_for_opening_bank_account,merchantsSettledReq.getLicense_for_opening_bank_account())
            ));*/

            Gson gson = new Gson();
            MerchantsSettledReq max = gson.fromJson(request.getAdditional_info(), MerchantsSettledReq.class);
            MerchantsSettledReqResp merchantsSettledReqResp = MerchantsSettledReqResp.from(max);


            merchantsSettledReqResp.setLiability_insurance_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.liability_insurance,request.getCode()));
            merchantsSettledReqResp.setLicense_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.license,request.getCode()));
            merchantsSettledReqResp.setBussiness_license(fileStorageService.loadDocumentWithDefault(EnumDocumentType.business_license,request.getCode()));
            merchantsSettledReqResp.setLicense_for_opening_bank_account(fileStorageService.loadDocumentWithDefault(EnumDocumentType.license_for_opening_bank_account,request.getCode()));



            requestResp = RequestResp.fromWithMearchantSettled(request,reviewers,merchantsSettledReqResp);


        }



        if(request.getType().equals(EnumRequestType.tour_approve)){
            Optional<TourBooking> optionalTourBooking = tourBookingRepository.findById(request.getSource());

            TourBooking tourBooking = optionalTourBooking.get();

            EntityModel entityModel1 = EntityModel.of(tourBooking);
            entityModel1.add(linkTo(methodOn(TourCampaignRestController.class).getReservation(tourBooking.getId())).withSelfRel());

            requestResp = RequestResp.fromWithTour_approve(request,reviewers,entityModel1);


        }


        EntityModel entityModel = EntityModel.of(requestResp);
        List<Review> review__s = reviewRepository.findAllByRequest(request.getId());

        requestResp.setReviews(review__s.stream().map(x->{
            return ReviewResp.from(x);
        }).collect(Collectors.toList()));



        //   entityModel.add(linkTo(methodOn(RequestRestController.class).createReviews(request.getId(),null)).withRel("addDocument_"));
        entityModel.add(linkTo(methodOn(TourCampaignRestController.class).getReservation(request.getId())).withRel("booking_url"));
    //    entityModel.add(linkTo(methodOn(RequestFuckRestController.class).addReviews(request.getId(),null)).withRel("addDocument____"));
        entityModel.add(linkTo(methodOn(RequestFuckRestController.class).getRequest(request.getId())).withRel("getRequest"));
        entityModel.add(linkTo(methodOn(RequestFuckRestController.class).getReviews(request.getId())).withRel("getReview"));
        entityModel.add(linkTo(methodOn(RequestFuckRestController.class).createRev(request.getId(),null)).withRel("addReview"));
        entityModel.add(linkTo(methodOn(RequestFuckRestController.class).createRequest(null)).withRel("createRequest"));


        entityModel.add(linkTo(methodOn(RequestFuckRestController.class).approveRequest(request.getId(),null)).withRel("createRequestApprove"));
        entityModel.add(linkTo(methodOn(RequestFuckRestController.class).rejectRequest(request.getId(),null)).withRel("createRequestReject"));


        return entityModel;
    }



    @Operation(summary = "1、申请")
    @PostMapping(value = "/requests", produces = "application/json")
    public Request createRequest( @RequestBody @Valid RequestReq pojo) {


        Authentication authentication = authenticationFacade.getAuthentication();

        Optional<Supplier> optionalSupplier = supplierRepository.findById(1l);

        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(1l,"找不到供应商");
        }



        Request settleAccount = applyForApprovalService.apply(optionalSupplier.get(),pojo);

        return settleAccount;



    }



    @Operation(summary = "1、申请")
    @PutMapping(value = "/requests/{REQUEST_ID}", produces = "application/json")
    public Request editRequest(@PathVariable long REQUEST_ID, @RequestBody @Valid RequestEditReq pojo) {


        System.out.println("==========="+ pojo.toString());
        Authentication authentication = authenticationFacade.getAuthentication();

        Optional<Request> optionalRequest = requestRepository.findById(REQUEST_ID);

        if(optionalRequest.isEmpty()) {
            throw new BookNotFoundException(REQUEST_ID,"找不到请求"+ EnumRequestType.Merchants_settled.name());
        }
        Request request = optionalRequest.get();

        MerchantsSettledReq merchantsSettledReq = pojo.getMerchantsSettledReq();
        merchantsSettledReq = marchertSettledService.validat(merchantsSettledReq);


        request = applyForApprovalService.edit(EnumRequestType.Merchants_settled,request,merchantsSettledReq);



        return request;



    }


    @Operation(summary = "1、拒绝")
    @PostMapping(value = "/requests/{REQUEST_ID}/rejects", produces = "application/json")
    public Request rejectRequest(@PathVariable long REQUEST_ID, @RequestBody @Valid ReviewReq pojo) {

        Optional<Request> optionalSupplier = requestRepository.findById(REQUEST_ID);
        if(optionalSupplier.isPresent()){
            Request settleAccount = applyForApprovalService.reject(optionalSupplier.get(),pojo);
            return settleAccount;
        }else{
            throw new BookNotFoundException(REQUEST_ID,"找不到请求"+ EnumRequestType.Merchants_settled.name());
        }
    }


    @Operation(summary = "1、批准申请")
    @PostMapping(value = "/requests/{REQUEST_ID}/approvals", produces = "application/json")
    public Request approveRequest(@PathVariable long REQUEST_ID, @RequestBody @Valid ReviewReq pojo) {

        Optional<Request> optionalSupplier = requestRepository.findById(REQUEST_ID);

        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(REQUEST_ID,"找不到请求"+ EnumRequestType.Merchants_settled.name());

        }
        Request settleAccount = applyForApprovalService.approve(optionalSupplier.get(),pojo);
        return settleAccount;


    }







    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/requests/page", produces = "application/json")
    public EntityModel page_getRequestList(@PathVariable long SUPPLIER_ID) {

/*

        Authentication authentication =  authenticationFacade.getAuthentication();

        UserDetails user = (UserDetails)authentication.getPrincipal();

        System.out.println(user.getAuthorities());*/

        Optional<Supplier> supplier = supplierRepository.findById(SUPPLIER_ID);
        if(supplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");

        }
        EntityModel entityModel =  EntityModel.of( Map.of(
                "request_type_list", Arrays.stream(EnumRequestType.values()).map(e->{
                    EnumResp resp = new EnumResp();
                    resp.setId(e.name());
                    resp.setText(e.toString());

                    return resp;

                }).collect(Collectors.toList())));

        entityModel.add(linkTo(methodOn(RequestFuckRestController.class).getRequestList(supplier.get().getId(),null,null)).withRel("getRequestList"));

        return entityModel;
    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/requests", produces = "application/json")
    public PagedModel getRequestList(@PathVariable long SUPPLIER_ID,
                                     @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,


                                     PagedResourcesAssembler<EntityModel<Request>> assembler) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);


        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(SUPPLIER_ID,"找不到供应商");
        }

        Page<Request> validatorOptional = requestRepository.findAll(pageable);


        List<Reviewer> reviewers = reviewerRepository.findAllByRequestIn(validatorOptional.stream().map(x->x.getId()).collect(Collectors.toList()));

        Map<Long,List<Reviewer>> reviewerMap = reviewers.stream().collect(Collectors.groupingBy(x->x.getRequest()));





        return assembler.toModel(validatorOptional.map(x->{



            EntityModel entityModel = EntityModel.of(RequestResp.from(x,reviewerMap.getOrDefault(x.getId(), Arrays.asList())));
            entityModel.add(linkTo(methodOn(RequestFuckRestController.class).approveRequest(x.getId(),null)).withRel("createRequestApprove"));
            entityModel.add(linkTo(methodOn(RequestFuckRestController.class).rejectRequest(x.getId(),null)).withRel("createRequestReject"));
            entityModel.add(linkTo(methodOn(RequestFuckRestController.class).getRequest(x.getId())).withSelfRel());



            return entityModel;
        }));

    }



/*
    @PostMapping(value = "/request_s/{REQUEST_ID}/review_s", produces = "application/json")
    public ResponseEntity<Review__> addReviews(@PathVariable long REQUEST_ID, ReviewReq reviewReq) {


    }
*/



/*
    @PostMapping(value = "/request_s/{RESERVATOIN_ID}/travelers", produces = "application/json")
    public ResponseEntity<Review__> bulkAddTravelers(@PathVariable long RESERVATOIN_ID, @RequestBody @Valid BulkTravelerReq transferPojo) {


    }
*/


    @Operation(summary = "1、申请")
    @PostMapping(value = "/requests/{RESERVATOIN_ID}/reviews", produces = "application/json")
    public ResponseEntity createRev(@PathVariable long RESERVATOIN_ID,@RequestBody @Valid ReviewReq pojo) {

        Optional<Request> validatorOptional = requestRepository.findById(RESERVATOIN_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到请求");
        }

        Review review = applyForApprovalService.addReviews(validatorOptional.get(),pojo);



        return ResponseEntity.ok(ReviewResp.from(review));



    }
}