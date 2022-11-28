package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.MovieEdit;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttributeEditReq;
import com.lt.dom.otcReq.MovieReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.AttributeRepository;
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
public class MovieServiceImpl {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AttributeRepository attributeRepository;


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;




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
    public Movie update(Movie theatre, MovieEdit theatreReq) {


        theatre.setName(theatreReq.getName());
        theatre.setName_long(theatreReq.getName_long());
        theatre.setDesc_long(theatreReq.getDesc_long());
        theatre.setDesc_short(theatreReq.getDesc_short());

        theatre.setRunTime(theatreReq.getRunTime());
        theatre.setTheatre(theatreReq.getTheatre());

        theatre.setEarliestShowingUtc(theatreReq.getEarliestShowingUtc());
        theatre.setReleaseDateUtc(theatreReq.getReleaseDateUtc());
        theatre.setSortableName(theatreReq.getSortableName());
    //    theatre.setDirectors(theatreReq.getDirectors().toString());
     //   theatre.setStarringActors(theatreReq.getStarringActors().toString());
        theatre.setSynopsis(theatreReq.getSynopsis());

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


        theatre = movieRepository.save(theatre);

/*        starringActorRespList.stream().forEach(e->{

            System.out.println(e.getUuid() );
            System.out.println(e.getPhoto().getCode() );
            System.out.println(e.getName() );
            if(e.getPhoto()!= null){
                fileStorageService.saveFromTempDocumentCode(e.getUuid(), EnumDocumentType.movie_star_photo,e.getPhoto().getCode());

            }

        });*/



/*
        if(theatreReq.getMedia().getHeroMobileDynamic()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(), theatreReq.getMedia().getHeroMobileDynamic(),EnumDocumentType.theatreImageStandard);

        }
        if(theatreReq.getMedia().getHeroDesktopDynamic()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(), theatreReq.getMedia().getHeroDesktopDynamic(),EnumDocumentType.theatreImageIcon);

        }
        if(theatreReq.getMedia().getPosterLarge()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(), theatreReq.getMedia().getHeroDesktopDynamic(),EnumDocumentType.theatreImageLarge);

        }*/
       if(theatreReq.getCover()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getCover(), EnumDocumentType.movie_cover);

        }
        if(theatreReq.getVideo()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getVideo(), EnumDocumentType.movie_vidio);

        }
        return theatre;
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
}
