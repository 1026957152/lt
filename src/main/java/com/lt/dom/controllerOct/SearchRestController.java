package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingDocumentIdsResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import com.lt.dom.specification.*;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.jpa.domain.Specification.where;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class SearchRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private RedemptionRepository redemptionRepository;
    @Autowired
    private CampaignAssignToTourBookingRepository campaignAssignToTourBookingRepository;


    @Autowired
    private TourBookingRepository tourBookingRepository;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private GuideRepository guideRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TravelerRepository travelerRepository;

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private AssetServiceImpl assetService;



    @GetMapping(value = "/campaigns/search__", produces = "application/json")
    public ResponseEntity<List<CampaignResp>> listCampaign(@RequestParam(value = "search") String search, Pageable pageable) {
        HelloSpecification spec =
                new HelloSpecification(new SearchCriteria("lastName", ":", "doe"));


     //   Arrays.asList("name","code", "claim_note"), search.getKey()
        Page<Campaign> campaignPageable = campaignRepository.findAll(spec,pageable);

        List<CampaignResp> campaignResps =  CampaignResp.pageMapToList(campaignPageable).getContent();

        return ResponseEntity.ok(campaignResps);


    }


    @GetMapping(value = "/campaigns/search", produces = "application/json")
    public ResponseEntity<PagedModel> searchCampaign(@RequestParam(value = "search") String search, Pageable pageable, PagedResourcesAssembler<EnumLongIdResp> assembler) {

        System.out.println("=================:" + search.toString() + "");
        CampaignSpecification spec =
                new CampaignSpecification(Arrays.asList("name","code", "claim_note"), search); //, "code", "claim_note"

        Page<Campaign> campaignPageable = campaignRepository.findAll(spec, pageable);

        Page<EnumLongIdResp> campaignResps = CampaignResp.pageMapToListEnumSimple(campaignPageable);

        return ResponseEntity.ok(assembler.toModel(campaignResps));


    }





















    @GetMapping(value = "/suppliers/search/Page_searchRedemptions", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_searchRedemptions() {


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

        entityModel.add(linkTo(methodOn(CampaignRestController.class).getCampaignList(null,null)).withRel("getCampaignList"));
        return entityModel;
    }


    @GetMapping(value = "/redemptions/search", produces = "application/json")
    public ResponseEntity<PagedModel> searchRedemptions(@RequestBody @Valid RedeptionQueryfieldsCriteria search, Pageable pageable, PagedResourcesAssembler<RedemptionEntryResp> assembler) {

        System.out.println("=================:" + search.toString() + "");
        RedemptionSpecification spec =
                new RedemptionSpecification(Arrays.asList("holder","campaign_name", "voucherCode"), null); //, "code", "claim_note"

        Page<RedemptionEntry> campaignPageable = redemptionEntryRepository.findAll(spec,pageable);

        Page<RedemptionEntryResp> campaignResps = RedemptionResp.pageFromWithout(campaignPageable);
        return ResponseEntity.ok(assembler.toModel(campaignResps));


    }


    @GetMapping(value = "/guides/search", produces = "application/json")
    public ResponseEntity<PagedModel> searchGuide(@RequestParam(value = "search") String search, Pageable pageable, PagedResourcesAssembler<EntityModel<EnumLongIdResp>> assembler) {

        System.out.println("=================:" + search.toString() + "");
        GuideSpecification spec =
                new GuideSpecification(Arrays.asList("real_name","code","phone"), search); //, "code", "claim_note"

        Page<Guide> campaignPageable = guideRepository.findAll(pageable);
        List<User> users = userRepository.findAllById(campaignPageable.stream().map(x->x.getUserId()).collect(Collectors.toList()));
        Map<Long, User> longUserMap = users.stream().collect(Collectors.toMap(x->x.getId(), x->x));
        Page<EntityModel<EnumLongIdResp>> campaignResps = GuideResp.pageMapToListEnumSimple(campaignPageable,longUserMap);
        return ResponseEntity.ok(assembler.toModel(campaignResps));


    }



    @GetMapping(value = "/user_guide/search", produces = "application/json")
    public ResponseEntity<PagedModel> searchUserForGuide(SearchQuery searchQuery, Pageable pageable, PagedResourcesAssembler<EntityModel<UserResp>> assembler) {

        String search = searchQuery.getSearch();
        System.out.println("=================:" + search.toString() + "");
        UserMultipleColumeSpecification spec =
                new UserMultipleColumeSpecification(Arrays.asList("realName","phone"), search); //, "code", "claim_note"

        Page<User> campaignPageable = userRepository.findAll(spec,pageable);


        Page<EntityModel<UserResp>> userPage = campaignPageable.map(x->{

            EntityModel entityModel =  EntityModel.of(UserResp.from(x));
            entityModel.add(linkTo(methodOn(UserRestController.class).beGuide(x.getId())).withRel("beGuide"));

            return entityModel;
        });

        return ResponseEntity.ok(assembler.toModel(userPage));


    }















    @GetMapping(value = "/suppliers/search/Page_searchCompany", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_searchCompany() {


        EntityModel entityModel = EntityModel.of(Map.of("type_list", Arrays.stream(EnumSupplierType.values()).map(x->{

            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList()),
                "status_list", Arrays.stream(EnumSupplierStatus.values()).map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())));

        entityModel.add(linkTo(methodOn(CampaignRestController.class).getCampaignList(null,null)).withRel("getCampaignList"));
        return entityModel;
    }


    @GetMapping(value = "/suppliers/search", produces = "application/json")
    public ResponseEntity<PagedModel> searchCompany(SupplierSearchQuery searchQuery,
                                                    @PageableDefault(sort = {"name", "id"}, direction = Sort.Direction.DESC, value = 5) final Pageable pageable,
                                                    PagedResourcesAssembler<EntityModel<SupplierResp>> assembler) {


        String search = searchQuery.getSearch();

        SupplierSpecification spec =
                new SupplierSpecification(Arrays.asList("desc","code","name"), search,searchQuery.getType(),searchQuery.getStatus()); //, "code", "claim_note"



        // System.out.println("=================:" + search.toString() + "");
        System.out.println("=================:" + pageable.toString() + "");
        //Page<Supplier> campaignPageable = supplierRepository.findAll(where(null == null? null:hasAuthor(null)).and(search == null? null:titleContains(search)),pageable);
        Page<Supplier> campaignPageable = supplierRepository.findAll(spec,pageable);


        return ResponseEntity.ok(assembler.toModel(campaignPageable.map(x->{

            EntityModel entityModel = EntityModel.of(SupplierResp.from(x));
            return entityModel;
        })));


    }

    static Specification<Supplier> hasAuthor(String author) {
        return (book, cq, cb) -> cb.equal(book.get("code"), author);
    }

    static Specification<Supplier> titleContains(String title) {
        return (book, cq, cb) -> cb.like(book.get("name"), "%" + title + "%");
    }





    @GetMapping(value = "/users/search", produces = "application/json")
    public ResponseEntity<PagedModel> searchUser(UserQueryfieldsCriteria search,
                                                    @PageableDefault(sort = {"phone", "id"}, direction = Sort.Direction.DESC, value = 5) final Pageable pageable,
                                                    PagedResourcesAssembler<EntityModel<SupplierResp>> assembler) {

        System.out.println("=================:" + search.toString() + "");
        System.out.println("=================:" + pageable.toString() + "");

        UserSpecification spec =
                new UserSpecification(search); //, "code", "claim_note"

        Page<User> campaignPageable = userRepository.findAll(where(spec),pageable);


        return ResponseEntity.ok(assembler.toModel(campaignPageable.map(x->{

            EntityModel entityModel = EntityModel.of(UserResp.from(x));
            return entityModel;
        })));


    }











    @GetMapping(value = "/tour_bookings/search/Page_searchTourBooking", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_searchTourBooking() {

        EntityModel entityModel = EntityModel.of(Map.of(/*"type_list", Arrays.stream(EnumBookingOjbectType.values()).map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList()),*/
                "status_list", Arrays.stream(EnumTourBookingStatus_.values()).map(x->{

                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
                }).collect(Collectors.toList())));

        entityModel.add(linkTo(methodOn(SearchRestController.class).searchTourBooking(null,null,null)).withRel("searchTourBooking"));
        return entityModel;
    }

    @GetMapping(value = "/tour_bookings/search", produces = "application/json")
    public ResponseEntity<PagedModel> searchTourBooking(TourBookingQueryfieldsCriteria searchQuery,
                                                        @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, value = 5) final Pageable pageable,
                                                        PagedResourcesAssembler<EntityModel<TourbookingTourResp>> assembler) {

        System.out.println("=================:" + searchQuery.toString() + "");
        System.out.println("=================:" + pageable.toString() + "");

        TourBookingSpecification spec =
                new TourBookingSpecification(searchQuery); //, "code", "claim_note"
        Page<TourBooking> tourBookingPage = tourBookingRepository.findAll(where(spec),pageable);




        List<CampaignAssignToTourBooking> campaignAssignToTourBookings = campaignAssignToTourBookingRepository.findAllByTourBookingIn(tourBookingPage.stream().map(x->x.getId()).collect(toList()));

        Map<Long,Campaign> campaignMap = campaignRepository.findAllById(campaignAssignToTourBookings.stream().map(x->x.getCampaign()).collect(toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),x->x));

        Map<Long,List<CampaignAssignToTourBookingResp>> campaignListMapt =
                campaignAssignToTourBookings.stream()
                .collect(Collectors.groupingBy(x->x.getTourBooking(),collectingAndThen(toList(),list->{
                    return list.stream().map(xx->{
                        return CampaignAssignToTourBookingResp.from(xx,campaignMap.get(xx.getCampaign()),Optional.empty(),Arrays.asList());
                    }).collect(toList());
                })));


        //toResp
        Page<EntityModel<TourbookingTourResp>> page =  tourBookingPage.map(x->{

            if(x.getProductType().equals(EnumProductType.Daytour)){

  /*              List<Traveler> travelers = travelerRepository.findAllByBooking(x.getId());
                List<Document> documents = documentRepository.findAllByRaletiveId(x.getId());
*/


                TourbookingTourResp resp = TourbookingTourResp.toResp(Pair.with(x,campaignListMapt.get(x.getId())));

              //  resp.setDocumentGroups(DocumentResp.groupfrom(documents));

                EntityModel entityModel = EntityModel.of(resp);

                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).bulkAddTravelers(x.getId(),null)).withRel("addTraveler"));
                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).createDocuments(x.getId(),new BookingDocumentIdsResp())).withRel("addDocument"));
                try {
                    entityModel.add(linkTo(methodOn(TourCampaignRestController.class).importExcelSubjectPreview(x.getId(),null)).withRel("addDocument"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                entityModel.add(linkTo(methodOn(TourCampaignRestController.class).getReservation(x.getId())).withSelfRel());

                return entityModel;
            }else{
                return EntityModel.of(new TourbookingTourResp());
            }
        });

        return ResponseEntity.ok(assembler.toModel(page));



    }


}




