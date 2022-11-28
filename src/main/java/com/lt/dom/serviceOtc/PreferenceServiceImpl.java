package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.controllerOct.PreferenceRestController;
import com.lt.dom.controllerOct.SettingRestController;
import com.lt.dom.controllerOct.ValueListRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PrefereneVo;
import com.lt.dom.otcReq.SettingUpdateReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.SettingVo;
import com.lt.dom.vo.UserPreferenceVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class PreferenceServiceImpl {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;



    public Preference update(Preference setting, PrefereneVo employerPojo) {

        switch (setting.getValue_type()){
            case boolean_:
                setting.setBoolean_value(employerPojo.getBoolean_value());
                break;
            case int_:
                setting.setInt_value(employerPojo.getInt_value());
                break;
            case float_:
                setting.setFloat_value(employerPojo.getFloat_value());
                break;
            case string_:
                setting.setString_value(employerPojo.getString_value());
        }

        return preferenceRepository.save(setting);

    }


/*    public void setupData() {

        preferenceRepository.saveAll(Arrays.stream(EnumSettings.values()).map(x->{
            Preference setting1 = new Preference();
            setting1.setValue_type(x.getType());
            setting1.setName(x.name());
            setting1.setSpace(EnumSettingSpace.default_);
            return setting1;
        }).collect(Collectors.toList()));

    }*/

/*    public void setupDataSupper(Long user) {

        if(preferenceRepository.findAllByUserAndSpace(user,EnumPreferenceSpace.default_).isEmpty()){
            preferenceRepository.saveAll(Arrays.stream(EnumPreference.values()).map(x->{
                Preference setting1 = new Preference();
                setting1.setValue_type(x.getType());
                setting1.setName(x.name());
                setting1.setSpace(EnumSettingSpace.default_);
                return setting1;
            }).collect(Collectors.toList()));
        }


    }*/

    public Object getValue(Preference setting) {


        switch (setting.getValue_type()){
            case boolean_:
                return setting.getBoolean_value();
            case int_:
                return setting.getInt_value();

            case float_:
                return setting.getFloat_value();
            case string_:
                return setting.getString_value();
        }

        return null;
    }




    public Setting setValue(Setting setting,Object value) {


        switch (setting.getValue_type()){
            case boolean_:
                setting.setBoolean_value(value!=null?Boolean.valueOf((Boolean) value ):null);
                break;
            case int_:
                setting.setInt_value(value!=null?Integer.valueOf((Integer) value):null);
                break;
            case float_:
                setting.setFloat_value(value!=null?Float.valueOf((Float) value):null);
                break;
            case string_:
                setting.setString_value(value!=null?(String) value:null);
                break;
            case image_:{
                if(value == null){
                }else{
                    fileStorageService.updateFromTempDocument(EnumSettings.Logo.name(),(PhotoResp) value,EnumDocumentType.logo);
                }
            }
        }

        return setting;
    }

 /*
    public List<Setting> update(EnumSettingSpace settingSpace, SettingVo employerPojo) {



        Map<EnumSettings,Setting> settingsSettingMap = preferenceRepository.findAllBySpace(settingSpace).stream().collect(Collectors.toMap(e->EnumSettings.valueOf(e.getName()), e->e));

        employerPojo.getDefault_product_tax_code();


        List<Pair<EnumSettings,Object>> pairList = new ArrayList<>();

        pairList.add(Pair.with(EnumSettings.default_product_tax_code,
                employerPojo.getDefault_product_tax_code()));




        pairList.add(Pair.with(EnumSettings.default_shipping_tax_code,
                employerPojo.getDefault_shipping_tax_code()));


        pairList.add(Pair.with(EnumSettings.home_page_high_Quality_Product_recommendation,
                employerPojo.getHome_page_high_Quality_Product_recommendation()));


        pairList.add(Pair.with(EnumSettings.Terms_and_conditions,
                employerPojo.getTerms_and_conditions()));


        pairList.add(Pair.with(EnumSettings.SMS_notification_for_new_reservation_requests,
                employerPojo.getSMS_notification_for_new_reservation_requests()));


        pairList.add(Pair.with(EnumSettings.Logo,
                employerPojo.getLogo()));


        List<Setting> settings = pairList.stream().map(e->{

            System.out.println("setting 的某一个"+e.getValue0().name()+" "+ e.getValue0().getType()+ e.getValue1());
            return setValue(settingsSettingMap.get(e.getValue0()),e.getValue1());
        }).collect(Collectors.toList());


        preferenceRepository.saveAll(settings);
        return settings;
    }
*/
    public EntityModel getSettingVo(User supplier) {



        UserPreferenceVo settingVo = new UserPreferenceVo();



        Map<EnumPreference,EntityModel> enumSettingsEntityModelMap =  Arrays.stream(EnumPreference.values())
                .collect(Collectors.toMap(e->e,e->{

                    EntityModel entityModel = EntityModel.of(EnumPreference.of(e));

                    if(e.equals(EnumSettings.home_page_high_Quality_Product_recommendation)){
                        entityModel.add(linkTo(methodOn(ValueListRestController.class).Page_getValueListByType(EnumValueListType.High_Quality_Product_recommendation)).withSelfRel());
                    }
                    return entityModel;
                }));
        settingVo.setItemMap(enumSettingsEntityModelMap);

        Setting setting = preferenceRepository.findByUserAndSpaceAndName(supplier.getId(),EnumPreferenceSpace.default_,EnumPreference.working_space.name());

        settingVo.setDefault_product_tax_code(setting.getString_value());

        settingVo.setLogo(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.logo,EnumSettings.Logo.name()));


        EntityModel entityModel = EntityModel.of(settingVo);

       // entityModel.add(linkTo(methodOn(PreferenceRestController.class).updateSetting(null)).withRel("updateSetting"));

        return entityModel;
    }


    public Preference getValue(User user, EnumPreference working_space) {

        Optional<Preference> preferenceOptional = preferenceRepository.findByUserAndName(user.getId(),working_space);
        if(preferenceOptional.isEmpty()){
            Preference setting1 = new Preference();
            setting1.setValue_type(working_space.getType());
            setting1.setName(working_space);
            setting1.setUser(user.getId());
            if(EnumPreference.working_space.equals(working_space)){
                setting1.setString_value(EnumPreferenceSpace.default_.name());
            }

            //setting1.setSpace(EnumSettingSpace.default_);

            setting1 = preferenceRepository.save(setting1);
            return setting1;
        }else {
            return preferenceOptional.get();
        }
    }

    public void whenDeleteEmployee(Employee employee) {

    }
}
