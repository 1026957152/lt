package com.lt.dom.controllerOct;

import cn.hutool.core.util.DesensitizedUtil;
import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.specification.BookingQueryfieldsCriteria;
import com.lt.dom.specification.BookingSpecification;
import com.lt.dom.specification.ComponentVoucherQueryfieldsCriteria;
import com.lt.dom.specification.ComponentVoucherSpecification;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.springframework.data.jpa.domain.Specification.where;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class ReportRestController {

    private static final Logger logger = LoggerFactory.getLogger(ReportRestController.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    @Autowired
    private BookingServiceImpl bookingService;


    @Autowired
    private CancellationServiceImpl cancellationService;


    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private ComponentVounchRepository componentVounchRepository;



    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private FulfillServiceImpl vonchorService;
    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;

    @Autowired
    private PublicationEntryRepository publicationEntryRepository;

    @GetMapping(value = "/redemption_entries", produces = "application/json")
    public Page<RedemptionEntry> pageRedemptionEntry(@ModelAttribute RedemptionFilters filters,
                                                     PageReq page) {


        Page<RedemptionEntry> validatorOptional = redemptionEntryRepository.findAll(PageRequest.of(page.getPage(),page.getLimit()));


        return validatorOptional.map(x->{
            return x;
        });



    }



    @GetMapping(value = "reports/customer_demographics", produces = "application/json")
    public ResponseEntity<Page<CustomerDemographicsResp>> pageCustomerDemographics(@RequestBody CustomerDemographicsFilters customerDemographicsFilters,
                                                                                   @RequestBody PageReq pageReq) {
        DesensitizedUtil.idCardNum("51343620000320711X", 1, 2);
        try {

            Page<Traveler> travelers = travelerRepository.findAll(PageRequest.of(pageReq.getPage(),pageReq.getLimit()));
            return ResponseEntity.ok(travelers.map(x->new CustomerDemographicsResp()));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }



    }


    @GetMapping(value = "reports/voucher_liability", produces = "application/json")
    public ResponseEntity<Page<VoucherLiabilityResp>> pageVoucherLiability(@RequestBody VoucherLiabilityFilters voucherLiabilityFilters,
                                                                           @RequestParam( defaultValue ="0") int page,
                                                                           @RequestParam( defaultValue = "10") int limit ) {
       // DesensitizedUtil.idCardNum("51343620000320711X", 1, 2);
        try {

            Page<Voucher> voucherLiabilityResps = voucherRepository.findAll(PageRequest.of(page,limit));

            return ResponseEntity.ok(voucherLiabilityResps.map(x->{
                VoucherLiabilityResp voucherLiabilityResp = new VoucherLiabilityResp();
                voucherLiabilityResp.setVoucher_code(x.getCode());
                return voucherLiabilityResp;
            }));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }



    }

    @GetMapping(value = "reports/sales", produces = "application/json")
    public ResponseEntity<ReportSaleResp> listAvailability( @RequestBody ReportReq reportReq) {

        try {
            return ResponseEntity.ok(new ReportSaleResp());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }



    }



    @GetMapping(value = "reports/publications", produces = "application/json")
    public ResponseEntity<ReportSaleResp> getPublicationStatistics( @RequestBody ReportReq reportReq) {


        List<PublicationEntry> publicationEntryList = publicationEntryRepository.findAll();



        Map<EnumPublicationObjectType, List<PublicationEntry>> postsPerType = publicationEntryList.stream()
                .collect(groupingBy(PublicationEntry::getToWhoType));

        long 个人领券 = postsPerType.get(EnumPublicationObjectType.customer).stream().count();
        long 个人领券金额= postsPerType.get(EnumPublicationObjectType.customer).stream().mapToInt(x->1).sum();

        long 企业领券 = postsPerType.get(EnumPublicationObjectType.business).stream().count();
       // long 企业领券 = postsPerType.get(EnumPublicationObjectType.business).stream().mapToInt(x->1).sum();





        List<RedemptionEntry> redemptionEntryList = redemptionEntryRepository.findAll();


        int 这家企业全部核销金额 = redemptionEntryList
                .stream().filter(x->x.getSupplier() == 100).mapToInt(x->x.getGift_amount()).sum();



        Map<Long, Integer> 每一个消费券的核销的金额 = redemptionEntryList.stream()
                .collect(groupingBy(RedemptionEntry::getSupplier, summingInt(RedemptionEntry::getGift_amount)));



/*

        int 这家企业全部核销金额 = redemptionEntryList
                .stream().filter(x->x.getSupplier() == 100).mapToInt(x->x.getGift_amount()).sum();
*/


        try {
            return ResponseEntity.ok(new ReportSaleResp());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }



    }



    @GetMapping(value = "/reports/Page_Booking_summary", produces = "application/json")

    public EntityModel Page_Booking_summary() {




        List<AgentConnection> agentConnections = agentRepository.findAll();


        List<Product> productList = productRepository.findAll();


        Map map = Map.of("agent_list", AgentConnection.List(agentConnections),
                "product_list", Product.List(productList),
                "metric_list", EnumReportMetric.from()
                );


            EntityModel entityModel = EntityModel.of(map);


            entityModel.add(linkTo(methodOn(ReportRestController.class).Booking_summary(null)).withRel("create"));


            return entityModel;

        }





    @GetMapping(value = "/reports/booking-summary", produces = "application/json")

    public List<ReportBookingSummary> Booking_summary( @RequestBody ReportReq reportReq) {


        System.out.println("=================:" + reportReq.toString() + "");

        BookingQueryfieldsCriteria searchQuery = new BookingQueryfieldsCriteria();

      //  searchQuery.setProduct(reportReq.getProduct());
        searchQuery.setAgent(reportReq.getAgent());

        BookingSpecification spec =
                new BookingSpecification(searchQuery); //, "code", "claim_note"



        List<Reservation> reservationList = reservationRepository.findAll(where(spec));


        return reservationList.stream().map(e->{

            ReportBookingSummary bookingSummary = new ReportBookingSummary();

            bookingSummary.setBooking_ID(e.getCode());
            bookingSummary.setBooking_name(e.getNote());
            bookingSummary.setCustomer_ID(e.getLog_buyer_id_number());
            bookingSummary.setCustomer_name(e.getLog_buyer_name());
          //  bookingSummary.setStart_date();
           // bookingSummary.setEnd_date();
            bookingSummary.setStatus(e.getStatus().toString());

            Payment payment = paymentService.getPayment(e);


            bookingSummary.setPayment_status(payment.getStatus().toString());


            Optional<Cancellation> cancellations = cancellationService.getCancellation(e);
            bookingSummary.setReason("ddd");
            if(cancellations.isPresent()){
                bookingSummary.setCancelled_date(cancellations.get().getCreatedDate().toString());
            }
            bookingSummary.setConfirmed_date("null");

            ;
            bookingService.getCost(e).toString();
           bookingSummary.setTotal_sale_price(e.getAmount()+"");


            bookingSummary.setMargin(bookingService.getMargin(e).toString());

            bookingSummary.setAgent_commission("不知道");
           // bookingSummary.setAgent(e.getAgent());

            return bookingSummary;

        }).collect(toList());





    }



    @GetMapping(value = "/reports/home-summary", produces = "application/json")
    public HomeSummary Home_summary() {

        List<Reservation> reservationList = reservationRepository.findAll();

        Long today_bookings = reservationList.stream().count();
        HomeSummary homeSummary = new HomeSummary();

        homeSummary.setBookings_Total_today(today_bookings.toString());

        homeSummary.setBookings_Total_today(today_bookings.toString());


        homeSummary.setPAX_Total_today(reservationList.stream().mapToInt(e->1).sum()+"");
        homeSummary.setPAX_Already_arrived(today_bookings.toString());



        homeSummary.setRevenueToday(reservationList.stream().mapToDouble(e->e.getAmount()).sum()+"");
        homeSummary.setRevenueToday(reservationList.stream().mapToDouble(e->e.getAmount()).sum()+"");

        return homeSummary;

    }














    @GetMapping(value = "/reports/Page_booking_source", produces = "application/json")

    public EntityModel Page_booking_source() {




        List<AgentConnection> agentConnections = agentRepository.findAll();


        List<Product> productList = productRepository.findAll();


        Map map = Map.of("agent_list", AgentConnection.List(agentConnections),
               // "product_list", Product.List(productList),
              //  "groupby_list", EnumReportBookingSourceGroupby.from(),
                "compression_list",EnumReportBookingCompression.from()
        );


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(ReportRestController.class).booking_source(null)).withRel("create"));


        return entityModel;

    }















    @GetMapping(value = "/reports/Page_arrivals", produces = "application/json")

    public EntityModel Page_arrivals() {




        List<AgentConnection> agentConnections = agentRepository.findAll();


        List<Product> productList = productRepository.findAll();
        List<Supplier> suppliers = supplierRepository.findAll();

        Map map = Map.of(
                "product_list", Product.List(productList),
                 "supplier_list", Supplier.List(suppliers),
                "compression_list",EnumReportBookingCompression.from()
        );


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(ReportRestController.class).arrivals(null)).withRel("create"));


        return entityModel;

    }




    @PostMapping(value = "/reports/booking-source", produces = "application/json")
    public ReportBookingSource booking_source( @RequestBody ReportBookingSourceReq reportReq) {
        logger.debug("da参数ange {} ",reportReq.toString());
        LocalDate bday = LocalDate.now();
        EnumReportBookingCompression compression = reportReq.getCompression();//"day";

/*        LocalDate today = LocalDate.now();
        Period age = Period.between(bday, today);*/

        final Map<EnumReportBookingCompression, TemporalAdjuster> ADJUSTERS = new HashMap<>();

        ADJUSTERS.put(EnumReportBookingCompression.day,TemporalAdjusters.ofDateAdjuster(d -> d)); // identity
        ADJUSTERS.put(EnumReportBookingCompression.week, TemporalAdjusters.previousOrSame(DayOfWeek.of(1)));
        ADJUSTERS.put(EnumReportBookingCompression.month, TemporalAdjusters.firstDayOfMonth());
        ADJUSTERS.put(EnumReportBookingCompression.year,TemporalAdjusters.firstDayOfYear());

        System.out.println("daysRange daysRange daysRange"+ ADJUSTERS);

        TemporalAdjuster adjuster = ADJUSTERS.get(compression);


        bday = bday.with(adjuster);


     //   int years = age.getYears(); int months = age.getMonths();





        List<LocalDate> daysRange = null;
                switch (compression){
            case day -> {
                daysRange = Stream.iterate(bday, date -> date.minusDays(1)).limit(12)

                        .collect(Collectors.toList());
            }

                    case week -> {
                        daysRange = Stream.iterate(bday, date -> date.minusWeeks(1)).limit(12)

                                .collect(Collectors.toList());
                    }
                    case month -> {
                        daysRange = Stream.iterate(bday, date -> date.minusMonths(1)).limit(12)

                                .collect(Collectors.toList());
                    }
                    case year -> {
                        daysRange = Stream.iterate(bday, date -> date.minusYears(1)).limit(3)

                                .collect(Collectors.toList());
                    }
            }





        System.out.println("daysRange daysRange daysRange"+ daysRange);

        List<Reservation> reservationList = reservationRepository.findAll();





/*
        Map<Object, List<Reservation>> result = reservationList.stream()
                .collect(Collectors.groupingBy(item -> item.getCreatedDate().toLocalDate()
                        .with(ADJUSTERS.get(compression.name()))));
*/


/*
        Map<EnumPlatform,List>  enumPlatformListMap =  reservationList.stream().collect(Collectors.groupingBy(e->e.getPlatform(),collectingAndThen(toList(),list->{


            Map<LocalDate, Integer> list_ = list.stream()
                    .collect(Collectors.groupingBy(item -> item.getCreatedDate().toLocalDate()
                            .with(ADJUSTERS.get(compression)),
                            collectingAndThen(toList(),listlist->{

                                return listlist.size();
                                    })

                    ));

            System.out.println("有数据的 马匹"+list_);
           ;
            Map<LocalDate, Integer>  fuckyou = daysRange.stream().map(days->{

                return Pair.with(days,list_.getOrDefault(days,0));
            }).collect(Collectors.toMap(xxx->xxx.getValue0(),xx->xx.getValue1()));
            System.out.println("有数据的 看卡顺序"+fuckyou);
            return  fuckyou.entrySet().stream().sorted(Map.Entry.<LocalDate, Integer>comparingByKey()).collect(toList());



            })));


       */


        List<Map>  head= daysRange.stream().map(e->{
            return Map.of("lable",e,"field",e);
        }).collect(toList());

        head.add(Map.of("lable","agent","field","agent"));




       // List<Reservation> reservationList_today = reservationRepository.findAllByCreatedDateIsAfter(LocalDate.now().atStartOfDay());



        ReportBookingSource reportBookingSource = new ReportBookingSource();


        List<LocalDate> finalDaysRange = daysRange;
        List<Map>  enumPlatformListMap__ =  reservationList.stream().collect(Collectors.groupingBy(e->e.getPlatform(),collectingAndThen(toList(), list->{


            Map<LocalDate, Integer> list_ = list.stream()
                    .collect(Collectors.groupingBy(item -> item.getCreatedDate().toLocalDate()
                                    .with(adjuster),
                            collectingAndThen(toList(),listlist->{
                                return listlist.size();
                            })

                    ));

            System.out.println("有数据的 马匹"+list_);
            ;
            Map<LocalDate, Integer>  fuckyou = finalDaysRange.stream().map(days->{

                return Pair.with(days,list_.getOrDefault(days,0));
            }).collect(Collectors.toMap(xxx->xxx.getValue0(),xx->xx.getValue1()));
            System.out.println("有数据的 看卡顺序"+fuckyou);
            return  fuckyou;//.entrySet().stream().sorted(Map.Entry.<LocalDate, Integer>comparingByKey()).collect(toList());



        }))).entrySet().stream().map(e->{

            e.getKey();
            Map hashMap = new HashMap<>(e.getValue());
            hashMap.put("agent",e.getKey());
            return hashMap;
        }).collect(toList());


        reportBookingSource.setGroupby_(Map.of("header",head,"list",enumPlatformListMap__));
/*
        reportBookingSource.setTest(result);


        List<Map>  collect_today =  Arrays.stream(EnumPlatform.values()).map(e->{

            return Map.of("key",e.name(),"bookings",enumPlatformListMap_today.getOrDefault(e,0));
        }).collect(toList());
        reportBookingSource.setGroupby_today(collect_today);*/
        return reportBookingSource;

    }




    @PostMapping(value = "/reports/arrivals", produces = "application/json")
    public List<ReportBookingSource> arrivals( @RequestBody ReportArrivalReq reportReq) {
        logger.debug("da参数ange {} ",reportReq.toString());
        LocalDate bday = LocalDate.now();
        EnumReportBookingCompression compression = reportReq.getCompression();//"day";



        final Map<EnumReportBookingCompression, TemporalAdjuster> ADJUSTERS = new HashMap<>();

        ADJUSTERS.put(EnumReportBookingCompression.day,TemporalAdjusters.ofDateAdjuster(d -> d)); // identity
        ADJUSTERS.put(EnumReportBookingCompression.week, TemporalAdjusters.previousOrSame(DayOfWeek.of(1)));
        ADJUSTERS.put(EnumReportBookingCompression.month, TemporalAdjusters.firstDayOfMonth());
        ADJUSTERS.put(EnumReportBookingCompression.year,TemporalAdjusters.firstDayOfYear());

        System.out.println("daysRange daysRange daysRange"+ ADJUSTERS);

        TemporalAdjuster adjuster = ADJUSTERS.get(compression);


        bday = bday.with(adjuster);




        List<LocalDate> daysRange = null;
        switch (compression){
            case day -> {
                daysRange = Stream.iterate(bday, date -> date.minusDays(1)).limit(12)

                        .collect(Collectors.toList());
            }

            case week -> {
                daysRange = Stream.iterate(bday, date -> date.minusWeeks(1)).limit(12)

                        .collect(Collectors.toList());
            }
            case month -> {
                daysRange = Stream.iterate(bday, date -> date.minusMonths(1)).limit(12)

                        .collect(Collectors.toList());
            }
            case year -> {
                daysRange = Stream.iterate(bday, date -> date.minusYears(1)).limit(3)

                        .collect(Collectors.toList());
            }
        }










        Optional<Supplier> supplierOptional = supplierRepository.findById(3L);
        if (supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商", "没找到");
        }

        Supplier supplier = supplierOptional.get();


        ComponentVoucherQueryfieldsCriteria criteria = new ComponentVoucherQueryfieldsCriteria();
       // criteria.setAgent(supplier.getId());

        criteria.setStarts_at(reportReq.getReport_data_from());


        criteria.setProduct(reportReq.getProduct());

        criteria.setStatuses(switch (reportReq.getReportType()){
            case NotRedeemed -> Arrays.asList(EnumComponentVoucherStatus.AlreadyRedeemed);


            case PartialyRedeemed ->Arrays.asList(EnumComponentVoucherStatus.NotRedeemed);

            default -> Arrays.asList();
        });


        criteria.setSupplier(reportReq.getSupplier());

        ComponentVoucherSpecification spec =
                new ComponentVoucherSpecification(criteria);

        List<ComponentVounch> reservationList = componentVounchRepository.findAll(where(spec));


        List<LocalDate> finalDaysRange = daysRange;


        return reservationList.stream().collect(Collectors.groupingBy(e->e.getSupplier())).entrySet().stream().map(e->{



            return arrivals(e.getKey(), e.getValue(),compression, finalDaysRange);

        }).collect(toList());








    }








    public ReportBookingSource arrivals(Long key, List<ComponentVounch> reservationList, EnumReportBookingCompression compression, List<LocalDate> daysRange) {

        LocalDate bday = LocalDate.now();


        final Map<EnumReportBookingCompression, TemporalAdjuster> ADJUSTERS = new HashMap<>();

        ADJUSTERS.put(EnumReportBookingCompression.day,TemporalAdjusters.ofDateAdjuster(d -> d)); // identity
        ADJUSTERS.put(EnumReportBookingCompression.week, TemporalAdjusters.previousOrSame(DayOfWeek.of(1)));
        ADJUSTERS.put(EnumReportBookingCompression.month, TemporalAdjusters.firstDayOfMonth());
        ADJUSTERS.put(EnumReportBookingCompression.year,TemporalAdjusters.firstDayOfYear());

        System.out.println("daysRange daysRange daysRange"+ ADJUSTERS);

        TemporalAdjuster adjuster = ADJUSTERS.get(compression);


        bday = bday.with(adjuster);





        System.out.println("daysRange daysRange daysRange"+ daysRange);








        List<Map>  head= daysRange.stream().map(e->{
            return Map.of("lable",e,"field",e);
        }).collect(toList());

        head.add(Map.of("lable","agent","field","agent"));





        ReportBookingSource reportBookingSource = new ReportBookingSource();


        List<LocalDate> finalDaysRange = daysRange;
        List<Map>  enumPlatformListMap__ =  reservationList.stream().collect(Collectors.groupingBy(e->e.getSupplier(),collectingAndThen(toList(), list->{
            Map<LocalDate, Integer> list_ = list.stream()
                    .collect(Collectors.groupingBy(item -> item.getCreatedDate().toLocalDate()
                                    .with(adjuster),
                            collectingAndThen(toList(),listlist->{
                                return listlist.size();
                            })
                    ));

            System.out.println("有数据的 马匹"+list_);
            ;
            Map<LocalDate, Integer>  fuckyou = finalDaysRange.stream().map(days->{

                return Pair.with(days,list_.getOrDefault(days,0));
            }).collect(Collectors.toMap(xxx->xxx.getValue0(),xx->xx.getValue1()));
            System.out.println("有数据的 看卡顺序"+fuckyou);
            return  fuckyou;//.entrySet().stream().sorted(Map.Entry.<LocalDate, Integer>comparingByKey()).collect(toList());



        }))).entrySet().stream().map(e->{

            e.getKey();
            Map hashMap = new HashMap<>(e.getValue());
            hashMap.put("agent",e.getKey());
            return hashMap;
        }).collect(toList());


        reportBookingSource.setGroupby_(Map.of(
                "groupby",key,
                "header",head,"list",enumPlatformListMap__));

        return reportBookingSource;

    }

}