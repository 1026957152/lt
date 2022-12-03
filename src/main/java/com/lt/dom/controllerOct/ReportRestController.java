package com.lt.dom.controllerOct;

import cn.hutool.core.util.DesensitizedUtil;
import com.lt.dom.OctResp.CustomerDemographicsResp;
import com.lt.dom.OctResp.ReportBookingSummary;
import com.lt.dom.OctResp.ReportSaleResp;
import com.lt.dom.OctResp.VoucherLiabilityResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.BookingServiceImpl;
import com.lt.dom.serviceOtc.CancellationServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@RestController
@RequestMapping("/oct")
public class ReportRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BookingServiceImpl bookingService;


    @Autowired
    private CancellationServiceImpl cancellationService;


    @Autowired
    private ReservationRepository reservationRepository;



    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private VonchorServiceImpl vonchorService;
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





    @GetMapping(value = "reports/Booking-summary", produces = "application/json")
    public List<ReportBookingSummary> Booking_summary( @RequestBody ReportReq reportReq) {




        List<Reservation> reservationList = reservationRepository.findAll();


        return reservationList.stream().map(e->{

            ReportBookingSummary bookingSummary = new ReportBookingSummary();

            bookingSummary.setBooking_ID(e.getCode());
            bookingSummary.setBooking_name(e.getNote());
            bookingSummary.setCustomer_ID(e.getLog_buyer_id_number());
            bookingSummary.setCustomer_name(e.getLog_buyer_name());
          //  bookingSummary.setStart_date();
           // bookingSummary.setEnd_date();
            bookingSummary.setStatus(e.getStatus().toString());
            bookingSummary.setPayment_status("");


            Optional<Cancellation> cancellations = cancellationService.getCancellation(e);
            bookingSummary.setReason("ddd");
            if(cancellations.isPresent()){
                bookingSummary.setCancelled_date(cancellations.get().getCreatedDate().toString());
            }
            bookingSummary.setConfirmed_date("null");

            bookingService.getCost(e);

         //   bookingSummary.setTotal_sale_price(e.getTotal_amount());


            bookingSummary.setMargin("");

            bookingSummary.setAgent_commission("不知道");
           // bookingSummary.setAgent(e.getAgent());

            return bookingSummary;

        }).collect(Collectors.toList());





    }


}