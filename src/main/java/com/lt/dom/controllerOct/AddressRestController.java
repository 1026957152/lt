package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ShippingCardAddressResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumRedemptionMethod;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AddressServiceImpl;
import com.lt.dom.serviceOtc.ExtraServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
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
public class AddressRestController {


    @Autowired
    private AddressServiceImpl addressService;

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
    private UserRepository userRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/address/Page_listAddress", produces = "application/json")
    public EntityModel<Address> Page_listAddress(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        List<ShippingCardAddress> theatreList = shippingAddressRepository.findAllBySupplier(supplier.getId());

        Map map = Map.of("shipping_address_list", theatreList);


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(AddressRestController.class).listAddress(supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(AddressRestController.class).createaAddress(supplier.getId(),null)).withRel("create"));


        return entityModel;

    }


    @GetMapping(value = "/users/{USER_ID}/addresses/Page_listUserAddress", produces = "application/json")
    public EntityModel<Address> Page_listUserAddress(@PathVariable long USER_ID ) {


        Optional<User> supplierOptional =userRepository.findById(USER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        User supplier = supplierOptional.get();

        List<ShippingCardAddress> theatreList = shippingAddressRepository.findAllByUser(supplier.getId());

        Map map = Map.of("shipping_address_list", theatreList.stream().map(e->{
            return ShippingAddressResp.from(e);
        }).collect(Collectors.toList()));


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(AddressRestController.class).listAddress(supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(AddressRestController.class).createaUserAddress(supplier.getId(),null)).withRel("create"));

        entityModel.add(linkTo(methodOn(AddressRestController.class).Page_createUserAddress(supplier.getId())).withRel("Page_create"));



        return entityModel;

    }



    @GetMapping(value = "/users/{USER_ID}/addresses", produces = "application/json")
    public PagedModel listAddress(@PathVariable long USER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<User> supplierOptional =userRepository.findById(USER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        User supplier = supplierOptional.get();

        Page<ShippingCardAddress> bookingRuleList = shippingAddressRepository.findAllByUser(supplier.getId(),pageable);



        return assembler.toModel(bookingRuleList.map(e->{

            ShippingAddressResp movieResp = ShippingAddressResp.from(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(AddressRestController.class).get(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(AddressRestController.class).editAddress(e.getId(),null)).withRel("update"));
            entityModel.add(linkTo(methodOn(AddressRestController.class).setDefault(e.getId())).withRel("setDefault"));
            entityModel.add(linkTo(methodOn(AddressRestController.class).delete(e.getId())).withRel("delete"));



            return entityModel;
        }));

    }

    @PostMapping(value = "/users/{USER_ID}/addresses", produces = "application/json")
    public EntityModel<Movie> createaUserAddress(@PathVariable long USER_ID ,@RequestBody @Valid ShippingCardAddressReq_ movieReq) {

        Optional<User> supplierOptional =userRepository.findById(USER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        User supplier = supplierOptional.get();


        ShippingCardAddress Movie = addressService.createUserAddress(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }





        @PostMapping(value = "/suppliers/{SUPPLIER_ID}/address", produces = "application/json")
    public EntityModel<Movie> createaAddress(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid ShippingCardAddressReq_ movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        ShippingCardAddress Movie = addressService.create(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(Movie);

        return entityModel;

    }

    @PutMapping(value = "/addresses/{Movie_ID}/default", produces = "application/json")
    public EntityModel<ShippingCardAddress> setDefault(@PathVariable long Movie_ID ) {

        Optional<ShippingCardAddress> supplierOptional = shippingAddressRepository.findById(Movie_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        ShippingCardAddress supplier = supplierOptional.get();


        ShippingCardAddress shippingCardAddress = addressService.setDefault(supplier);


        EntityModel entityModel = EntityModel.of(shippingCardAddress);

        return entityModel;

    }


    @PutMapping(value = "/addresses/{Movie_ID}", produces = "application/json")
    public EntityModel<Movie> editAddress(@PathVariable long Movie_ID , @RequestBody @Valid ShippingCardAddressReq_ MovieReq) {

        Optional<ShippingCardAddress> supplierOptional = shippingAddressRepository.findById(Movie_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        ShippingCardAddress supplier = supplierOptional.get();


        ShippingCardAddress shippingCardAddress = addressService.update(supplier,MovieReq);


        EntityModel entityModel = EntityModel.of(shippingCardAddress);

        return entityModel;

    }


    @GetMapping(value = "/addresses/{Movie_ID}", produces = "application/json")
    public EntityModel<ShippingCardAddressResp> get(@PathVariable long Movie_ID ) {

        Optional<ShippingCardAddress> supplierOptional = shippingAddressRepository.findById(Movie_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        ShippingCardAddress supplier = supplierOptional.get();




        ShippingAddressResp shippingCardAddressResp = ShippingAddressResp.from(supplier);

        EntityModel entityModel = EntityModel.of(shippingCardAddressResp);

        return entityModel;

    }
    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/addresses/{Movie_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long Movie_ID) {
        Optional<ShippingCardAddress> supplierOptional = shippingAddressRepository.findById(Movie_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        ShippingCardAddress supplier = supplierOptional.get();


        shippingAddressRepository.delete(supplier);

        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/addresses/Page_createAddress", produces = "application/json")
    public EntityModel<Movie> Page_createAddress(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(AddressRestController.class).createaAddress(supplier.getId(),null)).withRel("createMovie"));


        return entityModel;

    }

    @GetMapping(value = "/users/{USER_ID}/addresses/Page_createUserAddress", produces = "application/json")
    public EntityModel<Movie> Page_createUserAddress(@PathVariable long USER_ID ) {



        Optional<User> supplierOptional =userRepository.findById(USER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        User supplier = supplierOptional.get();


        Map map = Map.of();


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(AddressRestController.class).createaUserAddress(supplier.getId(),null)).withRel("create"));


        return entityModel;

    }




}