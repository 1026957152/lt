package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.HomeSearchItemResp;
import com.lt.dom.OctResp.TheatreReq;
import com.lt.dom.OctResp.home.HomeSearcherReminderResp;
import com.lt.dom.controllerOct.AttractionRestController;
import com.lt.dom.controllerOct.SearchRestController;
import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Theatre;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumReminderSearchItemType;
import com.lt.dom.otcenum.EnumUrlSourceType;
import com.lt.dom.repository.SeatRepository;
import com.lt.dom.repository.SeatingLayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SearchReminderServiceImpl {
    @Autowired
    private SeatRepository seatRepository;

    private Map<String,HomeSearchItemResp> maps = new HashMap();

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private SeatingLayoutRepository seatingLayoutRepository;

/*

    public Theatre create(Supplier supplier, TheatreReq theatreReq) {
        Theatre theatre = new Theatre();
        theatre.setName(theatreReq.getName());

        theatre.setSupplier(supplier.getId());


        theatre.setName(supplier.getName());
        theatre.setCode(idGenService.theatreCode());
        theatre.setLongName(theatreReq.getLongName());
        theatre.setShortdesc(theatreReq.getShortdesc());
        theatre.setLongdesc(theatreReq.getLongdesc());
        theatre.setGuestServicesPhoneNumber(theatreReq.getGuestServicesPhoneNumber());
        theatre.setClosed(theatreReq.getClosed());
        theatre.setOutageDescription(theatreReq.getOutageDescription());

        theatre.setSlug(theatreReq.getSlug());

        Address location = new Address();
        location.setAddressLine1(theatreReq.getLocation().getAddress());
        location.setLatitude(theatreReq.getLocation().getLatitude());
        location.setLongitude(theatreReq.getLocation().getLongitude());


        theatre.setLocation(location);

        theatre.setRedemptionMethods_json(new Gson().toJson(theatreReq.getRedemptionMethods()));

        theatre = theatreRepository.save(theatre);
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageIcon,theatreReq.getMedia().getTheatreImageIcon());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageStandard,theatreReq.getMedia().getTheatreImageStandard());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageLarge,theatreReq.getMedia().getTheatreImageLarge());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageThumbnail,
                theatreReq.getMedia().getTheatreImageThumbnail());



        return theatre;


    }*/

    public Theatre update(Theatre supplier, TheatreReq theatreReq) {
        return supplier;
    }

    public Map createSeatingLayout(String theatre, long id) {

        HomeSearchItemResp homeSearchItemResp = new HomeSearchItemResp();
        homeSearchItemResp.setId(id);
        homeSearchItemResp.setLable(theatre);
        homeSearchItemResp.setType(EnumReminderSearchItemType.place);
        homeSearchItemResp.setPhoto(fileStorageService.loadDocumentWithDefault(EnumDocumentType.attraction_thumb,"e.getCode()"));

        maps.put(theatre,homeSearchItemResp);
        return maps;
    }
    public Map createText(String theatre) {

        HomeSearchItemResp homeSearchItemResp = new HomeSearchItemResp();

        homeSearchItemResp.setLable(theatre);
        homeSearchItemResp.setType(EnumReminderSearchItemType.text);
        homeSearchItemResp.setPhoto(fileStorageService.loadDocumentWithDefault(EnumDocumentType.attraction_thumb,"e.getCode()"));

        maps.put(theatre,homeSearchItemResp);
        return maps;
    }

    public void get(HomeSearcherReminderResp homeResp, String title) {






        homeResp.setRecentlySearched(maps.values().stream().map(e->{
            HomeSearchItemResp homeSearchItemResp =e;

            EntityModel attractionEntityModel = EntityModel.of(homeSearchItemResp);

            if(e.getType().equals(EnumReminderSearchItemType.place)){
                attractionEntityModel.add(linkTo(methodOn(AttractionRestController.class).getAttraction(e.getId(), EnumUrlSourceType.search)).withSelfRel());

            }
            if(e.getType().equals(EnumReminderSearchItemType.text)){
                attractionEntityModel.add(linkTo(methodOn(SearchRestController.class).searchProducts(null,EnumUrlSourceType.search,null,null)).withSelfRel().expand());

            }
            return attractionEntityModel;

        }).collect(Collectors.toList()));



    }
}
