package com.lt.dom.controllerOct;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ClainQuotaPojo;
import com.lt.dom.otcReq.EquipmentPojo;
import com.lt.dom.repository.EquipmentRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.EquipmentServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class EquipmentRestController {


    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private VonchorServiceImpl vonchorService;
    @Autowired
    private EquipmentServiceImpl equipmentService;



    @Operation(summary = "2、新建设备")
    @PostMapping(value = "supplier/{SUPPLIER_ID}/equipments", produces = "application/json")
    public Equipment createEquipment(@PathVariable long SUPPLIER_ID, @RequestBody EquipmentPojo pojo) {


        Optional<Supplier> compaignOptional = supplierRepository.findById(SUPPLIER_ID);
        if(compaignOptional.isPresent()){
            Equipment componentRight = equipmentService.create(compaignOptional.get(),pojo);

            return componentRight;
        }

        throw  new RuntimeException();
    }

}