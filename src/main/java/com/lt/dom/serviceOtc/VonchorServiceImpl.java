package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.repository.ComponentRightRepository;
import com.lt.dom.repository.ComponentRightVounchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class VonchorServiceImpl {


    @Autowired
    private ComponentRightVounchRepository componentRightVounchRepository;

    public List<ComponentRightVounch> get票的权益(Voucher voucher){

        List<ComponentRightVounch> componentRightVounches = componentRightVounchRepository.findByVoucherId(voucher.getId());


        return componentRightVounches;
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
