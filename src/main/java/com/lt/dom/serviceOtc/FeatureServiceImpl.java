package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.home.FeatureResp;
import com.lt.dom.OctResp.home.HomeResp;
import com.lt.dom.OctResp.layout.LayoutResp;
import com.lt.dom.OctResp.layout.MeResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.controllerOct.IndexController;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumLayoutFeatured;
import com.lt.dom.otcenum.EnumPhotos;
import com.lt.dom.otcenum.Enumfeatured;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FeatureServiceImpl {

    @Autowired
    private LtConfig ltConfig;

    @Autowired
    private FileStorageServiceImpl fileStorageService;



    public void fill(HomeResp homeResp) {
        homeResp.setFeatures(Arrays.stream(Enumfeatured.values()).map(e->{

            FeatureResp resp = FeatureResp.from(e);
            EntityModel<FeatureResp> entityModel1 = EntityModel.of(resp);


            resp.setFeature_image(fileStorageService.loadDocument(Arrays.asList(EnumPhotos.thumb),EnumDocumentType.home_page_feature_logo,e.name()));

            if(e.equals(Enumfeatured.city_hero)){
                String link = linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel().toString();
                entityModel1.add(linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel());
                resp.setPath("/pages/onecard/show?url="+link);
            }



            return entityModel1;
        }).collect(Collectors.toList()));
    }


    public LayoutResp meFill() {

         Map<String,Object> map = Arrays.stream(EnumLayoutFeatured.values()).collect(Collectors.groupingBy(e->e.getGroup())).entrySet()
                .stream().map(e->{
            return Pair.with(e.getKey(),e.getValue().stream().map(ee->{


                FeatureResp resp = FeatureResp.from(ee);
                EntityModel<FeatureResp> entityModel1 = EntityModel.of(resp);


                resp.setIcon(fileStorageService.loadDocument(Arrays.asList(EnumPhotos.thumb),EnumDocumentType.home_page_feature_logo,ee.name()).getUrl_thumbnail());

                if(e.equals(Enumfeatured.city_hero)){
                    String link = linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel().getHref();
                    entityModel1.add(linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel());
                    resp.setPath("/pages/onecard/show?url="+link);
                }else{
                    String link = linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel().getHref();
                    entityModel1.add(linkTo(methodOn(IndexController.class).cityPass(null)).withSelfRel());
                    resp.setPath("/pages/onecard/show?url="+link);
                    //resp.setUrl("/pages/onecard/show?url="+link);
                }



                return entityModel1;

            }).collect(Collectors.toList()));

        }).collect(Collectors.toMap(e->e.getValue0(),e->e.getValue1()));

        LayoutResp layoutResp = new LayoutResp();
        layoutResp.setName("home");
        layoutResp.setVersion(1);
        layoutResp.setLayout(map);

        return layoutResp;
    }
}
