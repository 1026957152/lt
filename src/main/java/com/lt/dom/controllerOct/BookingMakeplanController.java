package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.MakeplanServiceImpl;
import com.lt.dom.vo.AvailabilityCalendarVO;
import com.lt.dom.vo.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class BookingMakeplanController {


    @Autowired
    private BookingResourceRepository bookingResourceRepository;

    @Autowired
    private BookingProviderRepository bookingProviderRepository;
    @Autowired
    private FileStorageServiceImpl fileStorageService;



    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private BookingServiceRepository bookingServiceRepository;
    @Autowired
    private MakeplanServiceImpl makeplanService;
    @Autowired
    private BookingMakeplanRepository bookingMakeplanRepository;

    @Autowired
    private TripRepository tripRepository;


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/makeplans", produces = "application/json")
    public PagedModel getTripList(@PathVariable long SUPPLIER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Page<BookingMakeplan> bookingRuleList = bookingMakeplanRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            BookingMakeplanResp movieResp = BookingMakeplanResp.from(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).getTrip(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).editTrip(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/booking_providers/{PROVIDER_ID}/makeplans", produces = "application/json")
    public EntityModel<BookingMakeplan> createBookingMakeplan(@PathVariable long PROVIDER_ID , @RequestBody @Valid BookingMakeplanReq tripReq) {

        Optional<BookingProvider> supplierOptional = bookingProviderRepository.findById(PROVIDER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingProvider supplier = supplierOptional.get();



        BookingMakeplan bookingMakeplan = makeplanService.create(supplier,tripReq);


        EntityModel entityModel = EntityModel.of(bookingMakeplan);

        return entityModel;

    }
/*

    @PostMapping(value = "/booking_providers/{PROVIDER_ID}/makeplans", produces = "application/json")
    public EntityModel<BookingMakeplan> createGuideBookingMakeplan(@PathVariable long PROVIDER_ID , @RequestBody @Valid BookingMakeplanReq tripReq) {

        Optional<BookingProvider> supplierOptional = bookingProviderRepository.findById(PROVIDER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingProvider supplier = supplierOptional.get();



        BookingMakeplan bookingMakeplan = makeplanService.create(supplier,tripReq);


        EntityModel entityModel = EntityModel.of(bookingMakeplan);

        return entityModel;

    }
    */

    @PostMapping(value = "/makeplan/{Trip_ID}", produces = "application/json")
    public EntityModel<Trip> editTrip(@PathVariable long Trip_ID ,@RequestBody @Valid TripReq TripReq) {

        Optional<Trip> supplierOptional = tripRepository.findById(Trip_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Trip supplier = supplierOptional.get();


        Trip Trip = makeplanService.update(supplier,TripReq);


        EntityModel entityModel = EntityModel.of(Trip);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/makeplan/Page_createTrip", produces = "application/json")
    public EntityModel<Trip> Page_createTrip(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).createBookingMakeplan(supplier.getId(),null)).withRel("createTrip"));


        return entityModel;

    }



    @GetMapping(value = "/makeplan/{Trip_ID}", produces = "application/json")
    public EntityModel<TripResp> getTrip(@PathVariable long Trip_ID) {

        Optional<Trip> validatorOptional = tripRepository.findById(Trip_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Trip Trip = validatorOptional.get();
        TripResp tripResp = TripResp.from(Trip);
        EntityModel entityModel = EntityModel.of(tripResp);
        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).getTrip(Trip.getId())).withRel("createTrip"));

        return entityModel;

    }





    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/booking_providers", produces = "application/json")
    public EntityModel<Place> createProvider(@PathVariable long SUPPLIER_ID , @RequestBody @Valid BookingProviderReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        BookingProvider place = makeplanService.createProvider(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }
    @GetMapping(value = "/booking_providers/{BOOKING_RESOURCE_ID}/front", produces = "application/json")
    public EntityModel<BookingProvider> getBookingProvider_front(@PathVariable long BOOKING_RESOURCE_ID ) {

        Optional<BookingProvider> bookingProviderOptional = bookingProviderRepository.findById(BOOKING_RESOURCE_ID);
        if(bookingProviderOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingProvider supplier = bookingProviderOptional.get();



        Optional<BookingResource> supplierOptional = bookingResourceRepository.findById(supplier.getResource());
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingResource bookingResource = supplierOptional.get();



        List<LocalDate> localDates =  LocalDate.now().datesUntil(LocalDate.now().plusDays(10))
                .collect(Collectors.toList());


        List<AvailabilityCalendarVO> availabilityCalendarVOS = makeplanService.getBookingResources_opening_hours(localDates,BookingResourceResp.from(bookingResource));


        BookingResourceResp bookingResourceResp = BookingResourceResp.fromwithAvailability(bookingResource,availabilityCalendarVOS);


        MediaResp mediaResp = new MediaResp();
        mediaResp.setPortrait(fileStorageService.loadDocumentWithCode(EnumDocumentType.makeplan_resource,bookingResource.getCode()));


        bookingResourceResp.setMedia(mediaResp);

        EntityModel entityModel = EntityModel.of(bookingResourceResp);
        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).createBookingMakeplan(supplier.getId(),null)).withRel("booking"));





        return entityModel;



    }

    @GetMapping(value = "/booking_providers/{BOOKING_RESOURCE_ID}", produces = "application/json")
    public EntityModel<BookingProvider> getBookingProvider(@PathVariable long BOOKING_RESOURCE_ID ) {

        Optional<BookingProvider> supplierOptional = bookingProviderRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingProvider supplier = supplierOptional.get();


        EntityModel entityModel = EntityModel.of(supplier);

        return entityModel;

    }


    @PutMapping(value = "/booking_providers/{BOOKING_RESOURCE_ID}", produces = "application/json")
    public EntityModel<BookingProvider> editBookingProvider(@PathVariable long BOOKING_RESOURCE_ID,BookingProviderReq bookingResourceReq ) {

        Optional<BookingProvider> supplierOptional = bookingProviderRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingProvider supplier = supplierOptional.get();
        BookingProvider place = makeplanService.editProvider(supplier,bookingResourceReq);


        EntityModel entityModel = EntityModel.of(supplier);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/booking_providers", produces = "application/json")
    public PagedModel getBookingProviders(@PathVariable long SUPPLIER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Page<BookingProvider> bookingRuleList = bookingProviderRepository.findAll(pageable);
        Map<Long,BookingService> bookingServiceMap = bookingServiceRepository.findAll().stream().collect(Collectors.toMap(BookingService::getId,e->e));

        Map<Long,BookingResource> bookingResourceMap = bookingResourceRepository.findAll().stream().collect(Collectors.toMap(BookingResource::getId,e->e));
        return assembler.toModel(bookingRuleList.map(e->{

            BookingResource bookingResource = bookingResourceMap.get(e.getResource());
            BookingService bookingService = bookingServiceMap.get(e.getService());

            BookingProviderResp movieResp = BookingProviderResp.from(e,bookingResource,bookingService);



            MediaResp mediaResp = new MediaResp();
            mediaResp.setPortrait(fileStorageService.loadDocumentWithCode(EnumDocumentType.makeplan_resource,bookingResource.getCode()));



            movieResp.setMedia(mediaResp);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingProvider(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).editBookingProvider(e.getId(),null)).withRel("booking"));
            return entityModel;
        }));

    }










    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/booking_resources/Page_createResource", produces = "application/json")
    public EntityModel<Museum> Page_createResource(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("resource_type_list", EnumResourceType.values(),
                "guide_level_list", EnumGuideLevel.values(),
                "guide_service_list", EnumGuideService.values(),
                "privacy_level_list", EnumPrivacyLevel.values()

                );


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).createResource(supplier.getId(),null)).withRel("create"));


        return entityModel;

    }

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/booking_resources", produces = "application/json")
    public EntityModel<BookingResource> createResource(@PathVariable long SUPPLIER_ID , @RequestBody @Valid BookingResourceReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        BookingResource place = makeplanService.createResource(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }


    @GetMapping(value = "/booking_resources/{BOOKING_RESOURCE_ID}", produces = "application/json")
    public EntityModel<BookingResource> getResource(@PathVariable long BOOKING_RESOURCE_ID ) {

        Optional<BookingResource> supplierOptional = bookingResourceRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingResource bookingResource = supplierOptional.get();

     //   BookingResourceResp bookingResourceResp = BookingResourceResp.from(supplier);




        List<LocalDate> localDates =  LocalDate.now().datesUntil(LocalDate.now().plusDays(10))
                .collect(Collectors.toList());


        List<AvailabilityCalendarVO> availabilityCalendarVOS = makeplanService.getBookingResources_opening_hours(localDates,BookingResourceResp.from(bookingResource));


        BookingResourceResp bookingResourceResp = BookingResourceResp.fromwithAvailability(bookingResource,availabilityCalendarVOS);


        MediaResp mediaResp = new MediaResp();
        mediaResp.setPortrait(fileStorageService.loadDocumentWithCode(EnumDocumentType.makeplan_resource,bookingResource.getCode()));


        bookingResourceResp.setMedia(mediaResp);

        EntityModel entityModel = EntityModel.of(bookingResourceResp);





        return entityModel;

    }


    @PutMapping(value = "/booking_resources/{BOOKING_RESOURCE_ID}", produces = "application/json")
    public EntityModel<BookingResource> editResource(@PathVariable long BOOKING_RESOURCE_ID,BookingResourceReq bookingResourceReq ) {

        Optional<BookingResource> supplierOptional = bookingResourceRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingResource supplier = supplierOptional.get();
        BookingResource place = makeplanService.editResource(supplier,bookingResourceReq);


        EntityModel entityModel = EntityModel.of(supplier);

        return entityModel;

    }



    @GetMapping(value = "/booking_services/{BOOKING_RESOURCE_ID}/Page_createValueList_getItems",produces = "application/json")
    public CollectionModel Page_createValueList_getItems(@PathVariable long BOOKING_RESOURCE_ID) {


        CollectionModel entityModel = null;

            List<BookingResource> bookingRuleList = bookingResourceRepository.findAll();

            if(bookingRuleList.isEmpty()){
                EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
                EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(EnumLongIdResp.class);
                //Resources<Object> resources = new Resources<>(Arrays.asList(wrapper));

                entityModel  = CollectionModel.of(Arrays.asList(wrapper));
            }else{
                entityModel = CollectionModel.of(BookingResource.List(bookingRuleList));



            }



        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).Page_createValueList_getItems(BOOKING_RESOURCE_ID)).withSelfRel());

        return entityModel;
    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/booking_resources", produces = "application/json")
    public PagedModel getBookingResources(@PathVariable long SUPPLIER_ID ,
                                          @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,

                                          PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();



        Page<BookingResource> bookingRuleList = bookingResourceRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            BookingResourceResp movieResp = BookingResourceResp.from(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).editResource(e.getId(),null)).withRel("edit"));
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).getResource(e.getId())).withSelfRel());


            return entityModel;
        }));

    }



    @GetMapping(value = "/booking_resources/{BOOKING_RESOURCE_ID}/opening_hours", produces = "application/json")
    public List<AvailabilityCalendarVO> getBookingResources_opening_hours(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from ,  @RequestParam

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PathVariable long BOOKING_RESOURCE_ID , Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {

        Optional<BookingResource> supplierOptional = bookingResourceRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingResource supplier = supplierOptional.get();

        BookingResourceResp bookingResourceResp = BookingResourceResp.from(supplier);


        List<LocalDate> localDates =  from.datesUntil(to)
                .collect(Collectors.toList());


        return makeplanService.getBookingResources_opening_hours(localDates,bookingResourceResp);





    }






    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/booking_services/Page_createBookingService", produces = "application/json")
    public EntityModel<Museum> Page_createBookingService(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


     //   Map map = Map.of("resource_type_list", EnumResourceType.values());

        Map map = Map.of("privacy_level_list", EnumPrivacyLevel.List());



        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).createBookingService(supplier.getId(),null)).withRel("create"));

        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).createProvider(supplier.getId(),null)).withRel("createProvider"));

        return entityModel;

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/booking_services", produces = "application/json")
    public EntityModel<BookingService> createBookingService(@PathVariable long SUPPLIER_ID , @RequestBody @Valid BookingServiceReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        BookingService place = makeplanService.createBookingService(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }


    @PutMapping(value = "/booking_services/{BOOKING_RESOURCE_ID}", produces = "application/json")
    public EntityModel<BookingService> editBookingService(@PathVariable long BOOKING_RESOURCE_ID,BookingServiceReq bookingResourceReq ) {

        Optional<BookingService> supplierOptional = bookingServiceRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingService supplier = supplierOptional.get();
        BookingService place = makeplanService.editBookingService(supplier,bookingResourceReq);


        EntityModel entityModel = EntityModel.of(supplier);

        return entityModel;

    }






    @GetMapping(value = "/booking_services/{BOOKING_RESOURCE_ID}/booking_providers/Page_createService_provider", produces = "application/json")
    public EntityModel<Museum> Page_createService_provider(@PathVariable long BOOKING_RESOURCE_ID  ) {


        Optional<BookingService> supplierOptional = bookingServiceRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingService supplier = supplierOptional.get();



        //   Map map = Map.of("resource_type_list", EnumResourceType.values());

        Map map = Map.of("privacy_level_list", EnumPrivacyLevel.List());



        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).createService_provider(supplier.getId(),null)).withRel("create"));

        return entityModel;

    }

    @PostMapping(value = "/booking_services/{BOOKING_RESOURCE_ID}/booking_providers", produces = "application/json")
    public EntityModel<Place> createService_provider(@PathVariable long BOOKING_RESOURCE_ID  , @RequestBody @Valid BookingProviderForServiceReq movieReq) {

        Optional<BookingService> supplierOptional = bookingServiceRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingService supplier = supplierOptional.get();



        BookingProvider place = makeplanService.createProviderForBookingService(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }
    @GetMapping(value = "/booking_services/{BOOKING_RESOURCE_ID}", produces = "application/json")
    public EntityModel<BookingService> getBookingService(@PathVariable long BOOKING_RESOURCE_ID ) {

        Optional<BookingService> supplierOptional = bookingServiceRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingService supplier = supplierOptional.get();

        BookingServiceResp resp = BookingServiceResp.from(supplier);
        MediaResp mediaResp = new MediaResp();
        mediaResp.setPortrait(fileStorageService.loadDocumentWithDefault(EnumDocumentType.makeplan_resource,supplier.getCode()));
        resp.setMedia(mediaResp);
        EntityModel entityModel = EntityModel.of(resp);
        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).Page_createService_provider(supplier.getId())).withRel("Page_createProvider"));


        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).createService_provider(supplier.getId(),null)).withRel("createProvider"));

        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingServiceProvider(supplier.getId(),null,null)).withRel("getProviderList"));


        entityModel.add(linkTo(methodOn(BookingMakeplanController.class).Page_createValueList_getItems(supplier.getId())).withRel("getBookingResourceList"));



        return entityModel;

    }



    @GetMapping(value = "/booking_services/{BOOKING_RESOURCE_ID}/providers", produces = "application/json")
    public PagedModel getBookingServiceProvider(@PathVariable long BOOKING_RESOURCE_ID,
                                                @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,

                                                PagedResourcesAssembler<EntityModel<BookingResource>> assembler) {        Optional<BookingService> supplierOptional = bookingServiceRepository.findById(BOOKING_RESOURCE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingService supplier = supplierOptional.get();

        Page<BookingProvider> bookingProviders = bookingProviderRepository.findAll(pageable);
        Map<Long,BookingResource> bookingResourceMap = bookingResourceRepository.
                findAllById(bookingProviders.stream().map(e->e.getResource()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));


        return assembler.toModel(bookingProviders.map(e->{
            BookingResource bookingResource = bookingResourceMap.get(e.getResource());
            BookingProviderResp bookingProviderResp = BookingProviderResp.from(e);
            bookingProviderResp.setResource(BookingResourceResp.from(bookingResource));
            EntityModel entityModel = EntityModel.of(bookingProviderResp);
            return entityModel;
        }));

    }







    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/booking_services", produces = "application/json")
    public PagedModel getBookingServices(@PathVariable long SUPPLIER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();



        Page<BookingService> bookingRuleList = bookingServiceRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            BookingServiceResp movieResp = BookingServiceResp.from(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).editBookingService(e.getId(),null)).withRel("edit"));
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingService(e.getId())).withSelfRel());


            return entityModel;
        }));

    }


    @GetMapping(value = "/booking_services/{BOOKING_SERVICE_ID}/slots", produces = "application/json")
    public EntityModel<Slot> getSlotList(@PathVariable long BOOKING_SERVICE_ID , @RequestBody @Valid BookingServiceResp movieReq) {

        Optional<BookingService> supplierOptional = bookingServiceRepository.findById(BOOKING_SERVICE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BookingService supplier = supplierOptional.get();




        EntityModel entityModel = EntityModel.of(Arrays.asList(new Slot()));

        return entityModel;

    }


    @GetMapping(value = "/booking_services/{BOOKING_SERVICE_ID}/available_dates", produces = "application/json")
    public EntityModel<Slot> getAvailable_DatesList(@PathVariable long BOOKING_SERVICE_ID , @RequestBody @Valid BookingServiceResp movieReq) {

        Optional<BookingService> supplierOptional = bookingServiceRepository.findById(BOOKING_SERVICE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        BookingService supplier = supplierOptional.get();
        EntityModel entityModel = EntityModel.of(Arrays.asList(new Slot()));
        return entityModel;
    }

    @GetMapping(value = "/booking_services/{BOOKING_SERVICE_ID}/next_available_date", produces = "application/json")
    public EntityModel<Slot> getNext_available_dateList(@PathVariable long BOOKING_SERVICE_ID , @RequestBody @Valid BookingServiceResp movieReq) {

        Optional<BookingService> supplierOptional = bookingServiceRepository.findById(BOOKING_SERVICE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        BookingService supplier = supplierOptional.get();
        EntityModel entityModel = EntityModel.of(Arrays.asList(new Slot()));
        return entityModel;
    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/providers", produces = "application/json")
    public PagedModel home(@PathVariable long SUPPLIER_ID , Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Page<BookingProvider> bookingRuleList = bookingProviderRepository.findAll(pageable);
        Map<Long,BookingService> bookingServiceMap = bookingServiceRepository.findAll().stream().collect(Collectors.toMap(BookingService::getId,e->e));

        Map<Long,BookingResource> bookingResourceMap = bookingResourceRepository.findAll().stream().collect(Collectors.toMap(BookingResource::getId,e->e));
        return assembler.toModel(bookingRuleList.map(e->{

            BookingResource bookingResource = bookingResourceMap.get(e.getResource());
            BookingService bookingService = bookingServiceMap.get(e.getService());


            BookingResourceResp resp = BookingResourceResp.fromSimple(bookingResource);



/*
            List<LocalDate> localDates =  LocalDate.now().datesUntil(LocalDate.now().plusDays(10))
                    .collect(Collectors.toList());
            List<AvailabilityCalendarVO> availabilityCalendarVOS = makeplanService.getBookingResources_opening_hours(localDates,BookingResourceResp.from(bookingResource));
            BookingResourceResp bookingResourceResp = BookingResourceResp.fromwithAvailability(bookingResource,availabilityCalendarVOS);
*/


            BookingProviderResp bookingProviderResp = BookingProviderResp.from(e,resp,bookingService);

            MediaResp mediaResp = new MediaResp();
            mediaResp.setPortrait(fileStorageService.loadDocumentWithDefault(EnumDocumentType.makeplan_resource,bookingResource.getCode()));


           // mediaResp.setIntroductionAudio(fileStorageService.loadDocumentWithCode(EnumDocumentType.city_walk_audio,bookingResource.getCode()).getUrl_thumbnail());


            bookingProviderResp.setMedia(mediaResp);



            EntityModel entityModel = EntityModel.of(bookingProviderResp);
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).getBookingProvider_front(e.getId())).withSelfRel());


           // entityModel.add(linkTo(methodOn(BookingMakeplanController.class).getResource(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(BookingMakeplanController.class).createBookingMakeplan(e.getId(),null)).withRel("booking"));
            return entityModel;
        }));

    }
}