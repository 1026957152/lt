package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.MovieReq;

import com.lt.dom.otcReq.ZoneResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class MovieRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieServiceImpl MovieService;

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
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private AttributeRepository attributeRepository;




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/movies/Page_listMovie", produces = "application/json")
    public EntityModel<Media> Page_listMovie(@PathVariable Long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        List<Theatre> theatreList = theatreRepository.findAllBySupplier(supplier.getId());

        Map map = Map.of("theatre_list", Theatre.List(theatreList));


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(MovieRestController.class).getMovieList(supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(MovieRestController.class).createMovie(supplier.getId(),null)).withRel("create"));


        return entityModel;

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/movies", produces = "application/json")
    public PagedModel getMovieList(@PathVariable long SUPPLIER_ID ,


                                   @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,


                                   PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

/*        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = validatorOptional.get();*/

        Page<Movie> bookingRuleList = MovieRepository.findAllBySupplier(pageable,supplier.getId());

        return assembler.toModel(bookingRuleList.map(e->{

            MovieResp movieResp = MovieResp.of(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(MovieRestController.class).getMovieEdit(e.getId())).withSelfRel());


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/movies", produces = "application/json")
    public EntityModel<Movie> createMovie(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid MovieReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Movie Movie = MovieService.create(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }


    @PutMapping(value = "/movie/{ATTRACTION_ID}/aboutTab", produces = "application/json")
    public ResponseEntity<Movie> editAttractionAboutTab(@PathVariable long ATTRACTION_ID, @RequestBody @Valid MovieEdit.AboutTap pojo) {

        Optional<Movie> validatorOptional = MovieRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Movie attraction = validatorOptional.get();


        attraction=  MovieService.editAboutTab(attraction,pojo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(attraction.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(attraction);


    }

    @PutMapping(value = "/movies/{Movie_ID}", produces = "application/json")
    public EntityModel<Movie> editMovie(@PathVariable long Movie_ID ,@RequestBody @Valid MovieEdit MovieReq) {

        Optional<Movie> supplierOptional = MovieRepository.findById(Movie_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Movie supplier = supplierOptional.get();


        Movie Movie = MovieService.update(supplier,MovieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/movies/Page_createMovie", produces = "application/json")
    public EntityModel<Movie> Page_createMovie(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(MovieRestController.class).createMovie(supplier.getId(),null)).withRel("createMovie"));


        return entityModel;

    }



    @GetMapping(value = "/movies/{Movie_ID}", produces = "application/json")
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

           Map<Long, PricingRate> longPricingTypeMap = pricingTypeRepository.findAllById(e.getValue().stream().map(ex->ex.getPricingType()).collect(Collectors.toList()))
                   .stream().collect(Collectors.toMap(ee->ee.getId(),ee->ee));

           return Map.of("zone_name",zoneResp.getName(),
                   "limit",10,
                   "remaining",100,
                   "skus",e.getValue().stream().map(ee-> {

                       PricingRate pricingRate = longPricingTypeMap.get(ee.getPricingType());
                       PricingTypeResp pricingTypeResp = PricingTypeResp.sku(pricingRate);
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
        entityModel.add(linkTo(methodOn(MovieRestController.class).getMovie(movie.getId())).withSelfRel());

        entityModel.add(linkTo(methodOn(MovieRestController.class).Page_selectSeat(movieProduct.getId())).withRel("Page_seat_selection").expand());


        return entityModel;

    }


    @GetMapping(value = "/movies/{Movie_ID}/seat_selection", produces = "application/json")
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

            Map<Long, PricingRate> longPricingTypeMap = pricingTypeRepository.findAllById(e.getValue().stream().map(ex->ex.getPricingType()).collect(Collectors.toList()))
                    .stream().collect(Collectors.toMap(ee->ee.getId(),ee->ee));

            return Map.of(

                    "zone_name",zoneResp.getName(),
                    "limit",10,
                    "remaining",100,


                    "skus",e.getValue().stream().map(ee-> {

                        PricingRate pricingRate = longPricingTypeMap.get(ee.getPricingType());
                        PricingTypeResp pricingTypeResp = PricingTypeResp.sku(pricingRate);
                        pricingTypeResp.setId(ee.getId());
                        return pricingTypeResp;
                    }).collect(Collectors.toList()));


        }).collect(Collectors.toList()));


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(BookingRestController.class).Page_BookingSku(movieProduct.getProduct(), null)).withRel("Page_createBooking").expand());


        return entityModel;

    }



    @GetMapping(value = "/movies/{Movie_ID}/edit", produces = "application/json")
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


        starringActorTab.setDeviceResps(movie.getStarringActors().stream().map(star->{
            MovieEdit.StarringActorResp starringActor = new MovieEdit.StarringActorResp();
            starringActor.setDesc(star.getDesc());
            starringActor.setName(star.getName());
            starringActor.setPhoto(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.movie_star_photo,star.getUuid()));

            return starringActor;
        }).collect(Collectors.toList()));
        EntityModel entityModel_starringActorTab = EntityModel.of(starringActorTab);

        entityModel_starringActorTab.add(linkTo(methodOn(MovieRestController.class).editMovie(movie.getId(),null)).withRel("edit"));
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
        entityModel_aboutTap.add(linkTo(methodOn(MovieRestController.class).editAttractionAboutTab(movie.getId(),null)).withRel("edit"));
        movieResp.setAboutTab(entityModel_aboutTap);



        movieResp.setCover(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.movie_cover,movie.getCode()));
        movieResp.setVideo(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.movie_vidio,movie.getCode()));


        EntityModel entityModel = EntityModel.of(movieResp);

        entityModel.add(linkTo(methodOn(MovieRestController.class).editMovie(movie.getId(),null)).withRel("edit"));






        return entityModel;

    }







    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/showtimes", produces = "application/json")
    public EntityModel<Showtime> createShowtime(@PathVariable long SUPPLIER_ID , @RequestBody @Valid ShowtimeReq movieReq) {

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
        Showtime Movie = MovieService.createShowtime(supplier,theatre,movie,seatingLayout,movieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }
}