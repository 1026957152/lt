package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BusStopReq;
import com.lt.dom.otcReq.RegionReq;
import com.lt.dom.otcReq.ThirdPartyProductReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumThirdParty;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThirdPartyServiceImpl {
    @Autowired
    private ThirdPartyProductRepository thirdPartyProductRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private IdGenServiceImpl idGenService;


    @Transient
    public ThirdParty create(Supplier supplier, RegionReq theatreReq) {
        ThirdParty theatre = new ThirdParty();

        theatre.setSupplier(supplier.getId());


        theatre.setName(theatreReq.getName());
        theatre.setCode(idGenService.nextId("thpa"));



        theatre = thirdPartyRepository.save(theatre);


        if(theatreReq.getPhoto().getCode()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),theatreReq.getPhoto(), EnumDocumentType.region_photo);

        }


        return theatre;


    }

    @Transient
    public ThirdParty update(ThirdParty theatre, RegionReq tripReq) {


        theatre.setName(tripReq.getName());

        theatre.setDescription(tripReq.getDescription().getText());
        theatre.setStatus(tripReq.getStatus());
        theatre.setBaseURL(tripReq.getBaseURL());
        theatre.setPlatform(tripReq.getPlatform());
        theatre.setPartner_id(tripReq.getPartner_id());
        theatre.setAuthorization_code(tripReq.getAuthorization_code());

        theatre = thirdPartyRepository.save(theatre);


        if(tripReq.getPhoto()!=null && tripReq.getPhoto().getCode()!=null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),tripReq.getPhoto(), EnumDocumentType.region_photo);

        }

        return theatre;
    }


    public ThirdParty createThirdPartyProduct(ThirdParty busRoute, List<BusStopReq> theatreReq) {


        List<Product> busStopList = productRepository.findAllById(theatreReq.stream().map(e->e.getId()).collect(Collectors.toList()));

        ThirdParty finalBusRoute = busRoute;
        busStopList.stream().forEach(e->{

            ThirdPartyProduct stopRegistration = new ThirdPartyProduct();
            stopRegistration.setThirdParty(finalBusRoute);
            stopRegistration.setProduct(e);

            stopRegistration.setId(new ThirdPartyProductKey(finalBusRoute.getId(), e.getId()));
            finalBusRoute.addProductInList(stopRegistration);

        });


        busRoute = thirdPartyRepository.save(finalBusRoute);


        return busRoute;


    }


    public Optional<Product> find(EnumThirdParty ts, Integer item_id) {

        ThirdParty thirdParty = thirdPartyRepository.findById(1l).get();
        ThirdPartyProductKey thirdPartyProductKey = new ThirdPartyProductKey(thirdParty.getId(),item_id);
        Optional<ThirdPartyProduct> optionalProduct = thirdPartyProductRepository.findById(thirdPartyProductKey);


        if(optionalProduct.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(optionalProduct.get().getProduct());

    }
}
