package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.ArtworkResp;
import com.lt.dom.OctResp.ExhibitionResp;
import com.lt.dom.OctResp.MediaResp;
import com.lt.dom.OctResp.MuseumResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;

import com.lt.dom.otcReq.ArtworkReq;
import com.lt.dom.otcReq.ExhibitionReq;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.MuseumReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumPhotos;
import com.lt.dom.otcenum.EnumRedemptionMethod;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.MuseumServiceImpl;
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
//https://www.postman.com/opamcurators/workspace/open-access-museums/example/1501710-e695127d-5c31-4f88-901e-ef4e2a376086
@RestController
@RequestMapping("/oct")
public class MuseumRestController {
    @Autowired
    private CollectionItemRepository collectionItemRepository;

    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MuseumRepository movieRepository;
    @Autowired
    private MuseumServiceImpl museumService;


    @Autowired
    private MuseumRepository museumRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/museums", produces = "application/json")
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

        Page<Museum> bookingRuleList = museumRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            MuseumResp movieResp = MuseumResp.from(e);

            LocationResp locationResp = new LocationResp();
            locationResp.setAddress("山西省榆阳区阜石路");
            movieResp.setAddress(locationResp);
            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(MuseumRestController.class).getMuseum(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(MuseumRestController.class).editMuseum(e.getId(),null)).withRel("edit"));


            return entityModel;
        }));

    }


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/museums", produces = "application/json")
    public EntityModel<Museum> createMuseum(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid MuseumReq tripReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Museum Museum = museumService.create(supplier,tripReq);


        EntityModel entityModel = EntityModel.of(Museum);

        return entityModel;

    }


    @PostMapping(value = "/museums/{Museum_ID}", produces = "application/json")
    public EntityModel<Museum> editMuseum(@PathVariable long Museum_ID ,@RequestBody @Valid MuseumReq MuseumReq) {

        Optional<Museum> supplierOptional = museumRepository.findById(Museum_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Museum supplier = supplierOptional.get();


        Museum Museum = museumService.update(supplier,MuseumReq);


        EntityModel entityModel = EntityModel.of(Museum);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/museums/Page_createMuseum", produces = "application/json")
    public EntityModel<Museum> Page_createMuseum(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("redemption_method_list", EnumRedemptionMethod.values());


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(MuseumRestController.class).createMuseum(supplier.getId(),null)).withRel("createMuseum"));


        return entityModel;

    }



    @GetMapping(value = "/museums/{Museum_ID}", produces = "application/json")
    public EntityModel<MuseumResp> getMuseum(@PathVariable long Museum_ID) {

        Optional<Museum> validatorOptional = museumRepository.findById(Museum_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }


        Museum Museum = validatorOptional.get();



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


            entityModel.add(linkTo(methodOn(MuseumRestController.class).getExhibit(e.getId())).withSelfRel());




            return entityModel;
        }).collect(Collectors.toList()));

        museumResp.setHeaderImage(fileStorageService.loadDocument(Arrays.asList(EnumPhotos.full),EnumDocumentType.museum_thumbnail,Museum.getCode()));
        EntityModel entityModel = EntityModel.of(museumResp);
        entityModel.add(linkTo(methodOn(MuseumRestController.class).getMuseum(Museum.getId())).withRel("createMuseum"));

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




        Exhibition place = museumService.createExhibit(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }*/

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/exhibit", produces = "application/json")
    public EntityModel<ExhibitionReq> createExhibit(@PathVariable long SUPPLIER_ID , @RequestBody @Valid ExhibitionReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Exhibition place = museumService.createExhibit(supplier,movieReq);


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


    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/artwork", produces = "application/json")
    public EntityModel<Artwork> createArtwork(@PathVariable long SUPPLIER_ID , @RequestBody @Valid ArtworkReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Artwork place = museumService.createArtwork(supplier,movieReq);


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




        Artwork place = museumService.createCollectionItem(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }
*/

}