package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.OctResp.PhoneResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.controllerOct.SettingRestController;
import com.lt.dom.controllerOct.ValueListRestController;
import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Departures;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Setting;
import com.lt.dom.otcReq.SettingReq;
import com.lt.dom.otcReq.SettingUpdateReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.SettingRepository;
import com.lt.dom.vo.SettingVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.dom.otcenum.EnumValueType.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SettingServiceImpl {


    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private FileStorageServiceImpl fileStorageService;



    public Setting update(Setting setting, SettingUpdateReq employerPojo) {

        switch (setting.getValue_type()){
            case boolean_:
                setting.setBoolean_value(true);
                break;
            case int_:
                setting.setInt_value(1);
                break;
            case float_:
                setting.setFloat_value(1f);
                break;
            case string_:
                setting.setString_value("122");
        }

        return settingRepository.save(setting);

    }


    public void setupData() {

        settingRepository.saveAll(Arrays.stream(EnumSettings.values()).map(x->{
            Setting setting1 = new Setting();
            setting1.setValue_type(x.getType());
            setting1.setName(x.name());
            setting1.setSpace(EnumSettingSpace.default_);
            return setting1;
        }).collect(Collectors.toList()));

    }

    public void setupDataSupper() {

        if(settingRepository.findAllBySpace(EnumSettingSpace.default_).isEmpty()){
            settingRepository.saveAll(Arrays.stream(EnumSettings.values()).map(x->{
                Setting setting1 = new Setting();
                setting1.setValue_type(x.getType());
                setting1.setName(x.name());
                setting1.setSpace(EnumSettingSpace.default_);
                return setting1;
            }).collect(Collectors.toList()));
        }


    }

    public Object getValue(Setting setting) {


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
    public List<Setting> update(EnumSettingSpace settingSpace,SettingVo employerPojo) {



       Map<EnumSettings,Setting> settingsSettingMap = settingRepository.findAllBySpace(settingSpace).stream().collect(Collectors.toMap(e->EnumSettings.valueOf(e.getName()),e->e));

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




 /*       if(employerPojo.getLogo()!= null){

            fileStorageService.updateFromTempDocument(EnumSettingSpace.default_.getKey(),employerPojo.getLogo(),EnumDocumentType.logo);
        }*/
        pairList.add(Pair.with(EnumSettings.SMS_notification_for_new_reservation_requests,
                employerPojo.getSMS_notification_for_new_reservation_requests()));


        pairList.add(Pair.with(EnumSettings.Logo,
                employerPojo.getLogo()));


        List<Setting> settings = pairList.stream().map(e->{

            System.out.println("setting 的某一个"+e.getValue0().name()+" "+ e.getValue0().getType()+ e.getValue1());
           return setValue(settingsSettingMap.get(e.getValue0()),e.getValue1());
        }).collect(Collectors.toList());


        settingRepository.saveAll(settings);
        return settings;
    }

    public EntityModel getSettingVo() {



        SettingVo settingVo = new SettingVo();

        ;
        settingVo.setItems(Arrays.stream(EnumSettings.values()).map(e->{
            EntityModel entityModel = EntityModel.of(EnumSettings.of(e));

            if(e.equals(EnumSettings.home_page_high_Quality_Product_recommendation)){
                entityModel.add(linkTo(methodOn(ValueListRestController.class).Page_getValueListByType(EnumValueListType.High_Quality_Product_recommendation)).withSelfRel());
            }
            return entityModel;
        }).collect(Collectors.toList()));


        ;

       Map<EnumSettings,EntityModel> enumSettingsEntityModelMap =  Arrays.stream(EnumSettings.values())
               .collect(Collectors.toMap(e->e,e->{

            EntityModel entityModel = EntityModel.of(EnumSettings.of(e));

            if(e.equals(EnumSettings.home_page_high_Quality_Product_recommendation)){
                entityModel.add(linkTo(methodOn(ValueListRestController.class).Page_getValueListByType(EnumValueListType.High_Quality_Product_recommendation)).withSelfRel());
            }
            return entityModel;
        }));
        settingVo.setItemMap(enumSettingsEntityModelMap);

        Setting setting = settingRepository.findBySpaceAndName(EnumSettingSpace.default_,EnumSettings.default_product_tax_code.name());

        settingVo.setDefault_product_tax_code(setting.getString_value());
        Setting setting_shipping = settingRepository.findBySpaceAndName(EnumSettingSpace.default_,EnumSettings.default_shipping_tax_code.name());

        settingVo.setDefault_shipping_tax_code(setting_shipping.getString_value());

        Setting setting_home = settingRepository.findBySpaceAndName(EnumSettingSpace.default_,EnumSettings.home_page_high_Quality_Product_recommendation.name());

        settingVo.setHome_page_high_Quality_Product_recommendation(setting_home.getInt_value());



        ;

        settingVo.setLogo(fileStorageService.loadDocumentWithCodeEdit(EnumDocumentType.logo,EnumSettings.Logo.name()));


        Setting Terms_and_conditions = settingRepository.findBySpaceAndName(EnumSettingSpace.default_,EnumSettings.Terms_and_conditions.name());
        settingVo.setTerms_and_conditions(Terms_and_conditions.getString_value());




        Setting SMS_notification_for_new_reservation_requests = settingRepository.findByName(EnumSettings.SMS_notification_for_new_reservation_requests.name());
        settingVo.setSMS_notification_for_new_reservation_requests(SMS_notification_for_new_reservation_requests.getBoolean_value());

        EntityModel entityModel = EntityModel.of(settingVo);

        entityModel.add(linkTo(methodOn(SettingRestController.class).updateSetting(null)).withRel("updateSetting"));

        return entityModel;
    }


}
