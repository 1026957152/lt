package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.CrossSellResp;
import com.lt.dom.OctResp.ProductBookingResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ExtraReq;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
//https://stripe.com/docs/payments/checkout/cross-sells#create-cross-sell
@Service
public class CrossSellServiceImpl {
    @Autowired
    private CrossSellRepository extraRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private AttributeRepository attributeRepository;


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;




    public CrossSell create(Product supplier, Product crossProduct) {


        CrossSellKey crossSellKey = new CrossSellKey();
        crossSellKey.setCrossSellProductId(crossProduct.getId());
        crossSellKey.setProductId(supplier.getId());

        CrossSell crossSell = new CrossSell() ;
        crossSell.setProduct(supplier);
        crossSell.setCrossSellProduct(crossProduct);
        crossSell.setId(crossSellKey);

      //  supplier.getCrossSells().add(crossSell);

        supplier.addCrossSellInList(crossSell);

        supplier = productRepository.save(supplier);
/*        fileStorageService.s(theatre.getCode(), EnumDocumentType.theatreImageIcon,theatreReq.getMedia().getHeroDesktopDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageStandard,theatreReq.getMedia().getHeroMobileDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageLarge,theatreReq.getMedia().getPosterLarge());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageThumbnail,
                theatreReq.getMedia().getHeroDesktopDynamic());*/



        return crossSell;


    }

    @Transactional
    public CrossSell update(CrossSell theatre, ExtraReq theatreReq) {


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


        theatre = extraRepository.save(theatre);


/*

*//*
        if(theatreReq.getMedia().getHeroMobileDynamic()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(), theatreReq.getMedia().getHeroMobileDynamic(),EnumDocumentType.theatreImageStandard);

        }
        if(theatreReq.getMedia().getHeroDesktopDynamic()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(), theatreReq.getMedia().getHeroDesktopDynamic(),EnumDocumentType.theatreImageIcon);

        }
        if(theatreReq.getMedia().getPosterLarge()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(), theatreReq.getMedia().getHeroDesktopDynamic(),EnumDocumentType.theatreImageLarge);

        }*//*
       if(theatreReq.getCover()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getCover(), EnumDocumentType.movie_cover);

        }
        if(theatreReq.getVideo()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getVideo(), EnumDocumentType.movie_vidio);

        }*/
        return theatre;
    }


    public void prepay_booking_page(Product product, ProductBookingResp productResp) {

        List<CrossSell> extras = extraRepository.findAll();
        productResp.setCrossSells(extras.stream().map(e->{
            CrossSellResp extraReq = CrossSellResp.from(e);
            return extraReq;
        }).collect(Collectors.toList()));
    }


}
