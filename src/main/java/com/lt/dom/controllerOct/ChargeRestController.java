package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ReservationTourResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.No_voucher_suitable_for_publicationException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.repository.ChargeRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.PaymentServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class ChargeRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/charges/{CHARGE_ID}/refunds", produces = "application/json")
    public ResponseEntity RefundCharge(@PathVariable long CHARGE_ID,@RequestBody @Valid BookingPojo pojo) {


        Optional<Charge> optionalProduct = chargeRepository.findById(CHARGE_ID);

        if(optionalProduct.isPresent()){


            Charge campaigns = paymentService.refundCharge(optionalProduct.get());

            return ResponseEntity.ok(campaigns);
        }else{
            System.out.println("找不到产品");
            throw new BookNotFoundException(pojo.getProductId(),"找不到产品");
        }


    }


}