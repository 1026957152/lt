package com.lt.dom.controller;

import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.domain.TouristAttraction;
import com.lt.dom.domain.CouponTemplatePojoList;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
//@RequestMapping("TouristAttraction")
public class OrderRestController {
    
    @GetMapping(value = "/{TouristAttraction_ID}", produces = "application/json")
    public TouristAttraction getBook(@PathVariable int APP_ID, @PathVariable int COUPON_TMPL_ID) {
        return null;
    }
    @GetMapping(value = "/{APP_ID}/{COUPON_TMPL_ID}", produces = "application/json")
    public TouristAttraction listCouponTemplate(@PathVariable int APP_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        return null;
    }



    @PostMapping(value = "/coupon_templates", produces = "application/json")
    public TouristAttraction createCouponTemplate(CouponTemplatePojo pojo) {
        return null;
    }


    @PutMapping(value = "/{APP_ID}/coupon_templates/{COUPON_TMPL_ID}", produces = "application/json")
    public TouristAttraction updateBook(@PathVariable int APP_ID, Map metadata) {
        return null;
    }

    @DeleteMapping(value = "/coupon_templates", produces = "application/json")
    public TouristAttraction createCouponTemplate(@PathVariable int id) {
        return null;
    }

}