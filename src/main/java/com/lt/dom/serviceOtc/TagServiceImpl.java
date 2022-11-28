package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.oct.Place;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.Tag;
import com.lt.dom.otcReq.PlaceReq;
import com.lt.dom.otcReq.TagReq;
import com.lt.dom.otcenum.EnumTagType;
import com.lt.dom.repository.PlaceRepository;
import com.lt.dom.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private TagRepository tagRepository;


    @Autowired
    private IdGenServiceImpl idGenService;



    public Tag create(Supplier supplier, TagReq theatreReq) {
        Tag theatre = new Tag();
        theatre.setName("");

        theatre.setSupplier(supplier.getId());



        theatre.setCode(idGenService.tripCode());
        theatre.setType(EnumTagType.Trips);
        theatre.setIcon(theatreReq.getIcon());
        theatre.setColor("");
        theatre.setValue(theatreReq.getText());
        theatre.setName(theatreReq.getName());
        theatre.setCode(idGenService.nextId(""));


        theatre = tagRepository.save(theatre);




        return theatre;


    }

    public Tag update(Tag supplier, TagReq tripReq) {

        supplier.setIcon(tripReq.getIcon());
        supplier.setName(tripReq.getName());
        supplier.setValue(tripReq.getText());
        supplier.setIcon(tripReq.getIcon());
        tagRepository.save(supplier);

        return supplier;
    }


    public Place createPlace(Tag supplier, PlaceReq placeReq) {
        Place showtime = new Place();

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




                showtime = placeRepository.save(showtime);
        return showtime;
    }
}
