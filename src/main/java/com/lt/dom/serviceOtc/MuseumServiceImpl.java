package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ArtworkReq;
import com.lt.dom.otcReq.ExhibitionReq;
import com.lt.dom.otcReq.MediaGenralReq;
import com.lt.dom.otcReq.MuseumReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumMediaType;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.repository.CollectionItemRepository;
import com.lt.dom.repository.ExhibitionRepository;
import com.lt.dom.repository.MuseumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MuseumServiceImpl {
    @Autowired
    private MuseumRepository museumRepository;
    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private CollectionItemRepository collectionItemRepository;
    @Autowired
    private MediaServiceImpl mediaService;


    @Autowired
    private IdGenServiceImpl idGenService;



    public Museum create(Supplier supplier, MuseumReq theatreReq) {
        Museum theatre = new Museum();
       // theatre.setName(theatreReq.getName());

        theatre.setSupplier(supplier.getId());


        theatre.setName(theatreReq.getName());
        theatre.setCode(idGenService.tripCode());
        theatre.setDesc_long(theatreReq.getDesc_long());
        theatre.setDesc_short(theatreReq.getDesc_short());
        theatre.setSlug(theatreReq.getSlug());
        theatre.setGuestServicesPhoneNumber(theatreReq.getGuestServicesPhoneNumber());
        theatre.setOpen_time(theatreReq.getOpen_time());
        theatre.setClose_time(theatreReq.getClose_time());
/*
        theatre.setDay_count(theatreReq.getDay_count());
        theatre.setEnds_on(theatreReq.getEnds_on());
        theatre.setStarts_on(theatreReq.getStarts_on());
*/


        theatre = museumRepository.save(theatre);


        if(theatreReq.getMedia().getIcon()!=null){
            fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.museum_icon,theatreReq.getMedia().getIcon());

        }
        if(theatreReq.getMedia().getIcon()!=null){
            fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.museum_large,theatreReq.getMedia().getLarge());
        }
        if(theatreReq.getMedia().getIcon()!=null){
            fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.museum_standard,theatreReq.getMedia().getStandard());
        }
        if(theatreReq.getMedia().getIcon()!=null){
            fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.museum_thumbnail,theatreReq.getMedia().getThumbnail());
        }

        return theatre;


    }

    public Museum update(Museum theatre, MuseumReq theatreReq) {





        theatre.setName(theatreReq.getName());

        theatre.setDesc_long(theatreReq.getDesc_long());
        theatre.setDesc_short(theatreReq.getDesc_short());
        theatre.setSlug(theatreReq.getSlug());
        theatre.setGuestServicesPhoneNumber(theatreReq.getGuestServicesPhoneNumber());
        theatre.setOpen_time(theatreReq.getOpen_time());
        theatre.setClose_time(theatreReq.getClose_time());


        Address address =Address.from(theatreReq.getLocation());




        theatre.setLocation(address);
        theatre = museumRepository.save(theatre);



        if(theatreReq.getCover()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(), theatreReq.getCover(),EnumDocumentType.museum_cover);
        }


        return theatre;
    }




    public Artwork createArtwork(Supplier supplier, ArtworkReq artworkReq) {
        Artwork showtime = new Artwork();
        showtime.setName(artworkReq.getName());
        showtime.setCode(idGenService.openidNo());
        showtime.setDescription(artworkReq.getDescription());
        showtime.setName_long(artworkReq.getName_long());
        showtime.setDesc_long(artworkReq.getDesc_long());
        showtime.setDesc_short(artworkReq.getDesc_short());


/*        showtime.setSupplier(supplier.getId());
        showtime.setSlug(placeReq.getSlug());
        showtime.setTitle(placeReq.getTitle());
        showtime.setDesc_long(placeReq.getDesc_long());
        showtime.setPrivacyLevel(placeReq.getPrivacyLevel());
        showtime.setDesc_short(placeReq.getDesc_short());*/


        showtime = collectionItemRepository.save(showtime);

        if(artworkReq.getMedia().getIcon()!=null){
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
/*        if(artworkReq.getMedia().getAudio()!=null){
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



    public Artwork updateArtwork(Artwork showtime, ArtworkReq artworkReq) {

        if(artworkReq.getName()!= null){
            showtime.setName(artworkReq.getName());

        }
        if(artworkReq.getDescription()!= null){
            showtime.setDescription(artworkReq.getDescription());


        }
        if(artworkReq.getName_long()!= null){
            showtime.setName_long(artworkReq.getName_long());
        }
        if(artworkReq.getDesc_long()!= null){
            showtime.setDesc_long(artworkReq.getDesc_long());
        }
        if(artworkReq.getIntro()!= null){
            showtime.setDesc_long(artworkReq.getIntro());
        }

        if(artworkReq.getPrivacyLevel()!= null){
            showtime.setPrivacyLevel(artworkReq.getPrivacyLevel());
        }

        showtime = collectionItemRepository.save(showtime);

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
        }*/
        if(artworkReq.getMedia().getIntroductionAudio()!=null){
            fileStorageService.updateFromTempDocument(showtime.getCode(), artworkReq.getMedia().getIntroductionAudio(),EnumDocumentType.artwork_audio);


/*            MediaGenralReq mediaGenralReq = new MediaGenralReq();
            mediaGenralReq.setDescription_lang("");
            mediaGenralReq.setDescription_text(artworkReq.getDescription());
            mediaGenralReq.setHref(FileStorageServiceImpl.url(tempDocument));
            mediaGenralReq.setPreview_image_url(FileStorageServiceImpl.url(tempDocument));
            mediaGenralReq.setMediaType(EnumMediaType.audio);
            mediaService.create(supplier,mediaGenralReq);*/

        }

        if(artworkReq.getMedia().getPortrait()!=null){
            fileStorageService.updateFromTempDocument(showtime.getCode(), artworkReq.getMedia().getPortrait(),EnumDocumentType.artwork_portrait);


        }

/*

        if(cityWalkReq.getIntroductionAudio()!= null){
            fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.city_walk_audio,cityWalkReq.getIntroductionAudio().getCode());

        }
*/

        return showtime;
    }


    public Exhibition createExhibit(Supplier supplier, ExhibitionReq theatreReq) {
        Exhibition exhibition = new Exhibition();
        exhibition.setCode(idGenService.openidNo());
        exhibition.setSupplier(supplier.getId());
        exhibition.setSlug(theatreReq.getSlug());
        exhibition.setTitle(theatreReq.getTitle());
        exhibition.setDesc_long(theatreReq.getDesc_long());
        exhibition.setPrivacyLevel(theatreReq.getPrivacyLevel());
        exhibition.setDesc_short(theatreReq.getDesc_short());
        exhibition.setOpening_date(theatreReq.getOpening_date());
        exhibition.setOrganizer(theatreReq.getOrganizer());

                exhibition = exhibitionRepository.save(exhibition);



        if(theatreReq.getMedia().getIcon()!=null){
            fileStorageService.saveFromTempDocumentCode(exhibition.getCode(), EnumDocumentType.museum_icon,theatreReq.getMedia().getIcon());

        }
        if(theatreReq.getMedia().getLarge()!=null){
            fileStorageService.saveFromTempDocumentCode(exhibition.getCode(), EnumDocumentType.museum_large,theatreReq.getMedia().getLarge());
        }
        if(theatreReq.getMedia().getStandard()!=null){
            fileStorageService.saveFromTempDocumentCode(exhibition.getCode(), EnumDocumentType.museum_standard,theatreReq.getMedia().getStandard());
        }
        if(theatreReq.getMedia().getThumbnail()!=null){
            fileStorageService.saveFromTempDocumentCode(exhibition.getCode(), EnumDocumentType.museum_thumbnail,theatreReq.getMedia().getThumbnail());
        }

        return exhibition;
    }
}
