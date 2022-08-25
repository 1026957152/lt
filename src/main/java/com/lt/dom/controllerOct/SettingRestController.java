package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.EmployeeResp;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.OctResp.EnumWithValueResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.EmployerUpdatePojo;
import com.lt.dom.otcReq.SettingReq;
import com.lt.dom.otcReq.SettingUpdateReq;
import com.lt.dom.otcenum.EnumRole;
import com.lt.dom.otcenum.EnumSettings;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.SettingRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.SettingServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import com.lt.dom.vo.SettingVo;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class SettingRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private SettingServiceImpl settingService;
    @Autowired
    private SettingRepository settingRepository;





    @GetMapping(value = "/settings", produces = "application/json")
    public EntityModel Page_getSettingList() {



        List<Setting> settings = settingRepository.findAll();
        EntityModel entityModel = EntityModel.of(Map.of(
                "setting",settingService.getSettingVo()

/*                "setting_list", settings.stream().map(x->{
            EnumWithValueResp enumResp = new EnumWithValueResp();
                    enumResp.setId(x.getName());
                    enumResp.setText(EnumSettings.valueOf(x.getName()).toString());
                    enumResp.setType(x.getValue_type().name());
            Object o = settingService.getValue(x);
                    enumResp.setValue(o!=null? o.toString():"");
            EntityModel entityModel1 = EntityModel.of(enumResp);
            entityModel1.add(linkTo(methodOn(SettingRestController.class).updateEmployee(x.getId(),null)).withRel("updateSetting"));
                 //   enumResp.setSelected(roleList.contains(x.getName()));
                    return entityModel1;
                }).collect(Collectors.toList())*/

                )
        );

     //   entityModel.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"));
     //   entityModel.add(linkTo(methodOn(SupplierRestController.class).pageEmployess(supplier.get().getId(),null)).withRel("getPageEmployees"));

        return entityModel;
    }




    @Operation(summary = "3、更新")
    @PutMapping(value = "/settings/{SETTING_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<Setting>> updateEmployee(@PathVariable long SETTING_ID, @RequestBody @Valid SettingUpdateReq employerPojo) {

        Optional<Setting> optional = settingRepository.findById(SETTING_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(SETTING_ID,"找不到设置");
        }

        Setting setting = optional.get();

        setting = settingService.update(setting,employerPojo);

        EntityModel entityModel = EntityModel.of(setting);
        return ResponseEntity.ok(entityModel);


    }


    @Operation(summary = "3、更新")
    @PutMapping(value = "/settings", produces = "application/json")
    public ResponseEntity<EntityModel<SettingVo>> updateSetting( @RequestBody @Valid SettingReq employerPojo) {



        SettingVo setting = settingService.update(employerPojo);

        EntityModel entityModel = EntityModel.of(setting);
        return ResponseEntity.ok(entityModel);


    }
}