package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.MovieReq;
import com.lt.dom.otcReq.PlaceReq;
import com.lt.dom.otcReq.TripReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.PlaceRepository;
import com.lt.dom.repository.ShowtimeRepository;
import com.lt.dom.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private TripRepository tripRepository;


    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private FileStorageServiceImpl fileStorageService;


    public Trip create(Supplier supplier, TripReq theatreReq) {
        Trip theatre = new Trip();
        theatre.setName(theatreReq.getName());

        theatre.setSupplier(supplier.getId());


        theatre.setName(supplier.getName());
        theatre.setCode(idGenService.tripCode());
        theatre.setDay_count(theatreReq.getDay_count());
        theatre.setEnds_on(theatreReq.getEnds_on());
        theatre.setStarts_on(theatreReq.getStarts_on());


        theatre = tripRepository.save(theatre);




        return theatre;


    }

    public Trip update(Trip supplier, TripReq tripReq) {
        return supplier;
    }


    public Place createPlace(Supplier supplier, PlaceReq placeReq) {
        Place showtime = new Place();

        showtime.setName(placeReq.getName());
        showtime.setSupplier(supplier.getId());
        showtime.setDescription_text(placeReq.getDescription().getText());
       // showtime.setLng(placeReq.getLng());
       // showtime.setLng(placeReq.getLng());

        if(placeReq.getCategories() != null){
            showtime.setCategories_json(new Gson().toJson(placeReq.getCategories()));

        }
     //   showtime.setDuration_estimate(placeReq.getDuration_estimate());
        showtime.setHas_shape_geometry(placeReq.getHas_shape_geometry());
        showtime.setLevel(placeReq.getLevel());
        showtime.setName_local(placeReq.getName_local());
        showtime.setName_suffix(placeReq.getName_suffix());
        showtime.setType(placeReq.getType());
        showtime.setCode(idGenService.nextId("plac_"));

//        showtime.setMovie(movie.getId());
//        showtime.setTheatre(theatre.getId());
//        showtime.setLayout(theatre.getId());
//        showtime.setMaximumIntendedAttendance(movieReq.getMaximumIntendedAttendance());
//        showtime.setSellUntilDateTime(movieReq.getSellUntilDateTime());
//        showtime.setVisibilityDateTime(movieReq.getVisibilityDateTime());
//        showtime.setShowDateTime(movieReq.getShowDateTime());
        showtime = placeRepository.save(showtime);

        if(placeReq.getPhoto()!=null){

            fileStorageService.updateFromTempDocument(showtime.getCode(),placeReq.getPhoto(), EnumDocumentType.place_photo);

        }
        return showtime;
    }




    public Place editPlace(Place showtime, PlaceReq placeReq) {

        showtime.setName(placeReq.getName());
        showtime.setDescription_text(placeReq.getDescription().getText());
        showtime.setLng(placeReq.getLng());
        showtime.setLng(placeReq.getLng());

        showtime.setCategories_json(new Gson().toJson(placeReq.getCategories()));
        showtime.setDuration_estimate(placeReq.getDuration_estimate());
        showtime.setHas_shape_geometry(placeReq.getHas_shape_geometry());
        showtime.setLevel(placeReq.getLevel());
        showtime.setName_local(placeReq.getName_local());
        showtime.setName_suffix(placeReq.getName_suffix());

        showtime.setType(placeReq.getType());
        showtime.setType_reference(placeReq.getType_reference());

        showtime = placeRepository.save(showtime);

        if(placeReq.getPhoto()!=null){

            fileStorageService.updateFromTempDocument(showtime.getCode(),placeReq.getPhoto(), EnumDocumentType.place_photo);

        }

        return showtime;
    }
}
