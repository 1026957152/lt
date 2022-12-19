package com.lt.dom.controllerOct;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.EquipmentPojo;
import com.lt.dom.repository.EquipmentRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.serviceOtc.EquipmentServiceImpl;
import com.lt.dom.serviceOtc.FulfillServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class EquipmentRestController {


    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private FulfillServiceImpl vonchorService;
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