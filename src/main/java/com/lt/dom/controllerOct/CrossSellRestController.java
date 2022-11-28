package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.CrossSellResp;
import com.lt.dom.OctResp.ExtraResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CrossSellReq;
import com.lt.dom.otcReq.ExtraReq;
import com.lt.dom.otcenum.EnumRedemptionMethod;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.CrossSellServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class CrossSellRestController {




    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CrossSellRepository crossSellRepository;
    @Autowired
    private CrossSellServiceImpl crossSellService;

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




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/crossSells/Page_listMovie", produces = "application/json")
    public EntityModel<Media> Page_listMovie(@PathVariable long SUPPLIER_ID ) {


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


    @GetMapping(value = "/products/{PRODUCT_ID}/crossSells", produces = "application/json")
    public PagedModel listExtras(@PathVariable long PRODUCT_ID , Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Product> supplierOptional = productRepository.findById(PRODUCT_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Product supplier = supplierOptional.get();

/*        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Product product = validatorOptional.get();*/

        Page<CrossSell> bookingRuleList = new PageImpl(supplier.getCrossSells());

        return assembler.toModel(bookingRuleList.map(e->{

            CrossSellResp movieResp = CrossSellResp.from(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(CrossSellRestController.class).getExtra(e.getId().getCrossSellProductId())).withSelfRel());


            return entityModel;
        }));

    }


    @PostMapping(value = "/products/{PRODUCT_ID}/crossSells", produces = "application/json")
    public EntityModel<CrossSell> createExtra(@PathVariable long PRODUCT_ID ,@RequestBody @Valid CrossSellReq movieReq) {

        Optional<Product> supplierOptional = productRepository.findById(PRODUCT_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Product supplier = supplierOptional.get();

        Optional<Product> crossSellProductOptional = productRepository.findById(movieReq.getId());
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        Product crossSellProduct = crossSellProductOptional.get();
        CrossSell Movie = crossSellService.create(supplier,crossSellProduct);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }

/*

    @PutMapping(value = "/movie/{ATTRACTION_ID}/aboutTab", produces = "application/json")
    public ResponseEntity<Movie> editAttractionAboutTab(@PathVariable long ATTRACTION_ID, @RequestBody @Valid MovieEdit.AboutTap pojo) {

        Optional<Movie> validatorOptional = MovieRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Movie attraction = validatorOptional.get();


        attraction=  extraService.editAboutTab(attraction,pojo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(attraction.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(attraction);


    }
*/

    @PutMapping(value = "/crossSells/{Movie_ID}", produces = "application/json")
    public EntityModel<Movie> editMovie(@PathVariable long Movie_ID ,@RequestBody @Valid ExtraReq MovieReq) {

        Optional<CrossSell> supplierOptional = crossSellRepository.findById(Movie_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        CrossSell supplier = supplierOptional.get();


        CrossSell Movie = crossSellService.update(supplier,MovieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/crossSells/Page_createMovie", produces = "application/json")
    public EntityModel<Movie> Page_createMovie(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(CrossSellRestController.class).createExtra(supplier.getId(),null)).withRel("createMovie"));


        return entityModel;

    }



    @GetMapping(value = "/crossSells/{Movie_ID}", produces = "application/json")
    public EntityModel<CrossSell> getExtra(@PathVariable long Movie_ID) {

        Optional<CrossSell> validatorOptional = crossSellRepository.findById(Movie_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        CrossSell movie = validatorOptional.get();
        CrossSellResp movieResp = CrossSellResp.from(movie);

/*

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
*/

        EntityModel entityModel = EntityModel.of(movieResp);
        entityModel.add(linkTo(methodOn(CrossSellRestController.class).getExtra(movie.getCrossSellProduct().getId())).withSelfRel());


        return entityModel;

    }







}