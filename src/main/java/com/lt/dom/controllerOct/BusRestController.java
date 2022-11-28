package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.*;
import com.lt.dom.OctResp.home.HomeBusResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.BusServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class BusRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BusStopRepository busStopRepository;


    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private BusServiceImpl buseservice;
    @Autowired
    private PlaceRegistrationRepository placeRegistrationRepository;

    @Autowired
    private PlaceRepository placeRepository;


    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private SeatingLayoutRepository seatingLayoutRepository;
    @Autowired
    private MovieProductRepository movieProductRepository;
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private PricingTypeRepository pricingTypeRepository;


    @Autowired
    private MovieRepository MovieRepository;

    @Autowired
    private BusRouteRepository busRouteRepository;
    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private AttributeRepository attributeRepository;



    @GetMapping(value = "/Page_bues", produces = "application/json")
    public EntityModel<Media> Page_bues() {


        List<BusRoute> supplierOptional = busRouteRepository.findAll();
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }


        HomeBusResp homeBusResp = new HomeBusResp();

        List<EntityModel> list = supplierOptional.stream().map(supplier->{
            BusRouteResp busRouteResp = BusRouteResp.of(supplier);

            busRouteResp.setStops(supplier.getStops().stream().map(e->{
                BusStopResp busStopReq = BusStopResp.fromWithId(e.getStop());

                busStopReq.setDeparture_time(LocalTime.now());

                busStopReq.setPlaces(e.getStop().getPlaceRegistrations().stream().map(placeR->{

                    Place place = placeR.getPlace();
                    PlaceResp placeResp = PlaceResp.from(placeR.getPlace());

                    placeResp.setPhoto(fileStorageService.loadDocumentWithDefault( EnumDocumentType.place_photo,place.getCode()));
                    EntityModel entityModel = EntityModel.of(placeResp);
                    switch(place.getType()){
                        case tourist_attraction:{
                            Link link = linkTo(methodOn(AttractionRestController.class).getAttraction(place.getType_reference(), EnumUrlSourceType.normal)).withSelfRel();
                            entityModel.add(link);
                            placeResp.setPath("/pages/attractions/show?url="+link.getHref());
                            placeResp.setLink(true);
                        }

                            break;
                        case movie_theater:{
                            Link link = linkTo(methodOn(TheatreRestController.class).getTheatre(place.getType_reference())).withSelfRel();
                            entityModel.add(link);
                            placeResp.setPath("/pages/venue/show?url="+link.getHref());
                            placeResp.setLink(true);

                        }
                        break;
                        default:
                            placeResp.setLink(false);

                    }
               //     entityModel.add(linkTo(methodOn(TripRestController.class).getPlace(place.getId())).withSelfRel());

                    return entityModel;
                }).collect(Collectors.toList()));

                EntityModel entityModel = EntityModel.of(busStopReq);
                entityModel.add(linkTo(methodOn(BusRestController.class).listPlace(e.getStop().getId())).withSelfRel());

                entityModel.add(linkTo(methodOn(BusRestController.class).getStop(e.getStop().getId())).withSelfRel());
                return entityModel;
            }).collect(Collectors.toList()));


            busRouteResp.withBuses(supplier.getBuses());

            EntityModel entityModel = EntityModel.of(busRouteResp);
            entityModel.add(linkTo(methodOn(BusRestController.class).getRoute(supplier.getId())).withSelfRel());
            return entityModel;
        }).collect(Collectors.toList());


        homeBusResp.setLines(list);







        List<Product>  productList  = productRepository
                .findAllByType(EnumProductType.BusTicket)
                .stream().filter(e->EnumPrivacyLevel.public_.equals(e.getPrivacyLevel())).collect(Collectors.toList());

        Map<Long, PricingType> longPricingTypeMap = pricingTypeRepository.findAllById(productList.stream().map(e->e.getDefault_price()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));


        List<EntityModel> productResps = productList.stream().map(e->{

            PricingType pricingType_default = null;
            if(e.getDefault_price() == null){
                if(longPricingTypeMap.isEmpty()){
                    pricingType_default = longPricingTypeMap.values().stream().findAny().get();

                }else{
                    pricingType_default = new PricingType();
                }
            }else{
                pricingType_default = longPricingTypeMap.get(e.getDefault_price());

            }



            ProductResp productResp = new ProductResp();
            productResp.setDesc_short(e.getName_long());
            productResp.setName(e.getName());
            productResp.setDefault_sku(PricingTypeResp.sku_simple(pricingType_default));
            EntityModel<ProductResp> entityModel = EntityModel.of(productResp);
            entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());

            return entityModel;
        }).collect(Collectors.toList());


        homeBusResp.setAvailableFares(productResps);

        EntityModel entityModel = EntityModel.of(homeBusResp);
        entityModel.add(linkTo(methodOn(BusRestController.class).Page_buyTicket()).withRel("Page_buyTicket"));

        return entityModel;

    }


    @GetMapping(value = "/buses/Page_buyTicket", produces = "application/json")
    public EntityModel<Media> Page_buyTicket() {


/*
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();
*/


        List<Product>  productList  = productRepository
                .findAllByType(EnumProductType.BusTicket)
                .stream().filter(e->EnumPrivacyLevel.public_.equals(e.getPrivacyLevel())).collect(Collectors.toList());

        Map<Long, PricingType> longPricingTypeMap = pricingTypeRepository.findAllById(productList.stream().map(e->e.getDefault_price()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));


        List<EntityModel> productResps = productList.stream().map(e->{

            PricingType pricingType_default = null;
            if(e.getDefault_price() == null){
                if(longPricingTypeMap.isEmpty()){
                    pricingType_default = longPricingTypeMap.values().stream().findAny().get();

                }else{
                    pricingType_default = new PricingType();
                }
            }else{
                pricingType_default = longPricingTypeMap.get(e.getDefault_price());

            }



            ProductResp productResp = new ProductResp();
            productResp.setDesc_short(e.getName_long());
            productResp.setName(e.getName());
            productResp.setDefault_sku(PricingTypeResp.sku_simple(pricingType_default));
            EntityModel<ProductResp> entityModel = EntityModel.of(productResp);
            entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());

            return entityModel;
        }).collect(Collectors.toList());

        EntityModel entityModel = EntityModel.of(Map.of("list",productResps));


        return entityModel;

    }


    @GetMapping(value = "/busStops/{SUPPLIER_ID}", produces = "application/json")
    public List listPlace(@PathVariable long SUPPLIER_ID ) {


        Optional<BusStop> supplierOptional = busStopRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BusStop supplier = supplierOptional.get();



        return supplier.getPlaceRegistrations().stream().map(e->{

            PlaceResp movieResp = PlaceResp.from(e.getPlace());

            EntityModel entityModel = EntityModel.of(movieResp);
          //  entityModel.add(linkTo(methodOn(CityWalkRestController.class).getPlace(e.getId())).withSelfRel());


            return entityModel;
        }).collect(Collectors.toList());

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/routes", produces = "application/json")
    public PagedModel listRoute(@PathVariable long SUPPLIER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Page<BusRoute> bookingRuleList = busRouteRepository.findAllBySupplier(pageable,supplier.getId());

        return assembler.toModel(bookingRuleList.map(e->{

            BusRouteResp movieResp = BusRouteResp.of(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(BusRestController.class).getMovieEdit(e.getId())).withSelfRel());


            return entityModel;
        }));

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/stops", produces = "application/json")
    public PagedModel listPlace(@PathVariable long SUPPLIER_ID , Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Page<BusStop> bookingRuleList = busStopRepository.findAllBySupplier(pageable,supplier.getId());

        return assembler.toModel(bookingRuleList.map(e->{

            BusStopResp movieResp = BusStopResp.of(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(BusRestController.class).getStop(e.getId())).withSelfRel());

            entityModel.add(linkTo(methodOn(BusRestController.class).editStop(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/stops", produces = "application/json")
    public EntityModel<BusStop> createStop(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid BusStopReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        BusStop Movie = buseservice.createStop(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }



    @PutMapping(value = "/stops/{STOP_ID}", produces = "application/json")
    public EntityModel<BusStop> editStop(@PathVariable long STOP_ID ,@RequestBody @Valid BusStopReq movieReq) {

        Optional<BusStop> supplierOptional = busStopRepository.findById(STOP_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BusStop supplier = supplierOptional.get();


        BusStop busStop = buseservice.editStop(supplier,movieReq);

        BusStopResp busStopReq = BusStopResp.from(busStop);

        EntityModel entityModel = EntityModel.of(busStopReq);

        return entityModel;

    }

    @GetMapping(value = "/stops/{STOP_ID}", produces = "application/json")
    public EntityModel<BusStop> getStop(@PathVariable long STOP_ID) {

        Optional<BusStop> supplierOptional = busStopRepository.findById(STOP_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        BusStop supplier = supplierOptional.get();


        BusStopResp busStopReq = BusStopResp.from(supplier);


        busStopReq.setPlaces(supplier.getPlaceRegistrations().stream().map(e->{
            PlaceResp placeResp = PlaceResp.from(e.getPlace());
            return placeResp;
        }).collect(Collectors.toList()));
        EntityModel entityModel = EntityModel.of(busStopReq);

        return entityModel;

    }

    @GetMapping(value = "/routes/{ROUTE_ID}", produces = "application/json")
    public EntityModel<BusRoute> getRoute(@PathVariable long ROUTE_ID ) {

        Optional<BusRoute> supplierOptional = busRouteRepository.findById(ROUTE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        BusRoute supplier = supplierOptional.get();
        BusRouteResp busRouteResp = BusRouteResp.of(supplier);

;
        busRouteResp.setStops(supplier.getStops().stream().map(e->{
            BusStopResp busStopReq = BusStopResp.fromWithId(e.getStop());

            busStopReq.setDeparture_time(LocalTime.now());

            busStopReq.setPlaces(e.getStop().getPlaceRegistrations().stream().map(placeR->{

                Place place = placeR.getPlace();
                PlaceResp placeResp = PlaceResp.from(placeR.getPlace());

                placeResp.setPhoto(fileStorageService.loadDocumentWithDefault( EnumDocumentType.movie_cover,place.getCode()));

                return placeResp;
            }).collect(Collectors.toList()));

            EntityModel entityModel = EntityModel.of(busStopReq);
            entityModel.add(linkTo(methodOn(BusRestController.class).listPlace(e.getStop().getId())).withSelfRel());

            entityModel.add(linkTo(methodOn(BusRestController.class).getStop(e.getStop().getId())).withSelfRel());
            return entityModel;
        }).collect(Collectors.toList()));


        busRouteResp.withBuses(supplier.getBuses());

        EntityModel entityModel = EntityModel.of(busRouteResp);
        entityModel.add(linkTo(methodOn(BusRestController.class).getRoute(supplier.getId())).withSelfRel());
        return entityModel;

    }


    @PutMapping(value = "/routes/{ROUTE_ID}", produces = "application/json")
    public EntityModel<BusRoute> editRoute(@PathVariable long ROUTE_ID,@RequestBody @Valid BusRouteReq movieReq ) {

        Optional<BusRoute> supplierOptional = busRouteRepository.findById(ROUTE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        BusRoute supplier = supplierOptional.get();

        supplier = buseservice.editRoute(supplier,movieReq);

        EntityModel entityModel = EntityModel.of(supplier);
        entityModel.add(linkTo(methodOn(BusRestController.class).getRoute(supplier.getId())).withSelfRel());
        return entityModel;

    }


    @GetMapping(value = "/routes/{ROUTE_ID}/edit", produces = "application/json")
    public EntityModel<BusRoute> getRouteEidt(@PathVariable long ROUTE_ID ,@RequestBody @Valid BusRouteReq movieReq) {

        Optional<BusRoute> supplierOptional = busRouteRepository.findById(ROUTE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        BusRoute supplier = supplierOptional.get();
        BusRouteResp busRouteResp = BusRouteResp.of(supplier);
        ;
        busRouteResp.setStops(supplier.getStops().stream().map(e->{
            BusStopResp busStopReq = BusStopResp.fromWithId(e.getStop());
            EntityModel entityModel = EntityModel.of(busStopReq);
            entityModel.add(linkTo(methodOn(BusRestController.class).getStop(e.getStop().getId())).withSelfRel());
            return entityModel;
        }).collect(Collectors.toList()));


        busRouteResp.withBuses(supplier.getBuses());

        EntityModel entityModel = EntityModel.of(busRouteResp);
        entityModel.add(linkTo(methodOn(BusRestController.class).editRoute(supplier.getId(),null)).withSelfRel());
        return entityModel;

    }


    @PostMapping(value = "/routes/{ROUTE_ID}/stops", produces = "application/json")
    public EntityModel<BusRoute> addStop(@PathVariable long ROUTE_ID ,@RequestBody @Valid List<BusStopReq> stopReqs) {

        Optional<BusRoute> supplierOptional = busRouteRepository.findById(ROUTE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        BusRoute supplier = supplierOptional.get();
        BusRoute busRoute = buseservice.addStop(supplier,stopReqs);

        BusRouteResp busRouteResp = BusRouteResp.of(busRoute);
        EntityModel entityModel = EntityModel.of(busRouteResp);
        entityModel.add(linkTo(methodOn(BusRestController.class).getRoute(supplier.getId())).withSelfRel());
        return entityModel;

    }

    @PostMapping(value = "/stops/{STOP_ID}/places", produces = "application/json")
    public EntityModel<BusRoute> addPlaceForStop(@PathVariable long STOP_ID, @RequestBody @Valid List<BusStopReq> stopReqs) {

        Optional<BusStop> supplierOptional = busStopRepository.findById(STOP_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        BusStop supplier = supplierOptional.get();
        BusStop busRoute = buseservice.addPlaceForStop(supplier,stopReqs);

        BusStopResp busRouteResp = BusStopResp.of(busRoute);
        EntityModel entityModel = EntityModel.of(busRouteResp);
        entityModel.add(linkTo(methodOn(BusRestController.class).getRoute(supplier.getId())).withSelfRel());
        return entityModel;

    }


    @PutMapping(value = "/stops/{STOP_ID}/places", produces = "application/json")
    public EntityModel<BusRoute> editPlaceForStop(@PathVariable long STOP_ID, @RequestBody @Valid PlaceRegistrationReq stopReq) {

        Optional<BusStop> supplierOptional = busStopRepository.findById(STOP_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        BusStop supplier = supplierOptional.get();
        Optional<Place> busStopList = placeRepository.findById(stopReq.getId());
        Place place = busStopList.get();


        Optional<PlaceRegistration> placeRegistration =
                placeRegistrationRepository.findById(new BusStopPlaceKey(place.getId(),supplier.getId()));
        if(placeRegistration.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到");

        }
        PlaceRegistration busRoute = buseservice.editPlaceForStop(placeRegistration.get(),stopReq);

        //   BusStopResp busRouteResp = BusStopResp.of(placeRegistration.get());
        EntityModel entityModel = EntityModel.of(busRoute);
        entityModel.add(linkTo(methodOn(BusRestController.class).getRoute(supplier.getId())).withSelfRel());
        return entityModel;

    }



    @PostMapping(value = "/routes/{ROUTE_ID}/buses", produces = "application/json")
    public EntityModel<BusRoute> addBus(@PathVariable long ROUTE_ID ,@RequestBody @Valid List<BusReq> stopReqs) {

        Optional<BusRoute> supplierOptional = busRouteRepository.findById(ROUTE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        BusRoute supplier = supplierOptional.get();
        BusRoute busRoute = buseservice.addBuses(supplier,stopReqs);

        BusRouteResp busRouteResp = BusRouteResp.of(busRoute);
        EntityModel entityModel = EntityModel.of(busRouteResp);
        entityModel.add(linkTo(methodOn(BusRestController.class).getRoute(supplier.getId())).withSelfRel());
        return entityModel;

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/routes", produces = "application/json")
    public EntityModel<Movie> createRoute(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid BusRouteReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        BusRoute Movie = buseservice.createRoute(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }

    @PutMapping(value = "/stops/{ATTRACTION_ID}/aboutTab", produces = "application/json")
    public ResponseEntity<Movie> editAttractionAboutTab(@PathVariable long ATTRACTION_ID, @RequestBody @Valid MovieEdit.AboutTap pojo) {

        Optional<Movie> validatorOptional = MovieRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Movie attraction = validatorOptional.get();


        attraction=  buseservice.editAboutTab(attraction,pojo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(attraction.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(attraction);


    }

    @PutMapping(value = "/buses/{Movie_ID}", produces = "application/json")
    public EntityModel<Movie> editMovie(@PathVariable long Movie_ID ,@RequestBody @Valid MovieEdit MovieReq) {

        Optional<Movie> supplierOptional = MovieRepository.findById(Movie_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Movie supplier = supplierOptional.get();


        Movie Movie = buseservice.update(supplier,MovieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/buses/Page_createMovie", produces = "application/json")
    public EntityModel<Movie> Page_createMovie(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(BusRestController.class).createRoute(supplier.getId(),null)).withRel("createMovie"));


        return entityModel;

    }



    @GetMapping(value = "/buses/{Movie_ID}", produces = "application/json")
    public EntityModel<Movie> getMovie(@PathVariable long Movie_ID) {

        Optional<Movie> validatorOptional = MovieRepository.findById(Movie_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Movie movie = validatorOptional.get();
        MovieResp movieResp = MovieResp.from(movie);


        Optional<Theatre> theatre = theatreRepository.findById(movie.getSupplier());

       if(theatre.isPresent()){

           TheatreResp theatreResp = TheatreResp.simple(theatre.get());

           LocationResp locationResp = LocationResp.from(theatre.get().getLocation());

           theatreResp.setAddress(locationResp);


           EntityModel entityModel = EntityModel.of(theatreResp);
           entityModel.add(linkTo(methodOn(TheatreRestController.class).getTheatre(theatre.get().getId())).withSelfRel());

           movieResp.setTheatre(entityModel);
       }



        List<MovieProduct> passProduct = movieProductRepository.findByMovie(movie.getId());
        MovieProduct movieProduct = passProduct.get(0);


        List<Sku> skuList = movieProduct.getZonePricings();

        Map<Long,Zone> zoneMap = zoneRepository.findAllById(skuList.stream().map(e->e.getZone()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

        ;
        movieResp.setBlocks(skuList.stream().collect(Collectors.groupingBy(e->e.getZone())).entrySet().stream().map(e->{
           ZoneResp zoneResp = ZoneResp.from(zoneMap.get(e.getKey()));

           Map<Long,PricingType> longPricingTypeMap = pricingTypeRepository.findAllById(e.getValue().stream().map(ex->ex.getPricingType()).collect(Collectors.toList()))
                   .stream().collect(Collectors.toMap(ee->ee.getId(),ee->ee));

           return Map.of("zone_name",zoneResp.getName(),
                   "limit",10,
                   "remaining",100,
                   "skus",e.getValue().stream().map(ee-> {

                       PricingType pricingType = longPricingTypeMap.get(ee.getPricingType());
                       PricingTypeResp pricingTypeResp = PricingTypeResp.sku(pricingType);
                       pricingTypeResp.setId(ee.getId());
                       return pricingTypeResp;
                   }).collect(Collectors.toList()));


       }).collect(Collectors.toList()));



        Map<String,Attribute> attributeList = attributeRepository.findAllByObjectCode(movie.getCode()).stream().collect(Collectors.toMap(e->e.getKey(),e->e));


        movieResp.setShow_intro(AttributeResp.fromWithOutFeature(attributeList.get("show_intro")));
        movieResp.setStory_intro(AttributeResp.fromWithOutFeature(attributeList.get("story_intro")));
        movieResp.setTeam_intro(AttributeResp.fromWithOutFeature(attributeList.get("team_intro")));

        /// movieResp.setPerformances();

        movieResp.setCover(fileStorageService.loadDocumentWithDefault( EnumDocumentType.movie_cover,movie.getCode()));
        movieResp.setVideo(fileStorageService.loadDocumentWithCode( EnumDocumentType.movie_vidio,movie.getCode()));

        EntityModel entityModel = EntityModel.of(movieResp);
        entityModel.add(linkTo(methodOn(BusRestController.class).getMovie(movie.getId())).withSelfRel());

        entityModel.add(linkTo(methodOn(BusRestController.class).Page_selectSeat(movieProduct.getId())).withRel("Page_seat_selection").expand());


        return entityModel;

    }


    @GetMapping(value = "/buses/{Movie_ID}/seat_selection", produces = "application/json")
    public EntityModel<Movie> Page_selectSeat(@PathVariable long Movie_ID) {


        Optional<MovieProduct> validatorOptional = movieProductRepository.findById(Movie_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        MovieProduct movieProduct = validatorOptional.get();




        List<Sku> skuList = movieProduct.getZonePricings();

        Map<Long,Zone> zoneMap = zoneRepository.findAllById(skuList.stream().map(e->e.getZone()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));


       Map map =  Map.of(
               "seats",Arrays.asList(),

               "blocks",skuList.stream().collect(Collectors.groupingBy(e->e.getZone())).entrySet().stream().map(e->{
            ZoneResp zoneResp = ZoneResp.from(zoneMap.get(e.getKey()));

            Map<Long,PricingType> longPricingTypeMap = pricingTypeRepository.findAllById(e.getValue().stream().map(ex->ex.getPricingType()).collect(Collectors.toList()))
                    .stream().collect(Collectors.toMap(ee->ee.getId(),ee->ee));

            return Map.of(

                    "zone_name",zoneResp.getName(),
                    "limit",10,
                    "remaining",100,


                    "skus",e.getValue().stream().map(ee-> {

                        PricingType pricingType = longPricingTypeMap.get(ee.getPricingType());
                        PricingTypeResp pricingTypeResp = PricingTypeResp.sku(pricingType);
                        pricingTypeResp.setId(ee.getId());
                        return pricingTypeResp;
                    }).collect(Collectors.toList()));


        }).collect(Collectors.toList()));


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(BookingRestController.class).Page_BookingSku(movieProduct.getProduct(), null)).withRel("Page_createBooking").expand());


        return entityModel;

    }



    @GetMapping(value = "/buses/{Movie_ID}/edit", produces = "application/json")
    public EntityModel<Movie> getMovieEdit(@PathVariable long Movie_ID) {

        Optional<Movie> validatorOptional = MovieRepository.findById(Movie_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Movie movie = validatorOptional.get();
        List<Movie> movieList = movieRepository.findAll();
        MovieEdit movieResp = MovieEdit.from(movie);
        movieResp.setParameterList(
                Map.of("movie_list",Movie.List(movieList))
        );
        MovieEdit.StarringActorTab starringActorTab = MovieEdit.StarringActorResp.from();


        starringActorTab.setStarringActors(movie.getStarringActors().stream().map(star->{
            MovieEdit.StarringActorResp starringActor = new MovieEdit.StarringActorResp();
            starringActor.setDesc(star.getDesc());
            starringActor.setName(star.getName());
            starringActor.setPhoto(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.movie_star_photo,star.getUuid()));

            return starringActor;
        }).collect(Collectors.toList()));
        EntityModel entityModel_starringActorTab = EntityModel.of(starringActorTab);

        entityModel_starringActorTab.add(linkTo(methodOn(BusRestController.class).editMovie(movie.getId(),null)).withRel("edit"));
        movieResp.setStarringActorTab(entityModel_starringActorTab);














        Map<String,Attribute> attributeList = attributeRepository.findAllByObjectCode(movie.getCode()).stream().collect(Collectors.toMap(e->e.getKey(),e->e));




        Attribute 演出介绍 =attributeList.getOrDefault("show_intro",new Attribute());
        Attribute 剧情介绍 =attributeList.getOrDefault("story_intro",new Attribute());// attributeList.stream().filter(e->e.getKey().equals("story_intro")).findAny().get() ;

        Attribute 演出团体 =attributeList.getOrDefault("team_intro",new Attribute());// attributeList.stream().filter(e->e.getKey().equals("team_intro")).findAny().get() ;



        MovieEdit.AboutTap aboutTap = MovieEdit.AboutTap.from(演出介绍,剧情介绍,演出团体);

        aboutTap.setParameterList(
                Map.of(
                        "feature_tag_list",EnumFeatureTag.List()

                ));
        EntityModel entityModel_aboutTap = EntityModel.of(aboutTap);
        entityModel_aboutTap.add(linkTo(methodOn(BusRestController.class).editAttractionAboutTab(movie.getId(),null)).withRel("edit"));
        movieResp.setAboutTab(entityModel_aboutTap);



        movieResp.setCover(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.movie_cover,movie.getCode()));
        movieResp.setVideo(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.movie_vidio,movie.getCode()));


        EntityModel entityModel = EntityModel.of(movieResp);

        entityModel.add(linkTo(methodOn(BusRestController.class).editMovie(movie.getId(),null)).withRel("edit"));






        return entityModel;

    }





    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/bus_vehicles", produces = "application/json")
    public EntityModel<BusVehicle> createBusVehicle(@PathVariable long SUPPLIER_ID , @RequestBody @Valid BusVehicleReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        BusVehicle busVehicle = buseservice.createBusVehicle(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(busVehicle);

        return entityModel;

    }



    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/vehicle_", produces = "application/json")
    public EntityModel<Showtime> createVehicle(@PathVariable long SUPPLIER_ID , @RequestBody @Valid ShowtimeReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Optional<Movie> movieOptional = movieRepository.findById(movieReq.getLayout());

        if(movieOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧目");

        }
        Movie movie = movieOptional.get();

        Optional<Theatre> theatreOptional =theatreRepository.findById(movieReq.getTheatre());
        if(theatreOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        Theatre theatre = theatreOptional.get();


        Optional<SeatingLayout> seatingLayoutOptional =seatingLayoutRepository.findById(movieReq.getLayout());
        if(theatreOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        SeatingLayout seatingLayout = seatingLayoutOptional.get();
        Showtime Movie = buseservice.createShowtime(supplier,theatre,movie,seatingLayout,movieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }
}