package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.error.*;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumOrderStatus;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.util.CheckIdcardIsLegal;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
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

    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/tour_bookings/{RESERVATOIN_ID}", produces = "application/json")
    public ResponseEntity<TourbookingTourResp> getReservation(@PathVariable long RESERVATOIN_ID) {
        Optional<TourBooking> optionalProduct = tourBookingRepository.findById(RESERVATOIN_ID);


        if(optionalProduct.isPresent()) {
                System.out.println("找不到产品");
                throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());

        }
            TourBooking reservation = optionalProduct.get();

            if(optionalProduct.get().getProductType().equals(EnumProductType.Daytour)){


                Optional<Product> product = productRepository.findById(optionalProduct.get().getProductId());
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getRaletiveId());
                List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());

                TourbookingTourResp reservationTourResp = TourbookingTourResp.toResp(Quintet.with(reservation,product.get(),optionalTour.get(),travelers,documents));

                reservationTourResp.add(linkTo(methodOn(TourCampaignRestController.class).bulkAddTravelers(reservation.getId(),null)).withRel("add_travelers_url"));
                reservationTourResp.add(linkTo(methodOn(TourCampaignRestController.class).createDocuments(reservation.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));

                return ResponseEntity.ok(reservationTourResp);
            }
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));


    }

    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/tour_bookings", produces = "application/json")
    public ResponseEntity<Page<TourbookingTourResp>> pageReservation(@ModelAttribute PageReq pageReq ) {



        Page<TourBooking> optionalProduct = tourBookingRepository.findAll(PageRequest.of(pageReq.getPage(),pageReq.getLimit()));


        Page<TourbookingTourResp> page =  optionalProduct.map(x->{

            if(x.getProductType().equals(EnumProductType.Daytour)){
                Optional<Product> product = productRepository.findById(x.getProductId());
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getRaletiveId());
                List<Traveler> travelers = travelerRepository.findAllByBooking(x.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(x.getId());

                TourbookingTourResp resp = TourbookingTourResp.toResp(Quintet.with(x,product.get(),optionalTour.get(),travelers,documents));


                resp.add(linkTo(methodOn(TourCampaignRestController.class).bulkAddTravelers(x.getId(),null)).withRel("add_travelers_url"));
                resp.add(linkTo(methodOn(TourCampaignRestController.class).createDocuments(x.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));

                return TourbookingTourResp.toResp(Quintet.with(x,product.get(),optionalTour.get(),travelers,documents));
            }else{
                return new TourbookingTourResp();
            }
        });

        return ResponseEntity.ok(page);



    }



    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/tour_bookings", produces = "application/json")
    public ResponseEntity createBooking(@RequestBody @Valid TourBookingPojo pojo) {


        Optional<Product> optionalProduct = productRepository.findById(pojo.getProductId());

        if(optionalProduct.isPresent()){

            List<Campaign> campaigns = campaignService.getQualification();

            if(campaigns.size() == 0){
                throw new No_voucher_suitable_for_publicationException(0,Reservation.class.getSimpleName(),"没有 优惠券配额");
            }

            Optional<Guide> guideOptional = guideRepository.findById(pojo.getAdditional_info().getGuide());
            if(guideOptional.isEmpty()){
                throw new BookNotFoundException(0,"找不到导游");
            }

            Triplet<TourBooking,Product,Tour> booking = tourCampaignService.book(optionalProduct.get(),
                    campaigns.stream().findAny().get(),
                    pojo);
            ReservationTourResp resp = ReservationTourResp.toResp(booking);

            return ResponseEntity.ok(resp);
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(pojo.getProductId(),"找不到产品");
        }


    }







    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/travelers", produces = "application/json")
    public ResponseEntity<Traveler> addTravelers(@PathVariable long RESERVATOIN_ID, @RequestBody TravelerReq transferPojo) {


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            TourBooking reservation = optionalReservation.get();

            if(!reservation.getStatus().equals(EnumOrderStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),TourBooking.class.getSimpleName(),"订单非等待提交状态，不得提交"+reservation.getStatus());
            }
            Traveler booking = tourCampaignService.addTraveler(optionalReservation.get(),transferPojo);
            return ResponseEntity.ok(booking);
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }



    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/travelers/bulk", produces = "application/json")
    public ResponseEntity<List<Traveler>> bulkAddTravelers(@PathVariable long RESERVATOIN_ID, @RequestBody @Valid BulkTravelerReq transferPojo) {


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            TourBooking reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumOrderStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+reservation.getStatus());
            }



            List<Traveler> bookings = tourCampaignService.addBulkTraveler(optionalReservation.get(),transferPojo);
            return ResponseEntity.ok(bookings);
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }





    @RequestMapping("/tour_bookings/{RESERVATOIN_ID}/travelers/importCSV/async")
    public ResponseEntity importExcelSubjectPreview(@PathVariable long RESERVATOIN_ID, MultipartFile file) throws IOException {

        //<ImportExcel>
        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            TourBooking reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumOrderStatus.Pending)){
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
                    .collect(Collectors.toList());

            List<ImportExcelBookingTraveler> aa = postsPerType.stream().filter(x->x.getValue0()== "正确的").map(x->x.getValue1()).collect(Collectors.toList());

            if(aa.isEmpty()){
                ImportExcel bookings = tourCampaignService.addBulkTravelerByImportCVS(optionalReservation.get(), importExcelBookingTravelers);
                return ResponseEntity.ok(bookings);
            }



            return ResponseEntity.ok(postsPerType);


        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }


    }













    @GetMapping(value = "/tour_bookings/{RESERVATOIN_ID}/documents", produces = "application/json")
    public ResponseEntity<Map<EnumDocumentType,List<DocumentResp>>> listDocuments(@PathVariable long RESERVATOIN_ID, CouponTemplatePojoList  couponTemplatePojoList) {
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
    public ResponseEntity<List<EntityModel<MessageFileResp>>> createDocuments(@PathVariable long RESERVATOIN_ID, @ModelAttribute BookingDocumentIdsResp bookingDocumentResp ) {
        //@RequestParam("files") List<MultipartFile> files
        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);

        List<Pair<EnumDocumentType,String>> docTypeWithDocCodepairList = new ArrayList<>();
        if(nonNull(bookingDocumentResp.getContract_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getContract_files().stream().map(x->Pair.with(EnumDocumentType.contract,x)).collect(Collectors.toList()));
        if(nonNull(bookingDocumentResp.getInsurance_policy_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getInsurance_policy_files().stream().map(x->Pair.with(EnumDocumentType.insurance_policy,x)).collect(Collectors.toList()));
        if(nonNull(bookingDocumentResp.getPhoto_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getPhoto_files().stream().map(x->Pair.with(EnumDocumentType.photo,x)).collect(Collectors.toList()));
        if(nonNull(bookingDocumentResp.getBill_files()))
            docTypeWithDocCodepairList.addAll(bookingDocumentResp.getBill_files().stream().map(x->Pair.with(EnumDocumentType.bill,x)).collect(Collectors.toList()));


        System.out.println("--getInsurance_policy_files------------------------"+bookingDocumentResp.getInsurance_policy_files());

        if(optionalReservation.isEmpty()) {
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }


            List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(docTypeWithDocCodepairList.stream().map(x->x.getValue1()).distinct().collect(Collectors.toList()));


            System.out.println("------------------------------------------------"+tempDocuments.toString());

            if(tempDocuments.size() ==0){
                throw new BookNotFoundException(1,"找不到上传文件");
            }

            Map<String,TempDocument> documentMap = tempDocuments.stream().collect(Collectors.toMap(x->x.getCode(),x->x));

            List<Pair<EnumDocumentType,TempDocument>> docTypeWithTempDocPairList = docTypeWithDocCodepairList.stream().map(x->{
                TempDocument tempDocument = documentMap.get(x.getValue1());
                return Pair.with(x.getValue0(),tempDocument);
            }).collect(Collectors.toList());




            if(docTypeWithTempDocPairList.isEmpty()){
                throw new Missing_documentException(RESERVATOIN_ID,Reservation.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票");
            }



                List<Document> documents = fileStorageService.saveFromTempDocumentList(RESERVATOIN_ID,docTypeWithTempDocPairList);
                return ResponseEntity.ok(MessageFileResp.fromDocuments(documents.stream().map(x->{
                    return Pair.with(FileStorageServiceImpl.url(x.getFileName()),x);
                }).collect(Collectors.toList())));





    }












    @GetMapping(value = "/tour_bookings/{RESERVATOIN_ID}/campaigns", produces = "application/json")
    public ResponseEntity<List<Campaign>> listCampaigns(@PathVariable long RESERVATOIN_ID) {
        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){

            TourBooking reservation = optionalReservation.get();
            List<Campaign> documents = tourCampaignService.listCampaigns(optionalReservation.get());
            //      ResponseEntity responseEntity = ResponseEntity.ok(DocumentResp.from(documents));

            return ResponseEntity.ok(documents);
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }







    //TODO 领券核销
    @PostMapping(value = "/tour_bookings/{RESERVATOIN_ID}/submit", produces = "application/json")
    public ResponseEntity<TourbookingTourResp> submit(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {


        Optional<TourBooking> optionalReservation = tourBookingRepository.findById(RESERVATOIN_ID);


        if(optionalReservation.isPresent()){
            TourBooking reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumOrderStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"订单非等待提交状态，不得提交"+reservation.getStatus());
            }


            List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());
            if(documents.isEmpty()){
                Optional<Document> optionalDocument = documents.stream().filter(x->x.getType().equals(EnumDocumentType.insurance_policy)).findAny();

                throw new Missing_documentException(reservation.getId(),Reservation.class.getSimpleName(),"需要附上申请文档， 合同，合影照片，保险单和发票"+(optionalDocument.isPresent()?"":"无保险单"));

            }



            Optional<Supplier> optionalSupplier = supplierRepository.findById(reservation.getRedeemer());


            TourBooking booking = tourCampaignService.submit(optionalSupplier.get(),optionalReservation.get(),transferPojo);


            List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
           // List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());


            return ResponseEntity.ok(TourbookingTourResp.toResp(booking,travelers,documents));

        }else{
            System.out.println("抛出异常");
            throw new BookNotFoundException(RESERVATOIN_ID,Reservation.class.getSimpleName());
        }



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