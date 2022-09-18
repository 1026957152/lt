package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.error.*;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.ProductSubVo;
import com.lt.dom.serviceOtc.*;

import com.lt.dom.state.WorkflowOrderService;
import com.lt.dom.util.CheckIdcardIsLegal;
import com.lt.dom.util.HttpUtils;
import com.lt.dom.vo.FlowButtonVo;
import com.lt.dom.vo.TravelerVo;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
//import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//https://caroly.fun/archives/springboot%E6%95%B4%E5%90%88%E5%BE%AE%E4%BF%A1%E6%94%AF%E4%BB%98

@RestController
@RequestMapping("/oct")
public class TourCampaignRestController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ImportCVSServiceImpl importCVSService;


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TourBookingRepository tourBookingRepository;


    @Autowired
    private CampaignAssignToTourProductRepository campaignAssignToTourProductRepository;

    @Autowired
    private RealNameAuthenticationServiceImpl realNameAuthenticationService;
    @Autowired
    private TourCampaignServiceImpl tourCampaignService;
    @Autowired
    private TempDocumentRepository tempDocumentRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private SupplierRepository supplierRepository;


    @Autowired
    private CampaignServiceImpl campaignService;
    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CampaineAssignToBookingServiceImpl campaineAssignToBookingService;

    @Autowired
    private GuideRepository guideRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private AssetServiceImpl assetService;

    @Autowired
    private PublicationServiceImpl publicationService;

    @Autowired
    private CampaignAssignToTourBookingRepository campaignAssignToTourBookingRepository;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleServiceImpl roleService;


    @Autowired
    private ApplyForApprovalServiceImpl applyForApprovalService;

    @Autowired
    private WorkflowOrderService workflowOrderService;



/*
    @Autowired
    private StateMachinePersister<ApplicationReviewStates, ApplicationReviewEvents, String > persister;*/


/*
    @Autowired
    private StateMachineFactory<ApplicationReviewStates, ApplicationReviewEvents> stateMachineFactory;

*/



 /*   @Autowired
    private StateMachine<ApplicationReviewStates, ApplicationReviewEvents> stateMachine;*/

/*

    private StateMachine<ApplicationReviewStates, ApplicationReviewEvents> build(Long employeeId){
*/
/*        Optional<Employee> byId = employeeRepository.findById(employeeId);
        Employee employee = byId.get();*//*

        StateMachine<ApplicationReviewStates, ApplicationReviewEvents> stateMachine = stateMachine.startReactively();
        stateMachine.stop();
        stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<>() {
                @Override
                public void preStateChange(State<ApplicationReviewStates, ApplicationReviewEvents> state,
                                           Message<String> message,
                                           Transition<ApplicationReviewStates, ApplicationReviewEvents> transition,
                                           StateMachine<ApplicationReviewStates, ApplicationReviewEvents> stateMachine,
                                           StateMachine<ApplicationReviewStates, ApplicationReviewEvents> stateMachine1) {
*/
/*                    Optional.ofNullable(message).ifPresent(msg -> {
                        Long employeeId = Long.class.cast(msg.getHeaders().getOrDefault("EMPLOYEE_ID", -1));
                        Employee employee = employeeRepository.getById(employeeId);
                        employee.setState(state.getId());
                        employeeRepository.save(employee);
                    });*//*

                }
            });
            sma.resetStateMachine(new DefaultStateMachineContext<>(EmployeeState.valueOf(employee.getState().name()), null, null,  null));
        });
        stateMachine.start();
        return stateMachine;
    }
*/









    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/tour_bookings/{RESERVATOIN_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<TourbookingTourResp>> getReservation(@PathVariable long RESERVATOIN_ID) {


        //stateMachineFactory.getStateMachine();


        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo user = authenticationFacade.getUserVo(authentication);

     /*   StateMachine<ApplicationReviewStates, ApplicationReviewEvents> firstStateMachine = stateMachineFactory.getStateMachine();

      //  stateMachine.startReactively();

        persister
        try {
            persister.persist(firstStateMachine,firstStateMachine.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Optional<TourBooking> optionalProduct = tourBookingRepository.findById(RESERVATOIN_ID);



        if(optionalProduct.isEmpty()) {
                System.out.println("找不到产品");
                throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }
            TourBooking tourBooking = optionalProduct.get();

            if(optionalProduct.get().getProductType().equals(EnumProductType.Daytour)){


                Optional<Product> product = productRepository.findById(optionalProduct.get().getProductId());
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getTypeTo());
                List<Traveler> travelers = travelerRepository.findAllByBooking(tourBooking.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(tourBooking.getId());
                Optional<Asset> assetResp  = assetService.getQrOptional(tourBooking.getCode());





                List<PublicationEntry> publicationEntryList = publicationService.list(EnumAssociatedType.tour_booking,tourBooking.getId());



                List<CampaignAssignToTourBooking> campaignAssignToTourBookings = campaignAssignToTourBookingRepository.findByTourBooking(tourBooking.getId());
                List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourBookings.stream().map(x->x.getCampaign()).collect(Collectors.toSet()));

                Map<Long,Campaign> longCampaignMap = campaigns.stream().collect(Collectors.toMap(x->x.getId(),x->x));




                Map<Long,List<PublicationEntry>> longListMap= publicationEntryList.stream().collect(Collectors.groupingBy(x->x.getCampaign()));



                List cc = campaignAssignToTourBookings.stream().map(x->{
                    Campaign campaign = longCampaignMap.get(x.getCampaign());

                    CampaignResp.CampaignSummary campaignSummary = CampaignResp.CampaignSummary.from(campaign);

                    EntityModel entityModel = EntityModel.of(Map.of("name",campaign.getName(),
                            "code",x.getCode(),
                            "total_count",travelers.size(),
                            "claim_count",-1,
                            "redeemed_count",x.getRedeemed_count(),
                            "unredeemed_count",x.getUnredeemed_count(),
                            "summary",campaignSummary));

                    entityModel.add(linkTo(methodOn(CampaignRestController.class).getCampaign(campaign.getId())).withSelfRel());


                    return entityModel;



                }).collect(Collectors.toList());




                TourbookingTourResp reservationTourResp = TourbookingTourResp.toResp(Quintet.with(tourBooking,product.get(),optionalTour.get(),travelers,documents),assetResp.get());
               // reservationTourResp.setDocuments(DocumentResp.Listfrom(documents));
                reservationTourResp.setDocumentGroups(DocumentResp.groupfrom(documents));
              //  reservationTourResp.setPublication_entrys(cc);
                reservationTourResp.setCampaigns(cc);
/*

                publicationEntryList.stream()
                        .collect(Collectors.groupingBy(x->longCampaignMap.get(x.getCampaign_id()).getName(),collectingAndThen(toList(),list->{
                            return list.stream().map(xx->{
                                return xx;
                            }).collect(toList());
                        }))).entrySet().stream().map(x->{

                            List<PublicationEntry> list = x.getValue();

                            return Map.of("name",x.getKey(),"claim_count",list.size(),"items",x.getValue());
                        }).collect(Collectors.toList());
*/






                Optional<RequestResp> request = applyForApprovalService.getRequst(EnumRequestType.tour_approve,tourBooking.getCode());

                if(request.isPresent()){
                    reservationTourResp.setReviews(request.get().getReviews());
                }else{
                    reservationTourResp.setReviews(Arrays.asList());
                }


                EntityModel entityModel = EntityModel.of(reservationTourResp);
                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).bulkAddTravelers(tourBooking.getId(),null)).withRel("addBulkTraveler"));
                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).createDocuments(tourBooking.getId(),new BookingDocumentIdsResp())).withRel("addDocument"));
                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).addTravelers(tourBooking.getId(),null)).withRel("addTraveler"));


                if(roleService.valid(user.getRoles(),EnumRole.ROLE_TRAVEL_AGENCY)){
                    if(tourBooking.getStatus().equals(EnumTourBookingStatus.Draft)){

                        Link link = linkTo(methodOn(TourCampaignRestController.class).商户初次提交(tourBooking.getId(),null)).withRel(EnumRequestFlow.商户初次提交.id);
                        entityModel.add(link);

                        reservationTourResp.setFlowButton(new FlowButtonVo(EnumRequestApprove.request,link,EnumRequestFlow.商户初次提交.id));
                    }
                    if(tourBooking.getStatus().equals(EnumTourBookingStatus.AwaitingBill_photo_image)){

                        Link link = linkTo(methodOn(TourCampaignRestController.class).商户二次提交(tourBooking.getId(),null)).withRel(EnumRequestFlow.商户二次提交.id);
                        entityModel.add(link);
                        reservationTourResp.setFlowButton(new FlowButtonVo(EnumRequestApprove.request,link,EnumRequestFlow.商户二次提交.id));
                    }
                }
                if(roleService.valid(user.getRoles(),EnumRole.ROLE_ADMIN)){
                    if(tourBooking.getStatus().equals(EnumTourBookingStatus.Pending)){
                        Link link = linkTo(methodOn(TourCampaignRestController.class).旅投初次审核(tourBooking.getId(),null)).withRel(EnumRequestFlow.旅投初次审核.id);
                        entityModel.add(link);
                        reservationTourResp.setFlowButton(new FlowButtonVo(EnumRequestApprove.examine,link,EnumRequestFlow.旅投初次审核.id));
                    }
                    if(tourBooking.getStatus().equals(EnumTourBookingStatus.PendingBill_photo_image)){

                        Link link = linkTo(methodOn(TourCampaignRestController.class).旅投二次审核(tourBooking.getId(),null)).withRel(EnumRequestFlow.旅投二次审核.id);
                        entityModel.add(link);
                        reservationTourResp.setFlowButton(new FlowButtonVo(EnumRequestApprove.examine,link,EnumRequestFlow.旅投二次审核.id));
                    }
                }

                if(roleService.valid(user.getRoles(),EnumRole.ROLE_GOVERNMENT)){

                    if(tourBooking.getStatus().equals(EnumTourBookingStatus.AwaitingFundAppropriation)){

                        Link link = linkTo(methodOn(TourCampaignRestController.class).文旅局审核(tourBooking.getId(),null)).withRel(EnumRequestFlow.文旅局审核.id);
                        entityModel.add(link);
                        reservationTourResp.setFlowButton(new FlowButtonVo(EnumRequestApprove.examine,link,EnumRequestFlow.文旅局审核.id));
                    }
                }
                return ResponseEntity.ok(entityModel);

            }
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));


    }







    @GetMapping(value = "/tour_bookings/page", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_pageReservation() {



        EntityModel entityModel = EntityModel.of(Map.of("status_list", Arrays.stream(EnumTourBookingStatus.values()).map(x->{

                    EnumResp enumResp = new EnumResp();

                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())
        ));

        entityModel.add(linkTo(methodOn(TourCampaignRestController.class).pageReservation(null,null)).withRel("upload_bussiness_license_url"));
        return entityModel;
    }







    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/tour_bookings", produces = "application/json")
    public ResponseEntity<PagedModel> pageReservation( Pageable pageReq , PagedResourcesAssembler<EntityModel<TourbookingTourResp>> assembler) {



        Page<TourBooking> optionalProduct = tourBookingRepository.findAll(pageReq);


        Page<EntityModel<TourbookingTourResp>> page =  optionalProduct.map(x->{

            if(x.getProductType().equals(EnumProductType.Daytour)){
                Optional<Product> product = productRepository.findById(x.getProductId());
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getTypeTo());
                List<Traveler> travelers = travelerRepository.findAllByBooking(x.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(x.getId());


                Optional<Asset> asset = assetService.getQrOptional(x.getCode());
                TourbookingTourResp resp = TourbookingTourResp.toResp(Quintet.with(x,product.get(),optionalTour.get(),travelers,documents),asset.get());

                EntityModel entityModel = EntityModel.of(resp);

                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).bulkAddTravelers(x.getId(),null)).withRel("addTraveler"));
                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).createDocuments(x.getId(),new BookingDocumentIdsResp())).withRel("addDocument"));
                try {
                    entityModel.add(linkTo(methodOn(TourCampaignRestController.class).importExcelSubjectPreview(x.getId(),null)).withRel("addDocument"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return entityModel;
            }else{
                return EntityModel.of(new TourbookingTourResp());
            }
        });

        return ResponseEntity.ok(assembler.toModel(page));



    }








    @GetMapping(value = "/tour_bookings/Page_createTourBooking", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_createTourBooking() {

        List<Product> products = productRepository.findAll();



        Map<Long,Tour> longTourMap = tourRepository.findAllById(products.stream().map(x->x.getTypeTo()).collect(Collectors.toSet()))
                .stream().collect(Collectors.toMap(e->e.getProduct(),e->e));


        List<CampaignAssignToTourProduct> campaignAssignToTourProducts = campaignAssignToTourProductRepository.findByProductIn(products.stream().map(x->x.getId()).collect(toList()));

        Map<Long,Campaign> campaignMap = campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),x->x));

        Map<Long,List<Campaign>> campaignMap1 = campaignAssignToTourProducts.stream()
                .collect(Collectors.groupingBy(x->x.getProduct(),collectingAndThen(toList(),list->{
            return list.stream().map(xx->{
                return campaignMap.get(xx.getCampaign());
            }).collect(toList());

        })));


        EntityModel entityModel = EntityModel.of(Map.of(
                "product_list", products.stream().map(x->{


                   Tour tour =  longTourMap.get(x.getId());

                    List<Object>  aaa = campaignMap1.get(x.getId()).stream().map(x_->{
                        EnumLongIdResp enumResp_ = new EnumLongIdResp();
                        enumResp_.setId(x_.getId());
                        enumResp_.setText(x_.getName());
                        enumResp_.setInfo(x_.getName()+"_"+x_.getCode());
                        return enumResp_;

                    }).collect(toList());

                    EnumLongIdResp enumResp = new EnumLongIdResp();
                    enumResp.setId(x.getId());
                    enumResp.setText(x.getName());
                    enumResp.setInfo(Map.of("campaigns",aaa,"line_inf",tour.getLine_info()));
                    return enumResp;
                }).collect(toList())));


        entityModel.add(linkTo(methodOn(SearchRestController.class).searchCampaign(null,null,null)).withRel("searchCampaigns"));

        entityModel.add(linkTo(methodOn(DocumentRestController.class).createDocuments(null)).withRel("importCRV"));
        entityModel.add(linkTo(methodOn(TourCampaignRestController.class).createTourBooking(null,null)).withRel("createTourBooking"));
      //  Link link = new Link(FileStorageServiceImpl.url("templete_import_traveler.xlsx"),"getTemplete_import_traveler");
     //   Link link =  linkTo(FileUploadController.class).slash(""+"templete_import_traveler.xlsx").withRel("getTemplete_import_traveler");

        entityModel.add(FileStorageServiceImpl.url_static("templete_import_traveler.xlsx","downloadTemplate_import_traveler_excel"));


        try {
            entityModel.add(linkTo(methodOn(TourCampaignRestController.class).importExcel(null)).withRel("importTraveler"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityModel.add(linkTo(methodOn(SearchRestController.class).searchGuide(null,null,null)).withRel("searchGuide"));



        return entityModel;
    }









    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/tour_bookings", produces = "application/json")
    public ResponseEntity createTourBooking(@RequestBody @Valid TourBookingPojo pojo,final HttpServletRequest request) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        UserVo user = authenticationFacade.getUserVo(authentication);

        String ip = HttpUtils.getRequestIP(request);

        Optional<Product> optionalProduct = productRepository.findById(pojo.getProductId());

        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(pojo.getProductId(), "找不到产品");
        }

        Product product = optionalProduct.get();

            List<Campaign> campaigns = campaignService.getQualification(product);

            if(campaigns.size() == 0){
                throw new BookNotFoundException(Enumfailures.no_voucher_suitable_for_publication.name(),"没有 优惠券配额");
               // throw new No_voucher_suitable_for_publicationException(0,Reservation.class.getSimpleName(),"没有 优惠券配额");
            }

            Optional<Guide> guideOptional = guideRepository.findById(pojo.getTour().getGuide());
            if(guideOptional.isEmpty()){
                throw new BookNotFoundException(0,"找不到导游");
            }

            Guide guide = guideOptional.get();




        List<Pair<EnumDocumentType,String>> docTypeWithDocCodepairList = new ArrayList<>();
        if(nonNull(pojo.getContract_files()))
            docTypeWithDocCodepairList.addAll(pojo.getContract_files().stream().map(x->Pair.with(EnumDocumentType.contract,x)).collect(toList()));
        if(nonNull(pojo.getInsurance_policy_files()))
            docTypeWithDocCodepairList.addAll(pojo.getInsurance_policy_files().stream().map(x->Pair.with(EnumDocumentType.insurance_policy,x)).collect(toList()));
/*        if(nonNull(pojo.getPhoto_files()))
            docTypeWithDocCodepairList.addAll(pojo.getPhoto_files().stream().map(x->Pair.with(EnumDocumentType.photo,x)).collect(toList()));
        if(nonNull(pojo.getBill_files()))
            docTypeWithDocCodepairList.addAll(pojo.getBill_files().stream().map(x->Pair.with(EnumDocumentType.bill,x)).collect(toList()));*/

        System.out.println("--getInsurance_policy_files------------------------"+pojo.getInsurance_policy_files());



        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(docTypeWithDocCodepairList.stream().map(x->x.getValue1()).distinct().collect(toList()));

        System.out.println("------------------------------------------------"+tempDocuments.toString());

        if(tempDocuments.size() ==0){
            throw new BookNotFoundException(1,"找不到上传文件");
        }

        Map<String,TempDocument> documentMap = tempDocuments.stream().collect(Collectors.toMap(x->x.getCode(),x->x));

        List<Pair<EnumDocumentType,TempDocument>> docTypeWithTempDocPairList = docTypeWithDocCodepairList.stream().map(x->{
            TempDocument tempDocument = documentMap.get(x.getValue1());
            return Pair.with(x.getValue0(),tempDocument);
        }).collect(toList());






        if(docTypeWithTempDocPairList.isEmpty()){
            throw new ExistException(Enumfailures.missing_documents,"需要附上申请文档， 合同，合影照片，保险单和发票");
        }

        List<TravelerReq> travelerReqList = pojo.getTravelers();

        List<PhoneAuth> travelerReqs = travelerReqList.stream().map(x->{
            PhoneAuth auth = new PhoneAuth();
            auth.setIdCardName(x.getName());
            auth.setIdCardNo(x.getId());
            auth.setPhoneNo(x.getTel_home());
            return auth;
        }).collect(Collectors.toList());

        Pair<List<PhoneAuth>, List<PhoneAuth>> phoneAuthPhoneAuthPair = realNameAuthenticationService.bulkCheckRealname(travelerReqs);

        List<TravelerVo> travelerVoList = phoneAuthPhoneAuthPair.getValue1().stream().map(x->{
            TravelerVo traveler = new TravelerVo();
            traveler.setId(x.getIdCardNo());
            traveler.setName(x.getIdCardName());
            traveler.setTel_home(x.getPhoneNo());
            return traveler;
        }).collect(Collectors.toList());



        Session session = new Session();
        session.setIp(ip);


        Triplet<TourBooking,Product, ProductSubVo> booking = tourCampaignService.book(
                product,
                campaigns,
                guide,
                pojo,travelerVoList,user.getSupplier(),session);

        TourBooking tourBooking = booking.getValue0();


        workflowOrderService.旅行社新建(new Date(),tourBooking);


        List<Document> documents = fileStorageService.saveFromTempDocumentCode(tourBooking.getId(),docTypeWithTempDocPairList);


            ReservationTourResp resp = ReservationTourResp.toResp(Quartet.with(booking.getValue0(),booking.getValue1(),booking.getValue2(),Collections.EMPTY_LIST));

            return ResponseEntity.ok(resp);



    }































    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/travelers", produces = "application/json")
    public ResponseEntity<Traveler> addTravelers(@PathVariable long RESERVATOIN_ID, @RequestBody TravelerReq transferPojo) {


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {

            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }


            TourBooking reservation = optionalReservation.get();

            if(!reservation.getStatus().equals(EnumTourBookingStatus.Draft)){
                throw new Booking_not_pendingException(reservation.getId(),TourBooking.class.getSimpleName(),"订单非等待提交状态，不得提交"+reservation.getStatus());
            }
            Traveler booking = tourCampaignService.addTraveler(optionalReservation.get(),transferPojo);
            return ResponseEntity.ok(booking);

    }



    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/travelers/bulk", produces = "application/json")
    public ResponseEntity<List<Traveler>> bulkAddTravelers(@PathVariable long RESERVATOIN_ID, @RequestBody @Valid BulkTravelerReq transferPojo) {


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            TourBooking reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumTourBookingStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+reservation.getStatus());
            }



            List<Traveler> bookings = tourCampaignService.addBulkTraveler(optionalReservation.get(),transferPojo.getTravelers());
            return ResponseEntity.ok(bookings);
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }





    @RequestMapping("/tour_bookings/{RESERVATOIN_ID}/travelers/importCSV/prview")
    public ResponseEntity importExcelPreview(@PathVariable long RESERVATOIN_ID, MultipartFile file) throws IOException {


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()){
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到");
        }
        TourBooking reservation = optionalReservation.get();
        if(!reservation.getStatus().equals(EnumTourBookingStatus.Pending)){
            throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+reservation.getStatus());
        }


        List<ImportExcelBookingTraveler> importExcelBookingTravelers = importCVSService.importBulkTravelerExcelSubject(file,file.getInputStream());
        List<ImportExcelBookingTravelerResp> postsPerType = importExcelBookingTravelers.stream()
                .map(x->{
                    ImportExcelBookingTravelerResp importExcelBookingTravelerResp = new ImportExcelBookingTravelerResp();
                    importExcelBookingTravelerResp.setName(x.getName());
                   // importExcelBookingTravelerResp.setCreateDate(x.getCreateDate());
                    importExcelBookingTravelerResp.setTel_home(x.getTel_home());
                    importExcelBookingTravelerResp.setId(x.getId());

                    if(CheckIdcardIsLegal.checkIdCard(x.getId()) != "yes"){
                        importExcelBookingTravelerResp.setResult("正确的");
                    }else{
                        importExcelBookingTravelerResp.setResult("错误");
                    }

                    return importExcelBookingTravelerResp;

                })
                .collect(toList());

        return ResponseEntity.ok(postsPerType);

    }





    @RequestMapping("/tour_bookings/{RESERVATOIN_ID}/travelers/importCSV/async")
    public ResponseEntity importExcelSubjectPreview(@PathVariable long RESERVATOIN_ID, MultipartFile file) throws IOException {

        //<ImportExcel>
        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()){
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到");
        }
            TourBooking reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumTourBookingStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+reservation.getStatus());
            }


            List<ImportExcelBookingTraveler> importExcelBookingTravelers = importCVSService.importBulkTravelerExcelSubject(file,file.getInputStream());

            List<Pair<String,ImportExcelBookingTraveler>> postsPerType = importExcelBookingTravelers.stream()
                    .map(x->{
                        if(CheckIdcardIsLegal.checkIdCard(x.getId()) != "yes"){
                            return Pair.with("正确的",x);
                        }else{
                            return Pair.with("错误的",x);
                        }
                    })
                    // .collect(groupingBy(x->x.getValue0()));
                    .collect(toList());

            List<ImportExcelBookingTraveler> aa = postsPerType.stream().filter(x->x.getValue0()== "正确的").map(x->x.getValue1()).collect(toList());



            if(aa.isEmpty()){
                ImportExcel bookings = tourCampaignService.addBulkTravelerByImportCVS(optionalReservation.get(), importExcelBookingTravelers);
                return ResponseEntity.ok(bookings);
            }

            return ResponseEntity.ok(postsPerType);
    }












    @RequestMapping("/travelers/importCSV")
    public ResponseEntity importExcel(MultipartFile file) throws IOException {



        List<ImportExcelBookingTraveler> importExcelBookingTravelers = importCVSService.importBulkTravelerExcelSubject(file,file.getInputStream());

        List<Pair<String,ImportExcelBookingTravelerResp>> postsPerType = importExcelBookingTravelers.stream()
                .map(x->{

                    ImportExcelBookingTravelerResp importExcelBookingTravelerResp = new ImportExcelBookingTravelerResp();
                    importExcelBookingTravelerResp.setId(x.getId());
                    importExcelBookingTravelerResp.setName(x.getName());
                    importExcelBookingTravelerResp.setTel_home(x.getTel_home());

                    if(CheckIdcardIsLegal.checkIdCard(x.getId()) != "yes"){
                        importExcelBookingTravelerResp.setResult("正确的");
                        return Pair.with("正确的",importExcelBookingTravelerResp);
                    }else{
                        importExcelBookingTravelerResp.setResult("错误的");
                        return Pair.with("错误的",importExcelBookingTravelerResp);
                    }
                })
                // .collect(groupingBy(x->x.getValue0()));
                .collect(toList());

        List<ImportExcelBookingTravelerResp> aa = postsPerType.stream().filter(x->x.getValue0()== "正确的").map(x->x.getValue1()).collect(toList());

        List<ImportExcelBookingTravelerResp> aaerr = postsPerType.stream().filter(x->x.getValue0()== "错误的").map(x->x.getValue1()).collect(toList());


        String greetings = String.format(
                "成功 %1$d, 失败 %2$d !",
                aa.size(),
                aaerr.size());

        Map map = Map.of("success",aa,"error",aaerr,"info",Map.of("message",greetings,"success_number",aa.size(),"failure_number",aaerr.size()));

        return ResponseEntity.ok(map);



    }
































    @GetMapping(value = "/tour_bookings/{RESERVATOIN_ID}/documents", produces = "application/json")
    public ResponseEntity<Map<EnumDocumentType,List<EntityModel<DocumentResp>>>> listDocuments(@PathVariable long RESERVATOIN_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){

            TourBooking reservation = optionalReservation.get();
            List<Document> documents = documentRepository.findAllByRaletiveId(optionalReservation.get().getId());
      //      ResponseEntity responseEntity = ResponseEntity.ok(DocumentResp.from(documents));


            return ResponseEntity.ok(DocumentResp.from(documents));
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }


    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/documents/ids", produces = "application/json")
    public ResponseEntity<List<EntityModel<MessageFileResp>>> createDocuments(@PathVariable long RESERVATOIN_ID, @RequestBody BookingDocumentIdsResp bookingDocumentResp ) {
        //@RequestParam("files") List<MultipartFile> files
        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);





        List<Pair<EnumDocumentType,String>> docTypeWithDocCodepairList = new ArrayList<>();
        if(nonNull(bookingDocumentResp.getContract_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getContract_files().stream().map(x->Pair.with(EnumDocumentType.contract,x)).collect(toList()));
        if(nonNull(bookingDocumentResp.getInsurance_policy_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getInsurance_policy_files().stream().map(x->Pair.with(EnumDocumentType.insurance_policy,x)).collect(toList()));
        if(nonNull(bookingDocumentResp.getPhoto_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getPhoto_files().stream().map(x->Pair.with(EnumDocumentType.photo,x)).collect(toList()));
        if(nonNull(bookingDocumentResp.getBill_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getBill_files().stream().map(x->Pair.with(EnumDocumentType.bill,x)).collect(toList()));

        System.out.println("--docTypeWithDocCodepairList------------------------"+docTypeWithDocCodepairList);

        System.out.println("--getInsurance_policy_files------------------------"+bookingDocumentResp.getInsurance_policy_files());

        if(optionalReservation.isEmpty()) {
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }


            List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(docTypeWithDocCodepairList.stream().map(x->x.getValue1()).distinct().collect(toList()));


            System.out.println("------------------------------------------------"+tempDocuments.toString());

            if(tempDocuments.size() ==0){
                throw new BookNotFoundException(1,"找不到上传文件");
            }

            Map<String,TempDocument> documentMap = tempDocuments.stream().collect(Collectors.toMap(x->x.getCode(),x->x));

            List<Pair<EnumDocumentType,TempDocument>> docTypeWithTempDocPairList = docTypeWithDocCodepairList.stream().map(x->{
                TempDocument tempDocument = documentMap.get(x.getValue1());
                return Pair.with(x.getValue0(),tempDocument);
            }).collect(toList());




            if(docTypeWithTempDocPairList.isEmpty()){
                throw new Missing_documentException(RESERVATOIN_ID,Reservation.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票");
            }



                List<Document> documents = fileStorageService.saveFromTempDocumentCode(RESERVATOIN_ID,docTypeWithTempDocPairList);
                return ResponseEntity.ok(MessageFileResp.fromDocuments(documents.stream().map(x->{
                    return Pair.with(FileStorageServiceImpl.url(x.getFileName()),x);
                }).collect(toList())));





    }












    @GetMapping(value = "/tour_bookings/{RESERVATOIN_ID}/campaigns", produces = "application/json")
    public ResponseEntity<PagedModel> listCampaigns(@PathVariable long RESERVATOIN_ID,Pageable pageable,PagedResourcesAssembler<EntityModel<Map>> assembler)  {
        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }
        TourBooking reservation = optionalReservation.get();
        Page<CampaignAssignToTourBooking> campaignAssignToTourProducts = campaignAssignToTourBookingRepository.findAllByTourBooking(reservation.getId(),pageable);

        List<Campaign> campaigns =  campaignRepository.findAllById(campaignAssignToTourProducts.stream().map(x->x.getCampaign()).collect(Collectors.toList()));


        Map<Long,Campaign> longCampaignMap = campaigns.stream().collect(Collectors.toMap(x->x.getId(),x->x));

        List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());



        Map<Long,List<PublicationEntry>> longListMap_publi  = publicationService.list(EnumAssociatedType.tour_booking,reservation.getId())
                .stream().collect(Collectors.groupingBy(x->x.getCampaign()));



        PagedModel pagedModel= assembler.toModel(campaignAssignToTourProducts.map(x->{

            List<PublicationEntry>publicationEntryList =  longListMap_publi.get(x.getCampaign());

            Map<Long,List<PublicationEntry>> longListMap= publicationEntryList.stream().collect(Collectors.groupingBy(x_public->x_public.getCampaign()));


                Campaign campaign = longCampaignMap.get(x.getCampaign());
            Optional<Asset> assetResp  = assetService.getQrOptional(x.getCode());
            return EntityModel.of(Map.of("name",campaign.getName(),
                        "code",campaign.getCode(),
                        "total_count",travelers.size(),
                        "claim_count",longListMap.get(campaign.getId()).size(),
                    "asset",AssetResp.from(assetResp.get()))
                    );

        }));

        return ResponseEntity.ok(pagedModel);

    }





    //TODO 领券核销
    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/商户提交旅投初次审核", produces = "application/json")
    public ResponseEntity<TourBooking> 商户初次提交(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        User user = authenticationFacade.getUser(authentication);


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);

        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到订单 tourbooking"+ RESERVATOIN_ID);
        }

        TourBooking tourBooking = optionalReservation.get();

        if(!tourBooking.getStatus().equals(EnumTourBookingStatus.Draft)){
            throw new Booking_not_pendingException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单非等待草稿状态，不得提交"+tourBooking.getStatus());
        }


        List<Document> documents = documentRepository.findAllByRaletiveId(tourBooking.getId());
        if(documents.isEmpty()){
            Optional<Document> optionalDocument = documents.stream().filter(x->x.getType().equals(EnumDocumentType.insurance_policy)).findAny();
            throw new Missing_documentException(tourBooking.getId(),Reservation.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票"+(optionalDocument.isPresent()?"":"无保险单"));
        }

        Optional<Supplier> optionalSupplier = supplierRepository.findById(tourBooking.getRedeemer());


        applyForApprovalService.create(EnumRequestType.tour_approve,tourBooking,user);

        tourBooking.setStatus(EnumTourBookingStatus.Pending);
        tourBooking = tourBookingRepository.save(tourBooking);

        return ResponseEntity.ok(tourBooking);

    }





    //TODO 领券核销
    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/旅投初次审核通过", produces = "application/json")
    public ResponseEntity<TourBooking> 旅投初次审核(@PathVariable long RESERVATOIN_ID, @RequestBody ReviewReq reviewReq) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        User user = authenticationFacade.getUser(authentication);


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);

        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到订单 tourbooking"+ RESERVATOIN_ID);
        }

        TourBooking tourBooking = optionalReservation.get();

        if(!tourBooking.getStatus().equals(EnumTourBookingStatus.Pending)){
            throw new Booking_not_pendingException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单非等待草稿状态，不得提交"+tourBooking.getStatus());
        }



        applyForApprovalService.create(EnumRequestType.tour_approve,tourBooking,user);
        if(reviewReq.getType().equals(EnumRequestApproveReject.Approve)){
            tourBooking.setStatus(EnumTourBookingStatus.AwaitingBill_photo_image);
            tourBooking = tourBookingRepository.save(tourBooking);

        }
        if(reviewReq.getType().equals(EnumRequestApproveReject.Reject)){
            tourBooking.setStatus(EnumTourBookingStatus.Draft);
            tourBooking = tourBookingRepository.save(tourBooking);

        }
        return ResponseEntity.ok(tourBooking);

    }


    //TODO 领券核销
    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/商户二次提交审核", produces = "application/json")
    public ResponseEntity<TourBooking> 商户二次提交(@PathVariable long RESERVATOIN_ID, @RequestBody ReviewReq reviewReq) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        User user = authenticationFacade.getUser(authentication);


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);

        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到订单 tourbooking"+ RESERVATOIN_ID);
        }

        TourBooking tourBooking = optionalReservation.get();


        if(!tourBooking.getStatus().equals(EnumTourBookingStatus.AwaitingBill_photo_image)){
            throw new Booking_not_pendingException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单非等待草稿状态，不得提交"+tourBooking.getStatus());
        }


        List<Document> documents = documentRepository.findAllByRaletiveId(tourBooking.getId());
        if(documents.isEmpty()){
            Optional<Document> optionalDocument = documents.stream().filter(x->x.getType().equals(EnumDocumentType.insurance_policy)).findAny();
            throw new Missing_documentException(tourBooking.getId(),Reservation.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票"+(optionalDocument.isPresent()?"":"无保险单"));
        }

        applyForApprovalService.addReviews(EnumRequestType.tour_approve,tourBooking.getCode(),reviewReq);

        tourBooking.setStatus(EnumTourBookingStatus.AwaitingBill_photo_image);
        tourBooking = tourBookingRepository.save(tourBooking);

        return ResponseEntity.ok(tourBooking);

    }

    //TODO 领券核销
    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/旅投二次审核通过", produces = "application/json")
    public ResponseEntity<TourBooking> 旅投二次审核(@PathVariable long RESERVATOIN_ID, @RequestBody ReviewReq reviewReq) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        User user = authenticationFacade.getUser(authentication);

        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到订单 tourbooking"+ RESERVATOIN_ID);
        }

        TourBooking tourBooking = optionalReservation.get();
        if(!tourBooking.getStatus().equals(EnumTourBookingStatus.AwaitingBill_photo_image)){
            throw new Booking_not_pendingException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单非等待草稿状态，不得提交"+tourBooking.getStatus());
        }


        applyForApprovalService.addReviews(EnumRequestType.tour_approve,tourBooking.getCode(),reviewReq);


        if(reviewReq.getType().equals(EnumRequestApproveReject.Approve)){
            tourBooking.setStatus(EnumTourBookingStatus.AwaitingFundAppropriation);
            tourBooking = tourBookingRepository.save(tourBooking);
        }
        if(reviewReq.getType().equals(EnumRequestApproveReject.Reject)){
            tourBooking.setStatus(EnumTourBookingStatus.AwaitingBill_photo_image);
            tourBooking = tourBookingRepository.save(tourBooking);
        }

        return ResponseEntity.ok(tourBooking);

    }


    //TODO 领券核销
    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/文旅局审核", produces = "application/json")
    public ResponseEntity<TourBooking> 文旅局审核(@PathVariable long RESERVATOIN_ID, @RequestBody ReviewReq reviewReq) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        User user = authenticationFacade.getUser(authentication);

        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到订单 tourbooking"+ RESERVATOIN_ID);
        }

        TourBooking tourBooking = optionalReservation.get();
        if(!tourBooking.getStatus().equals(EnumTourBookingStatus.Draft)){
            throw new Booking_not_pendingException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单非等待草稿状态，不得提交"+tourBooking.getStatus());
        }


        applyForApprovalService.addReviews(EnumRequestType.tour_approve,tourBooking.getCode(),reviewReq);
        tourBooking.setStatus(EnumTourBookingStatus.Fulfillment);
        tourBooking = tourBookingRepository.save(tourBooking);

        return ResponseEntity.ok(tourBooking);

    }

    //TODO 领券核销
    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/request_approve", produces = "application/json")
    public ResponseEntity<TourbookingTourResp> request_approve(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        User user = authenticationFacade.getUser(authentication);


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);


        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到订单 tourbooking"+ RESERVATOIN_ID);
        }

            TourBooking tourBooking = optionalReservation.get();
            if(!tourBooking.getStatus().equals(EnumTourBookingStatus.Pending)){
                throw new Booking_not_pendingException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+tourBooking.getStatus());
            }


            List<Document> documents = documentRepository.findAllByRaletiveId(tourBooking.getId());
            if(documents.isEmpty()){
                Optional<Document> optionalDocument = documents.stream().filter(x->x.getType().equals(EnumDocumentType.insurance_policy)).findAny();
                throw new Missing_documentException(tourBooking.getId(),Reservation.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票"+(optionalDocument.isPresent()?"":"无保险单"));

            }

            Optional<Supplier> optionalSupplier = supplierRepository.findById(tourBooking.getRedeemer());

            TourBooking booking = tourCampaignService.tourBookingRequestLvApproval(optionalSupplier.get(),tourBooking,transferPojo,user);

            List<Traveler> travelers = travelerRepository.findAllByBooking(tourBooking.getId());
           // List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());

            return ResponseEntity.ok(TourbookingTourResp.toResp(booking,travelers,documents));

    }







    //TODO 领券核销
    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/approve", produces = "application/json")
    public ResponseEntity<TourbookingTourResp> approve(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {
        Authentication authentication =  authenticationFacade.getAuthentication();

        User user = authenticationFacade.getUser(authentication);


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);


        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(RESERVATOIN_ID,"找不到订单 tourbooking"+ RESERVATOIN_ID);
        }

        TourBooking tourBooking = optionalReservation.get();
        if(!tourBooking.getStatus().equals(EnumTourBookingStatus.Pending)){
            throw new Booking_not_pendingException(tourBooking.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+tourBooking.getStatus());
        }


        List<Document> documents = documentRepository.findAllByRaletiveId(tourBooking.getId());
        if(documents.isEmpty()){
            Optional<Document> optionalDocument = documents.stream().filter(x->x.getType().equals(EnumDocumentType.insurance_policy)).findAny();
            throw new Missing_documentException(tourBooking.getId(),Reservation.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票"+(optionalDocument.isPresent()?"":"无保险单"));

        }

        Optional<Supplier> optionalSupplier = supplierRepository.findById(tourBooking.getRedeemer());

        TourBooking booking = tourCampaignService.tourBookingRequestLvApproval(optionalSupplier.get(),tourBooking,transferPojo,user);

        List<Traveler> travelers = travelerRepository.findAllByBooking(tourBooking.getId());

        return ResponseEntity.ok(TourbookingTourResp.toResp(booking,travelers,documents));

    }





































    /*

          Pair<List<PhoneAuth>, List<PhoneAuth>> phoneAuthPhoneAuthPair = realNameAuthenticationService.bulkCheckRealname(travelerReqs);

            ImportCVSBulkTravelerResp importCVSBulkTravelerResp = new ImportCVSBulkTravelerResp();
            importCVSBulkTravelerResp.setRealNameCount(phoneAuthPhoneAuthPair.getValue0().size());
            importCVSBulkTravelerResp.setNotRealNameCount(phoneAuthPhoneAuthPair.getValue1().size());
            importCVSBulkTravelerResp.setRealnameTravelers(phoneAuthPhoneAuthPair.getValue0().stream()
                    .map(x->{
                        TravelerReq travelerReq = new TravelerReq();
                        travelerReq.setName(x.getIdCardName());
                        travelerReq.setId(x.getIdCardName());
                        travelerReq.setId(x.getPhoneNo());
                        return travelerReq;
                    })
                    .collect(Collectors.toList()));

            importCVSBulkTravelerResp.setRealnameTravelers(phoneAuthPhoneAuthPair.getValue1().stream()
                    .map(x->{
                        TravelerReq travelerReq = new TravelerReq();
                        travelerReq.setName(x.getIdCardName());
                        travelerReq.setId(x.getIdCardName());
                        return travelerReq;
                    })
                    .collect(Collectors.toList()));




    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/documents", produces = "application/json")
    public ResponseEntity createDocuments(@PathVariable long RESERVATOIN_ID,@ModelAttribute BookingDocumentResp bookingDocumentResp ) {
        //@RequestParam("files") List<MultipartFile> files
        Optional<TourBooking> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            List<String> fileNames = new ArrayList<>();
            if(nonNull(bookingDocumentResp.getContract_files())){
                List<String> Contract_files_fileNames = bookingDocumentResp.getContract_files().stream().map(x->x.getOriginalFilename()).collect(Collectors.toList());
                fileNames.addAll(Contract_files_fileNames);
            }
            if(nonNull(bookingDocumentResp.getInsurance_policy_files())){
                List<String> Insurance_policy_files_fileNames = bookingDocumentResp.getInsurance_policy_files().stream().map(x->x.getOriginalFilename()).collect(Collectors.toList());
                fileNames.addAll(Insurance_policy_files_fileNames);

            }
            if(nonNull(bookingDocumentResp.getPhoto_files())){
                List<String> Photo_files_fileNames = bookingDocumentResp.getPhoto_files().stream().map(x->x.getOriginalFilename()).collect(Collectors.toList());
                fileNames.addAll(Photo_files_fileNames);
            }
            if(nonNull(bookingDocumentResp.getBill_files())){
                List<String> Bill_files_fileNames = bookingDocumentResp.getBill_files().stream().map(x->x.getOriginalFilename()).collect(Collectors.toList());
                fileNames.addAll(Bill_files_fileNames);
            }


            if(fileNames.isEmpty()){
                throw new Missing_documentException(RESERVATOIN_ID,Reservation.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票");
            }


            try {

                List<String> fileNames_ = new ArrayList<>();
/*                bookingDocumentResp.getFiles().stream().forEach(x->{
                    Document document = fileStorageService.saveWithDocument(optionalReservation.get(), EnumDocumentType.estimate, x);
                    fileNames_.add(x.getOriginalFilename());
                });*//*
                if(!bookingDocumentResp.getContract_files().isEmpty()){
                    bookingDocumentResp.getContract_files().stream().forEach(x->{
                        Document document = fileStorageService.saveWithDocument(optionalReservation.get(), EnumDocumentType.contract,x);
                        fileNames_.add(x.getOriginalFilename());
                    });
                }
                if(!bookingDocumentResp.getInsurance_policy_files().isEmpty()){
                    bookingDocumentResp.getInsurance_policy_files().stream().forEach(x->{
                        Document document = fileStorageService.saveWithDocument(optionalReservation.get(), EnumDocumentType.insurance_policy, x);
                        fileNames_.add(x.getOriginalFilename());
                    });
                }
                if(!bookingDocumentResp.getPhoto_files().isEmpty()){
                    bookingDocumentResp.getPhoto_files().stream().forEach(x->{
                        Document document = fileStorageService.saveWithDocument(optionalReservation.get(), EnumDocumentType.photo, x);
                        fileNames_.add(x.getOriginalFilename());
                    });
                }
                if(!bookingDocumentResp.getBill_files().isEmpty()){
                    bookingDocumentResp.getBill_files().stream().forEach(x->{
                        Document document = fileStorageService.saveWithDocument(optionalReservation.get(), EnumDocumentType.bill, x);
                        fileNames_.add(x.getOriginalFilename());
                    });
                }

                return ResponseEntity.ok(new MessageFile("Upload file successfully: "+fileNames_));
            }catch (Exception e){

                e.printStackTrace();
                return ResponseEntity.badRequest()
                        .body(new MessageFile("Could not upload the file:"+fileNames));
            }
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }*/









}