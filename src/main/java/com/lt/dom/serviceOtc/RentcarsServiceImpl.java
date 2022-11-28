package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.MovieEdit;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RentcarsServiceImpl {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BusStopRepository stopRepository;

    @Autowired
    private BusRouteRepository busRouteRepository;

    @Autowired
    private BusStopRepository busStopRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceRegistrationRepository placeRegistrationRepository;

    @Autowired
    private BusVehicleRepository busVehicleRepository;

    @Autowired
    private AttributeRepository attributeRepository;


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private CarRepository carRepository;

    public PlaceRegistration editPlaceForStop(PlaceRegistration busRoute, PlaceRegistrationReq  theatreReq) {




        busRoute.setLabel(theatreReq.getLabel());



        busRoute = placeRegistrationRepository.save(busRoute);


        return busRoute;


    }

    public BusStop addPlaceForStop(BusStop busRoute, List<BusStopReq>  theatreReq) {


        List<Place> busStopList = placeRepository.findAllById(theatreReq.stream().map(e->e.getId()).collect(Collectors.toList()));
        BusStop busStop = busRoute;
        busStopList.stream().forEach(e->{

            PlaceRegistration stopRegistration = new PlaceRegistration();
            stopRegistration.setStop(busStop);
            stopRegistration.setPlace(e);

            stopRegistration.setId(new BusStopPlaceKey(e.getId(), busStop.getId()));
            busStop.addPlaceInList(stopRegistration);

        });


        busRoute = busStopRepository.save(busStop);


        return busRoute;


    }

    public BusRoute addStop(BusRoute busRoute, List<BusStopReq>  theatreReq) {


        List<BusStop> busStopList = busStopRepository.findAllById(theatreReq.stream().map(e->e.getId()).collect(Collectors.toList()));
        BusRoute route = busRoute;
        busStopList.stream().forEach(e->{

            StopRegistration stopRegistration = new StopRegistration();
            stopRegistration.setStop(e);
            stopRegistration.setRoute(route);

            stopRegistration.setId(new CourseRatingKey(route.getId(), e.getId()));
            route.addStationInList(stopRegistration);

        });


        busRoute = busRouteRepository.save(route);


        return busRoute;


    }

    public Car createCar(Supplier supplier, CarReq  theatreReq) {


        Car route = new Car();

        route.setName(theatreReq.getName());
        route.setSeatNumber(theatreReq.getSeatNumber());

        route.setSupplier(supplier.getId());
        route.setAirConditioning(theatreReq.getAirConditioning());
        route.setOperatorName(theatreReq.getOperatorName());
        route.setBrand(theatreReq.getBrand());

        route.setCode(idGenService.nextId("car_"));
        route = carRepository.save(route);


        if(theatreReq.getBrand_logo()!=null){
            fileStorageService.updateFromTempDocument(route.getCode(),theatreReq.getBrand_logo(), EnumDocumentType.car_brand_logo);

        }
        if(theatreReq.getPhoto()!=null){
            fileStorageService.updateFromTempDocument(route.getCode(),theatreReq.getPhoto(), EnumDocumentType.car_photo);

        }
        return route;


    }




    @Transactional
    public BusRoute editRoute(BusRoute theatre, BusRouteReq theatreReq) {

        theatre.setName(theatreReq.getName());

        theatre.setShortName(theatreReq.getShortName());
        theatre.setDescription(theatreReq.getDescription());

/*        theatre.setEarliestShowingUtc(theatreReq.getEarliestShowingUtc());
        theatre.setReleaseDateUtc(theatreReq.getReleaseDateUtc());
        theatre.setSortableName(theatreReq.getSortableName());
        //    theatre.setDirectors(theatreReq.getDirectors().toString());
        //   theatre.setStarringActors(theatreReq.getStarringActors());
        theatre.setSynopsis(theatreReq.getSynopsis());

        theatre.setSlug(theatreReq.getSlug());*/

        theatre = busRouteRepository.save(theatre);

/*        fileStorageService.s(theatre.getCode(), EnumDocumentType.theatreImageIcon,theatreReq.getMedia().getHeroDesktopDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageStandard,theatreReq.getMedia().getHeroMobileDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageLarge,theatreReq.getMedia().getPosterLarge());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageThumbnail,
                theatreReq.getMedia().getHeroDesktopDynamic());*/



        return theatre;


    }

    public BusRoute createRoute(Supplier supplier, BusRouteReq theatreReq) {
        BusRoute theatre = new BusRoute();
        theatre.setName(theatreReq.getName());

        theatre.setShortName(theatreReq.getShortName());
        theatre.setDescription(theatreReq.getDescription());

/*

        theatre.setDesc_long(theatreReq.getDesc_long());
        theatre.setDesc_short(theatreReq.getDesc_short());
*/



        theatre.setSupplier(supplier.getId());



        theatre.setCode(idGenService.theatreCode());
/*        theatre.setEarliestShowingUtc(theatreReq.getEarliestShowingUtc());
        theatre.setReleaseDateUtc(theatreReq.getReleaseDateUtc());
        theatre.setSortableName(theatreReq.getSortableName());
        //    theatre.setDirectors(theatreReq.getDirectors().toString());
        //   theatre.setStarringActors(theatreReq.getStarringActors());
        theatre.setSynopsis(theatreReq.getSynopsis());

        theatre.setSlug(theatreReq.getSlug());*/

        theatre = busRouteRepository.save(theatre);

/*        fileStorageService.s(theatre.getCode(), EnumDocumentType.theatreImageIcon,theatreReq.getMedia().getHeroDesktopDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageStandard,theatreReq.getMedia().getHeroMobileDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageLarge,theatreReq.getMedia().getPosterLarge());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageThumbnail,
                theatreReq.getMedia().getHeroDesktopDynamic());*/



        return theatre;


    }



    public BusStop editStop(BusStop theatre, BusStopReq theatreReq) {

        theatre.setName(theatreReq.getName());
        theatre.setLat(theatreReq.getLat());
        theatre.setLng(theatreReq.getLng());
        theatre.setDescription(theatreReq.getDescription());
        theatre.setShortName(theatreReq.getShortName());


        theatre = stopRepository.save(theatre);


        return theatre;


    }

    public BusStop createStop(Supplier supplier, BusStopReq theatreReq) {
        BusStop theatre = new BusStop();

        theatre.setName(theatreReq.getName());
        theatre.setLat(theatreReq.getLat());
        theatre.setLng(theatreReq.getLng());
        theatre.setDescription(theatreReq.getDescription());
        theatre.setShortName(theatreReq.getShortName());
/*

        theatre.setDesc_long(theatreReq.getDesc_long());
        theatre.setDesc_short(theatreReq.getDesc_short());
*/



        theatre.setSupplier(supplier.getId());



        theatre.setCode(idGenService.busStopCode());
/*        theatre.setEarliestShowingUtc(theatreReq.getEarliestShowingUtc());
        theatre.setReleaseDateUtc(theatreReq.getReleaseDateUtc());
        theatre.setSortableName(theatreReq.getSortableName());
        //    theatre.setDirectors(theatreReq.getDirectors().toString());
        //   theatre.setStarringActors(theatreReq.getStarringActors());
        theatre.setSynopsis(theatreReq.getSynopsis());

        theatre.setSlug(theatreReq.getSlug());*/

        theatre = stopRepository.save(theatre);
/*        fileStorageService.s(theatre.getCode(), EnumDocumentType.theatreImageIcon,theatreReq.getMedia().getHeroDesktopDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageStandard,theatreReq.getMedia().getHeroMobileDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageLarge,theatreReq.getMedia().getPosterLarge());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageThumbnail,
                theatreReq.getMedia().getHeroDesktopDynamic());*/



        return theatre;


    }


    public Movie create(Supplier supplier, MovieReq theatreReq) {
        Movie theatre = new Movie();
        theatre.setName(theatreReq.getName());
        theatre.setName_long(theatreReq.getName_long());
        theatre.setDesc_long(theatreReq.getDesc_long());
        theatre.setDesc_short(theatreReq.getDesc_short());



        theatre.setSupplier(supplier.getId());



        theatre.setCode(idGenService.theatreCode());
        theatre.setEarliestShowingUtc(theatreReq.getEarliestShowingUtc());
        theatre.setReleaseDateUtc(theatreReq.getReleaseDateUtc());
        theatre.setSortableName(theatreReq.getSortableName());
    //    theatre.setDirectors(theatreReq.getDirectors().toString());
     //   theatre.setStarringActors(theatreReq.getStarringActors());
        theatre.setSynopsis(theatreReq.getSynopsis());

        theatre.setSlug(theatreReq.getSlug());

        theatre = movieRepository.save(theatre);
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
    public Car update(Car route, CarReq theatreReq) {


        route.setName(theatreReq.getName());
        route.setSeatNumber(theatreReq.getSeatNumber());

        route.setAirConditioning(theatreReq.getAirConditioning());
        route.setOperatorName(theatreReq.getOperatorName());
        route.setBrand(theatreReq.getBrand());


        Car finalRoute = route;
        route.setFeatures(theatreReq.getFeatures().stream().map(e->{
            CarFeature carFeature = new CarFeature();
            carFeature.setType(e.getFeature());
            carFeature.setText(e.getText());
            carFeature.setCar(finalRoute);
            return carFeature;
        }).collect(Collectors.toList()));



        route.setTags(theatreReq.getTags().stream().map(e->{
            CarTag carTag = new CarTag();
            carTag.setType(e.getTag());
            carTag.setText(e.getText());
            carTag.setCar(finalRoute);
            return carTag;
        }).collect(Collectors.toList()));
        route = carRepository.save(route);


        if(theatreReq.getBrand_logo()!=null){
            fileStorageService.updateFromTempDocument(route.getCode(),theatreReq.getBrand_logo(), EnumDocumentType.car_brand_logo);

        }
        if(theatreReq.getPhoto()!=null){
            fileStorageService.updateFromTempDocument(route.getCode(),theatreReq.getPhoto(), EnumDocumentType.car_photo);

        }
        return route;
    }


    public Showtime createShowtime(Supplier supplier, Theatre theatre, Movie movie, SeatingLayout seatingLayout, ShowtimeReq movieReq) {
        Showtime showtime = new Showtime();
        showtime.setMovie(movie.getId());
        showtime.setTheatre(theatre.getId());
        showtime.setLayout(theatre.getId());
        showtime.setMaximumIntendedAttendance(movieReq.getMaximumIntendedAttendance());
        showtime.setSellUntilDateTime(movieReq.getSellUntilDateTime());
        showtime.setVisibilityDateTime(movieReq.getVisibilityDateTime());
        showtime.setShowDateTime(movieReq.getShowDateTime());
                showtime = showtimeRepository.save(showtime);
        return showtime;
    }

    public BusVehicle createBusVehicle(Supplier supplier, BusVehicleReq movieReq) {

        BusVehicle busVehicle = new BusVehicle();
        busVehicle.setPlateName(movieReq.getPlateName());
        busVehicle.setSupplier(supplier.getId());
        busVehicle.setCode(idGenService.busNo());

        return busVehicleRepository.save(busVehicle);
    }
}
