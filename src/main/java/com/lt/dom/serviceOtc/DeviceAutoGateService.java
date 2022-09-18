package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.notification.event.OnRegistrationCompleteEvent;
import com.lt.dom.notification.event.device.OnAutoGateIdentityUploadEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AssetAddReq;
import com.lt.dom.otcReq.DeviceRegisterPojo;
import com.lt.dom.otcReq.DeviceSendCommandPojo;
import com.lt.dom.otcReq.device.AutoGateIdentifyCallBckReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.AssetNode;
import com.lt.dom.vo.DeviceScanValidatorVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class DeviceAutoGateService {

    private static final String UNKNOWN = "UNKNOWN";

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    ComponentVounchValidatorRecordRepository componentVounchValidatorRecordRepository;

    public AutoGateIdentifyCallBckReq event(AutoGate assetDevice, AutoGateIdentifyCallBckReq autoGateIdentifyCallBckReq) {



        eventPublisher.publishEvent(new OnAutoGateIdentityUploadEvent(new User(),
                null, null));

        return autoGateIdentifyCallBckReq;


    }


    public void syncWith() {

        List<ComponentVounchValidatorRecord> componentVounchValidatorRecordList = componentVounchValidatorRecordRepository.findAll();

        componentVounchValidatorRecordList.stream().collect(Collectors.groupingBy(e->e.getDevice()))
                .entrySet().stream().forEach(e->{

                    Long deviceId = e.getKey();
            List<ComponentVounchValidatorRecord> componentVounchValidatorRecords = e.getValue();

            Device device = deviceRepository.findById(deviceId).get();


        });




    }
}