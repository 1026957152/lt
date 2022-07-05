package com.lt.dom.controller;

import com.lt.dom.domain.CouponTemplatePojo;

import com.lt.dom.domain.CouponTemplatePojoList;
import com.lt.dom.oct.CouponTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
//@RequestMapping(value = "CouponTemplate")
public class CouponTemplateRestController {
    
    @GetMapping(value = "/{CouponTemplate_ID}", produces = "application/json")
    public CouponTemplate getBook(@PathVariable int APP_ID, @PathVariable int COUPON_TMPL_ID) {
        return null;
    }
    @GetMapping(value = "/{APP_ID}/{COUPON_TMPL_ID}", produces = "application/json")
    public CouponTemplate listCouponTemplate(@PathVariable int APP_ID, CouponTemplatePojoList  couponTemplatePojoList) {
        return null;
    }



    @PostMapping(value = "/coupon_templates", produces = "application/json")
    public CouponTemplate createCouponTemplate(CouponTemplatePojo pojo) {
        return null;
    }


    @PutMapping(value = "/{APP_ID}/coupon_templates/{COUPON_TMPL_ID}", produces = "application/json")
    public CouponTemplate updateBook(@PathVariable int APP_ID, Map  metadata) {
        return null;
    }

    @DeleteMapping(value = "/coupon_templates", produces = "application/json")
    public CouponTemplate createCouponTemplate(@PathVariable int id) {
        return null;
    }

}