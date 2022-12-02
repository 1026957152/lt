package com.lt.dom.controllerOct;


import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;

import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.CityWalkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
public class CityWalkRestController {

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



    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/citywalks", produces = "application/json")
    public PagedModel getCityWalkList(@PathVariable long SUPPLIER_ID ,
                                      @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable,
                                      PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();



        Page<CityWalk> bookingRuleList = cityWalkRepository.findAll(pageable);

        return assembler.toModel(bookingRuleList.map(e->{

            CityWalkResp movieResp = CityWalkResp.from(e);

            movieResp.setAddress(LocationResp.from(e.getLocation()));

            EntityModel entityModel = EntityModel.of(movieResp);
            entityModel.add(linkTo(methodOn(CityWalkRestController.class).getStepCityWalk(e.getId())).withSelfRel());
            entityModel.add(linkTo(methodOn(CityWalkRestController.class).editCityWalk(e.getId(),null)).withRel("edit"));

            return entityModel;
        }));

    }




    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/citywalks/Page_getCityWalkList", produces = "application/json")
    public EntityModel<Media> Page_getCityWalkList(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("transport_list_type", EnumModeOfTransport.List()
        );

        EntityModel entityModel = EntityModel.of(map);


        entityModel.add(linkTo(methodOn(CityWalkRestController.class).createCityWalkStep1(supplier.getId(),null)).withRel("create"));
        entityModel.add(linkTo(methodOn(CityWalkRestController.class).getCityWalkList(supplier.getId(),null,null)).withRel("list"));


        return entityModel;

    }
    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/citywalks", produces = "application/json")
    public EntityModel<CityWalk> createCityWalk(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid CityWalkReq tripReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        CityWalk CityWalk = cityWalkService.create(supplier,tripReq);


        EntityModel entityModel = EntityModel.of(CityWalk);

        return entityModel;

    }

    @PutMapping(value = "/citywalks/{CityWalk_ID}/update_distribution", produces = "application/json")
    public EntityModel<CityWalk> createCityWalkStep2(@PathVariable long CityWalk_ID ,@RequestBody @Valid CityWalkReq tripReq) {

        Optional<CityWalk> supplierOptional = cityWalkRepository.findById(CityWalk_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        CityWalk supplier = supplierOptional.get();



        CityWalk CityWalk = cityWalkService.createEdit(supplier,tripReq);


        EntityModel entityModel = EntityModel.of(CityWalk);

        return entityModel;

    }



    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/citywalks/step1", produces = "application/json")
    public EntityModel<CityWalk> createCityWalkStep1(@PathVariable long SUPPLIER_ID ,@RequestBody @Valid CityWalkReq tripReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        CityWalk CityWalk = cityWalkService.createStep1(supplier,tripReq);


        EntityModel entityModel = EntityModel.of(CityWalk);
        entityModel.add(linkTo(methodOn(CityWalkRestController.class).getStepCityWalk(CityWalk.getId())).withSelfRel());

        return entityModel;

    }

    @PostMapping(value = "/citywalks/{CityWalk_ID}", produces = "application/json")
    public EntityModel<CityWalk> editCityWalk(@PathVariable long CityWalk_ID ,@RequestBody @Valid CityWalkReq CityWalkReq) {

        Optional<CityWalk> supplierOptional = cityWalkRepository.findById(CityWalk_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        CityWalk supplier = supplierOptional.get();


        CityWalk CityWalk = cityWalkService.update(supplier,CityWalkReq);


        EntityModel entityModel = EntityModel.of(CityWalk);

        return entityModel;

    }

    @GetMapping(value = "/suppliers/{SUPPLIER_ID}/cityWalks/Page_createCityWalk", produces = "application/json")
    public EntityModel<CityWalk> Page_createCityWalk(@PathVariable long SUPPLIER_ID ) {


        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();


        Map map = Map.of("transport_list_type", EnumModeOfTransport.List()
                );


        EntityModel entityModel = EntityModel.of(map);

        entityModel.add(linkTo(methodOn(CityWalkRestController.class).createCityWalkStep1(supplier.getId(),null)).withRel("createCityWalk"));


        return entityModel;

    }


    @GetMapping(value = "/citywalks/{CityWalk_ID}/steps", produces = "application/json")
    public EntityModel<CityWalkResp> getStepCityWalk(@PathVariable long CityWalk_ID) {

        Optional<CityWalk> validatorOptional = cityWalkRepository.findById(CityWalk_ID);
        if(validatorOptional.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }


        CityWalk cityWalk = validatorOptional.get();





        CityWalkStepEditResp.PlaceTab placeTab  = new CityWalkStepEditResp.PlaceTab();

        List<WayPoint> wayPoints = wayPointRepository.findAll();

        Map<Long,Place> places = placeRepository.findAll().stream().collect(Collectors.toMap(e->e.getId(),e->e));



        placeTab.setWay_points(IntStream.range(0,wayPoints.size()).mapToObj(i->{
            WayPoint e = wayPoints.get(i);

            Place place = places.get(e.getPlace());

            WayPointResp wayPointResp = WayPointResp.from(e,place);
            wayPointResp.setSeq(i+1);

            EntityModel entityModel = EntityModel.of(wayPointResp);

            MediaEditResp mediaResp_ = new MediaEditResp();
            mediaResp_.setPortrait(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.WayPoint_thumbnail,e.getCode()));


            mediaResp_.setIntroductionAudio(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.WayPoint_audio,e.getCode()));


           // wayPointResp.setMedia(mediaResp_);


            // entityModel.add(linkTo(methodOn(CityWalkRestController.class).getExhibit(e.getId())).withSelfRel());




            return entityModel;
        }).collect(Collectors.toList()));



        CityWalkStepEditResp.ScriptTab scriptTab = new CityWalkStepEditResp.ScriptTab();
        scriptTab.setWay_points(IntStream.range(0,wayPoints.size()).mapToObj(i->{
            WayPoint e = wayPoints.get(i);


            WayPointResp wayPointResp = WayPointResp.from(e);
            wayPointResp.setSeq(i+1);

            EntityModel entityModel = EntityModel.of(wayPointResp);
            MediaResp mediaResp_ = new MediaResp();
            mediaResp_.setPortrait(fileStorageService.loadDocumentWithCode(EnumDocumentType.WayPoint_thumbnail,e.getCode()));
            mediaResp_.setIntroductionAudio(fileStorageService.loadDocumentWithCode(EnumDocumentType.WayPoint_audio,e.getCode()));
            wayPointResp.setMedia(mediaResp_);
            entityModel.add(linkTo(methodOn(CityWalkRestController.class).editWayPointStep2(e.getId(),null)).withRel("update"));

            return entityModel;
        }).collect(Collectors.toList()));





        CityWalkStepEditResp.DistributionTab distributionTab = CityWalkStepEditResp.DistributionTab.from(cityWalk);

      //  CityWalkStepEditResp.DistributionTab distributionTab  = new CityWalkStepEditResp.DistributionTab();


/*        MediaResp mediaResp = new MediaResp();
        if(fileStorageService.loadDocumentWithCode(EnumDocumentType.city_walk_thumbnail,cityWalk.getCode()) == null){
            mediaResp.setPortrait("");
        }else{
            mediaResp.setPortrait(fileStorageService.loadDocumentWithCode(EnumDocumentType.city_walk_thumbnail,cityWalk.getCode()).getUrl_thumbnail());

        }


        if(fileStorageService.loadDocumentWithCode(EnumDocumentType.city_walk_audio,cityWalk.getCode()) == null) {
            mediaResp.setIntroductionAudio("");
        }else{
            mediaResp.setIntroductionAudio(fileStorageService.loadDocumentWithCode(EnumDocumentType.city_walk_audio,cityWalk.getCode()).getUrl_thumbnail());

        }
        distributionTab.setMedia(mediaResp);*/



        //  CityWalkResp.setHeaderImage(fileStorageService.loadDocumentWithCode(EnumDocumentType.CityWalk_thumbnail,CityWalk.getCode()).getUrl_thumbnail());
        if(fileStorageService.loadDocumentWithCode(EnumDocumentType.city_walk_audio,cityWalk.getCode()) == null) {
            distributionTab.setIntroductionAudio(null);
        }else{
            distributionTab.setIntroductionAudio(fileStorageService.loadDocumentWithCode(EnumDocumentType.city_walk_audio,cityWalk.getCode()));
        }




        List<PhotoResp> mediaRespList = fileStorageService.loadDocumentsWithCodeEdit(EnumDocumentType.city_walk_thumbnail,cityWalk.getCode());

        distributionTab.setMedias(mediaRespList);


        CityWalkStepEditResp cityWalkResp = new CityWalkStepEditResp();


        EntityModel entityModel_distribution = EntityModel.of(distributionTab);
        entityModel_distribution.add(linkTo(methodOn(CityWalkRestController.class).createCityWalkStep2(cityWalk.getId(),null)).withRel("update"));
        cityWalkResp.setDistributionTab(entityModel_distribution);





        EntityModel entityModel_place = EntityModel.of(placeTab);
        entityModel_place.add(linkTo(methodOn(CityWalkRestController.class).createWaypointsStep1(cityWalk.getId(),null)).withRel("createWaypoint"));
        cityWalkResp.setPlaceTab(entityModel_place);



        EntityModel entityModel_script = EntityModel.of(scriptTab);
        entityModel_script.add(linkTo(methodOn(CityWalkRestController.class).editScriptTab(cityWalk.getId(),null)).withRel("edit"));
        cityWalkResp.setScriptTab(entityModel_script);




        EntityModel entityModel = EntityModel.of(cityWalkResp);
        entityModel.add(linkTo(methodOn(CityWalkRestController.class).getCityWalk(cityWalk.getId())).withRel("createCityWalk"));



        return entityModel;

    }






    @GetMapping(value = "/citywalks/{CityWalk_ID}", produces = "application/json")
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


            // entityModel.add(linkTo(methodOn(CityWalkRestController.class).getExhibit(e.getId())).withSelfRel());




            return entityModel;
        }).collect(Collectors.toList()));

      //  CityWalkResp.setHeaderImage(fileStorageService.loadDocumentWithCode(EnumDocumentType.CityWalk_thumbnail,CityWalk.getCode()).getUrl_thumbnail());
        EntityModel entityModel = EntityModel.of(cityWalkResp);
        entityModel.add(linkTo(methodOn(CityWalkRestController.class).getCityWalk(cityWalk.getId())).withRel("createCityWalk"));


        entityModel.add(linkTo(methodOn(CityWalkRestController.class).createWaypoints(cityWalk.getId(),null)).withRel("createWaypoint"));

        return entityModel;

    }









    @PostMapping(value = "/citywalks/{CityWalk_ID}/waypoints", produces = "application/json")
    public EntityModel<WayPointResp> createWaypoints(@PathVariable long CityWalk_ID , @RequestBody @Valid WayPointReq movieReq) {

        Optional<CityWalk> supplierOptional = cityWalkRepository.findById(CityWalk_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        CityWalk supplier = supplierOptional.get();


        Optional<Place> optionalPlace = placeRepository.findById(movieReq.getPlace());
        if(optionalPlace.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 Place");
        }
        Place place = optionalPlace.get();



        WayPoint wayPoint = cityWalkService.createWayPoint(supplier,place,movieReq);

        WayPointResp wayPointResp = WayPointResp.from(wayPoint);

        EntityModel entityModel = EntityModel.of(wayPointResp);

        return entityModel;

    }





    @PostMapping(value = "/citywalks/{CityWalk_ID}/editScriptTab", produces = "application/json")
    public EntityModel<WayPointResp> editScriptTab(@PathVariable long CityWalk_ID , @RequestBody @Valid WayPointReq movieReq) {

        Optional<CityWalk> supplierOptional = cityWalkRepository.findById(CityWalk_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        CityWalk supplier = supplierOptional.get();



        WayPoint wayPoint = cityWalkService.createWayPointStep1(supplier,movieReq);

        WayPointResp wayPointResp = WayPointResp.from(wayPoint);

        EntityModel entityModel = EntityModel.of(wayPointResp);

        return entityModel;

    }

    @PostMapping(value = "/citywalks/{CityWalk_ID}/waypoints/step1", produces = "application/json")
    public EntityModel<WayPointResp> createWaypointsStep1(@PathVariable long CityWalk_ID , @RequestBody @Valid WayPointReq movieReq) {

        Optional<CityWalk> supplierOptional = cityWalkRepository.findById(CityWalk_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        CityWalk supplier = supplierOptional.get();



        WayPoint wayPoint = cityWalkService.createWayPointStep1(supplier,movieReq);

        WayPointResp wayPointResp = WayPointResp.from(wayPoint);

        EntityModel entityModel = EntityModel.of(wayPointResp);

        return entityModel;

    }


    @PostMapping(value = "/waypoints/{CityWalk_ID}", produces = "application/json")
    public EntityModel<CityWalk> editWayPointStep2(@PathVariable long CityWalk_ID , @RequestBody @Valid WayPointEditReq wayPointEditReq) {

        Optional<WayPoint> supplierOptional = wayPointRepository.findById(CityWalk_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        WayPoint supplier = supplierOptional.get();

        Optional<Place> optionalPlace = placeRepository.findById(wayPointEditReq.getPlace());
        if(optionalPlace.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 Place");
        }
        Place place = optionalPlace.get();



        WayPoint CityWalk = cityWalkService.updateWayPoint(supplier,place,wayPointEditReq);


        EntityModel entityModel = EntityModel.of(CityWalk);

        return entityModel;

    }


    @GetMapping(value = "/waypoints/{EXHIBITON_ID}", produces = "application/json")
    public EntityModel<WayPointResp> getWaypoints(@PathVariable long EXHIBITON_ID) {

        Optional<WayPoint> supplierOptional = wayPointRepository.findById(EXHIBITON_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        WayPoint exhibition = supplierOptional.get();

/*        List<WayPoint> artworks = wayPointRepository.findAll();

        ExhibitionResp exhibitionReq = ExhibitionResp.from(exhibition);*/
/*        exhibitionReq.setArtworkss(artworks.stream().map(e->{



            WayPointResp artworkResp = WayPointResp.from(e);
            MediaResp mediaResp = new MediaResp();
            mediaResp.setPortrait(fileStorageService.loadDocumentWithCodeToUrl(EnumDocumentType.artwork_thumbnail,e.getCode()));

            mediaResp.setAudio(fileStorageService.loadDocumentWithCodeToUrl(EnumDocumentType.artwork_audio,e.getCode()));
            artworkResp.setMedia(mediaResp);


            EntityModel entityModel = EntityModel.of(artworkResp);
          //  entityModel.add(linkTo(methodOn(CityWalkRestController.class).getExhibit(e.getId())).withSelfRel());

            return entityModel;
        }).collect(Collectors.toList()));*/



        EntityModel entityModel = EntityModel.of(exhibition);

        return entityModel;

    }


/*

    @PostMapping(value = "/suppliers/{SUPPLIER_ID}/artwork", produces = "application/json")
    public EntityModel<Pointsofinterest> addWayPoint(@PathVariable long SUPPLIER_ID , @RequestBody @Valid PointsofinterestReq movieReq) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(SUPPLIER_ID);
        if(supplierOptional.isEmpty()) {
            throw new BookNotFoundException("没有找到供应商","没找到");
        }

        Supplier supplier = supplierOptional.get();




        Pointsofinterest place = cityWalkService.createPointsofinterest(supplier,movieReq);


        EntityModel entityModel = EntityModel.of(place);

        return entityModel;

    }
*/







}