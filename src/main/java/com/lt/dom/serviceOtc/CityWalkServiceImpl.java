package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.OctResp.CityWalkReq;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumAudioOption;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.*;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
public class CityWalkServiceImpl {
    @Autowired
    private WayPointRepository wayPointRepository;
    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private CollectionItemRepository collectionItemRepository;
    @Autowired
    private MediaServiceImpl mediaService;

    @Autowired
    private PointsofinterestRepository pointsofinterestRepository;
    @Autowired
    private AssetServiceImpl assetService;


    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private CityWalkRepository cityWalkRepository;



    public CityWalk create(Supplier supplier, CityWalkReq cityWalkReq) {
        CityWalk theatre = new CityWalk();
        theatre.setTitle(cityWalkReq.getTitle());
        theatre.setCode(idGenService.cityWalkCode());
        theatre.setSupplier(supplier.getId());
        theatre.setIntro(cityWalkReq.getIntro());
        theatre.setWalk_duration(cityWalkReq.getWalk_duration());
        theatre.setTotal_duration(cityWalkReq.getTotal_duration());
        theatre.setWalk_distance(cityWalkReq.getWalk_distance());
        theatre.setModeOfTransport(cityWalkReq.getModeOfTransport());


        theatre = cityWalkRepository.save(theatre);



        if(cityWalkReq.getIntroductionAudio()!= null){
            fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.city_walk_audio,cityWalkReq.getIntroductionAudio().getCode());

        }



        CityWalk finalTheatre = theatre;
        cityWalkReq.getMedias().stream().forEach(theatreReq->{

            if(theatreReq.getPortrait()!=null){
                fileStorageService.saveFromTempDocumentCode(finalTheatre.getCode(), EnumDocumentType.city_walk_thumbnail,theatreReq.getThumbnail());
            }

        });


        assetService.getWithNew(theatre.getCode(),theatre.getId());

        return theatre;


    }



@Transactional
    public CityWalk createEdit(CityWalk theatre, CityWalkReq cityWalkReq) {

        theatre.setTitle(cityWalkReq.getTitle());
        theatre.setTour_summary(cityWalkReq.getTour_summary());
        theatre.setName_long(cityWalkReq.getName_long());
        theatre.setDesc_long(cityWalkReq.getDesc_long());
        theatre.setSlug(cityWalkReq.getSlug());
        theatre.setCityRegion(cityWalkReq.getCityRegion());

        theatre.setIntro(cityWalkReq.getIntro());
        theatre.setWalk_duration(cityWalkReq.getWalk_duration());
        theatre.setTotal_duration(cityWalkReq.getTotal_duration());
        theatre.setWalk_distance(cityWalkReq.getWalk_distance());

        theatre.setMajorLandmarks_json(new Gson().toJson(cityWalkReq.getMajorLandmarks()));

        Address location = new Address();
        location.setAddressLine1(cityWalkReq.getLocation().getAddress());
        location.setLatitude(cityWalkReq.getLocation().getLatitude());
        location.setLongitude(cityWalkReq.getLocation().getLongitude());
        theatre.setLocation(location);

    //    theatre.setModeOfTransport(cityWalkReq.getModeOfTransport());


        theatre = cityWalkRepository.save(theatre);


        Assert.notNull(cityWalkReq.getIntroductionAudio(),"IntroductionAudio 不能为空");

        if(cityWalkReq.getIntroductionAudio()!= null){
            fileStorageService.updateFromTempDocumentCode(theatre.getCode(), EnumDocumentType.city_walk_audio,cityWalkReq.getIntroductionAudio().getCode());

        }



        CityWalk finalTheatre = theatre;
        cityWalkReq.getMedias().stream().forEach(theatreReq->{

            if(theatreReq.getThumbnail()!=null){
                fileStorageService.updateFromTempDocumentCode(finalTheatre.getCode(), EnumDocumentType.city_walk_thumbnail,theatreReq.getThumbnail());
            }

        });


        assetService.getWithNew(theatre.getCode(),theatre.getId());

        return theatre;


    }


    public CityWalk createStep1(Supplier supplier, CityWalkReq cityWalkReq) {
        Assert.notNull(cityWalkReq.getTitle(), "名称 type must not be null!");
        Assert.notNull(cityWalkReq.getCityRegion(), "区域 type must not be null!");
        Assert.notNull(cityWalkReq.getModeOfTransport(), "模式 type must not be null!");

        CityWalk theatre = new CityWalk();
        theatre.setTitle(cityWalkReq.getTitle());
        theatre.setCode(idGenService.cityWalkCode());
        theatre.setSupplier(supplier.getId());
        theatre.setCityRegion(cityWalkReq.getCityRegion());

        theatre.setModeOfTransport(cityWalkReq.getModeOfTransport());


        theatre = cityWalkRepository.save(theatre);


        return theatre;


    }
    public CityWalk update(CityWalk supplier, CityWalkReq tripReq) {
        return supplier;
    }





    public Pointsofinterest createPointsofinterest(Supplier supplier, PointsofinterestReq artworkReq) {
        Pointsofinterest showtime = new Pointsofinterest();
        showtime.setTitle(artworkReq.getTitle());
     //   showtime.setCode(idGenService.openidNo());
        showtime.setDescription(artworkReq.getDescription());
/*
        showtime.setName_long(artworkReq.getName_long());
        showtime.setDesc_long(artworkReq.getDesc_long());
        showtime.setDesc_short(artworkReq.getDesc_short());
*/


/*        showtime.setSupplier(supplier.getId());
        showtime.setSlug(placeReq.getSlug());
        showtime.setTitle(placeReq.getTitle());
        showtime.setDesc_long(placeReq.getDesc_long());
        showtime.setPrivacyLevel(placeReq.getPrivacyLevel());
        showtime.setDesc_short(placeReq.getDesc_short());*/


        showtime = pointsofinterestRepository.save(showtime);

 /*       if(artworkReq.getMedia().getIcon()!=null){
            fileStorageService.saveFromTempDocumentCode(showtime.getCode(), EnumDocumentType.artwork_icon,artworkReq.getMedia().getIcon());

        }
        if(artworkReq.getMedia().getLarge()!=null){
            fileStorageService.saveFromTempDocumentCode(showtime.getCode(), EnumDocumentType.artwork_large,artworkReq.getMedia().getLarge());
        }
        if(artworkReq.getMedia().getStandard()!=null){
            fileStorageService.saveFromTempDocumentCode(showtime.getCode(), EnumDocumentType.artwork_standard,artworkReq.getMedia().getStandard());
        }
        if(artworkReq.getMedia().getThumbnail()!=null){
            fileStorageService.saveFromTempDocumentCode(showtime.getCode(), EnumDocumentType.artwork_thumbnail,artworkReq.getMedia().getThumbnail());
        }
        if(artworkReq.getMedia().getAudio()!=null){
            Document tempDocument = fileStorageService.saveFromTempDocumentCode(showtime.getCode(), EnumDocumentType.artwork_audio,artworkReq.getMedia().getAudio());


            MediaGenralReq mediaGenralReq = new MediaGenralReq();
            mediaGenralReq.setDescription_lang("");
            mediaGenralReq.setDescription_text(artworkReq.getDescription());
            mediaGenralReq.setHref(FileStorageServiceImpl.url(tempDocument));
            mediaGenralReq.setPreview_image_url(FileStorageServiceImpl.url(tempDocument));
            mediaGenralReq.setMediaType(EnumMediaType.audio);
            mediaService.create(supplier,mediaGenralReq);

        }*/
        return showtime;
    }

    public WayPoint createWayPoint(CityWalk citywallk, Place place, WayPointReq theatreReq) {
        WayPoint wayPoint = new WayPoint();

        wayPoint.setTitle(theatreReq.getTitle());
        wayPoint.setDesc_long(theatreReq.getDesc_long());
        wayPoint.setSlug(theatreReq.getSlug());
        wayPoint.setCode(idGenService.openidNo());
        wayPoint.setCityWalk(citywallk.getId());


        Address location = new Address();
        location.setAddressLine1(theatreReq.getLocation().getAddress());
        location.setLatitude(theatreReq.getLocation().getLatitude());
        location.setLongitude(theatreReq.getLocation().getLongitude());
        wayPoint.setLocation(location);

        wayPoint.setVisit_time(theatreReq.getVisit_time());
        wayPoint.setWalk_to_next_distance(theatreReq.getWalk_to_next_distance());
        wayPoint.setWalk_to_next_duration(theatreReq.getWalk_to_next_duration());
        wayPoint.setAudioOption(theatreReq.getAudioOption());
        wayPoint.setPlace(place.getId());

        wayPoint = wayPointRepository.save(wayPoint);

        if(theatreReq.getAudioOption().equals(EnumAudioOption.custom_upload)){
            fileStorageService.saveFromTempDocumentCode(wayPoint.getCode(), EnumDocumentType.WayPoint_audio,theatreReq.getAudio());
        }



        WayPoint finalExhibition = wayPoint;
        theatreReq.getMedias().stream().forEach(e->{
            if(e!=null){
                fileStorageService.saveFromTempDocumentCode(finalExhibition.getCode(), EnumDocumentType.WayPoint_thumbnail,e.getThumbnail());
            }
        });


        /*
        if(theatreReq.getMedia().getLarge()!=null){
            fileStorageService.saveFromTempDocumentCode(exhibition.getCode(), EnumDocumentType.museum_large,theatreReq.getMedia().getLarge());
        }
        if(theatreReq.getMedia().getStandard()!=null){
            fileStorageService.saveFromTempDocumentCode(exhibition.getCode(), EnumDocumentType.museum_standard,theatreReq.getMedia().getStandard());
        }
        if(theatreReq.getMedia().getThumbnail()!=null){
            fileStorageService.saveFromTempDocumentCode(exhibition.getCode(), EnumDocumentType.museum_thumbnail,theatreReq.getMedia().getThumbnail());
        }
*/

        return wayPoint;
    }


    public WayPoint createWayPointStep1(CityWalk supplier, WayPointReq theatreReq) {
        WayPoint exhibition = new WayPoint();

        exhibition.setTitle(theatreReq.getTitle());
        exhibition.setDesc_long(theatreReq.getDesc_long());
        exhibition.setSlug(theatreReq.getSlug());
        exhibition.setCode(idGenService.openidNo());
        exhibition.setCityWalk(supplier.getId());


        Address location = new Address();
        location.setAddressLine1(theatreReq.getLocation().getAddress());
        location.setLatitude(theatreReq.getLocation().getLatitude());
        location.setLongitude(theatreReq.getLocation().getLongitude());
        exhibition.setLocation(location);



        exhibition = wayPointRepository.save(exhibition);

        if(theatreReq.getAudioOption().equals(EnumAudioOption.custom_upload)){
            fileStorageService.saveFromTempDocumentCode(exhibition.getCode(), EnumDocumentType.WayPoint_audio,theatreReq.getAudio());
        }



        WayPoint finalExhibition = exhibition;
        theatreReq.getMedias().stream().forEach(e->{
            if(e!=null){
                fileStorageService.saveFromTempDocumentCode(finalExhibition.getCode(), EnumDocumentType.WayPoint_thumbnail,e.getThumbnail());
            }
        });


        return exhibition;
    }


    public WayPoint updateWayPoint(WayPoint wayPoint, Place place, WayPointEditReq theatreReq) {

        wayPoint.setTitle(theatreReq.getTitle());
        wayPoint.setDesc_long(theatreReq.getDesc_long());
        wayPoint.setSlug(theatreReq.getSlug());


        wayPoint.setVisit_time(theatreReq.getVisit_time());
        wayPoint.setWalk_to_next_distance(theatreReq.getWalk_to_next_distance());
        wayPoint.setWalk_to_next_duration(theatreReq.getWalk_to_next_duration());
        wayPoint.setAudioOption(theatreReq.getAudioOption());
        wayPoint.setPlace(place.getId());

        wayPoint = wayPointRepository.save(wayPoint);


        if(theatreReq.getAudioOption().equals(EnumAudioOption.custom_upload)){
            fileStorageService.updateFromTempDocumentCode(wayPoint.getCode(), EnumDocumentType.WayPoint_audio,theatreReq.getAudio());
        }


        WayPoint finalExhibition = wayPoint;
        theatreReq.getMedias().stream().forEach(e->{
            if(e!=null){
                fileStorageService.updateFromTempDocumentCode(finalExhibition.getCode(), EnumDocumentType.WayPoint_thumbnail,e.getThumbnail());
            }
        });



        return wayPoint;
    }

    public WayPoint updateWayPoint(WayPoint wayPoint, WayPointEditReq theatreReq) {

        Address address = new Address();
        address.setLatitude(theatreReq.getLocation_lat());
        address.setLongitude(theatreReq.getLocation_lng());
        wayPoint.setLocation(address);
        wayPoint = wayPointRepository.save(wayPoint);






        return wayPoint;
    }
}
