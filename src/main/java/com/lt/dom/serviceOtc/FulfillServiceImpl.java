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

    public Fulfilled_item create(Reservation reservation,Traveler tra) {
        Fulfilled_item fulfilled_item = new Fulfilled_item();
        fulfilled_item.setBooking(reservation.getId());
        fulfilled_item.setCode(idGenService.fillmentItem_Code());
        fulfilled_item.setTravelers(Arrays.asList(tra));
        // fulfilled_item.setLog_customer_name();
        return fulfiiledItemRepository.save(fulfilled_item);
    }

    public List<Fulfilled_item> find(Reservation reservation) {

        // fulfilled_item.setLog_customer_name();
        List<Fulfilled_item> fulfilled_items =  fulfiiledItemRepository.findAllByBooking(reservation.getId());
        return fulfilled_items;
    }

}
