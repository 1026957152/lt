package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ExtraResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ShippingRateReq;
import com.lt.dom.otcenum.EnumRedemptionMethod;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.ShippingRateServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class ShippingRateRestController {




    @Autowired
    private ShippingRateRepository shippingRateRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ExtraRepository extraRepository;
    @Autowired
    private ShippingRateServiceImpl shippingRateservice;



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/shippingRates/Page_listShippingRate", produces = "application/json")
    public EntityModel Page_listShippingRate(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

        List<ShippingRate> theatreList = shippingRateRepository.findAllBySupplier(supplier.getId());

        Map map = Map.of("shipping_rate_list", ShippingRate.List(theatreList));


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(ShippingRateRestController.class).listShippingRates(supplier.getId(),null,null)).withRel("list"));
        entityModel.add(linkTo(methodOn(ShippingRateRestController.class).createExtra(supplier.getId(),null)).withRel("create"));


        return entityModel;

    }


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/shippingRates", produces = "application/json")
    public PagedModel listShippingRates(@PathVariable long SUPPLIER_ID , Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


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

            ExtraResp ShippingRateResp = ExtraResp.from(e);

            EntityModel entityModel = EntityModel.of(ShippingRateResp);
            entityModel.add(linkTo(methodOn(ShippingRateRestController.class).getExtra(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(ShippingRateRestController.class).editShippingRate(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/shippingRates", produces = "application/json")
    public EntityModel<ShippingRate> createExtra(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid ShippingRateReq ShippingRateReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        ShippingRate ShippingRate = shippingRateservice.create(supplier,ShippingRateReq);


        EntityModel entityModel = EntityModel.of(ShippingRate);

        return entityModel;

    }



    @PutMapping(value = "/shippingRates/{ShippingRate_ID}", produces = "application/json")
    public EntityModel<ShippingRate> editShippingRate(@PathVariable long ShippingRate_ID ,@RequestBody @Valid ShippingRateReq ShippingRateReq) {

        Optional<ShippingRate> supplierOptional = shippingRateRepository.findById(ShippingRate_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        ShippingRate supplier = supplierOptional.get();


        ShippingRate ShippingRate = shippingRateservice.update(supplier,ShippingRateReq);


        EntityModel entityModel = EntityModel.of(ShippingRate);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/shippingRates/Page_createShippingRate", produces = "application/json")
    public EntityModel<ShippingRate> Page_createShippingRate(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(ShippingRateRestController.class).createExtra(supplier.getId(),null)).withRel("createShippingRate"));


        return entityModel;

    }



    @GetMapping(value = "/shippingRates/{ShippingRate_ID}", produces = "application/json")
    public EntityModel<Extra> getExtra(@PathVariable long ShippingRate_ID) {

        Optional<Extra> validatorOptional = extraRepository.findById(ShippingRate_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }
        Extra ShippingRate = validatorOptional.get();
        ExtraResp ShippingRateResp = ExtraResp.from(ShippingRate);

/*

        Optional<Theatre> theatre = theatreRepository.findById(ShippingRate.getSupplier());

        if(theatre.isPresent()){

            TheatreResp theatreResp = TheatreResp.simple(theatre.get());

            LocationResp locationResp = LocationResp.from(theatre.get().getLocation());

            theatreResp.setAddress(locationResp);


            EntityModel entityModel = EntityModel.of(theatreResp);
            entityModel.add(linkTo(methodOn(TheatreRestController.class).getTheatre(theatre.get().getId())).withSelfRel());

            ShippingRateResp.setTheatre(entityModel);
        }



        List<ShippingRateProduct> passProduct = ShippingRateProductRepository.findByShippingRate(ShippingRate.getId());
        ShippingRateProduct ShippingRateProduct = passProduct.get(0);


        List<Sku> skuList = ShippingRateProduct.getZonePricings();

        Map<Long,Zone> zoneMap = zoneRepository.findAllById(skuList.stream().map(e->e.getZone()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));

        ;
        ShippingRateResp.setBlocks(skuList.stream().collect(Collectors.groupingBy(e->e.getZone())).entrySet().stream().map(e->{
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



        Map<String,Attribute> attributeList = attributeRepository.findAllByObjectCode(ShippingRate.getCode()).stream().collect(Collectors.toMap(e->e.getKey(),e->e));


        ShippingRateResp.setShow_intro(AttributeResp.fromWithOutFeature(attributeList.get("show_intro")));
        ShippingRateResp.setStory_intro(AttributeResp.fromWithOutFeature(attributeList.get("story_intro")));
        ShippingRateResp.setTeam_intro(AttributeResp.fromWithOutFeature(attributeList.get("team_intro")));

        /// ShippingRateResp.setPerformances();

        ShippingRateResp.setCover(fileStorageService.loadDocumentWithDefault( EnumDocumentType.ShippingRate_cover,ShippingRate.getCode()));
        ShippingRateResp.setVideo(fileStorageService.loadDocumentWithCode( EnumDocumentType.ShippingRate_vidio,ShippingRate.getCode()));
*/

        EntityModel entityModel = EntityModel.of(ShippingRateResp);
        entityModel.add(linkTo(methodOn(ShippingRateRestController.class).getExtra(ShippingRate.getId())).withSelfRel());


        return entityModel;

    }







}