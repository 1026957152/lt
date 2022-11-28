package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.TheatreReq;
import com.lt.dom.OctResp.home.HomeSearcherReminderResp;
import com.lt.dom.oct.Message;
import com.lt.dom.oct.Seat;
import com.lt.dom.oct.SeatingLayout;
import com.lt.dom.oct.Theatre;
import com.lt.dom.otcReq.HelpMessage;
import com.lt.dom.otcReq.SeatingLayoutReq;
import com.lt.dom.repository.MessageRepository;
import com.lt.dom.repository.SeatRepository;
import com.lt.dom.repository.SeatingLayoutRepository;
import com.lt.dom.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private MessageRepository messageRepository;

    public Theatre update(Theatre supplier, TheatreReq theatreReq) {
        return supplier;
    }

    public Message createSeatingLayout(HelpMessage theatreReq) {

        Message seatingLayout = new Message();
        seatingLayout.setCode(idGenService.seatingLayoutCode());
       // seatingLayout.setSeats(theatreReq.getSeats());

        seatingLayout.setName(theatreReq.getName());
        seatingLayout.setText(theatreReq.getText());
        seatingLayout.setPhone(theatreReq.getPhone());
        seatingLayout = messageRepository.save(seatingLayout);



        return seatingLayout;
    }


    public void get(HomeSearcherReminderResp homeResp, String title) {




        homeResp.setRecentlySearched(Arrays.asList(Map.of("test","发一个")));



    }
}
