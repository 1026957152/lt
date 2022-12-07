package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.ExtraResp;
import com.lt.dom.OctResp.MovieEdit;
import com.lt.dom.OctResp.ProductBookingResp;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttributeEditReq;
import com.lt.dom.otcReq.ExtraEditReq;
import com.lt.dom.otcReq.ExtraReq;
import com.lt.dom.otcReq.MovieReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.AttributeRepository;
import com.lt.dom.repository.ExtraRepository;
import com.lt.dom.repository.MovieRepository;
import com.lt.dom.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExtraServiceImpl {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private ExtraRepository extraRepository;

    @Autowired
    private AttributeRepository attributeRepository;


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;




    public Extra create(Supplier supplier, ExtraReq theatreReq) {
        Extra theatre = new Extra();

        theatre.setInternalName(theatreReq.getInternalName());
        theatre.setTitle(theatreReq.getTitle());
        theatre.setDescription(theatreReq.getDescription());



        theatre.setSupplier(supplier.getId());



        theatre.setCode(idGenService.nextId("extr_"));



        theatre.setSlug(theatreReq.getSlug());


        theatre.setPrice_Net(theatreReq.getPricingFrom().getNet());
        theatre.setPrice_Original(theatreReq.getPricingFrom().getOriginal());
        theatre.setPrice_Retail(theatreReq.getPricingFrom().getRetail());

        theatre = extraRepository.save(theatre);
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
    public Extra update(Extra theatre, ExtraEditReq.EditTab theatreReq) {

        theatre.setInternalName(theatreReq.getInternalName());
        theatre.setTitle(theatreReq.getTitle());
        theatre.setDescription(theatreReq.getDescription());




        theatre.setSlug(theatreReq.getSlug());

        theatre.setSlug(theatreReq.getSlug());

/*

       List<MovieReq.StarringActor> starringActorRespList =  theatreReq.getStarringActors().stream().map(star->{
            star.setUuid(UUID.randomUUID().toString());
            return star;
        }).collect(Collectors.toList());


        Movie finalTheatre = theatre;
        theatre.setStarringActors(starringActorRespList.stream().map(star->{
            StarringActor starringActor = new StarringActor();
            starringActor.setDesc(star.getDesc());
            starringActor.setName(star.getName());
            starringActor.setUuid(star.getUuid());
            starringActor.setMovie(finalTheatre);
            return starringActor;
        }).collect(Collectors.toList()));
*/


        theatre = extraRepository.save(theatre);


/*

*/

  /*      if(theatreReq.getMedia().getHeroDesktopDynamic()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(), theatreReq.getMedia().getHeroDesktopDynamic(),EnumDocumentType.theatreImageIcon);

        }
        if(theatreReq.getMedia().getPosterLarge()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(), theatreReq.getMedia().getHeroDesktopDynamic(),EnumDocumentType.theatreImageLarge);

        }*//*
       if(theatreReq.getCover()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getCover(), EnumDocumentType.movie_cover);

        }
        if(theatreReq.getVideo()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getVideo(), EnumDocumentType.movie_vidio);

        }*/

        if(theatreReq.getPhoto().getCode()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getPhoto(), EnumDocumentType.extra_photo);

        }
        return theatre;
    }


    public void prepay_booking_page(Product product, ProductBookingResp productResp) {

        List<Extra> extras = extraRepository.findAll();
        productResp.setExtras(extras.stream().map(e->{
            ExtraResp extraReq = ExtraResp.from(e);


            extraReq.setPhoto(fileStorageService.loadDocumentWithDefault( EnumDocumentType.extra_photo,e.getCode()));

            return extraReq;
        }).collect(Collectors.toList()));
    }


    public Double getTotalPrice(List<Long> extras) {

       return extraRepository.findAllById(extras).stream().mapToDouble(e->{
            if(e.getPrice_Retail()!= null){
                return e.getPrice_Retail();
            }else {
                return 0;
            }
        }).sum();

    }
}
