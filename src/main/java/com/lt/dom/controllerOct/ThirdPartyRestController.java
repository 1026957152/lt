package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.OctResp.ThirdPartyProductResp;
import com.lt.dom.OctResp.ThirdPartyResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BusStopReq;
import com.lt.dom.otcReq.RegionReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.ThirdPartyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//https://www.postman.com/opamcurators/workspace/open-access-third/example/1501710-e695127d-5c31-4f88-901e-ef4e2a376086
@RestController
@RequestMapping("/oct")
public class ThirdPartyRestController {
    @Autowired
    private ThirdPartyProductRepository thirdPartyProductRepository;

    @Autowired
    private ThirdPartyProductRepository exhibitionRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ThirdPartyRepository regionRepository;


    @Autowired
    private ThirdPartyServiceImpl regionService;


    @Autowired
    private FileStorageServiceImpl fileStorageService;



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/third_parties/listThirdParty", produces = "application/json")
    public EntityModel<Media> Page_listThirdParty(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Map map = Map.of("status_list", EnumVoucherStatus.values());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).listThirdParty(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/third_parties", produces = "application/json")
    public PagedModel listThirdParty(@PathVariable long SUPPLIER_ID ,

                                     @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,

                                     PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Page<ThirdParty> bookingRuleList = regionRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            ThirdPartyResp movieResp = ThirdPartyResp.from(e);

  /*          LocationResp locationResp = new LocationResp();
            locationResp.setAddress("山西省榆阳区阜石路");
            movieResp.setAddress(locationResp);*/
            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).getThirdParty(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).editThirdParty(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/third_parties", produces = "application/json")
    public EntityModel<Region> createMuseum(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid RegionReq tripReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        ThirdParty region = regionService.create(supplier,tripReq);


        EntityModel entityModel = EntityModel.of(region);

        return entityModel;

    }


    @PutMapping(value = "/third_parties/{Museum_ID}", produces = "application/json")
    public EntityModel<Museum> editThirdParty(@PathVariable long Museum_ID , @RequestBody @Valid RegionReq regionReq) {

        Optional<ThirdParty> supplierOptional = regionRepository.findById(Museum_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        ThirdParty supplier = supplierOptional.get();


        ThirdParty region = regionService.update(supplier,regionReq);


        EntityModel entityModel = EntityModel.of(region);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/third_parties/Page_createMuseum", produces = "application/json")
    public EntityModel<Museum> Page_createMuseum(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).createMuseum(supplier.getId(),null)).withRel("createMuseum"));


        return entityModel;

    }



    @GetMapping(value = "/third_parties/{Museum_ID}", produces = "application/json")
    public EntityModel getThirdParty(@PathVariable long Museum_ID) {

        Optional<ThirdParty> validatorOptional = regionRepository.findById(Museum_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }


        ThirdParty region = validatorOptional.get();
        ThirdPartyResp thirdPartyResp = ThirdPartyResp.simpleFrom(region);


        thirdPartyResp.setProducts(region.getProducts().stream().map(e->{

            ProductResp productResp = ProductResp.basefrom(e.getProduct());
            EntityModel entityModel = EntityModel.of(productResp);
            entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).delete(e.getId().getThirdPartyId(),e.getId().getProductId())).withRel("delete"));


            return entityModel;

        }).collect(Collectors.toList()));
        thirdPartyResp.setPhoto(fileStorageService.loadDocumentWithDefault(EnumDocumentType.region_photo,region.getCode()));




        EntityModel entityModel = EntityModel.of(thirdPartyResp);
        entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).getThirdParty(region.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).createProduct(region.getId(),null)).withRel("addProduct"));


        return entityModel;

    }









/*

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/exhibit", produces = "application/json")
    public EntityModel<ExhibitionReq> createPlace(@PathVariable long SUPPLIER_ID , @RequestBody @Valid ExhibitionReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Exhibition place = thirdervice.createExhibit(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }*/


    @PostMapping(value = "/third_parties/{SUPPLIER_ID}/products", produces = "application/json")
    public EntityModel<ThirdPartyProduct> createProduct(@PathVariable long SUPPLIER_ID , @RequestBody @Valid List<BusStopReq> movieReq) {

        Optional<ThirdParty> supplierOptional = regionRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        ThirdParty supplier = supplierOptional.get();


        ThirdParty place = regionService.createThirdPartyProduct(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }









/*

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/exhibit", produces = "application/json")
    public EntityModel<ExhibitionReq> createPlace(@PathVariable long SUPPLIER_ID , @RequestBody @Valid CollectionItemReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Artwork place = thirdervice.createCollectionItem(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }
*/




    @Operation(summary = "4、删除Product对象")
    @DeleteMapping(value = "/third_parties/{SUPPLIER_ID}/products/{Movie_ID}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable long SUPPLIER_ID , @PathVariable long Movie_ID) {


        ThirdPartyProductKey thirdPartyProductKey = new ThirdPartyProductKey(SUPPLIER_ID,Movie_ID);

        Optional<ThirdPartyProduct> supplierOptional = thirdPartyProductRepository.findById(thirdPartyProductKey);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        ThirdPartyProduct supplier = supplierOptional.get();

        thirdPartyProductRepository.delete(supplier);

        return ResponseEntity.ok().build();
    }
    @Operation(summary = "4、删除Product对象")
    @GetMapping(value = "/third_parties/{SUPPLIER_ID}/products/{Movie_ID}", produces = "application/json")
    public EntityModel get(@PathVariable long SUPPLIER_ID , @PathVariable long Movie_ID) {


        ThirdPartyProductKey thirdPartyProductKey = new ThirdPartyProductKey(SUPPLIER_ID,Movie_ID);

        Optional<ThirdPartyProduct> supplierOptional = thirdPartyProductRepository.findById(thirdPartyProductKey);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }
        ThirdPartyProduct supplier = supplierOptional.get();



        EntityModel entityModel = EntityModel.of(supplier);
        return entityModel;
    }




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/third_parties/Page_listThirdPartyProduct", produces = "application/json")
    public EntityModel<Media> Page_listThirdPartyProduct(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        Map map = Map.of("status_list", EnumVoucherStatus.values());


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).listThirdPartyProduct(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/third_party_products", produces = "application/json")
    public PagedModel listThirdPartyProduct(@PathVariable long SUPPLIER_ID ,
                                            @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,

                                            PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Page<ThirdPartyProduct> bookingRuleList = thirdPartyProductRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            ThirdPartyProductResp movieResp = ThirdPartyProductResp.simpleFrom(e);

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).get(e.getId().getThirdPartyId(),e.getId().getProductId())).withSelfRel());
            entityModel.add(linkTo(methodOn(ThirdPartyRestController.class).delete(e.getId().getThirdPartyId(),e.getId().getProductId())).withRel("delete"));


            return entityModel;
        }));

    }



}