package com.lt.dom.controllerOct;

import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.domain.CouponTemplatePojoList;


import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.util.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dddd")
public class ComponentRightRestController {




    @Autowired
    private IdGenServiceImpl idGenService;

    @GetMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators/{ID}", produces = "application/json")
    public AccessValidator getComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();

        return componentRight;
    }

    @PostMapping(value = "/coupon_templates", produces = "application/json")
    public ComponentRight createCouponTemplate(@RequestBody CouponTemplatePojo  pojo) {
        ComponentRight componentRight = new ComponentRight();

        componentRight.setName(pojo.getName());
        componentRight.setNote(idGenService.nextId("Coupon_"));
        return componentRight;
    }






    @PostMapping(value = "/componet_rights/{COMPONENT_RIGHTS_ID}/access_validators", produces = "application/json")
    public AccessValidator addComponentRight_Validator(@PathVariable int COMPONENT_RIGHTS_ID, @PathVariable int COUPON_TMPL_ID) {
        AccessValidator componentRight = new AccessValidator();
        return componentRight;
    }


    @GetMapping(value = "/{APP_ID}/{COUPON_TMPL_ID}", produces = "application/json")
    public List<ComponentRight> listCouponTemplate(@PathVariable int APP_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        return null;
    }




    @PutMapping(value = "/{APP_ID}/component_right/{COMPONENT_RIGHT_ID}", produces = "application/json")
    public ComponentRight updateComponentRight(@PathVariable int APP_ID, Map  metadata) {
        return null;
    }

    @DeleteMapping(value = "/coupon_templates", produces = "application/json")
    public ComponentRight createCouponTemplate(@PathVariable String id) {
        return null;
    }

}