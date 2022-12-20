package com.lt.dom.serviceOtc;

import com.lt.dom.FulfiiledItemRepository;
import com.lt.dom.oct.*;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class FulfillServiceImpl {

    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private FulfiiledItemRepository fulfiiledItemRepository;
    @Autowired
    private ComponentVounchRepository componentVounchRepository;


    public void create(Reservation reservation,Traveler tra) {
        Fulfilled_item fulfilled_item = new Fulfilled_item();
        fulfilled_item.setBooking(reservation.getId());
        fulfilled_item.setCode(idGenService.fillmentItem_Code());
        fulfilled_item.setTravelers(Arrays.asList(tra));
        // fulfilled_item.setLog_customer_name();
        fulfiiledItemRepository.save(fulfilled_item);
    }


}
