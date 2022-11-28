package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.RegionServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//https://www.postman.com/opamcurators/workspace/open-access-regions/example/1501710-e695127d-5c31-4f88-901e-ef4e2a376086
@RestController
@RequestMapping("/oct")
public class RegionRestController {
    @Autowired
    private CollectionItemRepository collectionItemRepository;

    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private RegionRepository regionRepository;


    @Autowired
    private RegionServiceImpl regionService;

    @Autowired
    private MuseumRepository museumRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private AttractionRepository attractionRepository;


    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/regions", produces = "application/json")
    public PagedModel getMuseumList(@PathVariable long SUPPLIER_ID ,Pageable pageable, PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


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

        Page<Region> bookingRuleList = regionRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            RegionResp movieResp = RegionResp.simpleFrom(e);

  /*          LocationResp locationResp = new LocationResp();
            locationResp.setAddress("山西省榆阳区阜石路");
            movieResp.setAddress(locationResp);*/
            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(RegionRestController.class).getMuseum(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(RegionRestController.class).editMuseum(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/regions", produces = "application/json")
    public EntityModel<Region> createMuseum(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid RegionReq tripReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Region region = regionService.create(supplier,tripReq);


        EntityModel entityModel = EntityModel.of(region);

        return entityModel;

    }


    @PutMapping(value = "/regions/{Museum_ID}", produces = "application/json")
    public EntityModel<Museum> editMuseum(@PathVariable long Museum_ID ,@RequestBody @Valid RegionReq regionReq) {

        Optional<Region> supplierOptional = regionRepository.findById(Museum_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Region supplier = supplierOptional.get();


        Region region = regionService.update(supplier,regionReq);


        EntityModel entityModel = EntityModel.of(region);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/regions/Page_createMuseum", produces = "application/json")
    public EntityModel<Museum> Page_createMuseum(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(RegionRestController.class).createMuseum(supplier.getId(),null)).withRel("createMuseum"));


        return entityModel;

    }



    @GetMapping(value = "/regions/{Museum_ID}", produces = "application/json")
    public EntityModel getMuseum(@PathVariable long Museum_ID) {

        Optional<Region> validatorOptional = regionRepository.findById(Museum_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }


        Region region = validatorOptional.get();
        RegionResp museumResp = RegionResp.simpleFrom(region);

/*


        List<Exhibition> exhibitionList = exhibitionRepository.findAll();


        MuseumResp museumResp = MuseumResp.from(Museum);

        LocationResp locationResp = new LocationResp();
        locationResp.setAddress("山西省榆阳区阜石路");
        museumResp.setAddress(locationResp);
        museumResp.setExhibitions(exhibitionList.stream().map(e->{


            ExhibitionResp exhibitionResp = ExhibitionResp.from(e);

            EntityModel entityModel = EntityModel.of(exhibitionResp);

            MediaResp mediaResp = new MediaResp();
            mediaResp.setPortrait(fileStorageService.loadDocumentWithDefault(EnumDocumentType.museum_thumbnail,e.getCode()));

            exhibitionResp.setMedia(mediaResp);


            entityModel.add(linkTo(methodOn(RegionRestController.class).getExhibit(e.getId())).withSelfRel());




            return entityModel;
        }).collect(Collectors.toList()));
*/

        museumResp.setPhoto(fileStorageService.loadDocumentWithDefault(EnumDocumentType.region_photo,region.getCode()));

        List<Attraction>  attractionList  = attractionRepository.findAll();


        //  List<Attraction> attractionList = attractionRepository.findAll();
        museumResp.setRecommend_attractions(attractionList.stream().map(e->{




            AttractionResp attractionResp = AttractionResp.simpleFrom(e,fileStorageService.loadDocumentWithDefault(EnumDocumentType.attraction_thumb,e.getCode()));

            attractionResp.setTags(Arrays.asList(EnumKnownfor.Architecture.toString()));




            EntityModel attractionEntityModel = EntityModel.of(attractionResp);
            attractionEntityModel.add(linkTo(methodOn(AttractionRestController.class).getAttraction(e.getId(), EnumUrlSourceType.normal)).withSelfRel());

            return attractionEntityModel;
        }).collect(Collectors.toList()));



        EntityModel entityModel = EntityModel.of(museumResp);
        entityModel.add(linkTo(methodOn(RegionRestController.class).getMuseum(region.getId())).withSelfRel());

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




        Exhibition place = regionservice.createExhibit(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }*/
/*

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/exhibit", produces = "application/json")
    public EntityModel<ExhibitionReq> createExhibit(@PathVariable long SUPPLIER_ID , @RequestBody @Valid ExhibitionReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Exhibition place = null;//regionService.createExhibit(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }

    @GetMapping(value = "/exhibit/{EXHIBITON_ID}", produces = "application/json")
    public EntityModel<ExhibitionReq> getExhibit(@PathVariable long EXHIBITON_ID) {

        Optional<Exhibition> supplierOptional = exhibitionRepository.findById(EXHIBITON_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Exhibition exhibition = supplierOptional.get();

        List<Artwork> artworks = collectionItemRepository.findAll();

        ExhibitionResp exhibitionReq = ExhibitionResp.from(exhibition);
        exhibitionReq.setArtworkss(artworks.stream().map(e->{



            ArtworkResp artworkResp = ArtworkResp.from(e);
            MediaResp mediaResp = new MediaResp();
            mediaResp.setPortrait(fileStorageService.loadDocumentWithCode(EnumDocumentType.artwork_thumbnail,e.getCode()));

            mediaResp.setAudio(fileStorageService.loadDocumentWithCodeToUrl(EnumDocumentType.artwork_audio,e.getCode()));
            artworkResp.setMedia(mediaResp);


            EntityModel entityModel = EntityModel.of(artworkResp);
          //  entityModel.add(linkTo(methodOn(MuseumRestController.class).getExhibit(e.getId())).withSelfRel());

            return entityModel;
        }).collect(Collectors.toList()));



        EntityModel entityModel = EntityModel.of(exhibitionReq);

        return entityModel;

    }

*/







/*

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/exhibit", produces = "application/json")
    public EntityModel<ExhibitionReq> createPlace(@PathVariable long SUPPLIER_ID , @RequestBody @Valid CollectionItemReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Artwork place = regionservice.createCollectionItem(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }
*/

}