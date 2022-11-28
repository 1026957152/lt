package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.OctResp.TheatreReq;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.SeatingLayoutReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.SeatRepository;
import com.lt.dom.repository.SeatingLayoutRepository;
import com.lt.dom.repository.TheatreRepository;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CardServiceImpl {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private TheatreRepository theatreRepository;

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

    public SeatingLayout createSeatingLayout(Theatre theatre, SeatingLayoutReq theatreReq) {

        SeatingLayout seatingLayout = new SeatingLayout();
        seatingLayout.setCode(idGenService.seatingLayoutCode());
       // seatingLayout.setSeats(theatreReq.getSeats());

        seatingLayout.setRows_(theatreReq.getRows_());
        seatingLayout.setVersionNumber(theatreReq.getVersionNumber());
        seatingLayout.setTheatre(theatre.getId());
        seatingLayout = seatingLayoutRepository.save(seatingLayout);

        SeatingLayout finalSeatingLayout = seatingLayout;
        seatRepository.saveAll(theatreReq.getSeats().stream().map(e->{
            Seat seat = new Seat();
            seat.setSeatTier(e.getSeatTier());
            seat.setSeatingLayoutId(finalSeatingLayout.getId());
            seat.setRow_(e.getRow());
            seat.setColumn_(e.getColumn());
            seat.setType(e.getType());
            return seat;

        }).collect(Collectors.toList()));

        return seatingLayout;
    }



}
