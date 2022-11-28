package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumMediaType;
import com.lt.dom.repository.CollectionItemRepository;
import com.lt.dom.repository.ExhibitionRepository;
import com.lt.dom.repository.MuseumRepository;
import com.lt.dom.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;

@Service
public class RegionServiceImpl {
    @Autowired
    private RegionRepository regionRepository;
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


    @Transient
    public Region create(Supplier supplier, RegionReq theatreReq) {
        Region theatre = new Region();

        theatre.setSupplier(supplier.getId());


        theatre.setName(theatreReq.getName());
        theatre.setCode(idGenService.nextId("regi"));



        theatre = regionRepository.save(theatre);


        if(theatreReq.getPhoto().getCode()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getPhoto(), EnumDocumentType.region_photo);

        }


        return theatre;


    }

    @Transient
    public Region update(Region theatre, RegionReq tripReq) {


        theatre.setName(tripReq.getName());

        theatre.setDescription(tripReq.getDescription().getText());

        theatre = regionRepository.save(theatre);


        if(tripReq.getPhoto()!=null && tripReq.getPhoto().getCode()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),tripReq.getPhoto(), EnumDocumentType.region_photo);

        }

        return theatre;
    }



}
