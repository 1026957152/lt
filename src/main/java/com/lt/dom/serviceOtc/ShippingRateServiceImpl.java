package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.ExtraResp;
import com.lt.dom.OctResp.MovieEdit;
import com.lt.dom.OctResp.ProductBookingResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttributeEditReq;
import com.lt.dom.otcReq.ExtraReq;
import com.lt.dom.otcReq.ShippingRateReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.AttributeRepository;
import com.lt.dom.repository.ExtraRepository;
import com.lt.dom.repository.ShippingRateRepository;
import com.lt.dom.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShippingRateServiceImpl {
    @Autowired
    private ShippingRateRepository shippingRateRepository;

    @Autowired
    private ExtraRepository extraRepository;

    @Autowired
    private AttributeRepository attributeRepository;


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;




    public ShippingRate create(Supplier supplier, ShippingRateReq theatreReq) {
        ShippingRate theatre = new ShippingRate();

        theatre.setDisplay_name(theatreReq.getDisplay_name());
        theatre.setType(theatreReq.getType());
        theatre.setActive(theatreReq.getActive());
        theatre.setFixed_amount(theatreReq.getFixed_amount());


        theatre.setSupplier(supplier.getId());



        theatre.setCode(idGenService.nextId("shra_"));



        theatre = shippingRateRepository.save(theatre);
/*        fileStorageService.s(theatre.getCode(), EnumDocumentType.theatreImageIcon,theatreReq.getMedia().getHeroDesktopDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageStandard,theatreReq.getMedia().getHeroMobileDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageLarge,theatreReq.getMedia().getPosterLarge());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageThumbnail,
                theatreReq.getMedia().getHeroDesktopDynamic());*/



        return theatre;


    }

    @Transactional
    public Movie editAboutTab(Movie product, MovieEdit.AboutTap pojo) {

        List<AttributeEditReq> myA = new ArrayList<>();
        AttributeEditReq attraction = pojo.getShow_intro();
        attraction.setKey("show_intro");
        myA.add(attraction);

        attraction = pojo.getStory_intro();
        attraction.setKey("story_intro");
        myA.add(attraction);


        attraction = pojo.getTeam_intro();
        attraction.setKey("team_intro");
        myA.add(attraction);




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
    @Transactional
    public ShippingRate update(ShippingRate theatre, ShippingRateReq theatreReq) {



            theatre.setDisplay_name(theatreReq.getDisplay_name());
            theatre.setType(theatreReq.getType());
            theatre.setActive(theatreReq.getActive());
            theatre.setFixed_amount(theatreReq.getFixed_amount());



        theatre = shippingRateRepository.save(theatre);


        return theatre;
    }



}
