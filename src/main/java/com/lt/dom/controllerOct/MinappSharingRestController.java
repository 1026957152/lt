package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ReferralResp;
import com.lt.dom.oct.Marketing;
import com.lt.dom.otcReq.ReferralPojo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oct/sharings")
public class MinappSharingRestController {



    @PostMapping(value = "/{USER_ID}/referrals/", produces = "application/json")
    public Marketing createSharings(ReferralPojo pojo) {
        return new Marketing();
    }



}