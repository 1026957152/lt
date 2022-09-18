package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttractionReq;
import com.lt.dom.otcenum.EnumProductStatus;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AttractionServiceImpl {
    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    ApplicationEventPublisher eventPublisher;


    @Resource
    private AttractionRepository attractionRepository;



    public Attraction create(Supplier supplier, AttractionReq pojo) {

        Attraction attraction = new Attraction();
        attraction.setSupplier(supplier.getId());
        attraction.setCode(idGenService.attractionCode());
        attraction.setName(pojo.getName());
        attraction.setLongdesc(pojo.getLongdesc());
        attraction.setShortdesc(pojo.getShortdesc());
        attraction.setVideo(pojo.getVideo());
        attraction.setStatus(EnumProductStatus.draft);
      //  attraction.setThumbnail_image(pojo.getVideo_url());
        attraction = attractionRepository.save(attraction);

        return attraction;
    }
}
