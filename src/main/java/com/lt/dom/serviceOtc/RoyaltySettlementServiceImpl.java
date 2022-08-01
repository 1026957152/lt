package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumRoyaltyTransactionStatus;
import com.lt.dom.otcenum.EnumWhenSettle;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoyaltySettlementServiceImpl {





    @Autowired
    private RoyaltySettlementRepository royaltySettlementRepository;
    @Autowired
    private RoyaltyTransactionRepository royaltyTransactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RoyaltyRepository royaltyRepository;

    public List<Calendar> 订单支付后进行分润记录(List<Component> components) {

        List<RoyaltyRule> royaltyRules = components.stream().map(x->x.getRoyaltyRule()).filter(x->x.getWhenSettle() == EnumWhenSettle.payed).collect(Collectors.toList());

        RoyaltySettlement royaltySettlement = royaltySettlementRepository.save(new RoyaltySettlement());
        for(RoyaltyRule rp: royaltyRules){

            Royalty royalty = new Royalty();
            royalty.setAmount(rp.getAmount());

          //  royalty.setSupplierId(rp.g());
            royalty.setSettleAccount(rp.getRecipient());
            royalty.setRoyaltySettlementId(royaltySettlement.getId());
            royalty = royaltyRepository.save(royalty); // 分润的记录

            RoyaltyTransaction royaltyTransaction = new RoyaltyTransaction(royaltySettlement.getId(),rp.getRecipient(),rp.getAmount());
            royaltyTransaction.setStatus(EnumRoyaltyTransactionStatus.created);
            royaltyTransactionRepository.save(royaltyTransaction); // 总结算行为里面包含的每个结算行为
        }

       // royaltySettlement.setChargeId(charge.getId());
        royaltySettlement.setAmount(royaltyRules.stream().mapToInt(x->x.getAmount()).sum());
        royaltySettlement.setCount(royaltyRules.stream().count());


        royaltySettlement = royaltySettlementRepository.save(royaltySettlement); //总的一次结算行为


        return null;
    }
}
