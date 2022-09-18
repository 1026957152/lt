package com.lt.dom.controllerOct;
//https://developer.tuya.com/en/docs/cloud/52d5df4241?id=Kbn026xsi0cab

import com.lt.dom.OctResp.AssetDeviceResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcReq.device.AutoGateIdentifyCallBckReq;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.DeviceAutoGateService;
import com.lt.dom.serviceOtc.DeviceService;
import com.lt.dom.vo.AssetNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oct")
public class DeviceInfoController {

    @Autowired
    private DeviceAutoGateService deviceAutoGateService;
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private AvailabilityServiceImpl availabilityService;

    @Autowired
    private AutoGateRepository autoGateRepository;

    @Autowired
    private AssetDeviceRepository assetDeviceRepository;




    @PostMapping(value = "/devices/{device_id}/heartbeat", produces = "application/json")
    public Device Deactivate(@PathVariable long device_id) {

        Optional<Device> validatorOptional = deviceRepository.findById(device_id);
        if(validatorOptional.isEmpty()){
            System.out.println("抛出异常");
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }


        Device device = validatorOptional.get();

        deviceService.OnlineDevice(device);

        return validatorOptional.get();


    }












    @PostMapping(value = "/device/identifyCallBck", produces = "application/json")
    public ResponseEntity<AutoGateIdentifyCallBckReq> IdentifyCallBck(@RequestBody AutoGateIdentifyCallBckReq autoGateIdentifyCallBckReq) {

        Optional<AutoGate> assetDeviceOptional = autoGateRepository.findBySN(autoGateIdentifyCallBckReq.getId());

        if(assetDeviceOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到 asset");

        }
        AutoGate assetDevice = assetDeviceOptional.get();

        AutoGateIdentifyCallBckReq device =  deviceAutoGateService.event(assetDevice,autoGateIdentifyCallBckReq);

                return ResponseEntity.ok(device);

    }












}