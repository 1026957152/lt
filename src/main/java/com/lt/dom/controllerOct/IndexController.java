package com.lt.dom.controllerOct;

//https://blog.csdn.net/Dawn____Dawn/article/details/118080415

import cn.hutool.core.util.ObjectUtil;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.*;
import com.lt.dom.OctResp.home.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.HelpMessage;
import com.lt.dom.otcReq.IndexPhotosReq;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;

import com.lt.dom.specification.HomeQueryfieldsCriteria;
import com.lt.dom.specification.SightSpecification;
import com.lt.dom.specification.SightsQueryfieldsCriteria;
import com.lt.dom.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class IndexController {



    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    @Value("${blog_flag}")
    boolean miniapp_release ;








    @Autowired
    private AttractionRepository attractionRepository;
    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private SearchReminderServiceImpl searchReminderService;


    @Autowired
    private UserRepository userRepository;



    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PolicyNoteShowServiceImpl policyNoteShowService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private AttributeServiceImpl attributeService;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private TripPlanRepository tripPlanRepository;




    @Autowired
    private PriceServiceImpl priceService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;


    @Autowired
    private TempDocumentRepository tempDocumentRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private FeatureServiceImpl featureService;
    @Autowired
    private ValueListServiceImpl valueListService;
    @Autowired
    private PricingTypeRepository pricingTypeRepository;
    @Autowired
    private MediaServiceImpl mediaService;
    @Autowired
    private ProductServiceImpl productService;




    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private MuseumRepository museumRepository;
    @Autowired
    private MovieProductRepository movieProductRepository;



    @Autowired
    private PassServiceImpl passService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    JwtUtils jwtUtils;
    //QQ审核获取的qppid和appkey
/*    @Value("${qq.appid}")
    private String appid;
    @Value("${qq.appkey}")
    private String appkey;*/
    /**
     * QQ登录
     * 步骤一：用户跳转至授权页面
     * @author 点点
     * @date 2021年6月17日
     */
    @GetMapping(value = "/index" , produces = "application/json")
    public EntityModel<HomeResp> qqLogin(HttpServletRequest request)  {


        Authentication authentication = authenticationFacade.getAuthentication();




/*
        List<Scenario> scenarios = scenarioRepository.findAll();


        List<Campaign> campaignList = campaignRepository.findAll();
*/


       // List<Product> productList = productRepository.findAll();
        HomeResp homeResp = new HomeResp();
        EntityModel<HomeResp> entityModel = EntityModel.of(homeResp);







        featureService.fill(homeResp);




        //配置推荐的景区
        List<ValueListItem> valueListItems = valueListService.getByName(EnumValueListDefault.home_page_attractions_recommendation);
        System.out.println("测试顺序啊啊 "+ valueListItems.toString());

        System.out.println("测试顺序啊啊  stream 获得id后"+ valueListItems.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()).toString());

        List<Long> sortList = valueListItems.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList());
        Map<Long,Attraction>  attractionList  = attractionRepository
                .findAllByIdInAndPrivacyLevel(sortList,EnumPrivacyLevel.public_).stream().collect(Collectors.toMap(e->e.getId(), e->e));


        if(!miniapp_release) {

            //  List<Attraction> attractionList = attractionRepository.findAll();
            homeResp.setRecommend_attractions(sortList.stream().map(i -> {

                Attraction e = attractionList.get(i);

                System.out.println("stream里的顺序 " + e.getId());
                Pair<Boolean, String> stringPair = policyNoteShowService.getQr(EnumNoteShowWhereAndWhen.home_page);

                HomeResp.noteShow(homeResp, stringPair);

                AttractionResp attractionResp = AttractionResp.simpleFrom(e, fileStorageService.loadDocumentWithDefault(Arrays.asList(EnumPhotos.full), EnumDocumentType.attraction_thumb, e.getCode()));
                //   attractionResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.attraction_photos,e.getCode()));


                attractionResp.setTags(Arrays.asList(EnumKnownfor.Architecture.toString()));

/*
            Optional<Supplier> supplierOptional = supplierRepository.findById(e.getSupplier());
            if(supplierOptional.isPresent()){
                Supplier supplier = supplierOptional.get();
                attractionResp.withSupplier(supplier);

                System.out.println("=========找到了 景区的 供应商 啊啊啊啊啊 ");
            }else{
                System.out.println("=========没有找到  找到  啊啊 找到了 景区的 供应商 啊啊啊啊啊 ");
            }
*/


                EntityModel attractionEntityModel = EntityModel.of(attractionResp);
                attractionEntityModel.add(linkTo(methodOn(AttractionRestController.class).getAttraction(e.getId(), null)).withSelfRel().expand());
                attractionEntityModel.add(linkTo(methodOn(AttractionRestController.class).createTrip(e.getId(),null)).withRel("addToTripPlan").expand());



                return attractionEntityModel;
            }).collect(Collectors.toList()));


        }


/*

        Map<Long,List<Campaign>> longListMap =  campaignList.stream().collect(Collectors.groupingBy(e->e.getScenario()));



        CollectionModel categoryCollectionModel = CollectionModel.of(scenarios.stream().map(x->{


            EntityModel<ScenarioResp> scenarioEntityModel = EntityModel.of(ScenarioResp.from(x,longListMap.getOrDefault(x.getId(), Arrays.asList()).size()));

            scenarioEntityModel.add(linkTo(methodOn(ScenarioRestController.class).getScenario(x.getId())).withRel("getScenario"));
            return scenarioEntityModel;

        }).collect(Collectors.toList()));

        homeResp.setCampaignCategories(categoryCollectionModel);

*/

      //  List<Region> regionList = regionRepository.findAll();

        List<Place> regionList = placeRepository.findAllByType(EnumPlaceTyp.region);

        homeResp.setGreatYulin(regionList.stream().map(e->{

            RegionResp resp = RegionResp.from(e);
            resp.setPhoto(fileStorageService.loadDocumentWithDefault(EnumDocumentType.place_photo,e.getCode()));

            EntityModel entityModel1 = EntityModel.of(resp);
            entityModel1.add(linkTo(methodOn(RegionRestController.class).getRegionPlace(e.getId())).withSelfRel());


            return entityModel1;

        }).collect(Collectors.toList()));


        //配置推荐的产品

        EnumValueListDefault.High_Quality_Product_recommendation.name();

        List<ValueListItem> valueListItems_attracion = valueListService.getByName(EnumValueListDefault.High_Quality_Product_recommendation);
        List<Product>  productList  = productRepository
                .findAllById(valueListItems_attracion.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()))
                .stream()
                .filter(e->e.getPrivacyLevel().equals(EnumPrivacyLevel.public_))
                .filter(e->e.getStatus().equals(EnumProductStatus.active))

                .collect(Collectors.toList());
        List<PricingRate> pricingRateList = pricingTypeRepository.findByProductIdIn(productList.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long, List<PricingRate>>  longListPriceGroupMap = pricingRateList.stream().collect(Collectors.groupingBy(e->e.getProductId()));

     //   Map<Long, PricingType>  longPricingTypeMap = pricingTypeList.stream().collect(Collectors.toMap(e->e.getId(),e->e));
        List<EntityModel<ProductResp>> productRespList = productList.stream().map(e->{

            System.out.println("产品id "+e.getId());
/*            PricingType pricingType_default = null;
            if(e.getDefault_price() == null){
                if(longPricingTypeMap.isEmpty()){
                    pricingType_default = longPricingTypeMap.values().stream().findAny().get();

                }else{

                }
            }else{
                pricingType_default = longPricingTypeMap.get(e.getDefault_price());

            }*/
          //  PricingType pricingType_default = longPricingTypeMap.get(e.getDefault_price());
            ProductResp productResp = ProductResp.miniappHome(e);
       //     productResp.setDefault_sku(PricingTypeResp.sku_simple(pricingType_default));
         //   productResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.product_photos,e.getCode()));
            productResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getCode()));

            productResp.setAvailabilityNote(availabilityService.getAvailabilitySimple(e));


            productResp.setPriceRange(priceService.getPriceRange(longListPriceGroupMap.getOrDefault(e.getId(), Arrays.asList())));


            EntityModel<ProductResp> productRespEntityModel = EntityModel.of(productResp);
            productRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));
            productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            productRespEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId(), null)).withRel("Page_booking").expand());

            return productRespEntityModel;
        }).collect(Collectors.toList());

        if(!miniapp_release){

            homeResp.setRecommend_products(productRespList);

        }

        List<ValueListItem> valueListItems_carousel = valueListService.getByName(EnumValueListDefault.mini_app_carousel);

        List<Media>  mediaList_carousel  = mediaService.fromValueList(valueListItems_carousel.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));

        homeResp.setCarousel(mediaList_carousel.stream().map(e->{
            MediaResp mediaResp = MediaResp.simpleFrom(e);


            String link = linkTo(methodOn(IndexController.class).heroPass(null)).withSelfRel().getHref();

            mediaResp.setPath(featureService.fill(Enumfeatured.city_hero).getValue0().getPath());
            return mediaResp;
        }).collect(Collectors.toList()));





        //配置推荐的景区
        List<ValueListItem> valueListItems_movie = valueListService.getByName(EnumValueListDefault.mini_app_movie_recommendation);
        System.out.println("剧目  "+ valueListItems_movie.toString());

    //    List<Long> sortList_movie = valueListItems_movie.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList());

        List<Product>  productList1  = productService.fromValueList(valueListItems_carousel.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));


     //   List<Product> productList1 = productRepository.findAllById(valueListItems_movie.stream().map(e->e.getValue()));

        if(!miniapp_release){
            productList1 = Arrays.asList();

        }


   List<MovieProduct> movieProductList = movieProductRepository.findAllByProductIn(productList1.stream().map(e->e.getId()).collect(Collectors.toList()));

       List<Movie> movieList = movieRepository.findAllById(movieProductList.stream().map(e->e.getMovie()).collect(Collectors.toList()));

        Map<Long,Movie>  movieListMap  = movieList.stream().collect(Collectors.toMap(e->e.getId(),e->e));

        if(movieList.size()>0){

            homeResp.setMovies(movieProductList.stream().map(e->{

                Movie movie = movieListMap.get(e.getMovie());

                MovieResp movieResp = MovieResp.homeFrontSimple(movie);

                movieResp.setThumb(fileStorageService.loadDocumentWithDefault(EnumDocumentType.movie_cover,movie.getCode()));
                EntityModel entityModel1 = EntityModel.of(movieResp);
                entityModel1.add(linkTo(methodOn(MovieRestController.class).getMovie(e.getMovie())).withSelfRel());

                return entityModel1;
            }).collect(Collectors.toList()));


        }




    //    homeResp.setCarousel(fileStorageService.loadDocumentsWithCode(EnumDocumentType.index_carousel,EnumDocumentType.index_carousel.name()));

        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("get"));
        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("realnameAuth"));

        entityModel.add(linkTo(methodOn(IndexController.class).heroPass(null)).withRel("Page_cityPass"));

        entityModel.add(linkTo(methodOn(SearchRestController.class).Page_searchProduct(EnumUrlSourceType.normal)).withRel("Page_search"));

        entityModel.add(linkTo(methodOn(SearchRestController.class).Page_searchSight()).withRel("Page_sights"));

        entityModel.add(linkTo(methodOn(IndexController.class).Page_suggestions()).withRel("Page_suggestions"));

        entityModel.add(linkTo(methodOn(IndexController.class).reminder(null)).withRel("suggestions"));




        if(!miniapp_release){
            UserVo userVo = authenticationFacade.checkUserVo(authentication);

            if(userVo != null) {

                Optional<Pass> pass = passService.find(userVo.getUser_id());

                if(pass.isEmpty()){
                    passService.withMe(homeResp);
                }
            }else {


            }
        }


        if(!miniapp_release){
            String link = linkTo(methodOn(CarRestController.class).Page_car_nearby()).withSelfRel().getHref();

            Map map = Map.of("text","去哪里",

                    "path",EnumMiniappPagePath.nearby_car_rent.getPath()+"?url="+link
                    );
            EntityModel entityModel_pickup = EntityModel.of(map);
            entityModel_pickup.add(linkTo(methodOn(CarRestController.class).Page_car_nearby()).withRel("Page_car_nearby"));
            homeResp.setPickupDropoff(entityModel_pickup);
        }

        List<Object> objectList = new ArrayList<>();

        List<TripPlan> tripPlans = tripPlanRepository.findAll();
        List<Trip> bookingRuleList = tripRepository.findAll();


        objectList.addAll(tripPlans);
        objectList.addAll(bookingRuleList);



        List<EntityModel> listMap = objectList.stream().map(e->{

            TripResp movieResp = null;

            if(e instanceof TripPlan){

                TripPlan tripPlan= (TripPlan)e;
                movieResp = TripResp.simpleFrom(tripPlan);
                movieResp.setType("TripPlan");
                movieResp.setAttraction_count(tripPlan.getAttractions().size());
                movieResp.setDuration("1天");
                movieResp.setTripCover(fileStorageService.loadDocumentWithDefault(EnumDocumentType.trip_cover,tripPlan.getCode()));

                EntityModel entityModel_trip = EntityModel.of(movieResp);
                entityModel_trip.add(linkTo(methodOn(TripRestController.class).getTrip(tripPlan.getId())).withSelfRel());
                return entityModel_trip;
            }

            if(e instanceof Trip){



                Trip tripPlan= (Trip)e;
                movieResp = TripResp.simpleFrom(tripPlan);
                movieResp.setType("Trip");
                movieResp.setAttraction_count(1);
                movieResp.setDuration("1天");
                movieResp.setTripCover(fileStorageService.loadDocumentWithDefault(EnumDocumentType.trip_cover,tripPlan.getCode()));

                EntityModel entityModel_trip = EntityModel.of(movieResp);
                entityModel_trip.add(linkTo(methodOn(TripRestController.class).getTripPlaner(tripPlan.getId())).withSelfRel());
                return entityModel_trip;
            }


            return null;


        }).collect(Collectors.toList());


        if(!miniapp_release){
            homeResp.setPlanner(listMap);
        }



        entityModel.add(linkTo(methodOn(TripRestController.class).Page_planTrip()).withRel("Page_planTrip").expand());
        entityModel.add(linkTo(methodOn(TripRestController.class).tripPlanerListTripPlaner(null,null)).withRel("listTripPlaner").expand());


        return entityModel;


    }



















    @Operation(summary = "2、创建Product对象")
    @PostMapping(value = "/index/photo", produces = "application/json")
    public ResponseEntity<List<Document>> addImages(@RequestBody @Valid IndexPhotosReq pojo) {



        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(pojo.getImages().stream().map(e->e.getImage()).distinct().collect(Collectors.toList()));

        List<Document> documents = fileStorageService.saveFromTempDocumentCode(
                EnumDocumentType.index_carousel.name(),tempDocuments.stream().map(e->{
                    return Pair.with(EnumDocumentType.index_carousel,e);
                }).collect(Collectors.toList()));

        return ResponseEntity.ok(documents);
    }









    @GetMapping(value = "/index/hero_pass" , produces = "application/json")
    public EntityModel<HomePassResp> heroPass(HttpServletRequest request)  {


        HomePassResp homeResp = new HomePassResp();
        EntityModel<HomePassResp> entityModel = EntityModel.of(homeResp);



        Map maps = Map.of("lable","计划您的行程","text","浏览80多个景点，阅读温馨贴士，量身打造专属行程");
        Map maps2 = Map.of("lable","导航 榆林","text","我们的地图可带您快速轻松地浏览整个城市");
        Map maps3 = Map.of("lable","扫码即可进入","text","扫码主人卡即可免费进入最佳景点");
        homeResp.setFeatures(Arrays.asList(maps2,maps,maps3));


        EnumValueListDefault.High_Quality_Product_recommendation.name();


        List<ValueListItem> valueListItems = valueListService.getByName(EnumValueListDefault.city_pass_right_recommendation);



        List<ComponentRight>  componentRightList  = passService.fromValueList(valueListItems.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));



        List<EntityModel<ComponentRightResp>> entityModelList = componentRightList.stream().map(e->{

            ComponentRightResp pricingType_default = ComponentRightResp.from(e);

            EntityModel<ComponentRightResp> productRespEntityModel = EntityModel.of(pricingType_default);
      //      productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
    //        productRespEntityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(e.getId(),null)).withRel("Page_booking"));

            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setComponent_rights(entityModelList);














        List<ValueListItem> valueListItems_product = valueListService.getByName(EnumValueListDefault.hero_pass_01);



        //List<ComponentRight>  componentRightList  = passService.fromValueList(valueListItems.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));



        List<Product> productList = productRepository.findAllById(valueListItems_product.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));
        Map<Long, List<PricingRate>>  pricingType = pricingTypeRepository.findByProductIdIn(productList.stream().map(e->e.getId()).collect(Collectors.toList()))
                .stream().collect(Collectors.groupingBy(e->e.getProductId()));



        List<EntityModel<ProductResp>> productRespList = productList.stream().map(e->{

            List<PricingRate> pricingRate_default = pricingType.get(e.getId());


            ProductResp productResp = ProductResp.Passfrom(e, pricingRate_default);


            productResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.product_photos,e.getCode()));
            productResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getCode()));



            productResp.setPriceRange(priceService.getPriceRange(pricingRate_default));




            attributeService.fill(productResp,e);



            List<Component> componentList = componentRepository.findAllByProduct(e.getId());;

            productResp.setComponent_rights(componentList.stream().map(right->right.getComponentRightId()).collect(Collectors.toList()));

            EntityModel<ProductResp> productRespEntityModel = EntityModel.of(productResp);
           // productRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));
            productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            productRespEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId(), null)).withRel("Page_booking").expand());

            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setRecommend_products(productRespList);





        List<ValueListItem> valueListItems_insp = valueListService.getByName(EnumValueListDefault.hero_pass_inspired);

        List<Media>  mediaList  = mediaService.fromValueList(valueListItems_insp.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));



        homeResp.setGallery(mediaList.stream().map(e->{

            return MediaResp.from(e);
/*            GalleryImageVo imageVo = new GalleryImageVo();
            imageVo.setDesc(e.getDescription_text());
            imageVo.setImage(e.getHref());
            imageVo.setTitle(e.getTitle());
            return imageVo;*/
        }).collect(Collectors.toList()));




        List<ValueListItem> valueListItems_reviews = valueListService.getByName(EnumValueListDefault.hero_pass_travelers_reviews);

        List<Comment>  commentList  = commentService.fromValueList(valueListItems_reviews.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));



        homeResp.setReviews(commentList);















        entityModel.add(linkTo(methodOn(TripRestController.class).Page_planner()).withRel("Page_planner"));


        return entityModel;


    }





    @GetMapping(value = "/index/city_pass" , produces = "application/json")
    public EntityModel<HomePassResp> cityPass(HttpServletRequest request)  {


        HomePassResp homeResp = new HomePassResp();
        EntityModel<HomePassResp> entityModel = EntityModel.of(homeResp);








        List<ValueListItem> valueListItems = valueListService.getByName(EnumValueListDefault.city_pass_right_recommendation);



        List<ComponentRight>  componentRightList  = passService.fromValueList(valueListItems.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));



        List<EntityModel<ComponentRightResp>> entityModelList = componentRightList.stream().map(e->{

            ComponentRightResp pricingType_default = ComponentRightResp.from(e);

            EntityModel<ComponentRightResp> productRespEntityModel = EntityModel.of(pricingType_default);
            //      productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            //        productRespEntityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(e.getId(),null)).withRel("Page_booking"));

            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setComponent_rights(entityModelList);














        List<ValueListItem> valueListItems_product = valueListService.getByName(EnumValueListDefault.city_pass);




        List<Product> productList = productRepository
                .findAllById(valueListItems_product.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()))
                .stream().filter(e->e.getPrivacyLevel().equals(EnumPrivacyLevel.public_)).filter(e->e.getStatus().equals(EnumProductStatus.active)).toList()
                .stream().toList();
        Map<Long, List<PricingRate>>  pricingType = pricingTypeRepository.findByProductIdIn(productList.stream().map(e->e.getId()).collect(Collectors.toList()))
                .stream().collect(Collectors.groupingBy(e->e.getProductId()));



        List<EntityModel<ProductResp>> productRespList = productList.stream().map(e->{

            List<PricingRate> pricingRate_default = pricingType.get(e.getId());


            ProductResp productResp = ProductResp.Passfrom(e, pricingRate_default);


            productResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.product_photos,e.getCode()));
            productResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getCode()));



            List<Component> componentList = componentRepository.findAllByProduct(e.getId());;

            productResp.setComponent_rights(componentList.stream().map(right->right.getComponentRightId()).collect(Collectors.toList()));

            EntityModel<ProductResp> productRespEntityModel = EntityModel.of(productResp);
            // productRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));
            productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            productRespEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId(), null)).withRel("Page_booking").expand());

            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setRecommend_products(productRespList);


        List<Attraction> attractionList = attractionRepository.findAll();


        homeResp.setHighligths(attractionList.stream().map(e->{
            AttractionResp attractionResp = AttractionResp.cityPassfrom20221026(e);
            EntityModel entityModel_list = EntityModel.of(attractionResp);
            entityModel_list.add(linkTo(methodOn(AttractionRestController.class).getAttraction(e.getId(), null)).withSelfRel().expand());
            attractionResp.setThumb(fileStorageService.loadDocumentWithDefault(EnumDocumentType.attraction_thumb,e.getCode()));
            return entityModel_list;
        }).collect(Collectors.toList()));




/*
        List<ValueListItem> valueListItems_insp = valueListService.getByName(EnumValueListDefault.hero_pass_inspired);

        List<Media>  mediaList  = mediaService.fromValueList(valueListItems_insp.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));



        homeResp.setGallery(mediaList.stream().map(e->{

            return MediaResp.from(e);

        }).collect(Collectors.toList()));




        List<ValueListItem> valueListItems_reviews = valueListService.getByName(EnumValueListDefault.hero_pass_travelers_reviews);

        List<Comment>  commentList  = commentService.fromValueList(valueListItems_reviews.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));



        homeResp.setReviews(commentList);*/


        return entityModel;


    }




    @GetMapping(value = "/index/museum" , produces = "application/json")
    public EntityModel<HomeMuseumResp> museum(HttpServletRequest request)  {


        HomeMuseumResp homeResp = new HomeMuseumResp();
        EntityModel<HomeMuseumResp> entityModel = EntityModel.of(homeResp);


        List<Museum> productList = museumRepository.findAll();


        List<EntityModel<MuseumResp>> productRespList = productList.stream().map(e->{



            MuseumResp museumResp = MuseumResp.from(e);

            museumResp.setAddress(LocationResp.from(e.getLocation()));
         // productResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.museum_thumbnail,e.getCode()));
           museumResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.museum_thumbnail,e.getCode()));




            EntityModel<MuseumResp> productRespEntityModel = EntityModel.of(museumResp);
            // productRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));
            productRespEntityModel.add(linkTo(methodOn(MuseumRestController.class).getMuseum(e.getId())).withSelfRel());
        //    productRespEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId())).withRel("Page_booking"));

            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setMuseums(productRespList);





/*        List<ValueListItem> valueListItems_insp = valueListService.getByName(EnumValueListDefault.city_pass_inspired);

        List<Media>  mediaList  = mediaService.fromValueList(valueListItems_insp.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));



        homeResp.setGallery(mediaList.stream().map(e->{

            return MediaResp.from(e);

        }).collect(Collectors.toList()));*/




        return entityModel;


    }









    @GetMapping(value = "/index/activities" , produces = "application/json")
    public EntityModel<HomeHomeActivityResp> activities(HttpServletRequest request)  {


        HomeHomeActivityResp homeResp = new HomeHomeActivityResp();
        EntityModel<HomeHomeActivityResp> entityModel = EntityModel.of(homeResp);

        List<Product> productList = productRepository.findAllByType(EnumProductType.Event);


        List<EntityModel> productRespList = productList.stream().map(e->{




            ActivityResp museumResp = ProductResp.toActivity(e);

            LocationResp locationResp = new LocationResp();
            locationResp.setAddress("山西省榆阳区阜石路");
            museumResp.setActivityDate(LocalDateTime.now());
            museumResp.setDate(LocalDate.now());
            museumResp.setStartingTime(LocalTime.now());
            museumResp.setAddress(locationResp);
/*
            LocationResp locationResp = new LocationResp();
            locationResp.setAddress("山西省榆阳区阜石路");
            museumResp.setAddress(locationResp);
            // productResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.museum_thumbnail,e.getCode()));
            museumResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.museum_thumbnail,e.getCode()).getUrl_thumbnail());

*/


            museumResp.setThumb(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getCode()));

            EntityModel<ActivityResp> productRespEntityModel = EntityModel.of(museumResp);
            // productRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));
            productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            //    productRespEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId())).withRel("Page_booking"));

            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setActivities(productRespList);





/*        List<ValueListItem> valueListItems_insp = valueListService.getByName(EnumValueListDefault.city_pass_inspired);

        List<Media>  mediaList  = mediaService.fromValueList(valueListItems_insp.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));



        homeResp.setGallery(mediaList.stream().map(e->{

            return MediaResp.from(e);

        }).collect(Collectors.toList()));*/




        return entityModel;


    }




    @GetMapping(value = "/index/tours" , produces = "application/json")
    public EntityModel<HomeHomeActivityResp> tours(HttpServletRequest request)  {


        HomeHomeActivityResp homeResp = new HomeHomeActivityResp();
        EntityModel<HomeHomeActivityResp> entityModel = EntityModel.of(homeResp);

        List<Product> productList = productRepository.findAllByType(EnumProductType.Event);


        List<EntityModel> productRespList = productList.stream().map(e->{




            ActivityResp museumResp = ProductResp.toActivity(e);

            LocationResp locationResp = new LocationResp();
            locationResp.setAddress("山西省榆阳区阜石路");
            museumResp.setActivityDate(LocalDateTime.now());
            museumResp.setDate(LocalDate.now());
            museumResp.setStartingTime(LocalTime.now());
            museumResp.setAddress(locationResp);



            museumResp.setThumb(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getCode()));

            EntityModel<ActivityResp> productRespEntityModel = EntityModel.of(museumResp);
            productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setActivities(productRespList);





        return entityModel;


    }

    @GetMapping(value = "/products/Page_suggestions", produces = "application/json")
    public EntityModel Page_suggestions() {



        Map map = Map.of();


        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(IndexController.class).reminder(null)).withRel("search"));



        return entityModel;

    }



    @GetMapping(value = "/index/reminder" , produces = "application/json")
    public EntityModel<HomeSearcherReminderResp> reminder(HomeQueryfieldsCriteria searchQuery)  {


        HomeSearcherReminderResp homeResp = new HomeSearcherReminderResp();


        if(ObjectUtil.isEmpty(searchQuery.getTitle())){

            searchReminderService.get(homeResp,searchQuery.getTitle());

        }

        SightsQueryfieldsCriteria sightsQueryfieldsCriteria = new SightsQueryfieldsCriteria();
        sightsQueryfieldsCriteria.setTitle(searchQuery.getTitle());
        searchQuery.setPrivacyLevel(EnumPrivacyLevel.public_);
        SightSpecification spec =
                new SightSpecification(sightsQueryfieldsCriteria);
        List<Attraction> productList = attractionRepository.findAll(where(spec));





        homeResp.setSuggestions(productList.stream().map(e->{


            HomeSearchItemResp homeSearchItemResp = new HomeSearchItemResp();

            homeSearchItemResp.setLable(e.getName());
            homeSearchItemResp.setType(EnumReminderSearchItemType.place);
            homeSearchItemResp.setPhoto(fileStorageService.loadDocumentWithDefault(EnumDocumentType.attraction_thumb,e.getCode()));

            EntityModel attractionEntityModel = EntityModel.of(homeSearchItemResp);
            attractionEntityModel.add(linkTo(methodOn(AttractionRestController.class).getAttraction(e.getId(), EnumUrlSourceType.search)).withSelfRel());

            return attractionEntityModel;
        }).collect(Collectors.toList()));



        EntityModel<HomeSearcherReminderResp> entityModel = EntityModel.of(homeResp);


        entityModel.add(linkTo(methodOn(SearchRestController.class).Page_searchProduct(EnumUrlSourceType.search)).withRel("Page_searchProduct"));

        return entityModel;


    }




    @GetMapping(value = "/users/{USER_ID}/Page_support" , produces = "application/json")
    public EntityModel<HomeSearcherReminderResp> Page_support(@PathVariable long USER_ID, HomeQueryfieldsCriteria searchQuery)  {

        Optional<User> validatorOptional = userRepository.findById(USER_ID);

        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到用户");
        }
        User user = validatorOptional.get();

        Supplier supplier = supplierRepository.findById(3l).get();

        Address address = supplier.getLocation();


        Map helper = Map.of("call", "13468801683","address",LocationResp.from(address));


        EntityModel entityModel = EntityModel.of(helper);
        entityModel.add(linkTo(methodOn(IndexController.class).postMessage(null)).withRel("sendMessage"));



        return entityModel;


    }


    @PostMapping(value = "/help/messages", produces = "application/json")
    public EntityModel<BusVehicle> postMessage(@RequestBody @Valid HelpMessage movieReq) {

/*
        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();

*/

        Message  busVehicle = messageService.createSeatingLayout(movieReq);


        EntityModel entityModel = EntityModel.of(busVehicle);

        return entityModel;

    }




    @GetMapping(value = "/Page_hero", produces = "application/json")
    public EntityModel<Media> Page_hero( ) {

        Authentication authentication =  authenticationFacade.getAuthentication();
        UserVo userVo = authenticationFacade.getUserVo(authentication);


        HomeHeroPassResp homeHeroPassResp = new HomeHeroPassResp();
        passService.withMe(homeHeroPassResp,userVo);

        EntityModel entityModel = EntityModel.of(homeHeroPassResp);

        entityModel.add(linkTo(methodOn(BlogRestController.class).listBlog(null,null)).withRel("list"));


        return entityModel;

    }

}