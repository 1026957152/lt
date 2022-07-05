package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ReferralResp;


import com.lt.dom.oct.Referral;
import com.lt.dom.otcReq.ReferralPojo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oct")
public class ReferralRestController {



    @PostMapping(value = "/{USER_ID}/referrals/", produces = "application/json")
    public ReferralResp createReferral(ReferralPojo pojo) {
        return new ReferralResp();
    }



}