package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ReferralResp;


import com.lt.dom.oct.Referral;
import com.lt.dom.otcReq.ReferralPojo;
import com.lt.dom.serviceOtc.ReferralServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oct")
public class ReferralRestController {


    @Autowired
    public ReferralServiceImpl referralService;


    @PostMapping(value = "/{USER_ID}/referrals/", produces = "application/json")
    public ReferralResp createReferral(ReferralPojo pojo) {
        return new ReferralResp();
    }


    @GetMapping(value = "/referrals/", produces = "application/json")
    public ReferralResp get(@RequestParam String  scene) {  // scene 最大 32各字符


        referralService.getUserAndProductByScene(scene);

        return new ReferralResp();
    }


}