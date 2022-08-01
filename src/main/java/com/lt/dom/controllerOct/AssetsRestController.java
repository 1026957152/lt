package com.lt.dom.controllerOct;


import org.json.JSONObject;
import com.lt.dom.oct.Voucher;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class AssetsRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VonchorServiceImpl vonchorService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @GetMapping(value = "assets/qr/{QR_CODE}", produces = "application/json")
    public ResponseEntity<String> listAvailability(@PathVariable long QR_CODE) throws JOSEException {




        ExampleMatcher modelMatcher = ExampleMatcher.matching()
              //  .withIgnorePaths("id")
                .withIgnorePaths("campaign")
                .withIgnorePaths("active")
                .withIgnorePaths("code");

        //   .withMatcher("model", ignoreCase());

        Voucher probe = new Voucher();
     //   probe.setCode(QR_CODE);
        probe.setId(QR_CODE);
        Example<Voucher> example = Example.of(probe,modelMatcher);


        Optional<Voucher> optionalVoucher = voucherRepository.findOne(example);


        System.out.println(JSONObject.valueToString(optionalVoucher.get()));

        if(optionalVoucher.isPresent()){
            // Create an HMAC-protected JWS object with some payload
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                    new Payload(JSONObject.valueToString(optionalVoucher.get())));

// We need a 256-bit key for HS256 which must be pre-shared
            byte[] sharedKey = new byte[32];
            new SecureRandom().nextBytes(sharedKey);

// Apply the HMAC to the JWS object
            jwsObject.sign(new MACSigner(sharedKey));
// Output in URL-safe format
            System.out.println(jwsObject.serialize());



            return ResponseEntity.ok(jwsObject.serialize());
        }



        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));




    }

}