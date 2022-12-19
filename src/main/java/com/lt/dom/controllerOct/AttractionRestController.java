package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.HeroCardInfoResp;
import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.ImageVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import java.util.stream.IntStream;

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
    private SearchReminderServiceImpl searchReminderService;

    @Autowired
    private TripPlanRepository tripPlanRepository;

    @Autowired
    private TripServiceImpl tripService;


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
    private CityWalkRepository cityWalkRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private WayPointRepository wayPointRepository;


    @Autowired
    private AssetServiceImpl assetService;
    @Operation(summary = "2、创建Product对象")
    @GetMapping(value = "/attractions/{ATTRACTION_ID}/Page_edit_detail", produces = "application/json")
    public ResponseEntity<EntityModel> getEditAttraction(@PathVariable long ATTRACTION_ID) {

        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Attraction attraction = validatorOptional.get();

        List<Product> attractionPage = productRepository.findAllByTypeAndTypeTo(EnumProductType.Attraction,attraction.getId());


        List<PricingRate> pricingRateList =  pricingTypeRepository.findByProductIdIn(attractionPage.stream().map(e->e.getId()).collect(Collectors.toList()));
        Map<Long,List<PricingRate>> longListMap = pricingRateList.stream().collect(Collectors.groupingBy(e->e.getProductId()));

        Map<Long, PricingRate> longPricingTypeMap =  pricingRateList.stream().collect(Collectors.toMap(e->e.getId(), e->e));

        AttractionEditResp attractionResp = AttractionEditResp.from(attraction);


        AttractionEditResp.InfoTab infoTab = AttractionEditResp.InfoTab.from(attraction);
        infoTab.setParameterList(
                Map.of(
                        "privacy_level_list",EnumPrivacyLevel.List(),
                        "tag_list",EnumTags.List(),
                        "feature_tag_list",EnumFeatureTag.List()
        ));

        EntityModel entityModel_infoTab = EntityModel.of(infoTab);
        entityModel_infoTab.add(linkTo(methodOn(AttractionRestController.class).editAttractionInfo(attraction.getId(),null)).withRel("edit"));

        infoTab.setImages(fileStorageService.loadDocumentsWithCodeEdit( EnumDocumentType.attraction_photos,attraction.getCode()));
        infoTab.setVideo(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.attraction_video,attraction.getCode()));
        infoTab.setThumb(fileStorageService.loadDocumentWithCodeEdit( EnumDocumentType.attraction_thumb,attraction.getCode()));
        attractionResp.setInfoTab(entityModel_infoTab);



        List<Attribute> attributeList = attributeRepository.findAllByObjectCode(attraction.getCode());
        AttractionEditResp.AboutTap aboutTap = AttractionEditResp.AboutTap.from(attributeList);

        aboutTap.setParameterList(
                Map.of(
                        "feature_tag_list",EnumFeatureTag.List()

                ));
        EntityModel entityModel_aboutTap = EntityModel.of(aboutTap);
        entityModel_aboutTap.add(linkTo(methodOn(AttractionRestController.class).editAttractionAboutTab(attraction.getId(),null)).withRel("edit"));
        attractionResp.setAboutTab(entityModel_aboutTap);




/*

        attractionResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.attraction_photos,attraction.getCode()));
        attractionResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(Arrays.asList(EnumPhotos.thumb),EnumDocumentType.attraction_thumb,attraction.getCode()));
        attractionResp.setVideo(fileStorageService.loadDocumentWithDefault(EnumDocumentType.attraction_video,attraction.getCode()));

*/




        HeroCardInfoResp heroCardInfoResp = new HeroCardInfoResp();
        heroCardInfoResp.setPrice("普通预定20元");
        heroCardInfoResp.setOption("主人卡包含该景点");
        heroCardInfoResp.setPurchase("购买主任卡");
        EntityModel entityModel_hero = EntityModel.of(heroCardInfoResp);
        entityModel_hero.add(linkTo(methodOn(IndexController.class).heroPass(null)).withRel("cityPass"));

        attractionResp.setHeroCardInfo(entityModel_hero);




        AttractionEditResp.ProductTap productTap = AttractionEditResp.ProductTap.from(attraction);

        productTap.setParameterList(
                Map.of(
                        "product_list",Product.List(attractionPage)
                ));
        EntityModel entityModel_productTap = EntityModel.of(productTap);
        entityModel_productTap.add(linkTo(methodOn(AttractionRestController.class).editAttractionProduct(attraction.getId(),null)).withRel("edit"));
        attractionResp.setProductTab(entityModel_productTap);



        EntityModel entityModel = EntityModel.of(attractionResp);






/*
        String link = linkTo(methodOn(AttractionRestController.class).getAttraction(attraction.getId())).withRel("create").getHref();

        String url_with_link = String.format(EnumReferralType.fill_up_passager_info.getUrl(),link);

*/





        List<CityWalk> cityWalks = cityWalkRepository.findAll();
        AttractionEditResp.SelfGuideTap selfGuideTap = AttractionEditResp.SelfGuideTap.from();
        selfGuideTap.setSelfGuided(attraction.getSelfGuided());
        selfGuideTap.setParameterList(
                Map.of(
                        "self_guide_list",CityWalk.List(cityWalks)
                ));
        EntityModel entityModel_selfGuideTap = EntityModel.of(selfGuideTap);
        entityModel_selfGuideTap.add(linkTo(methodOn(AttractionRestController.class).editAttractionSelfGuide(attraction.getId(),null)).withRel("edit"));
        attractionResp.setSelfGuideTap(entityModel_selfGuideTap);





        Asset asset = assetService.getWithNew(attraction.getCode(),attraction.getId(),EnumAssetType.tour_qr);
        Asset asset_booking = assetService.getWithNew(attraction.getCode(),attraction.getId(),EnumAssetType.booking_qr);

   //     attractionResp.setAssetList(AssetResp.toList(Arrays.asList(asset,asset_booking)));

        AttractionEditResp.AssetTap assetTap = AttractionEditResp.AssetTap.from();
        assetTap.setAssetMap(AssetResp.toMap(Arrays.asList(asset,asset_booking)));
        assetTap.setParameterList(
                Map.of(

                ));
        EntityModel entityModel_assetTap = EntityModel.of(assetTap);
        attractionResp.setAssetTap(entityModel_assetTap);

        return ResponseEntity.ok(entityModel);


    }

    @PutMapping(value = "/attractions/{ATTRACTION_ID}/aboutTab", produces = "application/json")
    public ResponseEntity<AttractionResp> editAttractionAboutTab(@PathVariable long ATTRACTION_ID,@RequestBody @Valid AttractionEditResp.AboutTap pojo) {

        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Attraction attraction = validatorOptional.get();


        Attraction product_pair=  attractionService.editAboutTab(attraction,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(AttractionResp.from(attraction));


    }

    @PutMapping(value = "/attractions/{ATTRACTION_ID}/productTab", produces = "application/json")
    public ResponseEntity<AttractionResp> editAttractionProduct(@PathVariable long ATTRACTION_ID,@RequestBody @Valid AttractionEditResp.ProductTap pojo) {

        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Attraction attraction = validatorOptional.get();



        Attraction product_pair=  attractionService.editAttractionProduct(attraction,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(AttractionResp.from(attraction));


    }
    @PutMapping(value = "/attractions/{ATTRACTION_ID}/selfGuideTab", produces = "application/json")
    public ResponseEntity<AttractionResp> editAttractionSelfGuide(@PathVariable long ATTRACTION_ID,@RequestBody @Valid AttractionEditResp.SelfGuideTap pojo) {

        System.out.println("===logger:"+pojo.getSelfGuided());
        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Attraction attraction = validatorOptional.get();



        Attraction product_pair=  attractionService.editAttractionSelfGuide(attraction,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(AttractionResp.from(attraction));


    }

    @PutMapping(value = "/attractions/{ATTRACTION_ID}/infoTab", produces = "application/json")
    public ResponseEntity<AttractionResp> editAttractionInfo(@PathVariable long ATTRACTION_ID,@RequestBody @Valid AttractionEditResp.InfoTab pojo) {

        System.out.println("========传递的参数是"+pojo.toString());
        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Attraction attraction = validatorOptional.get();



        Attraction product_pair=  attractionService.editAttractionInfo(attraction,pojo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product_pair.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(AttractionResp.from(attraction));


    }



    @Operation(summary = "2、创建Product对象")
    @GetMapping(value = "/attractions/{ATTRACTION_ID}", produces = "application/json")
    public ResponseEntity<EntityModel> getAttraction(@PathVariable long ATTRACTION_ID,@RequestParam(required = false) EnumUrlSourceType sourceType) {



        System.out.println("参数参数   "+sourceType);
        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 景区"+ATTRACTION_ID);
        }
        Attraction attraction = validatorOptional.get();

        if(sourceType != null && sourceType.equals(EnumUrlSourceType.search)){
            searchReminderService.createSeatingLayout(attraction.getName(),attraction.getId());
        }

        List<Product> attractionPage = null;
        if(attraction.getProducts_json() != null){
            attractionPage = productRepository.findAllById(Arrays.stream(new Gson().fromJson(attraction.getProducts_json(),Long[].class)).toList());

        }else{
            attractionPage = productRepository.findAllById(Arrays.asList());

        }


      List<PricingRate> pricingRateList =  pricingTypeRepository.findByProductIdIn(attractionPage.stream().map(e->e.getId()).collect(Collectors.toList()));
      Map<Long,List<PricingRate>> longListMap = pricingRateList.stream().collect(Collectors.groupingBy(e->e.getProductId()));

        Map<Long, PricingRate> longPricingTypeMap =  pricingRateList.stream().collect(Collectors.toMap(e->e.getId(), e->e));

        AttractionResp attractionResp = AttractionResp.from(attraction);


        HeroCardInfoResp heroCardInfoResp = new HeroCardInfoResp();
        heroCardInfoResp.setPrice("普通预定20元");
        heroCardInfoResp.setOption("主人卡包含该景点");
        heroCardInfoResp.setPurchase("购买主任卡");
        EntityModel entityModel_hero = EntityModel.of(heroCardInfoResp);
        entityModel_hero.add(linkTo(methodOn(IndexController.class).heroPass(null)).withRel("cityPass"));

        attractionResp.setHeroCardInfo(entityModel_hero);




        List<Attribute> attributeList = attributeRepository.findAllByObjectCode(attraction.getCode());
        attractionResp.withAbout(attributeList);
/*

        AttributeResp attributeResp =new AttributeResp();
        attributeResp.setName("购票须知");
        attributeResp.setDescription(" you will need to wait in line to receive a time slot. We recommend arriving between 9AM and 3PM for the shortest wait times. Use the Rockefeller Plaza entrance on 50th Street (between 5th and 6th Avenues) and go down the staircase to enter the Go City pass line.");

        AttributeResp attributeResp2 =new AttributeResp();
        attributeResp2.setName("购票须知");
        attributeResp2.setDescription(" you will need to wait in line to receive a time slot. We recommend arriving between 9AM and 3PM for the shortest wait times. Use the Rockefeller Plaza entrance on 50th Street (between 5th and 6th Avenues) and go down the staircase to enter the Go City pass line.");
        AttributeResp attributeResp5 =new AttributeResp();
        attributeResp5.setName("团购票");
        attributeResp5.setDescription("if you are traveling with a tour group, please send a tour group leader to exchange all Go City Passes for timed admission tickets between 10 AM and 11 AM on the day you plan to visit the observatory. Tour groups that arrive after 11 AM without timed admission tickets may be turned away.");

      //  attractionResp.setKnowBeforeYouGo(Arrays.asList(attributeResp,attributeResp2,attributeResp5));
*/
/*





        attributeResp =new AttributeResp();
        attributeResp.setBefore("https://newyorkpass.com/themes/custom/go_theme/css/groot_newyork/brand/primary/address.svg");

        attributeResp.setName("地址");
        attributeResp.setDescription("山西省榆林市镇北台孵化中心");
        AttributeResp attributeResp3 =new AttributeResp();
        attributeResp3.setName("公交车");
        attributeResp3.setBefore("https://newyorkpass.com/themes/custom/go_theme/css/groot_newyork/brand/primary/bus.svg");

        attributeResp3.setDescription("乘坐18路攻击，到达终点站，向北100米");
        attractionResp.setGettingThere(Arrays.asList(attributeResp,attributeResp3));


        attributeResp =new AttributeResp();
        attributeResp.setName("每天");
        attributeResp.setDescription("早上9点 – 下午5点，最后入园下午4点");



        attractionResp.setHoursOfOperation(Arrays.asList(attributeResp));*/


        attractionResp.setProducts(attractionPage.stream().map(e->{



            ProductResp productResp =  ProductResp.basefrom(e);


                PricingRate pricingRate = longPricingTypeMap.get(e.getDefault_price());
            if(pricingRate != null){
                PricingTypeResp pricingTypeResp_default = PricingTypeResp.from(pricingRate);

                productResp.setDefault_price(pricingTypeResp_default);
            }




           productResp.setPriceTypes(longListMap.getOrDefault(e.getId(),Arrays.asList()).stream().map(ee->{
                PricingTypeResp pricingTypeResp = PricingTypeResp.from(ee);


                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

               // entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(ee.getId())).withRel("createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));


            EntityModel entityModel = EntityModel.of(productResp);

            entityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId(),null)).withRel("Page_createBooking").expand());

            entityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));

            entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            return entityModel;

        }).collect(Collectors.toList()));



        attractionResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.attraction_photos,attraction.getCode()));

       // attractionResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(Arrays.asList(EnumPhotos.thumb),EnumDocumentType.attraction_thumb,attraction.getCode()));
        attractionResp.setVideo(fileStorageService.loadDocumentWithDefault(EnumDocumentType.attraction_video,attraction.getCode()));




        EntityModel entityModel = EntityModel.of(attractionResp);
      entityModel.add(linkTo(methodOn(AttractionRestController.class).getImages(attraction.getId(),null,null)).withRel("getImageList"));
        entityModel.add(linkTo(methodOn(AttractionRestController.class).Page_getImageList(attraction.getId())).withRel("Page_getImageList"));


        LocationResp locationResp = LocationResp.from(attraction.getLocation());

        attractionResp.setLocation(locationResp);



        String link = linkTo(methodOn(AttractionRestController.class).getAttraction(attraction.getId(), EnumUrlSourceType.normal)).withRel("create").getHref();

        String url_with_link = String.format(EnumReferralType.fill_up_passager_info.getUrl(),link);

        if(attraction.getSelfGuided()!= null){


            Optional<CityWalk> cityWalks = cityWalkRepository.findById(attraction.getSelfGuided());
            CityWalk cityWalk = cityWalks.get();

 /*           CityWalkResp cityWalkResp = CityWalkResp.fromSnip(cityWalk);


*/

            CityWalkResp cityWalkResp = CityWalkResp.simplefrom(cityWalk);
            List<WayPoint> wayPoints = wayPointRepository.findAll();



            cityWalkResp.setWay_points(IntStream.range(0,wayPoints.size()).mapToObj(i->{
                WayPoint e = wayPoints.get(i);
                WayPointResp wayPointResp = WayPointResp.locationFrom(e);
                EntityModel entityModel_ = EntityModel.of(wayPointResp);
                return entityModel_;
            }).collect(Collectors.toList()));

            ViewPortResp viewPortResp = new ViewPortResp();
            viewPortResp.setBtmRightPoint(ViewPortResp.PointDTO.from());
            viewPortResp.setTopLeftPoint(ViewPortResp.PointDTO.from());
            cityWalkResp.setViewPort(viewPortResp);


            EntityModel entityModel_city = EntityModel.of(cityWalkResp);
            entityModel_city.add(linkTo(methodOn(AudioGuideRestController.class).getCityWalk(cityWalk.getId(), null)).withSelfRel());

            String link_city = linkTo(methodOn(AudioGuideRestController.class).getCityWalk(cityWalk.getId(), null)).withSelfRel().getHref();

            cityWalkResp.setName(cityWalk.getTitle());
            cityWalkResp.setUrl(link_city);
            cityWalkResp.setPath(EnumMiniappPagePath.audio_guide.getPath()+"?url="+link_city);//,"tour",cityWalkResp);


            EntityModel entityModel1 = EntityModel.of(cityWalkResp);

            attractionResp.setSelfGuidedTour(entityModel1);

            attractionResp.setSelfGuided(true);
        }else{
            attractionResp.setSelfGuided(false);
        }


/*

        Asset asset = assetService.getWithNew(attraction.getCode(),attraction.getId(),EnumAssetType.tour_qr);

        Asset asset_booking = assetService.getWithNew(attraction.getCode(),attraction.getId(),EnumAssetType.booking_qr);

       attractionResp.setAssetList(AssetResp.toList(Arrays.asList(asset,asset_booking)));

        attractionResp.setAssetMap(AssetResp.toMap(Arrays.asList(asset,asset_booking)));

*/


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
    public PagedModel getAttractionList(@PathVariable long SUPPLIER_ID,
                                        @PageableDefault(sort = {"createdDate",
                                                "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,


                                        PagedResourcesAssembler<EntityModel<Attraction>> assembler) {

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

            attractionResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.attraction_thumb,e.getCode()));

            EntityModel entityModel = EntityModel.of(attractionResp);
         //   entityModel.add(linkTo(methodOn(AttractionRestController.class).Page_updateAttraction(e.getId())).withRel("Page_update"));
            entityModel.add(linkTo(methodOn(AttractionRestController.class).getEditAttraction(e.getId())).withSelfRel());


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

        List<PricingRate> pricingRates = pricingTypeRepository.findByProductIdIn(attractionPage.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<PricingRate>> longListMap = pricingRates.stream().collect(Collectors.groupingBy(e->e.getProductId()));
        return assembler.toModel(attractionPage.map(e->{






            ProductResp productResp =  ProductResp.from(e);
            productResp.setPriceTypes(longListMap.getOrDefault(e.getId(),Arrays.asList()).stream().map(ee->{
                PricingTypeResp pricingTypeResp = PricingTypeResp.from(ee);


                EntityModel entityModel1 = EntityModel.of(pricingTypeResp);

             //   entityModel1.add(linkTo(methodOn(BookingRestController.class).Page_createBookingFromPriceType(ee.getId())).withRel("createBooking"));

                return entityModel1;
            }).collect(Collectors.toList()));


            EntityModel entityModel = EntityModel.of(productResp);

            entityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId(), null)).withRel("Page_createBooking").expand());

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
/*

        if(tempDocuments.size() > 0){

            TempDocument tempDocument = tempDocuments.get(0);
            attraction.setThumbnail_image(fileStorageService.saveFromTempDocument(attraction.getCode(),EnumDocumentType.attraction_thumb,tempDocument));
        }

            attraction.setVideo(fileStorageService.saveFromTempDocumentCode(attraction.getCode(),EnumDocumentType.attraction_video,pojo.getVideo()).getCode());
       //     attractionRepository.save(attraction);


        List<Document> documents = fileStorageService.saveFromTempDocumentCode(
                attraction.getCode(),tempDocuments.stream().map(e->{
                    return Pair.with(EnumDocumentType.attraction_photos,e);
                }).collect(Collectors.toList()));


*/



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





















/*    @GetMapping(value = "/attractions/{ATTRACTION_ID}/Page_updateAttraction",produces = "application/json")
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


    }*/

/*
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
           //     attraction.setThumbnail_image(optionalTempDocument.get().getCode());
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

    }*/







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






    @PostMapping(value = "/attractioons/{ATTRACTION_ID}/trip_plan", produces = "application/json")
    public EntityModel<Attraction> createTrip(@PathVariable Long ATTRACTION_ID,@RequestBody @Valid AttractionTripReq attractionTripReq) {

        Optional<Attraction> validatorOptional = attractionRepository.findById(ATTRACTION_ID);
        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找活动对象");
        }
        Attraction attraction = validatorOptional.get();




        Optional<TripPlan> tripPlanOptional = tripPlanRepository.findById(attractionTripReq.getId());
        TripPlan tripPlan = tripPlanOptional.get();



        tripPlan = tripService.addTripPlan(tripPlanOptional.get(),attraction);

        EntityModel entityModel = EntityModel.of(tripPlan);

        return entityModel;

    }


}