package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.WayPointEditReq;
import com.lt.dom.otcReq.WayPointReq;
import com.lt.dom.otcReq.WayPointResp;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumModeOfTransport;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.CityWalkRepository;
import com.lt.dom.repository.PlaceRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.WayPointRepository;
import com.lt.dom.serviceOtc.CityWalkServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//https://www.postman.com/opamcurators/workspace/open-access-CityWalks/example/1501710-e695127d-5c31-4f88-901e-ef4e2a376086
@RestController
@RequestMapping("/oct")
public class AudioGuideRestController {

    @Autowired
    private WayPointRepository wayPointRepository;

    @Autowired
    private PlaceRepository placeRepository;




    @Autowired
    private SupplierRepository supplierRepository;


    @Autowired
    private CityWalkServiceImpl cityWalkService;


    @Autowired
    private CityWalkRepository cityWalkRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;



    @GetMapping(value = "/audio_guide/{CityWalk_ID}", produces = "application/json")
    public EntityModel<CityWalkResp> getCityWalk(@PathVariable long CityWalk_ID) {

        Optional<CityWalk> validatorOptional = cityWalkRepository.findById(CityWalk_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }


        CityWalk cityWalk = validatorOptional.get();



        CityWalkResp cityWalkResp = CityWalkResp.from(cityWalk);


        List<WayPoint> wayPoints = wayPointRepository.findAll();

        Map<Long,Place> places = placeRepository.findAll().stream().collect(Collectors.toMap(e->e.getId(),e->e));




        MediaResp mediaResp = new MediaResp();
        mediaResp.setPortrait(fileStorageService.loadDocumentWithDefault(EnumDocumentType.city_walk_thumbnail,cityWalk.getCode()));


        mediaResp.setIntroductionAudio(fileStorageService.loadDocumentWithDefault(EnumDocumentType.city_walk_audio,cityWalk.getCode()));


        cityWalkResp.setMedia(mediaResp);




        if(cityWalk.getLocation()!= null){

            EntityModel entityModel = EntityModel.of(LocationResp.from(cityWalk.getLocation()));

            entityModel.add(linkTo(methodOn(MapRestController.class).getBalance(cityWalk.getLocation().getId())).withRel("lineMap"));

            cityWalkResp.setAddress(entityModel);

        }

        cityWalkResp.setWay_points(IntStream.range(0,wayPoints.size()).mapToObj(i->{
            WayPoint e = wayPoints.get(i);
            Place place = places.get(e.getPlace());

            WayPointResp wayPointResp = WayPointResp.from(e,place);
            wayPointResp.setSeq(i+1);

            EntityModel entityModel = EntityModel.of(wayPointResp);

            MediaResp mediaResp_ = new MediaResp();
            mediaResp_.setPortrait(fileStorageService.loadDocumentWithDefault(EnumDocumentType.WayPoint_thumbnail,e.getCode()));

            mediaResp_.setIntroductionAudio(fileStorageService.loadDocumentWithDefault(EnumDocumentType.WayPoint_audio,e.getCode()));


            wayPointResp.setMedia(mediaResp_);


            entityModel.add(linkTo(methodOn(AudioGuideRestController.class).editWayPoint(e.getId(),null)).withRel("updateLocation"));




            return entityModel;
        }).collect(Collectors.toList()));

      //  CityWalkResp.setHeaderImage(fileStorageService.loadDocumentWithCode(EnumDocumentType.CityWalk_thumbnail,CityWalk.getCode()).getUrl_thumbnail());
        EntityModel entityModel = EntityModel.of(cityWalkResp);
        entityModel.add(linkTo(methodOn(AudioGuideRestController.class).getCityWalk(cityWalk.getId())).withRel("createCityWalk"));


       // entityModel.add(linkTo(methodOn(AudioGuideRestController.class).createWaypoints(cityWalk.getId(),null)).withRel("createWaypoint"));

        return entityModel;

    }





    @PutMapping(value = "/waypoints/{CityWalk_ID}/location", produces = "application/json")
    public EntityModel<CityWalk> editWayPoint(@PathVariable long CityWalk_ID , @RequestBody @Valid WayPointEditReq wayPointEditReq) {

        Optional<WayPoint> supplierOptional = wayPointRepository.findById(CityWalk_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        WayPoint supplier = supplierOptional.get();


        WayPoint CityWalk = cityWalkService.updateWayPoint(supplier,wayPointEditReq);
        EntityModel entityModel = EntityModel.of(CityWalk);

        return entityModel;

    }




}