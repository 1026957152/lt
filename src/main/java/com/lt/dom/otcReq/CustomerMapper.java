package com.lt.dom.otcReq;

import com.lt.dom.oct.Campaign;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;



@Mapper(componentModel = "spring")
public interface CustomerMapper {
/*    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(CompaignEditPojo dto, @MappingTarget Campaign entity);*/
}