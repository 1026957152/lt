package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;

import java.util.List;
import java.util.stream.Collectors;



public class ReferralServiceImpl {


    //product_id, compainId 和 userId
    public List<RoyaltyRuleData> redeemRoyalty(Product product, RoyaltyTemplate royaltyTemplate){


        Referral referral = new Referral();

        return null;
/*
        referral.get
        if(royaltyRule.getRoyaltyRuleData().stream().filter(x -> (x.getLevel() == referral.getLevel())).findAny().isPresent()){

        }
        referral.getLevel();
        return royaltyRule.getRoyaltyRuleData().stream().map(x-> {
            x.getLevel();
            Royalty royalty = new Royalty();
            royalty.setSettle_account(user.getSettleAccount());
            return royalty;
        }).collect(Collectors.toList());

*/


    }
}
