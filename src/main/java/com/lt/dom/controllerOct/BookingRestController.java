package com.lt.dom.controllerOct;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.lt.dom.OctResp.*;
import com.lt.dom.config.WxConfigUtil;
import com.lt.dom.config.WxPayConfig;
import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Booking_not_pendingException;
import com.lt.dom.error.Missing_documentException;
import com.lt.dom.error.No_voucher_suitable_for_publicationException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;

//https://caroly.fun/archives/springboot%E6%95%B4%E5%90%88%E5%BE%AE%E4%BF%A1%E6%94%AF%E4%BB%98

@RestController
@RequestMapping("/oct")
public class BookingRestController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ImportCVSServiceImpl importCVSService;

    @Autowired
    private WxPayConfig wxPayConfig;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookingServiceImpl bookingService;
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


    @Operation(summary = "1???????????????")
    @GetMapping(value = "/bookings/{RESERVATOIN_ID}", produces = "application/json")
    public ResponseEntity<ReservationTourResp> getReservation(@PathVariable long RESERVATOIN_ID) {
        Optional<Reservation> optionalProduct = reservationRepository.findById(RESERVATOIN_ID);


        if(optionalProduct.isPresent()){
            Reservation reservation = optionalProduct.get();

            if(optionalProduct.get().getProductType().equals(EnumProductType.Daytour)){


                // reservationResp = new ReservationResp();
                Optional<Product> product = productRepository.findById(optionalProduct.get().getProductId());
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getRaletiveId());
                List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());
                return ResponseEntity.ok(ReservationTourResp.toResp(Quintet.with(reservation,product.get(),optionalTour.get(),travelers,documents)));
            }
            System.out.println("????????????");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }else{
            System.out.println("???????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }


    }

    @Operation(summary = "1???????????????")
    @GetMapping(value = "/bookings", produces = "application/json")
    public ResponseEntity<Page<ReservationTourResp>> pageReservation(@ModelAttribute PageReq pageReq ) {



        Page<Reservation> optionalProduct = reservationRepository.findAll(PageRequest.of(pageReq.getPage(),pageReq.getLimit()));


        Page<ReservationTourResp> page =  optionalProduct.map(x->{

            if(x.getProductType().equals(EnumProductType.Daytour)){
                Optional<Product> product = productRepository.findById(x.getProductId());
                Optional<Tour> optionalTour = tourRepository.findById(product.get().getRaletiveId());
                List<Traveler> travelers = travelerRepository.findAllByBooking(x.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(x.getId());
                return ReservationTourResp.toResp(Quintet.with(x,product.get(),optionalTour.get(),travelers,documents));
            }else{
                return new ReservationTourResp();
            }
        });

        return ResponseEntity.ok(page);



    }



    @Operation(summary = "2???????????????")
    @PostMapping(value = "/bookings", produces = "application/json")
    public ResponseEntity createBooking(@RequestBody @Valid BookingPojo pojo) {


        Optional<Product> optionalProduct = productRepository.findById(pojo.getProductId());

        if(optionalProduct.isPresent()){

            List<Campaign> campaigns = campaignService.getQualification();

            if(campaigns.size() == 0){
                throw new No_voucher_suitable_for_publicationException(0,Reservation.class.getSimpleName(),"?????? ???????????????");
            }

            Triplet<Reservation,Product,Tour> booking = bookingService.book(optionalProduct.get(),campaigns.stream().findAny().get(),pojo);

            ReservationTourResp resp = ReservationTourResp.toResp(booking);

            return ResponseEntity.ok(resp);
        }else{
            System.out.println("???????????????");
            throw new BookNotFoundException(pojo.getProductId(),Product.class.getSimpleName());
        }


    }







    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/travelers", produces = "application/json")
    public ResponseEntity<Traveler> addTravelers(@PathVariable long RESERVATOIN_ID, @RequestBody TravelerReq transferPojo) {


        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();

            if(!reservation.getStatus().equals(EnumOrderStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"??????????????????????????????????????????"+reservation.getStatus());
            }
            Traveler booking = bookingService.addTraveler(optionalReservation.get(),transferPojo);
            return ResponseEntity.ok(booking);
        }else{
            System.out.println("???????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }



    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/travelers/bulk", produces = "application/json")
    public ResponseEntity<List<Traveler>> bulkAddTravelers(@PathVariable long RESERVATOIN_ID, @RequestBody @Valid BulkTravelerReq transferPojo) {


        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumOrderStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"??????????????????????????????????????????"+reservation.getStatus());
            }



            List<Traveler> bookings = bookingService.addBulkTraveler(optionalReservation.get(),transferPojo);
            return ResponseEntity.ok(bookings);
        }else{
            System.out.println("???????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }





    @RequestMapping("/bookings/{RESERVATOIN_ID}/travelers/importCSV/async")
    public ResponseEntity importExcelSubjectPreview(@PathVariable long RESERVATOIN_ID, MultipartFile file) throws IOException {

        //<ImportExcel>
        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumOrderStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"??????????????????????????????????????????"+reservation.getStatus());
            }


            List<ImportExcelBookingTraveler> importExcelBookingTravelers = importCVSService.importBulkTravelerExcelSubject(file,file.getInputStream());

            List<Pair<String,ImportExcelBookingTraveler>> postsPerType = importExcelBookingTravelers.stream()
                    .map(x->{
                        if(CheckIdcardIsLegal.checkIdCard(x.getId()) != "yes"){
                            return Pair.with("?????????",x);
                        }else{
                            return Pair.with("?????????",x);
                        }
                    })
                   // .collect(groupingBy(x->x.getValue0()));
                    .collect(Collectors.toList());

            List<ImportExcelBookingTraveler> aa = postsPerType.stream().filter(x->x.getValue0()== "?????????").map(x->x.getValue1()).collect(Collectors.toList());

            if(aa.isEmpty()){
                ImportExcel bookings = bookingService.addBulkTravelerByImportCVS(optionalReservation.get(), importExcelBookingTravelers);
                return ResponseEntity.ok(bookings);
            }



            return ResponseEntity.ok(postsPerType);
/*            Pair<List<PhoneAuth>, List<PhoneAuth>> phoneAuthPhoneAuthPair = realNameAuthenticationService.bulkCheckRealname(travelerReqs);

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
                    .collect(Collectors.toList()));*/




        }else{
            System.out.println("???????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }


    }













    @GetMapping(value = "/bookings/{RESERVATOIN_ID}/documents", produces = "application/json")
    public ResponseEntity<Map<EnumDocumentType,List<DocumentResp>>> listDocuments(@PathVariable long RESERVATOIN_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
        if(optionalReservation.isPresent()){

            List<Document> documents = documentRepository.findAllByRaletiveId(optionalReservation.get().getId());


            return ResponseEntity.ok(DocumentResp.from(documents));
        }else{
            System.out.println("???????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }

    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/documents", produces = "application/json")
    public ResponseEntity createDocuments(@PathVariable long RESERVATOIN_ID,@ModelAttribute BookingDocumentResp bookingDocumentResp ) {
        //@RequestParam("files") List<MultipartFile> files
        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);
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
                throw new Missing_documentException(RESERVATOIN_ID,Reservation.class.getSimpleName(),"??????????????????????????? ??????????????????????????????????????????");
            }


            try {

                List<String> fileNames_ = new ArrayList<>();
/*                bookingDocumentResp.getFiles().stream().forEach(x->{
                    Document document = fileStorageService.saveWithDocument(optionalReservation.get(), EnumDocumentType.estimate, x);
                    fileNames_.add(x.getOriginalFilename());
                });*/
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
            System.out.println("???????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }

    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/documents/ids", produces = "application/json")
    public ResponseEntity<List<MessageFileResp>> createDocuments(@PathVariable long RESERVATOIN_ID,@ModelAttribute BookingDocumentIdsResp bookingDocumentResp ) {
        //@RequestParam("files") List<MultipartFile> files
        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);

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

        if(!optionalReservation.isPresent()){

            List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(docTypeWithDocCodepairList.stream().map(x->x.getValue1()).distinct().collect(Collectors.toList()));


            System.out.println("------------------------------------------------"+tempDocuments.toString());


            Map<String,TempDocument> documentMap = tempDocuments.stream().collect(Collectors.toMap(x->x.getCode(),x->x));

            List<Pair<EnumDocumentType,TempDocument>> docTypeWithTempDocPairList = docTypeWithDocCodepairList.stream().map(x->{
                TempDocument tempDocument = documentMap.get(x.getValue1());
                return Pair.with(x.getValue0(),tempDocument);
            }).collect(Collectors.toList());




            if(docTypeWithTempDocPairList.isEmpty()){
                throw new Missing_documentException(RESERVATOIN_ID,Reservation.class.getSimpleName(),"??????????????????????????? ??????????????????????????????????????????");
            }


            try {
                List<Document> documents = fileStorageService.saveFromTempDocumentList(RESERVATOIN_ID,docTypeWithTempDocPairList);
                return ResponseEntity.ok(MessageFileResp.fromDocuments(documents.stream().map(x->{
                    return Pair.with(FileStorageServiceImpl.url(x.getFileName()),x);
                }).collect(Collectors.toList())));


            }catch (Exception e){

                e.printStackTrace();
                return null;
                //return ResponseEntity.badRequest()
                //        .body(new MessageFile("Could not upload the file:"+fileNames));
            }
        }else{
            System.out.println("???????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }

    }












    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/pay", produces = "application/json")
    public String pay(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {


        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);


        if(optionalReservation.isPresent()){
            ChargeResp booking = bookingService.pay(optionalReservation.get(),transferPojo);


            String form="";
            return form;
        }else{
            System.out.println("???????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }




    }



    //TODO ????????????
    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/submit", produces = "application/json")
    public ResponseEntity<ReservationTourResp> submit(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {


        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);


        if(optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();
            if(!reservation.getStatus().equals(EnumOrderStatus.Pending)){
                throw new Booking_not_pendingException(reservation.getId(),Reservation.class.getSimpleName(),"??????????????????????????????????????????"+reservation.getStatus());
            }


            List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());
            if(documents.isEmpty()){
                Optional<Document> optionalDocument = documents.stream().filter(x->x.getType().equals(EnumDocumentType.insurance_policy)).findAny();

                throw new Missing_documentException(reservation.getId(),Reservation.class.getSimpleName(),"??????????????????????????? ??????????????????????????????????????????"+(optionalDocument.isPresent()?"":"????????????"));

            }



            Optional<Supplier> optionalSupplier = supplierRepository.findById(reservation.getRedeemer());


            Reservation booking = bookingService.submit(optionalSupplier.get(),optionalReservation.get(),transferPojo);


            List<Traveler> travelers = travelerRepository.findAllByBooking(reservation.getId());
           // List<Document> documents = documentRepository.findAllByRaletiveId(reservation.getId());


            return ResponseEntity.ok(ReservationTourResp.toResp(booking,travelers,documents));

        }else{
            System.out.println("????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Reservation.class.getSimpleName());
        }



    }


    @PostMapping(value = "/bookings/{RESERVATOIN_ID}/h5pay", produces = "application/json")
    public void h5Pay(@PathVariable long RESERVATOIN_ID, @RequestBody BookingPayPojo transferPojo) {


        Optional<Reservation> optionalReservation = reservationRepository.findById(RESERVATOIN_ID);


        if(optionalReservation.isPresent()){
            ChargeResp booking = bookingService.pay(optionalReservation.get(),transferPojo);

        }else{
            System.out.println("???????????????");
            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }




    }




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















   // @Override
    public Map<String, String> payRequest(double totalAmount, String orderId) throws Exception {
        try {
            // ??????????????????????????????????????????????????????
            String totalproce = String.valueOf(Float.parseFloat(totalAmount+"") * 100);
            WxConfigUtil configUtil = new WxConfigUtil(wxPayConfig.getCertPath(),wxPayConfig.getAppId(),wxPayConfig.getMchId(),wxPayConfig.getKey());
            WXPay wxPay = new WXPay(configUtil);
            Map<String, String> data = new HashMap<>();

            // ??????????????????id
            data.put("appid", configUtil.getAppID()); // ????????????APPID
            data.put("mch_id", configUtil.getMchID()); // ?????????
            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            data.put("sub_mch_id", wxPayConfig.getSubMchId()); // ????????????
            data.put("nonce_str", WXPayUtil.generateNonceStr());// ???????????????
            data.put("out_trade_no", orderId); // ???????????????
            data.put("total_fee", totalproce);  // ?????????
            InetAddress inetAddress = InetAddress.getLocalHost();// ?????????ip
            String spbillCreateIp= inetAddress== null? "": inetAddress.getHostAddress();
            data.put("spbill_create_ip", spbillCreateIp);// ??????IP
            data.put("notify_url", wxPayConfig.getTENPAY_ORDER_CALLBACK());// ????????????
            data.put("trade_type", wxPayConfig.getTRADE_TYPE_APP());// ????????????
            String sign= WXPayUtil.generateSignature(data, configUtil.getKey(), WXPayConstants.SignType.MD5);	// ?????????????????????
            data.put("sign", sign);
            // ????????????API??????????????????
            Map<String, String> response = wxPay.unifiedOrder(data);
            String returnCode = response.get("return_code");    // ???????????????
            Map<String, String> param = new LinkedHashMap<>();
            // ?????????????????????????????????
            if (returnCode.equals("SUCCESS")) {
                // ????????????????????????????????????
                String resultCode = response.get("result_code");
                param.put("appid", response.get("sub_appid"));// ???????????????id
                param.put("partnerid", response.get("sub_mch_id"));// ????????????
                param.put("package", wxPayConfig.getPackageValue());
                param.put("noncestr", WXPayUtil.generateNonceStr());// ???????????????
                param.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));     // ?????????
                if (resultCode.equals("SUCCESS")) {
                    param.put("prepayid", response.get("prepay_id"));// ????????????????????? ID
                    String retutnSign = WXPayUtil.generateSignature(param, configUtil.getKey(), WXPayConstants.SignType.MD5);	// ???????????????
                    param.put("sign", retutnSign);
                    String str1 = WXPayUtil.mapToXml(param);
                    param.put("packages", wxPayConfig.getPackageValue());
                    return param;
                } else {
                    throw new Exception("");
                }
            } else {
                throw new Exception("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("");
    }




    @PostMapping(value = "/xxxxx")
    public String wxPayNotify(HttpServletRequest request) {
        String resXml = "";
        try {
            InputStream inputStream = request.getInputStream();
            //???InputStream?????????xmlString
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            String result = payBack(resXml);
            return result;
        } catch (Exception e) {
            //logger.info("????????????????????????:" + e.getMessage());
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[????????????]]></return_msg>" + "</xml> ";
            return result;
        }
    }


  //  @Override
    public String payBack(String resXml) {
        WxConfigUtil config = null;
        try {
            config = new WxConfigUtil(wxPayConfig.getCertPath(),wxPayConfig.getAppId(),wxPayConfig.getMchId(),wxPayConfig.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        WXPay wxpay = new WXPay(config);
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            notifyMap = WXPayUtil.xmlToMap(resXml);// ???????????? SDK ????????? map ????????????
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {// ???????????????????????????????????????????????????
                String return_code = notifyMap.get("return_code");// ??????
                if (return_code.equals("SUCCESS")) {
                    String out_trade_no = notifyMap.get("out_trade_no");// ???????????????
                    String type = notifyMap.get("attach");
                    if (out_trade_no != null) {
                        // ??????????????????????????????????????????
                        synchronized (out_trade_no) {
                            // ????????????????????????????????????????????????
                            Integer status =null;// payLogMapper.getOneOrderStatusByPayNo(out_trade_no);
                            if (status == null || status != 2) {
                                // ??????????????????
                               // orderBiz.order(type,out_trade_no);
                            } else {
                         //       logger.info("????????????????????????,???????????????: " + out_trade_no);
                            }
                        }
                        xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
                      //  logger.info("?????????????????????????????????????????????");
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[????????????]]></return_msg>" + "</xml> ";
                    }
                }
                return xmlBack;
            } else {
                // ????????????????????????????????????sign?????????????????????????????????
              //  logger.info("????????????????????????????????????");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[????????????]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            //logger.info("??????????????????????????????:"+ e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[????????????]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }
}