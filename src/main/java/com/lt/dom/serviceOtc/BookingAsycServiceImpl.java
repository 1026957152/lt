package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.ImportCVSBulkTravelerResp;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.TravelerReq;
import com.lt.dom.otcenum.EnumExportStatus;
import com.lt.dom.repository.ChargeRepository;
import com.lt.dom.repository.ImportExcelRepository;
import com.lt.dom.repository.ReservationRepository;
import com.lt.dom.repository.TravelerRepository;
import org.javatuples.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookingAsycServiceImpl {

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
    private ImportExcelRepository importExcelRepository;

    @Autowired
    private IdGenServiceImpl idGenService;


    @Async
    public void addBulkTravelerByImportCVS(ImportExcel export, List<ImportExcelBookingTraveler> importExcelBookingTravelers) {

        List<PhoneAuth> travelerReqs = importExcelBookingTravelers.stream().map(x->{
            PhoneAuth auth = new PhoneAuth();
            auth.setIdCardName(x.getName());
            auth.setIdCardNo(x.getId());
            auth.setPhoneNo(x.getTel_home());
            return auth;

        }).collect(Collectors.toList());


        Pair<List<PhoneAuth>, List<PhoneAuth>> phoneAuthPhoneAuthPair = realNameAuthenticationService.bulkCheckRealname(travelerReqs);

        ImportCVSBulkTravelerResp importCVSBulkTravelerResp = new ImportCVSBulkTravelerResp();
        importCVSBulkTravelerResp.setRealNameCount(phoneAuthPhoneAuthPair.getValue0().size());
        importCVSBulkTravelerResp.setNotRealNameCount(phoneAuthPhoneAuthPair.getValue1().size());
        importCVSBulkTravelerResp.setRealnameTravelers(phoneAuthPhoneAuthPair.getValue0().stream()
                        .map(x->{
                            TravelerReq travelerReq = new TravelerReq();
                            travelerReq.setName(x.getIdCardName());
                            travelerReq.setId(x.getIdCardName());
                            travelerReq.setId(x.getPhoneNo());
                            return travelerReq;
                        })
                        .collect(Collectors.toList()));

        importCVSBulkTravelerResp.setRealnameTravelers(phoneAuthPhoneAuthPair.getValue1().stream()
                        .map(x->{
                            TravelerReq travelerReq = new TravelerReq();
                            travelerReq.setName(x.getIdCardName());
                            travelerReq.setId(x.getIdCardName());
                            return travelerReq;
                        })
                        .collect(Collectors.toList()));



        export.setStatus(EnumExportStatus.DONE);
        export.setDone_at(LocalDateTime.now());
        export.setAttachment(JSONObject.valueToString(phoneAuthPhoneAuthPair.getValue0()));
        export = importExcelRepository.save(export);


    }
}
