package com.lt.dom.serviceOtc;



import com.lt.dom.OctResp.MovieEdit;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttributeEditReq;
import com.lt.dom.otcReq.MovieReq;
import com.lt.dom.otcReq.ShippingCardAddressReq;
import com.lt.dom.otcReq.ShippingCardAddressReq_;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.AttributeRepository;
import com.lt.dom.repository.MovieRepository;
import com.lt.dom.repository.ShippingAddressRepository;
import com.lt.dom.repository.ShowtimeRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private AttributeRepository attributeRepository;


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;



    public ShippingCardAddress createUserAddress(User supplier, ShippingCardAddressReq_ theatreReq) {
        ShippingCardAddress theatre = new ShippingCardAddress();


        theatre.setCity(theatreReq.getPrefecture());
        theatre.setState(theatreReq.getProvince());
        theatre.setCounty(theatreReq.getCounty());
        theatre.setTown(theatreReq.getTown());


        theatre.setPostal_code(theatreReq.getPostal_code());
        theatre.setMobile(theatreReq.getPhone());
        theatre.setFirst_name(theatreReq.getName());
        theatre.setAddress_line2(theatreReq.getAddress_line2());
        theatre.setAddress_line1(theatreReq.getAddress_line1());


        if(ObjectUtils.isNotEmpty(theatreReq.getDefault_()) ){

            if(theatreReq.getDefault_()){
                List<ShippingCardAddress> shippingCardAddresses = shippingAddressRepository.findAllByUser(supplier.getId());
                shippingAddressRepository.saveAll(shippingCardAddresses.stream().filter(e->e.getDefault_()).map(e->{
                    e.setDefault_(false);
                    return e;
                }).collect(Collectors.toList()));
            }


            theatre.setDefault_(theatreReq.getDefault_());
        }else{
            theatre.setDefault_(false);
        }





        theatre.setUser(supplier.getId());



        theatre.setCode(idGenService.nextId("addr_"));



        theatre = shippingAddressRepository.save(theatre);

/*        fileStorageService.s(theatre.getCode(), EnumDocumentType.theatreImageIcon,theatreReq.getMedia().getHeroDesktopDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageStandard,theatreReq.getMedia().getHeroMobileDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageLarge,theatreReq.getMedia().getPosterLarge());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageThumbnail,
                theatreReq.getMedia().getHeroDesktopDynamic());*/



        return theatre;


    }


    public ShippingCardAddress create(Supplier supplier, ShippingCardAddressReq_ theatreReq) {
        ShippingCardAddress theatre = new ShippingCardAddress();

        theatre.setCity(theatreReq.getPrefecture());
        theatre.setState(theatreReq.getProvince());
        theatre.setCounty(theatreReq.getCounty());
        theatre.setTown(theatreReq.getTown());


        theatre.setPostal_code(theatreReq.getPostal_code());
        theatre.setMobile(theatreReq.getPhone());
        theatre.setFirst_name(theatreReq.getName());
        theatre.setAddress_line2(theatreReq.getAddress_line2());

        theatre.setAddress_line1(theatreReq.getAddress_line1());


        theatre.setSupplier(supplier.getId());



        theatre.setCode(idGenService.nextId("addr_"));



        theatre = shippingAddressRepository.save(theatre);

/*        fileStorageService.s(theatre.getCode(), EnumDocumentType.theatreImageIcon,theatreReq.getMedia().getHeroDesktopDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageStandard,theatreReq.getMedia().getHeroMobileDynamic());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageLarge,theatreReq.getMedia().getPosterLarge());
        fileStorageService.saveFromTempDocumentCode(theatre.getCode(), EnumDocumentType.theatreImageThumbnail,
                theatreReq.getMedia().getHeroDesktopDynamic());*/



        return theatre;


    }

    @Transactional
    public ShippingCardAddress update(ShippingCardAddress theatre, ShippingCardAddressReq_ theatreReq) {


        theatre.setCity(theatreReq.getPrefecture());
        theatre.setState(theatreReq.getProvince());
        theatre.setCounty(theatreReq.getCounty());
        theatre.setTown(theatreReq.getTown());


        theatre.setPostal_code(theatreReq.getPostal_code());
        theatre.setMobile(theatreReq.getPhone());
        theatre.setFirst_name(theatreReq.getName());
        theatre.setAddress_line2(theatreReq.getAddress_line2());

        theatre.setAddress_line1(theatreReq.getAddress_line1());



        if(ObjectUtils.isNotEmpty(theatreReq.getDefault_()) ){

            if(theatreReq.getDefault_()){
                List<ShippingCardAddress> shippingCardAddresses = shippingAddressRepository.findAllByUser(theatre.getUser());
                shippingAddressRepository.saveAll(shippingCardAddresses.stream().filter(e->e.getDefault_()).map(e->{
                    e.setDefault_(false);
                    return e;
                }).collect(Collectors.toList()));
            }


            theatre.setDefault_(theatreReq.getDefault_());
        }else{
            theatre.setDefault_(false);
        }


        theatre.setDefault_(theatreReq.getDefault_());


        theatre = shippingAddressRepository.save(theatre);

        return theatre;
    }


    @Transactional
    public ShippingCardAddress setDefault(ShippingCardAddress theatre) {





            if(!theatre.getDefault_()){
                List<ShippingCardAddress> shippingCardAddresses = shippingAddressRepository.findAllByUser(theatre.getUser());
                shippingAddressRepository.saveAll(shippingCardAddresses.stream().filter(e->e.getDefault_()).map(e->{
                    e.setDefault_(false);
                    return e;
                }).collect(Collectors.toList()));
                theatre.setDefault_(true);
                theatre = shippingAddressRepository.save(theatre);
            }









        return theatre;
    }

}
