package com.lt.dom.controllerOct;

import com.lt.dom.oct.Product;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.FulfillServiceImpl;
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
public class SystemRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private FulfillServiceImpl vonchorService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @GetMapping(value = "version", produces = "application/json")
    public List<Calendar> version(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isPresent()){
            try {
                return availabilityService.listAvailability(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }

    @GetMapping(value = "health", produces = "application/json")
    public List<Calendar> health(@PathVariable long PRODUCT_ID) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isPresent()){
            try {
                return availabilityService.listAvailability(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }

}