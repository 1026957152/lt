package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.MediaGenralEditResp;
import com.lt.dom.otcReq.MediaGenralReq;
import com.lt.dom.repository.MediaRepository;
import com.lt.dom.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaServiceImpl {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;



    public Media create(Supplier supplier, MediaGenralReq theatreReq) {
        Media theatre = new Media();
        theatre.setTitle(theatreReq.getTitle());

        theatre.setSupplier(supplier.getId());


        theatre.setDescription_lang(theatreReq.getDescription_lang());
        theatre.setCode(idGenService.theatreCode());
        theatre.setDescription_text(theatreReq.getDescription_text());
        theatre.setHref(theatreReq.getHref());
        theatre.setPreview_image_url(theatreReq.getPreview_image_url());
        theatre.setMediaType(theatreReq.getMediaType());


        theatre = mediaRepository.save(theatre);




        return theatre;


    }

    public Media update(Media supplier, MediaGenralEditResp theatreReq) {

        supplier.setDescription_text(theatreReq.getDescription_text());
        supplier.setPreview_image_url(theatreReq.getPreview_image_url());
        supplier.setHref(theatreReq.getHref());
        supplier.setTitle(theatreReq.getTitle());

        supplier = mediaRepository.save(supplier);
        return supplier;
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

    public List<Media> fromValueList(List<Long> collect) {


        List<Media> componentRightList = mediaRepository.findAllById(collect);
        return componentRightList;

    }
}
