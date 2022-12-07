package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ProductPojo;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;
import com.lt.dom.repository.AttributeRepository;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.PricingTypeRepository;
import com.lt.dom.vo.NegotiatedPricingType;
import com.lt.dom.vo.ProductPriceRangeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttributeServiceImpl {

    @Autowired
    private AttributeRepository attributeRepository;

    public void fill(ProductResp productResp, Product e) {
        List<Attribute> attributes = attributeRepository.findAllByObjectCode(e.getCode());
        productResp.setAbout(attributes.stream().map(attribute->{
            return AttributeResp.from(attribute);
        }).collect(Collectors.toList()));
    }
}
