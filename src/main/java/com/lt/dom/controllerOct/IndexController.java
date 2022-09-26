package com.lt.dom.controllerOct;

//https://blog.csdn.net/Dawn____Dawn/article/details/118080415

import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.*;
import com.lt.dom.OctResp.home.HomePassResp;
import com.lt.dom.OctResp.home.HomeResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.IndexPhotosReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.GalleryImageVo;
import com.lt.dom.vo.ImageVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class IndexController {

    @Autowired
    private AttractionRepository attractionRepository;
    @Autowired
    private ScenarioRepository scenarioRepository;

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
    private PassServiceImpl passService;


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


/*
        List<Scenario> scenarios = scenarioRepository.findAll();


        List<Campaign> campaignList = campaignRepository.findAll();
*/


       // List<Product> productList = productRepository.findAll();
        HomeResp homeResp = new HomeResp();
        EntityModel<HomeResp> entityModel = EntityModel.of(homeResp);







        featureService.fill(homeResp);





        List<Attraction> attractionList = attractionRepository.findAll();



        homeResp.setRecommend_attractions(attractionList.stream().map(e->{



            Pair<Boolean,String> stringPair =    policyNoteShowService.getQr(EnumNoteShowWhereAndWhen.home_page);

           HomeResp.noteShow(homeResp,stringPair);

            AttractionResp attractionResp = AttractionResp.from(e,fileStorageService.loadDocument(Arrays.asList(EnumPhotos.thumb),EnumDocumentType.attraction_thumb,e.getCode()));
         //   attractionResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.attraction_photos,e.getCode()));



            attractionResp.setTags(Arrays.asList(EnumKnownfor.Architecture.toString()));

            Optional<Supplier> supplierOptional = supplierRepository.findById(e.getSupplier());
            if(supplierOptional.isPresent()){
                Supplier supplier = supplierOptional.get();
                attractionResp.withSupplier(supplier);

                System.out.println("=========找到了 景区的 供应商 啊啊啊啊啊 ");
            }else{
                System.out.println("=========没有找到  找到  啊啊 找到了 景区的 供应商 啊啊啊啊啊 ");
            }


            EntityModel attractionEntityModel = EntityModel.of(attractionResp);
            attractionEntityModel.add(linkTo(methodOn(AttractionRestController.class).getAttraction(e.getId())).withSelfRel());

            return attractionEntityModel;
        }).collect(Collectors.toList()));



/*

        Map<Long,List<Campaign>> longListMap =  campaignList.stream().collect(Collectors.groupingBy(e->e.getScenario()));



        CollectionModel categoryCollectionModel = CollectionModel.of(scenarios.stream().map(x->{


            EntityModel<ScenarioResp> scenarioEntityModel = EntityModel.of(ScenarioResp.from(x,longListMap.getOrDefault(x.getId(), Arrays.asList()).size()));

            scenarioEntityModel.add(linkTo(methodOn(ScenarioRestController.class).getScenario(x.getId())).withRel("getScenario"));
            return scenarioEntityModel;

        }).collect(Collectors.toList()));

        homeResp.setCampaignCategories(categoryCollectionModel);

*/



        EnumValueListDefault.High_Quality_Product_recommendation.name();

        List<ValueListItem> valueListItems = valueListService.getByName(EnumValueListDefault.High_Quality_Product_recommendation);


        List<Product>  productList  = productRepository.findAllById(valueListItems.stream().map(e->Long.valueOf(e.getValue())).collect(Collectors.toList()));


        Map<Long, PricingType>  pricingType = pricingTypeRepository.findAllById(productList.stream().map(e->e.getDefault_price()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));



        List<EntityModel<ProductResp>> productRespList = productList.stream().map(e->{

            PricingType pricingType_default = pricingType.get(e.getDefault_price());
            ProductResp productResp = ProductResp.from(e,pricingType_default);
            productResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.product_photos,e.getCode()));
            productResp.setThumbnail_image(fileStorageService.loadDocument(EnumDocumentType.product_thumb,e.getCode()));



            EntityModel<ProductResp> productRespEntityModel = EntityModel.of(productResp);
            productRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));
            productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            productRespEntityModel.add(linkTo(methodOn(BookingRestController.class).createBooking(e.getId(),null)).withRel("Page_booking"));

            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setRecommend_products(productRespList);





        homeResp.setCarousel(fileStorageService.loadDocumentsWithCode(EnumDocumentType.index_carousel,EnumDocumentType.index_carousel.name()));

        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("get"));
        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("realnameAuth"));

        entityModel.add(linkTo(methodOn(IndexController.class).cityPass(null)).withRel("Page_cityPass"));


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

















    @GetMapping(value = "/index/city_pass" , produces = "application/json")
    public EntityModel<HomePassResp> cityPass(HttpServletRequest request)  {


        HomePassResp homeResp = new HomePassResp();
        EntityModel<HomePassResp> entityModel = EntityModel.of(homeResp);





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















        List<Product> productList = productRepository.findAllByType(EnumProductType.Pass);
        Map<Long, List<PricingType>>  pricingType = pricingTypeRepository.findByProductIdIn(productList.stream().map(e->e.getId()).collect(Collectors.toList()))
                .stream().collect(Collectors.groupingBy(e->e.getProductId()));



        List<EntityModel<ProductResp>> productRespList = productList.stream().map(e->{

            List<PricingType> pricingType_default = pricingType.get(e.getId());


            ProductResp productResp = ProductResp.Passfrom(e,pricingType_default);


            productResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.product_photos,e.getCode()));
            productResp.setThumbnail_image(fileStorageService.loadDocument(EnumDocumentType.product_thumb,e.getCode()));



            List<Component> componentList = componentRepository.findAllByProduct(e.getId());;

            productResp.setComponent_rights(componentList.stream().map(right->right.getComponentRightId()).collect(Collectors.toList()));

            EntityModel<ProductResp> productRespEntityModel = EntityModel.of(productResp);
           // productRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));
            productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());
            productRespEntityModel.add(linkTo(methodOn(BookingRestController.class).Page_createBooking(e.getId())).withRel("Page_booking"));

            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setRecommend_products(productRespList);




        GalleryImageVo imageVo = new GalleryImageVo();
        imageVo.setDesc("在镇北台的日子，感受边塞的壮美");
        imageVo.setImage(fileStorageService.loadDocument(EnumDocumentType.product_thumb,"e.getCode()"));
        imageVo.setTitle("红石峡结伴一游");


        GalleryImageVo imageVo_h = new GalleryImageVo();
        imageVo_h.setDesc("塞外奇侠");
        imageVo_h.setImage(fileStorageService.loadDocument(EnumDocumentType.product_thumb,"e.getCode()"));
        imageVo_h.setTitle("结伴一游");


        GalleryImageVo imageVo_2 = new GalleryImageVo();
        imageVo_2.setDesc("塞外奇侠");
        imageVo_2.setImage(fileStorageService.loadDocument(EnumDocumentType.product_thumb,"e.getCode()"));
        imageVo_2.setTitle("结伴一游");


        GalleryImageVo imageVo_3 = new GalleryImageVo();
        imageVo_3.setDesc("塞外奇侠");
        imageVo_3.setImage(fileStorageService.loadDocument(EnumDocumentType.product_thumb,"e.getCode()"));
        imageVo_3.setTitle("结伴一游");




        homeResp.setGallery(Arrays.asList(imageVo,imageVo_h,imageVo_3,imageVo_2));



        homeResp.setCarousel(fileStorageService.loadDocumentsWithCode(EnumDocumentType.index_carousel,EnumDocumentType.index_carousel.name()));

/*
        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("get"));
        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("realnameAuth"));
*/



        return entityModel;


    }














}