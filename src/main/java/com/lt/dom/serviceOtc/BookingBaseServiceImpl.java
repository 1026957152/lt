package com.lt.dom.serviceOtc;

import com.google.gson.Gson;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.otcReq.BookingSkuPojo;
import com.lt.dom.otcReq.TravelerReq;
import com.lt.dom.otcReq.WindowsTicketBookingPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.requestvo.BookingTypeTowhoVoSku;
import com.lt.dom.serviceOtc.product.*;
import com.lt.dom.vo.PlatRefundVo;
import com.lt.dom.vo.PlatUserVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.dom.serviceOtc.JsonParse.GSON;


@Service
public class BookingBaseServiceImpl {
    Logger logger = LoggerFactory.getLogger(BookingBaseServiceImpl.class);



    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    LineItemRepository lineItemRepository;


    public void checkin(Long bookingLine) {

        Optional<LineItem> lineItemList = lineItemRepository.findById(bookingLine);


        LineItem lineItem = lineItemList.get();
        if(lineItem.isCheckin()){
            return;
        }
        lineItem.setCheckin(true);
        lineItem.setCheckin_at(LocalDateTime.now());
        lineItemRepository.save(lineItem);

        Optional<Reservation> reservationOptional = reservationRepository.findById(lineItem.getBooking());
        Reservation reservation = reservationOptional.get();
        reservation.setCheckin(true);
        reservation.setCheckin_at(LocalDateTime.now());
        reservationRepository.save(reservation);


    }
}
