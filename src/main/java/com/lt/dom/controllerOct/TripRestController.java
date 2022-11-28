package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.PlaceResp;
import com.lt.dom.OctResp.TripResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PlaceEditReq;
import com.lt.dom.otcReq.PlaceReq;
import com.lt.dom.otcReq.TripReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.TripServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class TripRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private TripServiceImpl tripService;


    @Autowired
    private TripRepository tripRepository;


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/trips", produces = "application/json")
    public PagedModel getTripList(@PathVariable long SUPPLIER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


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

        Page<Trip> bookingRuleList = tripRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            TripResp movieResp = TripResp.from(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(TripRestController.class).getTrip(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(TripRestController.class).editTrip(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/trips", produces = "application/json")
    public EntityModel<Trip> createTrip(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid TripReq tripReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Trip Trip = tripService.create(supplier,tripReq);


        EntityModel entityModel = EntityModel.of(Trip);

        return entityModel;

    }


    @PostMapping(value = "/trips/{Trip_ID}", produces = "application/json")
    public EntityModel<Trip> editTrip(@PathVariable long Trip_ID ,@RequestBody @Valid TripReq TripReq) {

        Optional<Trip> supplierOptional = tripRepository.findById(Trip_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Trip supplier = supplierOptional.get();


        Trip Trip = tripService.update(supplier,TripReq);


        EntityModel entityModel = EntityModel.of(Trip);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/trips/Page_createTrip", produces = "application/json")
    public EntityModel<Trip> Page_createTrip(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(TripRestController.class).createTrip(supplier.getId(),null)).withRel("createTrip"));


        return entityModel;

    }



    @GetMapping(value = "/trips/{Trip_ID}", produces = "application/json")
    public EntityModel<TripResp> getTrip(@PathVariable long Trip_ID) {

        Optional<Trip> validatorOptional = tripRepository.findById(Trip_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Trip Trip = validatorOptional.get();
        TripResp tripResp = TripResp.from(Trip);
        EntityModel entityModel = EntityModel.of(tripResp);
        entityModel.add(linkTo(methodOn(TripRestController.class).getTrip(Trip.getId())).withRel("createTrip"));

        return entityModel;

    }





    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/trips/Page_listPlace", produces = "application/json")
    public EntityModel<Trip> Page_listPlace(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of(   "place_level_list", EnumPlaceLevel.List(),
                "place_type_list", EnumPlaceTyp.List(),
                "category_list",EnumPlaceCategories.List());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(TripRestController.class).listPlaces(supplier.getId(),null,null)).withRel("list"));

        entityModel.add(linkTo(methodOn(TripRestController.class).createPlace(supplier.getId(),null)).withRel("create"));


        return entityModel;

    }




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/places", produces = "application/json")
    public PagedModel listPlaces(@PathVariable long SUPPLIER_ID  ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Page<Place> placePage = placeRepository.findAll(pageable);

        return assembler.toModel(placePage.map(e->{
            PlaceResp placeResp = PlaceResp.from(e);

            EntityModel entityModel = EntityModel.of(placeResp);

            entityModel.add(linkTo(methodOn(TripRestController.class).getPlaceEdit(e.getId())).withSelfRel());

            return entityModel;

        }));

    }






    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/places", produces = "application/json")
    public EntityModel<Place> createPlace(@PathVariable long SUPPLIER_ID , @RequestBody @Valid PlaceReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Place place = tripService.createPlace(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }


    @GetMapping(value = "/places/{SUPPLIER_ID}", produces = "application/json")
    public EntityModel<Place> getPlace(@PathVariable long SUPPLIER_ID) {

        Optional<Place> supplierOptional = placeRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Place supplier = supplierOptional.get();


        EntityModel entityModel = EntityModel.of(supplier);
        switch (supplier.getType()){
            case tourist_attraction:
                entityModel.add(linkTo(methodOn(AttractionRestController.class).getAttraction(supplier.getType_reference(), EnumUrlSourceType.normal)).withSelfRel());
                break;

        }



        return entityModel;

    }


    @GetMapping(value = "/places/{SUPPLIER_ID}/edit", produces = "application/json")
    public EntityModel<Place> getPlaceEdit(@PathVariable long SUPPLIER_ID) {

        Optional<Place> supplierOptional = placeRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Place supplier = supplierOptional.get();

        PlaceEditReq placeEditReq = PlaceEditReq.from(supplier);

        Map map = Map.of(   "place_level_list", EnumPlaceLevel.List(),
                "place_type_list", EnumPlaceTyp.List(),
                "category_list",EnumPlaceCategories.List());

        placeEditReq.setParameterList(map);

        EntityModel entityModel = EntityModel.of(placeEditReq);

        entityModel.add(linkTo(methodOn(TripRestController.class).getPlaceEdit(supplier.getId())).withSelfRel());

        entityModel.add(linkTo(methodOn(TripRestController.class).editPlace(supplier.getId(),null)).withRel("edit"));

        return entityModel;

    }
    @PutMapping(value = "/places/{SUPPLIER_ID}", produces = "application/json")
    public EntityModel<Place> editPlace(@PathVariable long SUPPLIER_ID , @RequestBody @Valid PlaceReq movieReq) {

        Optional<Place> supplierOptional = placeRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Place supplier = supplierOptional.get();


        supplier = tripService.editPlace(supplier,movieReq);

        EntityModel entityModel = EntityModel.of(supplier);

        return entityModel;

    }

}