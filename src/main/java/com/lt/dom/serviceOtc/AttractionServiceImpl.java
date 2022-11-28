package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.OctResp.AttractionEditResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttractionReq;
import com.lt.dom.otcReq.AttributeEditReq;
import com.lt.dom.otcReq.ProductEditAboutTabPojo;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumProductStatus;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttractionServiceImpl {
    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    AttributeRepository attributeRepository;


    @Resource
    private AttractionRepository attractionRepository;


    @Autowired
    FileStorageServiceImpl fileStorageService;

    public Attraction create(Supplier supplier, AttractionReq pojo) {

        Attraction attraction = new Attraction();
        attraction.setSupplier(supplier.getId());
        attraction.setCode(idGenService.attractionCode());
        attraction.setName(pojo.getName());
        attraction.setLongdesc(pojo.getLongdesc());
        attraction.setShortdesc(pojo.getShortdesc());
        //attraction.setVideo(pojo.getVideo());
        attraction.setStatus(EnumProductStatus.draft);

        attraction.setProducts_json(new Gson().toJson(Arrays.asList()));
        attraction.setTags_json(new Gson().toJson(Arrays.asList()));
       // attraction.setThumbnail_image(new Gson().toJson(Arrays.asList()));
      //  attraction.setThumbnail_image(pojo.getVideo_url());
        attraction = attractionRepository.save(attraction);

        return attraction;
    }




    @Transactional
    public Attraction editAttractionInfo(Attraction attraction, AttractionEditResp.InfoTab pojo) {


        attraction.setName(pojo.getName());
        attraction.setLongdesc(pojo.getLongdesc());
        attraction.setShortdesc(pojo.getShortdesc());
        attraction.setPrivacyLevel(pojo.getPrivacyLevel());
        attraction.setTags_json((new Gson()).toJson(pojo.getTags()));


        Address address =attraction.getLocation();
        if(address == null){
            address = new Address();

        }
        address.setAddressLine1(pojo.getLocation().getAddress());
        address.setLatitude(pojo.getLocation().getLatitude());
        address.setLongitude(pojo.getLocation().getLongitude());
        attraction.setLocation(address);

        attraction = attractionRepository.save(attraction);


        Attraction finalProduct = attraction;
        pojo.getImages().stream().forEach(photoResp->{

            if(photoResp.getCode()!=null){

                fileStorageService.updateFromTempDocument(finalProduct.getCode(),photoResp,EnumDocumentType.attraction_photos);

            }

        });


        if(pojo.getVideo()!=null && pojo.getVideo().getCode()!= null){
            fileStorageService.updateFromTempDocumentCode(finalProduct.getCode(), EnumDocumentType.attraction_video,pojo.getVideo().getCode());
        }

        if(pojo.getThumb()!=null && pojo.getThumb().getCode()!= null){
            fileStorageService.updateFromTempDocumentCode(finalProduct.getCode(), EnumDocumentType.attraction_thumb,pojo.getThumb().getCode());
        }

        return attraction;
    }





    public Attraction editAttractionSelfGuide(Attraction attraction, AttractionEditResp.SelfGuideTap pojo) {


        attraction.setSelfGuided(pojo.getSelfGuided());

        attraction = attractionRepository.save(attraction);


        return attraction;
    }

    public Attraction editAttractionProduct(Attraction attraction, AttractionEditResp.ProductTap pojo) {


        attraction.setProducts_json(new Gson().toJson(pojo.getProducts()));

        attraction = attractionRepository.save(attraction);


        return attraction;
    }



    @Transactional
    public Attraction editAboutTab(Attraction product, AttractionEditResp.AboutTap pojo) {

        List<AttributeEditReq> myA = new ArrayList<>();
        myA.addAll(pojo.getKnowBeforeYouGo().stream().map(e->{
            e.setKey("knowBeforeYouGo");
            return e;
        }).collect(Collectors.toList()));
        myA.addAll(pojo.getGettingThere().stream().map(e->{
            e.setKey("gettingThere");
            return e;
        }).collect(Collectors.toList()));
        myA.addAll(pojo.getHoursOfOperation().stream().map(e->{
            e.setKey("hoursOfOperation");
            return e;
        }).collect(Collectors.toList()));


        List<Long> has = myA.stream().map(e->e.getId()).collect(Collectors.toList());


        List<Attribute> attributes = attributeRepository.findAllByObjectCode(product.getCode());

        Map<Long,Attribute> attributeMap = attributes.stream().collect(Collectors.toMap(e->e.getId(), e->e));

        attributeRepository.deleteAllById(attributes.stream().filter(e->!has.contains(e.getId())).map(e->e.getId()).collect(Collectors.toList()));
        List<Attribute> attributeList = myA.stream().map(e->{

            Attribute attribute = attributeMap.getOrDefault(e.getId(), new Attribute());
            attribute.setDescription(e.getText());
            attribute.setFeatureType(e.getType());
            attribute.setObjectCode(product.getCode());
            attribute.setKey(e.getKey());
            attribute.setName(e.getName());
            return attribute;

        }).collect(Collectors.toList());


        attributeRepository.saveAll(attributeList);

        return product;



    }
}
