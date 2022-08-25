package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.CampaignAssignToTourBookingResp;
import com.lt.dom.OctResp.GuideInchargeBookingResp;
import com.lt.dom.OctResp.TourbookingGuideResp;
import com.lt.dom.OctResp.TourbookingTourResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingDocumentIdsResp;
import com.lt.dom.otcenum.EnumAssociatedType;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Quintet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class GuideRestController {

    @Autowired
    private TravelerRepository travelerRepository;

    @Autowired
    private CampaignRepository campaignRepository;


    @Autowired
    private AssetServiceImpl assetService;

    @Autowired
    private InchargeBookingRepository inchargeBookingRepository;
    @Autowired
    private TourBookingRepository tourBookingRepository;

    @Autowired
    private CampaignAssignToTourBookingRepository campaignAssignToTourBookingRepository;
    @Autowired
    private SupplierRepository supplierRepository;


    @GetMapping(value = "guides/{GUIDE_ID}/incharge_bookings", produces = "application/json")
    public PagedModel listAvailability(@PathVariable long GUIDE_ID, Pageable pageable,PagedResourcesAssembler<EntityModel<GuideInchargeBookingResp>> assembler) {

        Page<GuideInchargeBooking> validatorOptional = inchargeBookingRepository.findAllByGuideId(GUIDE_ID,pageable);

        List<TourBooking> tourBookingList = tourBookingRepository.findAllById(validatorOptional.stream().map(x->x.getBooking()).collect(Collectors.toList()));
        Map<Long,TourBooking> tourBookingMap = tourBookingList.stream().collect(Collectors.toMap(e->e.getId(), e->e));

        List<CampaignAssignToTourBooking> campaignAssignToTourBookings = campaignAssignToTourBookingRepository.findAllByTourBookingIn(tourBookingList.stream().map(x->x.getId()).collect(Collectors.toList()));
        Map<Long,List<CampaignAssignToTourBooking>> campaignAssignToTourBookingMap = campaignAssignToTourBookings.stream().collect(Collectors.groupingBy(e->e.getTourBooking()));
        Map<Long,Supplier> longSupplierMap = supplierRepository
                .findAllById(tourBookingList.stream().map(x->x.getOwner()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));



        return assembler.toModel(validatorOptional.map(x->{
            EntityModel entityModel = EntityModel.of(GuideInchargeBookingResp.from(
                    x,
                    tourBookingMap.get(x.getBooking()),
                    campaignAssignToTourBookingMap.get(x.getBooking()),longSupplierMap.get(x.getAgency())));


            entityModel.add(linkTo(methodOn(GuideRestController.class).getReservation(x.getBooking())).withSelfRel());
            entityModel.add(linkTo(methodOn(GuideRestController.class).campaigns(x.getBooking(),null,null)).withRel("getCampaigns"));


            return entityModel;
        }));

    }





    @GetMapping(value = "tour_booking/{TOUR_BOOKING_ID}/campaigns", produces = "application/json")
    public PagedModel campaigns(@PathVariable long TOUR_BOOKING_ID, Pageable pageable,PagedResourcesAssembler<EntityModel<GuideInchargeBookingResp>> assembler) {

        Page<CampaignAssignToTourBooking> validatorOptional = campaignAssignToTourBookingRepository.findAllByTourBooking(TOUR_BOOKING_ID,pageable);

        List<TourBooking> tourBooking = tourBookingRepository.findAllById(validatorOptional.stream().map(x->x.getTourBooking()).collect(Collectors.toList()));


        Map<Long,TourBooking> tourBookingMap = tourBooking.stream().collect(Collectors.toMap(e->e.getId(), e->e));


        Map<String, Asset> assetMap = assetService.getQrs(validatorOptional.stream().map(x->x.getCode()).collect(Collectors.toList()));


        return assembler.toModel(validatorOptional.map(x->{
            EntityModel entityModel = EntityModel.of(CampaignAssignToTourBookingResp.from(x,assetMap.containsKey(x.getCode())? Optional.of(assetMap.get(x.getCode())): Optional.empty()));

            return entityModel;
        }));

    }


    @Operation(summary = "1、获得订购")
    @GetMapping(value = "/tour_bookings/{RESERVATOIN_ID}/guide", produces = "application/json")
    public ResponseEntity<EntityModel<TourbookingTourResp>> getReservation(@PathVariable long RESERVATOIN_ID) {
        Optional<TourBooking> optionalProduct = tourBookingRepository.findById(RESERVATOIN_ID);


        if(optionalProduct.isEmpty()) {

            throw new BookNotFoundException(RESERVATOIN_ID,Product.class.getSimpleName());
        }
        TourBooking tourBooking = optionalProduct.get();

        if(!optionalProduct.get().getProductType().equals(EnumProductType.Daytour)) {

            throw new BookNotFoundException(RESERVATOIN_ID,"不是 Daytour 类型");

        }


            List<Traveler> travelers = travelerRepository.findAllByBooking(tourBooking.getId());


            List<CampaignAssignToTourBooking> campaignAssignToTourBookings = campaignAssignToTourBookingRepository.findByTourBooking(tourBooking.getId());
        List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourBookings.stream().map(x->x.getCampaign()).collect(Collectors.toSet()));

        Map<Long, Campaign> campaignMap =  campaigns.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        Map<String, Asset> assetMap = assetService.getQrs(campaignAssignToTourBookings.stream().map(x->x.getCode()).collect(Collectors.toList()));


        List<EntityModel> campaignAssignToTourBookingList = campaignAssignToTourBookings.stream().map(x->{
            EntityModel entityModel = EntityModel.of(CampaignAssignToTourBookingResp.from(x,campaignMap.get(x.getCampaign()),assetMap.containsKey(x.getCode())? Optional.of(assetMap.get(x.getCode())): Optional.empty(),travelers));


            return entityModel;
        }).collect(Collectors.toList());

            TourbookingGuideResp reservationTourResp = TourbookingGuideResp.toResp(tourBooking,travelers,campaignAssignToTourBookingList);





            EntityModel entityModel = EntityModel.of(reservationTourResp);

            return ResponseEntity.ok(entityModel);



    }





}