package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumExportVoucher;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumSupplierType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.specification.GuideSpecification;
import com.lt.dom.specification.SearchQuery;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    @Autowired
    private GuideRepository guideRepository;


    @Autowired
    private UserRepository userRepository;



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










    @GetMapping(value = "/guides/Page_getGuideList", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_getGuideList() {


        EntityModel entityModel = EntityModel.of(Map.of("type_list", Arrays.stream(EnumSupplierType.values()).map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList()),
                "status_list", Arrays.stream(EnumSupplierType.values()).map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())));
        entityModel.add(linkTo(methodOn(ExportRestController.class).createExport(EnumExportVoucher.redemption,null)).withRel("createExport"));

        entityModel.add(linkTo(methodOn(GuideRestController.class).getGuideList(null,null,null)).withRel("getGuideList"));
        return entityModel;
    }

    @GetMapping(value = "/guides", produces = "application/json")
    public ResponseEntity<PagedModel> getGuideList(SearchQuery searchQuery,Pageable pageable, PagedResourcesAssembler<EntityModel<EnumLongIdResp>> assembler) {

        String search = searchQuery.getSearch();
        System.out.println("=================:" + search.toString() + "");
        GuideSpecification spec =
                new GuideSpecification(Arrays.asList("real_name","code","phone"), search); //, "code", "claim_note"

        Page<Guide> campaignPageable = guideRepository.findAll(pageable);
        List<User> users = userRepository.findAllById(campaignPageable.stream().map(x->x.getUserId()).collect(Collectors.toList()));
        Map<Long, User> longUserMap = users.stream().collect(Collectors.toMap(x->x.getId(), x->x));
        Page<EntityModel<EnumLongIdResp>> campaignResps = GuideResp.pageMapToListEnumSimple(campaignPageable,longUserMap);
        return ResponseEntity.ok(assembler.toModel(campaignResps));


    }



}