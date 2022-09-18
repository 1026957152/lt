package com.lt.dom.controllerOct;
//https://developer.tuya.com/en/docs/cloud/52d5df4241?id=Kbn026xsi0cab

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.notification.event.OnRefundCreatedEvent;
import com.lt.dom.notification.event.device.OnQrcodeReaderScanSuccessEvent;
import com.lt.dom.oct.AutoGate;
import com.lt.dom.oct.Device;
import com.lt.dom.oct.User;
import com.lt.dom.otcReq.device.AutoGateIdentifyCallBckReq;
import com.lt.dom.otcenum.EnumEvents;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.AssetDeviceRepository;
import com.lt.dom.repository.AutoGateRepository;
import com.lt.dom.repository.DeviceRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.DeviceAutoGateService;
import com.lt.dom.serviceOtc.DeviceService;
import com.lt.dom.vo.QrcodeReaderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class DeviceInfoNotifyController {

    @Autowired
    ApplicationEventPublisher eventPublisher;


    @PostMapping(value = "/qr_notify_") //consumes= "text/html",  //  produces = "text/plain"
    public String Deactivate(@RequestBody String params,@RequestHeader Map<String, String> headers) {

        headers.forEach((key, value) -> {
           System.out.println(String.format("Header '%s' = %s", key, value));
        });
        System.out.println("==========="+ params);


        String[] a = params.split("&&");
        String vgdecoderesult_  = a[0];
        String devicenumber_  = a[1];

        String vgdecoderesult = vgdecoderesult_.split("=")[1];
        String devicenumber = devicenumber_.split("=")[1];

        QrcodeReaderResult qrcodeReaderResult = new QrcodeReaderResult(vgdecoderesult,devicenumber);

        System.out.println(qrcodeReaderResult.toString());
        eventPublisher.publishEvent(new OnQrcodeReaderScanSuccessEvent(new User(),
                qrcodeReaderResult, EnumEvents.qrcode_reader$scan));

        return "code=0000";

    }













}