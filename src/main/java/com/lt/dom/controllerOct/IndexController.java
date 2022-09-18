package com.lt.dom.controllerOct;

//https://blog.csdn.net/Dawn____Dawn/article/details/118080415

import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.*;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.IndexPhotosReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumKnownfor;
import com.lt.dom.otcenum.EnumPhotos;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
    private CampaignRepository campaignRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TempDocumentRepository tempDocumentRepository;

    @Autowired
    private SupplierRepository supplierRepository;


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


        List<Scenario> scenarios = scenarioRepository.findAll();


        List<Campaign> campaignList = campaignRepository.findAll();


        List<Product> productList = productRepository.findAll();
        HomeResp homeResp = new HomeResp();
        EntityModel<HomeResp> entityModel = EntityModel.of(homeResp);



        List<EntityModel<ProductResp>> productRespList = productList.stream().map(e->{
            ProductResp productResp = ProductResp.from(e);
            EntityModel<ProductResp> productRespEntityModel = EntityModel.of(productResp);
            productRespEntityModel.add(linkTo(methodOn(SupplierRestController.class).getSupplier(e.getSupplierId())).withRel("getSupplier"));


            productRespEntityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());

            return productRespEntityModel;
        }).collect(Collectors.toList());

        homeResp.setProducts(productRespList);







        List<Attraction> attractionList = attractionRepository.findAll();



        homeResp.setAttractions(attractionList.stream().map(e->{


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




        Map<Long,List<Campaign>> longListMap =  campaignList.stream().collect(Collectors.groupingBy(e->e.getScenario()));



        CollectionModel categoryCollectionModel = CollectionModel.of(scenarios.stream().map(x->{


            EntityModel<ScenarioResp> scenarioEntityModel = EntityModel.of(ScenarioResp.from(x,longListMap.getOrDefault(x.getId(), Arrays.asList()).size()));

            scenarioEntityModel.add(linkTo(methodOn(ScenarioRestController.class).getScenario(x.getId())).withRel("getScenario"));
            return scenarioEntityModel;

        }).collect(Collectors.toList()));

        homeResp.setCampaignCategories(categoryCollectionModel);




        homeResp.setCarousel(fileStorageService.loadDocumentsWithCode(EnumDocumentType.index_carousel,EnumDocumentType.index_carousel.name()));

        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("get"));
        entityModel.add(linkTo(methodOn(CampaignRestController.class).listCampaign(1)).withRel("realnameAuth"));



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



}