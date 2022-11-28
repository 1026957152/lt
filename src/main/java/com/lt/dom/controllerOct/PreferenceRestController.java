package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.UserProfileResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PrefereneSettingUpdateReqVo;

import com.lt.dom.otcReq.PrefereneVo;
import com.lt.dom.otcenum.EnumPreference;
import com.lt.dom.otcenum.EnumPreferenceSpace;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.PreferenceServiceImpl;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class PreferenceRestController {

    @Autowired
    private PreferenceServiceImpl preferenceservice;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private UserRepository userRepository;





    @GetMapping(value = "/users/{USER_ID}/preferences", produces = "application/json")
    public EntityModel Page_getSettingList(@PathVariable long USER_ID) {
        Optional<User> optionalSupplier = userRepository.findById(USER_ID);
        if(optionalSupplier.isEmpty()) {
            throw new BookNotFoundException(USER_ID,"找不到供应商");

        }
        User supplier = optionalSupplier.get();





        List<Preference> preferences = preferenceRepository.findAllByUser(supplier.getId());
        EntityModel entityModel = EntityModel.of(Map.of(
                        "setting",preferenceservice.getSettingVo(supplier)

/*                "setting_list", preferences.stream().map(x->{
            EnumWithValueResp enumResp = new EnumWithValueResp();
                    enumResp.setId(x.getName());
                    enumResp.setText(Enumpreferences.valueOf(x.getName()).toString());
                    enumResp.setType(x.getValue_type().name());
            Object o = preferenceservice.getValue(x);
                    enumResp.setValue(o!=null? o.toString():"");
            EntityModel entityModel1 = EntityModel.of(enumResp);
            entityModel1.add(linkTo(methodOn(SettingRestController.class).updateEmployee(x.getId(),null)).withRel("updateSetting"));
                 //   enumResp.setSelected(roleList.contains(x.getName()));
                    return entityModel1;
                }).collect(Collectors.toList())*/

                )
        );

        //   entityModel.add(linkTo(methodOn(SupplierRestController.class).linkEmployee(supplier.get().getId(),null)).withRel("addEmployees"));
        entityModel.add(linkTo(methodOn(SettingRestController.class).Page_getSettingList(supplier.getId())).withSelfRel());

        return entityModel;
    }




    @Operation(summary = "3、更新")
    @PutMapping(value = "/preferences/{SETTING_ID}", produces = "application/json")
    public ResponseEntity<EntityModel<Setting>> updateEmployee(@PathVariable long SETTING_ID, @RequestBody @Valid UserProfileResp.PreferenceSpaceEdit employerPojo) {

        Optional<Preference> optional = preferenceRepository.findById(SETTING_ID);
        if(optional.isEmpty()) {
            throw new BookNotFoundException(SETTING_ID,"找不到设置");
        }
        Preference setting = optional.get();


        PrefereneVo prefereneVo = new PrefereneVo();
        prefereneVo.setString_value(employerPojo.getPreferenceSpace().name());
        prefereneVo.setValue_type(EnumPreference.working_space.getType());
        setting = preferenceservice.update(setting,prefereneVo);

        EntityModel entityModel = EntityModel.of(setting);
        return ResponseEntity.ok(entityModel);


    }





/*    @Operation(summary = "3、更新")
    @PutMapping(value = "/preferences", produces = "application/json")
    public ResponseEntity<CollectionModel> updateSetting(@RequestBody @Valid SettingVo employerPojo) {



        List<Setting> setting = preferenceservice.update(EnumSettingSpace.default_,employerPojo);

        CollectionModel entityModel = CollectionModel.of(setting);
        return ResponseEntity.ok(entityModel);


    }*/

}