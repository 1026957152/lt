package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.GuideInchargeBookingResp;
import com.lt.dom.oct.GuideInchargeBooking;
import com.lt.dom.oct.Product;
import com.lt.dom.repository.InchargeBookingRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class GuideRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private InchargeBookingRepository inchargeBookingRepository;
    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @GetMapping(value = "guides/{GUIDE_ID}/incharge_bookings", produces = "application/json")
    public Page<GuideInchargeBookingResp> listAvailability(@PathVariable long GUIDE_ID) {

        Page<GuideInchargeBooking> validatorOptional = inchargeBookingRepository.findAllByGuideId(GUIDE_ID,PageRequest.of(0,1));

        return validatorOptional.map(x->{
            return GuideInchargeBookingResp.from(x);
        });





    }




}