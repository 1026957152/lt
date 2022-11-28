package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.ComponentRightRepository;
import com.lt.dom.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PartnerServiceImpl {


    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private IdGenServiceImpl idGenService;


    public List<Partner> createPartner(Long supplier_id,Long partner_id) {


        List<Partner> partnerList = partnerRepository.findAllBySupplierAndPartner(supplier_id,partner_id);

        if(!partnerList.isEmpty()){
            throw new ExistException(Enumfailures.resource_not_found,"已经存在该合作对象了");
        }

        String code = idGenService.nextId("share");

        Partner partner = new Partner();
        partner.setShareCode(code);
        partner.setCode(idGenService.partnerNo());
        partner.setSupplier(supplier_id);
        partner.setPartner(partner_id);

        partner = partnerRepository.save(partner);



        partner = new Partner();
        partner.setCode(idGenService.partnerNo());
        partner.setShareCode(code);
        partner.setSupplier(partner_id);
        partner.setPartner(supplier_id);

        partner = partnerRepository.save(partner);

        return Arrays.asList(partner,partner);
    }

    public List<ComponentRight> listComponentRights(Partner partner) {



        ComponentRight probe = new ComponentRight();
        probe.setSupplier(partner.getSupplier());

        Example<ComponentRight> example = Example.of(probe);

        return componentRightRepository.findAll(example);
    }




    public Optional<Partner> getPartner(long supplierId, long supplierId1) {
        List<Partner> partner = partnerRepository.findAllBySupplierAndPartner(supplierId,supplierId);

        if(partner.isEmpty()){
            return Optional.empty();

        }
        return Optional.of(partner.get(0));
    }
}
