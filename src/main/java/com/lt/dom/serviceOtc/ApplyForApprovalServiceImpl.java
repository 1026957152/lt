package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CertificationApplyReq;
import com.lt.dom.otcenum.EnumApplicationType;
import com.lt.dom.otcenum.EnumRequestStatus;
import com.lt.dom.repository.ApplyCertificationRepository;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.requestvo.RequestMerchants_settled;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplyForApprovalServiceImpl {


    @Autowired
    private ApplyCertificationRepository applyCertificationRepository;

    @Autowired
    private SupplierRepository supplierRepository;



    public static List<LocalDate> getAvailability(List<Departures> departures) {


        List<LocalDate> localDates = departures.stream().filter(x->x.getType() == "" ).map(x->{

            return getDatesBetweenUsingJava9(x);

        }).flatMap(List::stream).collect(Collectors.toList());

        List<LocalDate> close = departures.stream().filter(x->x.getType() == "" ).map(x->{

            return getDatesBetweenUsingJava9(x);

        }).flatMap(List::stream).collect(Collectors.toList());


        return localDates.stream().filter(close::contains).collect(Collectors.toList());


    }


    public static List<LocalDate> getDatesBetweenUsingJava9(Departures departures) {

        List<LocalDate> localDates =  departures.getPeriodFrom().datesUntil(departures.getPeriodTo())
                .collect(Collectors.toList());


        return localDates.stream().filter(x->{
            if(departures.isMonday()){
                return x.getDayOfWeek() == DayOfWeek.MONDAY;
            }
            if(departures.isThursday()){
                return x.getDayOfWeek() == DayOfWeek.THURSDAY;
            }
            if(departures.isWednesday()){
                return x.getDayOfWeek() == DayOfWeek.WEDNESDAY;
            }
            if(departures.isWednesday()){
                return x.getDayOfWeek() == DayOfWeek.WEDNESDAY;
            }
            return false;
        }).collect(Collectors.toList());
    }

    public ApplyCertification apply(Supplier supplier, CertificationApplyReq pojo) {

        ApplyCertification applyCertification = new ApplyCertification();
        applyCertification.setType(EnumApplicationType.Merchants_settled);
        applyCertification.setSupplier(supplier.getId());

        applyCertification.setAdditional_info(JSONObject.valueToString(pojo));
        applyCertification = applyCertificationRepository.save(applyCertification);


        return applyCertification;
    }


    public ApplyCertification reject(ApplyCertification applyCertification, CertificationApplyReq pojo) {
        applyCertification.setStatus(EnumRequestStatus.REJECT);
        applyCertification = applyCertificationRepository.save(applyCertification);
        return applyCertification;
    }

    public ApplyCertification approve(ApplyCertification applyCertification, CertificationApplyReq pojo) {

        if(applyCertification.getType().equals(EnumApplicationType.Merchants_settled)){

            RequestMerchants_settled requestMerchants_settled = (RequestMerchants_settled)JSONObject.stringToValue(applyCertification.getAdditional_info());

            Optional<Supplier> supplier = supplierRepository.findById(requestMerchants_settled.getSupplier());
           // if(supplier.get().getStatus());


            supplierRepository.save(supplier.get());
        }
        applyCertification.setAdditional_info(JSONObject.valueToString(pojo));
        applyCertification.setStatus(EnumRequestStatus.APPROVE);

        applyCertification = applyCertificationRepository.save(applyCertification);
        return applyCertification;
    }
}
