package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.TermResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumFinancialAccountStatus;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.otcenum.EnumTermType;
import com.lt.dom.otcenum.EnumUserType;
import com.lt.dom.repository.BalanceRepository;
import com.lt.dom.repository.TermRepository;
import com.lt.dom.repository.TransactionEntryRepository;
import com.lt.dom.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TermServiceImpl {


    @Autowired
    private TermRepository termRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BalanceRepository balanceRepository;




    public Map get(Product product) {

        String note = "游客预约系统在线预约时需要提供出行人的身份证西南西，并需要在进馆时出具对应身份证件用于验证，请确保录入信息真实有效。一码君行将通过加密等方式保护您录入的身份证件信息。";

/*
        productResp.setBooking_note(note);
        productResp.setTraveller_term(note);
*/



        List<Term> termList = termRepository.findAllByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType.product,product.getId());

        Map<EnumTermType,Term> map = termList.stream().collect(Collectors.toMap(e->e.getType(),e->e));

        Map termMap  = Map.of(
                "type", EnumTermType.TermsOfUse,
                "text", note,
                "requiresExplicitConsent",true);
        return termMap;
    }




    public Map<EnumTermType,List<Term>> getMap(Product product) {

        String note = "游客预约系统在线预约时需要提供出行人的身份证西南西，并需要在进馆时出具对应身份证件用于验证，请确保录入信息真实有效。一码君行将通过加密等方式保护您录入的身份证件信息。";

/*
        productResp.setBooking_note(note);
        productResp.setTraveller_term(note);
*/



        List<Term> termList = termRepository.findAllByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType.product,product.getId());

        Map<EnumTermType,List<Term>> map = termList.stream().collect(Collectors.groupingBy(e->e.getType()));


        return map;
    }

    public Map<EnumTermType, TermResp> getMapResp(Product product) {

        List<Term> termList = termRepository.findAllByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType.product,product.getId());

        Map<EnumTermType,List<Term>> map = termList.stream().collect(Collectors.groupingBy(e->e.getType()));

        Map<EnumTermType, TermResp> mmm =map.entrySet().stream().map(e->{

            return Map.entry(e.getKey(),e.getValue().get(0));

        }).collect(Collectors.toMap(e->e.getKey(),e->TermResp.from(e.getValue())));



        return mmm;
    }
}
