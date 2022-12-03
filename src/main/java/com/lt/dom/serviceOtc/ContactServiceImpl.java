package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.CrossSellResp;
import com.lt.dom.OctResp.ProductBookingResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ExtraReq;
import com.lt.dom.otcReq.IdentifierReq;
import com.lt.dom.otcenum.EnumChannelStatus;
import com.lt.dom.otcenum.EnumIdentifiersType;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.repository.AttributeRepository;
import com.lt.dom.repository.ContactRepository;
import com.lt.dom.repository.CrossSellRepository;
import com.lt.dom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//https://stripe.com/docs/payments/checkout/cross-sells#create-cross-sell
@Service
public class ContactServiceImpl {
    @Autowired
    private ContactRepository contactRepository;


    @Autowired
    private IdGenServiceImpl idGenService;




    public Contact create(EnumRelatedObjectType enumRelatedObjectType,Long supplier, List<IdentifierReq> identifierReqs) {

        Optional<Contact> contactOptional = contactRepository.findByRelatedObjectTypeAndRelatedObjectId(enumRelatedObjectType,supplier);


        if(contactOptional.isPresent()){

            return contactOptional.get();
        };

        Contact crossSellKey = new Contact();

        crossSellKey.setRelatedObjectId(supplier);
        crossSellKey.setRelatedObjectType(enumRelatedObjectType);


        Contact finalCrossSellKey = crossSellKey;
        crossSellKey.setIdentifiers(identifierReqs.stream().map(e->{
            Identifier identifier = new Identifier();
            identifier.setType(e.getType());
            identifier.setLinkId(e.getId());
            identifier.setContact(finalCrossSellKey);
            identifier.setStatus(EnumChannelStatus.subscribed);
            return identifier;
        }).collect(Collectors.toList()));



        crossSellKey = contactRepository.save(crossSellKey);


        return crossSellKey;


    }

    @Transactional
    public Contact update(Contact theatre, ExtraReq theatreReq) {




        theatre = contactRepository.save(theatre);

        return theatre;
    }


    public Optional<Contact> find(EnumRelatedObjectType product, Long id) {

        Optional<Contact> contactOptional = contactRepository.findByRelatedObjectTypeAndRelatedObjectId(product,id);
        return contactOptional;
    }
}
