package com.lt.dom.serviceOtc;


import com.lt.dom.controllerOct.SettingRestController;
import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Departures;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Setting;
import com.lt.dom.otcReq.SettingReq;
import com.lt.dom.otcReq.SettingUpdateReq;
import com.lt.dom.otcenum.EnumSettings;
import com.lt.dom.otcenum.EnumValueType;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.SettingRepository;
import com.lt.dom.vo.SettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static com.lt.dom.otcenum.EnumValueType.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SettingServiceImpl {


    @Autowired
    private SettingRepository settingRepository;


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
            return setting1;
        }).collect(Collectors.toList()));

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

    public SettingVo update(SettingReq employerPojo) {

        employerPojo.getDefault_product_tax_code();

        SettingVo settingVo = new SettingVo();
        Setting setting = settingRepository.findByName(EnumSettings.default_product_tax_code.name());
        settingVo.setDefault_product_tax_code(setting.getString_value());

        Setting setting_shipping = settingRepository.findByName(EnumSettings.default_shipping_tax_code.name());

        settingVo.setDefault_shipping_tax_code(setting_shipping.getString_value());

        return settingVo;
    }

    public EntityModel getSettingVo() {



        SettingVo settingVo = new SettingVo();
        Setting setting = settingRepository.findByName(EnumSettings.default_product_tax_code.name());
        settingVo.setDefault_product_tax_code(setting.getString_value());

        Setting setting_shipping = settingRepository.findByName(EnumSettings.default_shipping_tax_code.name());

        settingVo.setDefault_shipping_tax_code(setting_shipping.getString_value());


        EntityModel entityModel = EntityModel.of(settingVo);

        entityModel.add(linkTo(methodOn(SettingRestController.class).updateSetting(null)).withRel("updateSetting"));

        return entityModel;
    }
}
