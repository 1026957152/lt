package com.lt.dom.serviceOtc;


import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BusStopReq;
import com.lt.dom.otcReq.RegionReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumThirdParty;
import com.lt.dom.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThirdPartyServiceImpl {

    Logger logger = LoggerFactory.getLogger(ThirdPartyServiceImpl.class);

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

    public List<Product> findAll(EnumThirdParty ts, String key_word, PageRequest of) {


        logger.info("第三方{}对接,按照关键字 {} 请求产品列表",ts,key_word);

        ThirdParty thirdParty = thirdPartyRepository.findById(1l).get();


        List<Long> list = thirdParty.getProducts().stream().map(e->
                e.getProduct().getId()).collect(Collectors.toList());
        logger.info("第三方{}对接,目前产品有 ids {}",ts,list);


        Page<Product> optionalProduct = productRepository.findAllByIdIn(list,of);


        return optionalProduct.getContent();

    }

    public List<Product>  findAll(EnumThirdParty ts, long longValue) {



        logger.error("第三方{}对接,按照ID{} 请求产品列表",ts,longValue);
        ThirdParty thirdParty = thirdPartyRepository.findById(1l).get();



        List<Long> list = thirdParty.getProducts().stream().map(e->
                e.getProduct().getId()).collect(Collectors.toList())
                .stream().filter(e->e == longValue).collect(Collectors.toList());

        List<Product> optionalProduct = productRepository.findAllById(list);

        return optionalProduct;
    }
}
