package com.lt.dom.serviceOtc;


import com.lt.dom.controllerOct.BarcodesController;
import com.lt.dom.controllerOct.ComponentRightRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.IntoOnecodeRepository;
import com.lt.dom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class IntoOnecodeServiceImpl {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IntoOnecodeRepository intoOnecodeRepository;

    @Autowired
    private IdGenServiceImpl idGenService;



    public IntoOnecode getAvailability(User user)  {

        Optional<IntoOnecode> intoOnecodeOptional = intoOnecodeRepository.findByUser(user.getId());

        IntoOnecode intoOnecode = null;

        if(intoOnecodeOptional.isEmpty()){
            intoOnecode = new IntoOnecode();
            intoOnecode.setType(EnumAssetType.qr);
            intoOnecode.setIdId(idGenService.oneCodeNo());
            intoOnecode.setUser(user.getId());
            intoOnecode = intoOnecodeRepository.save(intoOnecode);


        }else{
            intoOnecode = intoOnecodeOptional.get();

        }
        return intoOnecode;


    }


    public User byCode(Object value) {

        Optional<IntoOnecode> intoOnecodeOptional = intoOnecodeRepository.findByIdId((String)value );

        if(intoOnecodeOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到文旅吗"+ value);
        }

        IntoOnecode intoOnecode = intoOnecodeOptional.get();

        Optional<User> userOptional = userRepository.findById(intoOnecode.getUser());
        return userOptional.get();

    }
}
