package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.*;
import com.lt.dom.controllerOct.ProductRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class YoulikeServiceImpl {

    @Autowired
    private PriceServiceImpl priceService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PricingTypeRepository pricingTypeRepository;







    public List<EntityModel> youlike( Product product) {

        List<Product>  productList  = productRepository
                .findAllByType(product.getType())
                .stream()
                .filter(e->EnumPrivacyLevel.public_.equals(e.getPrivacyLevel()))
                .filter(e->EnumProductStatus.active.equals(e.getStatus()))

                .collect(Collectors.toList());

        List<PricingRate> pricingRateList = pricingTypeRepository.findAllById(productList.stream().map(e->e.getDefault_price()).collect(Collectors.toList()));

        Map<Long, List<PricingRate>>  longListPriceGroupMap = pricingRateList.stream().collect(Collectors.groupingBy(e->e.getProductId()));


        /*

        Map<Long, PricingType> longPricingTypeMap = pricingTypeRepository.findAllById(productList.stream().map(e->e.getDefault_price()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));
*/


        return productList.stream().map(e->{

/*
            PricingType pricingType_default = null;
            if(e.getDefault_price() == null){
                if(longPricingTypeMap.isEmpty()){
                    pricingType_default = longPricingTypeMap.values().stream().findAny().get();

                }else{
                    pricingType_default = new PricingType();
                }
            }else{
                pricingType_default = longPricingTypeMap.getOrDefault(e.getDefault_price(),new PricingType());

            }
*/

            //  PricingType pricingType_default = longPricingTypeMap.get(e.getDefault_price());
            ProductResp productResp = ProductResp.miniappHome(e);

           // productResp.setDefault_sku(PricingTypeResp.sku_simple(pricingType_default));
            productResp.setPriceRange(priceService.getPriceRange(longListPriceGroupMap.get(e.getId())));


            //   productResp.setImages(fileStorageService.loadDocuments(EnumDocumentType.product_photos,e.getCode()));
            productResp.setThumbnail_image(fileStorageService.loadDocumentWithDefault(EnumDocumentType.product_thumb,e.getCode()));



            EntityModel<ProductResp> entityModel = EntityModel.of(productResp);
            entityModel.add(linkTo(methodOn(ProductRestController.class).getProduct(e.getId())).withSelfRel());

            return entityModel;
        }).collect(Collectors.toList());




    }
}
