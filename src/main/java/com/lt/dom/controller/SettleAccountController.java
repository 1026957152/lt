package com.lt.dom.controller;

import com.lt.dom.domain.SettleAccount;

import com.lt.dom.otcReq.SettleAccountPojo;
import com.lt.dom.otcReq.SettleAccountPojoList;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
//@RequestMapping("SettleAccount-rest")
public class SettleAccountController {
    
    @GetMapping(value = "/{SettleAccount_ID}", produces = "application/json")
    public SettleAccount getBook(@PathVariable int APP_ID, @PathVariable int COUPON_TMPL_ID) {
        return null;
    }
    @GetMapping(value = "/{APP_ID}/{COUPON_TMPL_ID}", produces = "application/json")
    public SettleAccount listSettleAccount(@PathVariable int APP_ID, SettleAccountPojoList SettleAccountPojoList) {
        return null;
    }



    @PostMapping(value = "/coupon_templates", produces = "application/json")
    public SettleAccount createSettleAccount(SettleAccountPojo pojo) {
        return null;
    }


    @PutMapping(value = "/{APP_ID}/coupon_templates/{COUPON_TMPL_ID}", produces = "application/json")
    public SettleAccount updateBook(@PathVariable int APP_ID, Map metadata) {
        return null;
    }

    @DeleteMapping(value = "/coupon_templates", produces = "application/json")
    public SettleAccount createSettleAccount(@PathVariable int id) {
        return null;
    }

}