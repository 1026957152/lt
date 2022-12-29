package com.lt.dom.serviceOtc;

import com.lt.dom.FulfiiledItemRepository;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.otcenum.EnumVoucherStatus;
import com.lt.dom.otcenum.EnumVoucherType;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
         fulfiiledItemRepository.save(fulfilled_item);

        Fulfilled_item fulfilled_item_ = new Fulfilled_item();
        fulfilled_item.setBooking(reservation.getId());
        fulfilled_item.setCode(idGenService.fillmentItem_Code()+"_");
        fulfilled_item.setTravelers(Arrays.asList(tra));
        // fulfilled_item.setLog_customer_name();
        return fulfiiledItemRepository.save(fulfilled_item);
    }


    public VoucherTicket create(Reservation reservation,
                                Product product,
                                EnumVoucherType voucherType,
                                Long relateId, Cardholder cardholder) {
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
        if(cardholder!= null){
            voucher.setRealnameHolder(cardholder.getId());
        }
        voucher = voucherTicketRepository.save(voucher);

        Fulfilled_item fulfilled_item = new Fulfilled_item();
        fulfilled_item.setBooking(reservation.getId());
        fulfilled_item.setCode(idGenService.fillmentItem_Code());
        fulfilled_item.setRelatedObjectId(voucher.getId());
        fulfilled_item.setRelatedObjectType(EnumRelatedObjectType.voucher);
        fulfilled_item.setRelatedObjectCode(voucher.getCode());
       // fulfilled_item.setTravelers(Arrays.asList(tra));
        // fulfilled_item.setLog_customer_name();
        fulfiiledItemRepository.save(fulfilled_item);
        return voucher;
    }



    public List<Fulfilled_item> find(Reservation reservation) {

        // fulfilled_item.setLog_customer_name();
        List<Fulfilled_item> fulfilled_items =  fulfiiledItemRepository.findAllByBooking(reservation.getId());
        return fulfilled_items;
    }

    public List<String> findCodes(Reservation reservation) {

        // fulfilled_item.setLog_customer_name();
        List<Fulfilled_item> fulfilled_items =  fulfiiledItemRepository.findAllByBooking(reservation.getId());
        return fulfilled_items.stream().map(e->e.getRelatedObjectCode()).collect(Collectors.toList());
    }
    public List<VoucherTicket> find_(Reservation reservation) {

        // fulfilled_item.setLog_customer_name();
        List<VoucherTicket> fulfilled_items =  voucherTicketRepository.findAllByBooking(reservation.getId());
        return fulfilled_items;
    }


    public List<VoucherTicket> find_(Cardholder cardholder) {

        // fulfilled_item.setLog_customer_name();
        List<VoucherTicket> fulfilled_items =  voucherTicketRepository.findAllByBooking(cardholder.getId());
        return fulfilled_items;
    }

    public void create(Reservation reservation, EnumRelatedObjectType product, Long id, String code) {

        Fulfilled_item fulfilled_item = new Fulfilled_item();
        fulfilled_item.setBooking(reservation.getId());
        fulfilled_item.setCode(idGenService.fillmentItem_Code());
        fulfilled_item.setRelatedObjectId(id);
        fulfilled_item.setRelatedObjectType(product);
        fulfilled_item.setRelatedObjectCode(code);
        fulfiiledItemRepository.save(fulfilled_item);

    }
}
