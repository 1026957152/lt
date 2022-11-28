package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.ImportCVSBulkTravelerResp;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.RealNameAuthentication.RealNameAuthenticationServiceImpl;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.BookingResource;
import com.lt.dom.oct.ImportExcel;
import com.lt.dom.oct.Reward;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcReq.RewardReq;
import com.lt.dom.otcReq.TravelerReq;
import com.lt.dom.otcenum.EnumDocumentType;
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
    @Autowired
    private FileStorageServiceImpl fileStorageService;



    public Reward create(RewardReq pojo) {
        Reward reward = new Reward();

        reward.setName("");
        reward.setName(pojo.getName());

        reward = rewordRepository.save(reward);

        return reward;
    }

    public Reward create(Supplier product, RewardReq pojo) {
        Reward reward = new Reward();
        reward.setName(pojo.getName());
        reward.setType(pojo.getType());
        reward.setCode(idGenService.nextId(""));
        reward.setStock(pojo.getStock());
        reward.setRedeemed(pojo.getRedeemed());
        reward.setDescription(pojo.getDescription_text());
        reward.setSupplier(product.getId());

        reward.setProduct(pojo.getParameters().getProduct().getId());
        reward.setEarningRule_score(pojo.getEarningRules().getScore());
        reward.setEarningRule_level(pojo.getEarningRules().getLevel());


        reward = rewordRepository.save(reward);


        Reward finalShowtime = reward;
        pojo.getMedias().stream().forEach(e->{
            if(e!=null){
                fileStorageService.saveFromTempDocumentCode(finalShowtime.getCode(), EnumDocumentType.makeplan_resource,e.getThumbnail());

            }
        });

        return reward;
    }
}
