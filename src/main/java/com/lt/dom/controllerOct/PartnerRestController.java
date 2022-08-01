package com.lt.dom.controllerOct;

import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Partner;
import com.lt.dom.oct.Product;
import com.lt.dom.repository.PartnerRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.PartnerServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class PartnerRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerServiceImpl partnerService;





    @GetMapping(value = "partners/{PARTNER_ID}/component_rights", produces = "application/json")
    public List<ComponentRight> listComponents(@PathVariable long PARTNER_ID) {


        Optional<Partner> optionalPartner = partnerRepository.findById(PARTNER_ID);

        if(optionalPartner.isPresent()){
            try {
                return partnerService.listComponentRights(optionalPartner.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));

    }

}