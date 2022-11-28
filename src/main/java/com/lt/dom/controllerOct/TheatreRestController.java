package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.LocationReq;
import com.lt.dom.OctResp.MovieResp;
import com.lt.dom.OctResp.TheatreReq;
import com.lt.dom.OctResp.TheatreResp;
import com.lt.dom.controllerOct.Axh.XhSupplierRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.SeatingLayoutReq;
import com.lt.dom.otcenum.*;

import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.TheatreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class TheatreRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MovieProductRepository movieProductRepository;

    @Autowired
    private MovieRepository movieRepository;



    @Autowired
    private TheatreServiceImpl theatreService;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/movies/Page_listTheatre", produces = "application/json")
    public EntityModel<Media> Page_listTheatre(@PathVariable Long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        List<Theatre> theatreList = theatreRepository.findAllBySupplier(supplier.getId());

        Map map = Map.of("theatre_list", Theatre.List(theatreList));


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(TheatreRestController.class).getTheatreList(supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(TheatreRestController.class).createTheatre(supplier.getId(),null)).withRel("create"));


        return entityModel;

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/theatres", produces = "application/json")
    public PagedModel getTheatreList(@PathVariable long SUPPLIER_ID ,
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

        Page<Theatre> bookingRuleList = theatreRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            TheatreResp theatreResp = TheatreResp.of(e);

            EntityModel entityModel = EntityModel.of(theatreResp);
            entityModel.add(linkTo(methodOn(TheatreRestController.class).getTheatreEdit(e.getId())).withSelfRel());
        //    entityModel.add(linkTo(methodOn(TheatreRestController.class).getTheatreEdit(e.getId())).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/theatres", produces = "application/json")
    public EntityModel<Theatre> createTheatre(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid TheatreReq theatreReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Theatre theatre = theatreService.create(supplier,theatreReq);

        EntityModel entityModel = EntityModel.of(theatre);

        return entityModel;

    }


    @PutMapping(value = "/theatres/{THEATRE_ID}", produces = "application/json")
    public EntityModel<Theatre> editTheatre(@PathVariable long THEATRE_ID ,@RequestBody @Valid TheatreReq theatreReq) {

        Optional<Theatre> supplierOptional = theatreRepository.findById(THEATRE_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Theatre supplier = supplierOptional.get();


        Theatre theatre = theatreService.update(supplier,theatreReq);


        TheatreReq theatreReq1 = TheatreReq.from(theatre);


        EntityModel entityModel = EntityModel.of(theatreReq1);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/theatres/Page_createTheatre", produces = "application/json")
    public EntityModel<Theatre> Page_createTheatre(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(TheatreRestController.class).createTheatre(supplier.getId(),null)).withRel("createTheatre"));


        return entityModel;

    }



    @GetMapping(value = "/theatres/{THEATRE_ID}", produces = "application/json")
    public EntityModel<Theatre> getTheatre(@PathVariable long THEATRE_ID) {

        Optional<Theatre> validatorOptional = theatreRepository.findById(THEATRE_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");
        }

        Theatre theatre = validatorOptional.get();
        TheatreReq theatreResp = TheatreReq.from(theatre);



        Address address = theatre.getLocation();
        theatreResp.setLocation(LocationReq.from(address));

        theatreResp.setCover(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.theatreImageStandard,theatre.getCode()));
        EntityModel entityModel = EntityModel.of(theatreResp);
        entityModel.add(linkTo(methodOn(TheatreRestController.class).getTheatre(theatre.getId())).withSelfRel());




        List<MovieProduct> movieProductList = movieProductRepository.findAll();//movieProductRepository.findAllByTheatre(theatre.getId());

        List<Movie> movieList = movieRepository.findAllById(movieProductList.stream().map(e->e.getMovie()).collect(Collectors.toList()));

        Map<Long,Movie>  movieListMap  = movieList.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        theatreResp.setMovies(movieProductList.stream().map(e->{

            Movie movie = movieListMap.get(e.getMovie());

            MovieResp movieResp = MovieResp.homeFrontSimple(movie);

            movieResp.setThumb(fileStorageService.loadDocumentWithDefault(EnumDocumentType.movie_cover,movie.getCode()));
            EntityModel entityModel1 = EntityModel.of(movieResp);
            entityModel1.add(linkTo(methodOn(MovieRestController.class).getMovie(e.getMovie())).withSelfRel());

            return entityModel1;
        }).collect(Collectors.toList()));

        return entityModel;

    }



    @GetMapping(value = "/theatres/{THEATRE_ID}/edit", produces = "application/json")
    public EntityModel<Theatre> getTheatreEdit(@PathVariable long THEATRE_ID) {

        Optional<Theatre> validatorOptional = theatreRepository.findById(THEATRE_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");
        }

        Theatre theatre = validatorOptional.get();
        TheatreReq theatreResp = TheatreReq.from(theatre);


        theatreResp.setParameterList(Map.of("redemption_method_list",EnumRedemptionMethod.EnumList()));

        Address address = theatre.getLocation();
        theatreResp.setLocation(LocationReq.from(address));

        theatreResp.setCover(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.theatreImageStandard,theatre.getCode()));




        List<MovieProduct> movieProductList = movieProductRepository.findAllByTheatre(theatre.getId());

        List<Movie> movieList = movieRepository.findAllById(movieProductList.stream().map(e->e.getMovie()).collect(Collectors.toList()));

        Map<Long,Movie>  movieListMap  = movieList.stream().collect(Collectors.toMap(e->e.getId(),e->e));


        theatreResp.setMovies(movieProductList.stream().map(e->{

            Movie movie = movieListMap.get(e.getMovie());

            MovieResp movieResp = MovieResp.homeFrontSimple(movie);

            movieResp.setThumb(fileStorageService.loadDocumentWithDefault(EnumDocumentType.movie_cover,movie.getCode()));
            EntityModel entityModel1 = EntityModel.of(movieResp);
            entityModel1.add(linkTo(methodOn(MovieRestController.class).getMovie(e.getMovie())).withSelfRel());

            return entityModel1;
        }).collect(Collectors.toList()));

        EntityModel entityModel = EntityModel.of(theatreResp);

        entityModel.add(linkTo(methodOn(TheatreRestController.class).editTheatre(theatre.getId(),null)).withRel("edit"));

    //    entityModel.add(linkTo(methodOn(TheatreRestController.class).getTheatreEdit(theatre.getId())).withRel("createTheatre"));
        entityModel.add(linkTo(methodOn(TheatreRestController.class).Page_createSeatingLayout(theatre.getId())).withRel("Page_createSeatingLayout"));
        entityModel.add(linkTo(methodOn(TheatreRestController.class).createSeatingLayout(theatre.getId(),null)).withRel("createSeatingLayout"));



        return entityModel;

    }










    @GetMapping(value = "/theatres/{THEATRE_ID}/Page_createSeatingLayout", produces = "application/json")
    public EntityModel<Theatre> Page_createSeatingLayout(@PathVariable long THEATRE_ID ) {


        Optional<Theatre> validatorOptional = theatreRepository.findById(THEATRE_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        Theatre theatre = validatorOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values(),
                "seat_type_list", EnumSeatType.values(),
                "seat_tier_list", EnumSeatTier.values()

        );


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(TheatreRestController.class).createSeatingLayout(theatre.getId(),null)).withRel("createTheatre"));


        return entityModel;

    }


    @PostMapping(value = "/theatres/{THEATRE_ID}/seating_layouts", produces = "application/json")
    public EntityModel<SeatingLayout> createSeatingLayout(@PathVariable long THEATRE_ID ,@RequestBody @Valid SeatingLayoutReq theatreReq) {

        Optional<Theatre> validatorOptional = theatreRepository.findById(THEATRE_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到剧院");

        }
        Theatre theatre = validatorOptional.get();

        SeatingLayout seatingLayout = theatreService.createSeatingLayout(theatre,theatreReq);


        EntityModel entityModel = EntityModel.of(seatingLayout);

        return entityModel;

    }

}