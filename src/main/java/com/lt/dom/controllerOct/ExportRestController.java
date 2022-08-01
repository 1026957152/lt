package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ExportResp;
import com.lt.dom.oct.Export;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Reservation;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.otcReq.ExportReq;
import com.lt.dom.repository.ExportRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.ExportServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class ExportRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExportRepository exportRepository;

    @Autowired
    private ExportServiceImpl exportService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;

    @GetMapping(value = "/exports", produces = "application/json")
    public ResponseEntity<Page<ExportResp>> pageExports(Pageable pageable) {

        Page<Export> validatorOptional = exportRepository.findAll(pageable);

        return ResponseEntity.ok(validatorOptional.map(x->ExportResp.from(x)));




    }


    @GetMapping(value = "exports/{EXPORT_ID}", produces = "application/json")
    public ResponseEntity<Export> getExports(@PathVariable long EXPORT_ID) {

        Optional<Export> validatorOptional = exportRepository.findById(EXPORT_ID);
        if(validatorOptional.isPresent()){
            try {
                return ResponseEntity.ok(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }



    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/exports", produces = "application/json")
    public ResponseEntity<ExportResp> createExport(@RequestBody ExportReq pojo) {

        Export export = exportService.createExport(pojo);

        return ResponseEntity.ok(ExportResp.from(export));



/*        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));*/


    }







}