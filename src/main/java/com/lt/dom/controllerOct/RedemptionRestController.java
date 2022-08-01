package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.SummarySupplierRedemptionResp;
import com.lt.dom.oct.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.RedemptionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class RedemptionRestController {



    @Autowired
    private RedemptionServiceImpl redemptionService;

    @Autowired
    private RedemptionRepository redemptionRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;


    @PostMapping(value = "/redemptions/{REDEMPTION_ID}/rollback", produces = "application/json")
    public List<Redemption> redemption(@PathVariable long REDEMPTION_ID) {


        Optional<Redemption> validatorOptional = redemptionRepository.findById(REDEMPTION_ID);

        if(validatorOptional.isPresent()){
            try {
                return redemptionService.rollback(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }





    @Operation(summary = "3、list所有员工")
    @GetMapping(value = "/{SUPPLIER_ID}/redemption_entries", produces = "application/json")
    public ResponseEntity<SummarySupplierRedemptionResp> getRedemptionEntry(@PathVariable long SUPPLIER_ID) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(SUPPLIER_ID);

        if(optionalSupplier.isPresent()){

            List<RedemptionEntry> clainQuotas = redemptionEntryRepository.findAllBySupplier(optionalSupplier.get().getId());



            SummarySupplierRedemptionResp summarySupplierRedemptionResp = null;//SummarySupplierRedemptionResp.from(Pair.with(optionalSupplier.get(),clainQuotas));
            return ResponseEntity.ok(summarySupplierRedemptionResp);
        }

        throw new RuntimeException();
    }
















}