package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ExtraEditReq;
import com.lt.dom.otcReq.ExtraReq;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.ZoneResp;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumFeatureTag;
import com.lt.dom.otcenum.EnumRedemptionMethod;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class ExtraRestController {




    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ExtraRepository extraRepository;
    @Autowired
    private ExtraServiceImpl extraService;

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




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/extras/Page_listExtra", produces = "application/json")
    public EntityModel<Media> Page_listExtra(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        List<Theatre> theatreList = theatreRepository.findAllBySupplier(supplier.getId());

        Map map = Map.of("theatre_list", Theatre.List(theatreList));


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(ExtraRestController.class).listExtra(supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(ExtraRestController.class).createExtra(supplier.getId(),null)).withRel("create"));


        return entityModel;

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/extras", produces = "application/json")
    public PagedModel listExtra(@PathVariable long SUPPLIER_ID , Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


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

        Page<Extra> bookingRuleList = extraRepository.findAllBySupplier(pageable,supplier.getId());

        return assembler.toModel(bookingRuleList.map(e->{

            ExtraResp movieResp = ExtraResp.from(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(ExtraRestController.class).getExtraEdit(e.getId())).withSelfRel());


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/extras", produces = "application/json")
    public EntityModel<Extra> createExtra(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid ExtraReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Extra Movie = extraService.create(supplier,movieReq);


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

    @PutMapping(value = "/extras/{Movie_ID}", produces = "application/json")
    public EntityModel<Movie> editMovie(@PathVariable long Movie_ID ,@RequestBody @Valid ExtraEditReq.EditTab  MovieReq) {

        Optional<Extra> supplierOptional = extraRepository.findById(Movie_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Extra supplier = supplierOptional.get();


        Extra Movie = extraService.update(supplier,MovieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/extras/Page_createMovie", produces = "application/json")
    public EntityModel<Movie> Page_createMovie(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(ExtraRestController.class).createExtra(supplier.getId(),null)).withRel("createMovie"));


        return entityModel;

    }



    @GetMapping(value = "/extras/{Movie_ID}", produces = "application/json")
    public EntityModel<Extra> getExtra(@PathVariable long Movie_ID) {

        Optional<Extra> validatorOptional = extraRepository.findById(Movie_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Extra movie = validatorOptional.get();
        ExtraResp movieResp = ExtraResp.from(movie);

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
        entityModel.add(linkTo(methodOn(ExtraRestController.class).getExtra(movie.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(ExtraRestController.class).editMovie(movie.getId(),null)).withRel("edit"));


        return entityModel;

    }



    @GetMapping(value = "/extras/{Movie_ID}/edit", produces = "application/json")
    public EntityModel<Extra> getExtraEdit(@PathVariable long Movie_ID) {

        Optional<Extra> validatorOptional = extraRepository.findById(Movie_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Extra movie = validatorOptional.get();


        ExtraResp  resp = ExtraResp.from(movie);

        ExtraEditReq.EditTab  editTab = ExtraEditReq.EditTab .from(movie);

        editTab.setPhoto(fileStorageService.loadDocumentWithDefault(EnumDocumentType.extra_photo,movie.getCode()));




        EntityModel entityModel_edit = EntityModel.of(editTab);
        entityModel_edit.add(linkTo(methodOn(ExtraRestController.class).editMovie(movie.getId(),null)).withRel("edit"));

        ExtraEditReq extraEditReq = new ExtraEditReq();
        extraEditReq.setEditTab(entityModel_edit);
        extraEditReq.setExtraResp(resp);

        EntityModel entityModel = EntityModel.of(extraEditReq);
        entityModel.add(linkTo(methodOn(ExtraRestController.class).getExtra(movie.getId())).withSelfRel());


        return entityModel;

    }





}