package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttractionReq;
import com.lt.dom.otcReq.ImageReq;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.AttractionServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.vo.ImageVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class AttractionRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private AttractionServiceImpl attractionService;
    @Autowired
    private PricingTypeRepository pricingTypeRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private TempDocumentRepository tempDocumentRepository;
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private AssetServiceImpl assetService;


    @Operation(summary = "2、创建Product对象")
    @GetMapping(value = "/attractions/{ATTRACTION_ID}", produces = "application/json")
    public ResponseEntity<EntityModel> getAttraction(@PathVariable long ATTRACTION_ID) {

        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Attraction attraction = validatorOptional.get();

        List<Product> attractionPage = productRepository.findAllByTypeAndTypeTo(EnumProductType.Attraction,attraction.getId());


      List<PricingType> pricingTypeList =  pricingTypeRepository.findByProductIdIn(attractionPage.stream().map(e->e.getId()).collect(Collectors.toList()));
      Map<Long,List<PricingType>> longListMap = pricingTypeList.stream().collect(Collectors.groupingBy(e->e.getProductId()));

        Map<Long,PricingType> longPricingTypeMap =  pricingTypeList.stream().collect(Collectors.toMap(e->e.getId(),e->e));

        AttractionResp attractionResp = AttractionResp.from(attraction);

        attractionResp.setProducts(attractionPage.stream().map(e->{



            ProductResp productResp =  ProductResp.from(e);

            PricingType pricingType = longPricingTypeMap.get(e.getDefault_price());

            PricingTypeResp pricingTypeResp_default = PricingTypeResp.from(pricingType);

            productResp.setDefault_price(pricingTypeResp_default);

           productResp.setPriceTypes(longListMap.getOrDefault(e.getId(),Arrays.asList()).stream().map(ee->{
                PricingTypeResp pricingTypeResp = PricingTypeResp.from(ee);


                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

                entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(ee.getId())).withRel("createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));


            EntityModel entityModel = EntityModel.of(productResp);

            entityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId())).withRel("Page_createBooking"));

            entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));

            entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            return entityModel;

        }).collect(Collectors.toList()));



        attractionResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.attraction_photos,attraction.getCode()));

        attractionResp.setThumbnail_image(fileStorageService.loadDocument(Arrays.asList(EnumPhotos.thumb),EnumDocumentType.attraction_thumb,attraction.getCode()));
        attractionResp.setVideo(fileStorageService.loadDocument(EnumDocumentType.attraction_video,attraction.getCode()));




        EntityModel entityModel = EntityModel.of(attractionResp);
      entityModel.add(linkTo(methodOn(AttractionRestController.class).getImages(attraction.getId(),null,null)).withRel("getImageList"));
        entityModel.add(linkTo(methodOn(AttractionRestController.class).Page_getImageList(attraction.getId())).withRel("Page_getImageList"));


        LocationResp locationResp = new LocationResp();
        locationResp.setAddress("榆林北极");
        locationResp.setLatitude(0);
        locationResp.setLongitude(0);
        attractionResp.setLocation(locationResp);



        String link = linkTo(methodOn(AttractionRestController.class).getAttraction(attraction.getId())).withRel("create").getHref();

        String url_with_link = String.format(EnumReferralType.fill_up_passager_info.getUrl(),link);



        return ResponseEntity.ok(entityModel);


    }















    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/attractions/page_getAttractionList", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_getAttractionList(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 供应商");
        }

        Supplier supplier = validatorOptional.get();



        Map map = Map.of(

                "status_list", EnumProductStatus.from(),
                "status_list", assetService.getQrEntityModel(supplier.getCode())



        );




        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(AttractionRestController.class).createAttraction(supplier.getId(),null)).withRel("create"));
        entityModel.add(linkTo(methodOn(AttractionRestController.class).getAttractionList(supplier.getId(),null,null)).withRel("getList"));

   //   entityModel.add(linkTo(methodOn(AssetRestController.class).getAsset(supplier.getId(),null,null)).withRel("getList"));


        return entityModel;
    }





    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/attractions", produces = "application/json")
    public PagedModel getAttractionList(@PathVariable long SUPPLIER_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<Attraction>> assembler) {

        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 供应商");
        }



        Page<Attraction> attractionPage = attractionRepository.findAll(pageable);

        Map<String,List<PhotoResp>> photoRespList = fileStorageService.loadDocuments(
                EnumDocumentType.attraction_thumb,
                attractionPage.stream().map(e->e.getCode()).collect(Collectors.toList()));

        return assembler.toModel(attractionPage.map(e->{

            AttractionResp attractionResp = AttractionResp.from(e);
            List<PhotoResp> list = photoRespList.getOrDefault(e.getCode(),Arrays.asList());

            if(list.size()> 0 ){
                attractionResp.setThumbnail_image(list.get(0));
            }

            attractionResp.setThumbnail_image(fileStorageService.loadDocument(EnumDocumentType.attraction_thumb,e.getCode()));

            EntityModel entityModel = EntityModel.of(attractionResp);
            entityModel.add(linkTo(methodOn(AttractionRestController.class).Page_updateAttraction(e.getId())).withRel("Page_update"));
            entityModel.add(linkTo(methodOn(AttractionRestController.class).getAttraction(e.getId())).withSelfRel());


            return entityModel;

        }));



    }






    @GetMapping(value = "/attractions/{ATTRACTION_ID}/products", produces = "application/json")
    public PagedModel getProduct(@PathVariable long ATTRACTION_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<ProductResp>> assembler) {

        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找活动对象");
        }
        Attraction attraction = validatorOptional.get();

        Page<Product> attractionPage = productRepository.findAllByTypeAndTypeTo(EnumProductType.Attraction,attraction.getId(),pageable);

        List<PricingType> pricingTypes = pricingTypeRepository.findByProductIdIn(attractionPage.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<PricingType>> longListMap = pricingTypes.stream().collect(Collectors.groupingBy(e->e.getProductId()));
        return assembler.toModel(attractionPage.map(e->{






            ProductResp productResp =  ProductResp.from(e);
            productResp.setPriceTypes(longListMap.getOrDefault(e.getId(),Arrays.asList()).stream().map(ee->{
                PricingTypeResp pricingTypeResp = PricingTypeResp.from(ee);


                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

                entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(ee.getId())).withRel("createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));


            EntityModel entityModel = EntityModel.of(productResp);

            entityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId())).withRel("Page_createBooking"));

            entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));

            entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            return entityModel;

        }));



    }







    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/suppler/{SUPPLIER_ID}/attractions", produces = "application/json")
    public ResponseEntity<Attraction> createAttraction(@PathVariable long SUPPLIER_ID, @RequestBody @Valid AttractionReq pojo) {

        System.out.println("---------------"+ pojo.toString());
        Optional<Supplier> validatorOptional = supplierRepository.findById(SUPPLIER_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 商户"+SUPPLIER_ID);
        }
        Supplier supplier = validatorOptional.get();





        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(pojo.getImages().stream().map(e->e.getImage()).distinct().collect(Collectors.toList()));


        //Optional<ImageReq> optionalImageReq_default = pojo.getImages().stream().filter(e->e.isDefault_()).findAny();





        Attraction attraction=  attractionService.create(supplier,pojo);

        if(tempDocuments.size() > 0){

            TempDocument tempDocument = tempDocuments.get(0);
            attraction.setThumbnail_image(fileStorageService.saveFromTempDocument(attraction.getCode(),EnumDocumentType.attraction_thumb,tempDocument));
        }

            attraction.setVideo(fileStorageService.saveFromTempDocumentCode(attraction.getCode(),EnumDocumentType.attraction_video,pojo.getVideo()));
       //     attractionRepository.save(attraction);


        List<Document> documents = fileStorageService.saveFromTempDocumentCode(
                attraction.getCode(),tempDocuments.stream().map(e->{
                    return Pair.with(EnumDocumentType.attraction_photos,e);
                }).collect(Collectors.toList()));





        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(attraction.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(attraction);

    }











    @PostMapping(value = "tourist_attractions/{TOURIST_ATTRACTION_ID}/attractions", produces = "application/json")
    public Attraction addAttraction(@PathVariable long TOURIST_ATTRACTION_ID) {

        Optional<Attraction> attractionOptional = attractionRepository.findById(TOURIST_ATTRACTION_ID);
        if(attractionOptional.isPresent()){
            try {
                return null;//touristAttractionService.addAttraction(attractionOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }



    @GetMapping(value = "tourist_attractions/{TOURIST_ATTRACTION_ID}/attractions", produces = "application/json")
    public CollectionModel<Attraction> listAttraction(@PathVariable long TOURIST_ATTRACTION_ID, Pageable pageable) {

        List<Attraction> attractionOptional = null;//attractionRepository.findByTouristAttractionId(TOURIST_ATTRACTION_ID);

/*        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/

        Link link = linkTo(TouristAttractionRestController.class).withSelfRel();
        CollectionModel<Attraction> result = CollectionModel.of(attractionOptional, link);
        return result;

    }





















    @GetMapping(value = "/attractions/{ATTRACTION_ID}/Page_updateAttraction",produces = "application/json")
    public ResponseEntity<EntityModel> Page_updateAttraction(@PathVariable long ATTRACTION_ID) {

        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找活动对象");
        }
        Attraction attraction = validatorOptional.get();

        AttractionResp attractionResp = AttractionResp.from(attraction);



        attractionResp.setImages(fileStorageService.loadDocumentsWithCode(EnumDocumentType.attraction_photos,attraction.getCode()));



        attractionResp.setThumbnail_image(fileStorageService.loadDocumentWithCode(EnumDocumentType.attraction_thumb,attraction.getCode()));
        attractionResp.setVideo(fileStorageService.loadDocumentWithCode(EnumDocumentType.attraction_video,attraction.getCode()));



        EntityModel entityModel = EntityModel.of(attractionResp);

        entityModel.add(linkTo(methodOn(AttractionRestController.class).updateAttractions(attraction.getId(),null)).withRel("update"));

        return ResponseEntity.ok(entityModel);


    }


    @Operation(summary = "2、创建Product对象")
    @PutMapping(value = "/attractions/{ATTRACTION_ID}", produces = "application/json")
    public ResponseEntity<Attraction> updateAttractions(@PathVariable long ATTRACTION_ID, @RequestBody @Valid AttractionReq pojo) {


        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找活动对象");
        }
        Attraction attraction = validatorOptional.get();




        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(pojo.getImages().stream().map(e->e.getImage()).distinct().collect(Collectors.toList()));


        Optional<ImageReq> optionalImageReq_default = pojo.getImages().stream().filter(e->e.isDefault_()).findAny();

        if(optionalImageReq_default.isPresent()){
            Optional<TempDocument> optionalTempDocument = tempDocuments.stream().filter(e->e.getCode().equals(optionalImageReq_default.get().getImage())).findAny();
            if(optionalTempDocument.isPresent()){
                attraction.setThumbnail_image(optionalTempDocument.get().getCode());
                attractionRepository.save(attraction);

            }
        }



            List<Document> documents = fileStorageService.saveFromTempDocumentCode(
                    attraction.getCode(),tempDocuments.stream().map(e->{
                return Pair.with(EnumDocumentType.attraction_photos,e);
            }).collect(Collectors.toList()));



        //Attraction attraction=  attractionService.create(supplier,pojo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(attraction.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(attraction);

    }







    @GetMapping(value = "/attractions/{ATTRACTION_ID}/images/page", produces = "application/json")
    public EntityModel<Map<String,Object>> Page_getImageList(@PathVariable long ATTRACTION_ID) {


        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找活动对象");
        }
        Attraction attraction = validatorOptional.get();



        Map map = Map.of(

                "status_list", EnumProductStatus.from()

        );


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(AttractionRestController.class).getImages(attraction.getId(),null,null)).withRel("getList"));
        entityModel.add(linkTo(methodOn(AttractionRestController.class).addImage(attraction.getId(),null)).withRel("create"));


        return entityModel;
    }








    @GetMapping(value = "/attractions/{ATTRACTION_ID}/images", produces = "application/json")
    public PagedModel getImages(@PathVariable long ATTRACTION_ID, Pageable pageable, PagedResourcesAssembler<EntityModel<Attraction>> assembler) {

        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找活动对象");
        }
        Attraction attraction = validatorOptional.get();





        Page<Document> attractionPage = documentRepository.findAllByTypeAndReference(EnumDocumentType.attraction_photos,attraction.getCode(),pageable);

        Map<String,List<PhotoResp>> photoRespList = fileStorageService.loadDocuments(
                EnumDocumentType.attraction_thumb,
                attractionPage.stream().map(e->e.getCode()).collect(Collectors.toList()));

        return assembler.toModel(attractionPage.map(e->{


            PhotoResp photoResp = fileStorageService.url_range(Arrays.asList(EnumPhotos.thumb,EnumPhotos.large),e);

            EntityModel entityModel = EntityModel.of(photoResp);


            return entityModel;

        }));



    }







    @PostMapping(value = "/attractions/{ATTRACTION_ID}/images", produces = "application/json")
    public Document addImage(@PathVariable long ATTRACTION_ID,@RequestBody @Valid ImageReq pojo) {


        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找活动对象");
        }
        Attraction attraction = validatorOptional.get();


        Optional<TempDocument> tempDocumentOptional = tempDocumentRepository.findByCode(pojo.getImage());

        if(tempDocumentOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 文档");

        }
        TempDocument tempDocument = tempDocumentOptional.get();

        ImageVo imageVo = pojo.toImageVo();
        Document documents = fileStorageService.saveFromTempDocumentWithRich(attraction.getCode(),imageVo,EnumDocumentType.attraction_photos,tempDocument);


        return documents;
    }




}