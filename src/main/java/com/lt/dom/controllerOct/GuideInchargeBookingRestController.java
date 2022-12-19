package com.lt.dom.controllerOct;

import com.lt.dom.oct.GuideInchargeBooking;
import com.lt.dom.repository.InchargeBookingRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oct")
public class GuideInchargeBookingRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private InchargeBookingRepository inchargeBookingRepository;
    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @GetMapping(value = "incharge_bookings", produces = "application/json")
    public Page<GuideInchargeBooking> listAvailability(@PathVariable long PRODUCT_ID, Pageable pageable) {


        Page<GuideInchargeBooking> validatorOptional = inchargeBookingRepository.findAll(pageable);



        return validatorOptional;


    }

}