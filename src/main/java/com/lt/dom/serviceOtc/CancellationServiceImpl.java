package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.TheatreReq;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingCancelPojo;
import com.lt.dom.otcReq.SeatingLayoutReq;
import com.lt.dom.otcenum.EnumBookingStatus;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CancellationServiceImpl {
    @Autowired
    private CancellationRepository cancellationRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private SeatingLayoutRepository seatingLayoutRepository;



    public Reservation cancel(Reservation reservation, BookingCancelPojo cancelBookingRequest) {



        reservation.setStatus(EnumBookingStatus.Cancelled);
        // reservation.setRejectionReason(cancelBookingRequest.getReason());

        Cancellation cancellation = new Cancellation();
        cancellation.setBooking(reservation.getId());
        cancellation.setCancel_reason(cancelBookingRequest.getReason());
        cancellation.setNote(cancelBookingRequest.getNote());
        cancellation.setNotifyParticipants(cancelBookingRequest.getNotifyParticipants());

        cancellationRepository.save(cancellation);
        return reservationRepository.save(reservation);

    }
    public Optional<Cancellation> getCancellation(Reservation e) {


        List<Cancellation> cancellationList = cancellationRepository.findAllByBooking(e.getId());

        if(cancellationList.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(cancellationList.get(0));

    }
}
