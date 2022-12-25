package com.lt.dom.serviceOtc;

import com.lt.dom.FulfiiledItemRepository;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumVoucherStatus;
import com.lt.dom.otcenum.EnumVoucherType;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.lt.dom.serviceOtc.JsonParse.GSON;


@Service
public class FulfillServiceImpl {
    @Autowired
    private VoucherTicketRepository voucherTicketRepository;
    @Autowired
    private AvailabilityServiceImpl availabilityService;

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


    public VoucherTicket create(Reservation reservation,
                                 Product product,
                                 EnumVoucherType voucherType,
                                 Long relateId) {
        VoucherTicket voucher = new VoucherTicket();
        voucher.setBooking(reservation.getId());
        voucher.setLable(product.getName());
        LocalDateTime ex = availabilityService.getExpiry_datetime(product);
        voucher.setExpiry_datetime(ex);
        voucher.setExpiration_date(ex);
        VoucherTicket.BusTicket busTicket = new VoucherTicket.BusTicket();
        busTicket.setSku(product.getId());
        voucher.setData_json(GSON.toJson(busTicket));
        voucher.setCode(idGenService.voucherCode());
        voucher.setType(voucherType);
        voucher.setStatus(EnumVoucherStatus.Created);
        voucher.setPublished(false);
        voucher.setActive(false);
        voucher.setRelateId(relateId);
        voucher = voucherTicketRepository.save(voucher);
        return voucher;
    }



/*    public List<Fulfilled_item> find(Reservation reservation) {

        // fulfilled_item.setLog_customer_name();
        List<Fulfilled_item> fulfilled_items =  fulfiiledItemRepository.findAllByBooking(reservation.getId());
        return fulfilled_items;
    }*/
    public List<VoucherTicket> find_(Reservation reservation) {

        // fulfilled_item.setLog_customer_name();
        List<VoucherTicket> fulfilled_items =  voucherTicketRepository.findAllByBooking(reservation.getId());
        return fulfilled_items;
    }

}
