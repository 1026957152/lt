package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.ImportCVSBulkTravelerResp;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.ImportExcel;
import com.lt.dom.oct.Reward;
import com.lt.dom.otcReq.RewardReq;
import com.lt.dom.otcReq.TravelerReq;
import com.lt.dom.otcenum.EnumExportStatus;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RewardServiceImpl {

    @Autowired
    RealNameAuthenticationServiceImpl realNameAuthenticationService;
    @Autowired
    TravelerRepository travelerRepository;
    @Autowired
    VoucherServiceImpl voucherService;
    @Autowired
    ChargeRepository chargeRepository;

    @Autowired
    ReservationRepository reservationRepository;


    @Autowired
    private RewordRepository rewordRepository;

    @Autowired
    private IdGenServiceImpl idGenService;



    public Reward create(RewardReq pojo) {
        Reward reward = new Reward();

        reward.setName("");
        reward.setName(pojo.getName());

        reward = rewordRepository.save(reward);

        return reward;
    }
}
