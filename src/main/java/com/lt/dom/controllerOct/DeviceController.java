package com.lt.dom.controllerOct;
//https://developer.tuya.com/en/docs/cloud/52d5df4241?id=Kbn026xsi0cab

import com.lt.dom.OctResp.AssetDeviceResp;
import com.lt.dom.OctResp.ComponentRightResp;
import com.lt.dom.OctResp.DeviceResp;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.ComponentRightServiceImpl;
import com.lt.dom.serviceOtc.DeviceService;
import com.lt.dom.serviceOtc.ValidatorServiceImpl;
import com.lt.dom.vo.AssetNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class DeviceController {

    @Autowired
    private AssetAuthorizedRepository assetAuthorizedRepository;
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ComponentVounchValidatorRecordRepository componentVounchValidatorRecordRepository;


    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private AssetDeviceRepository assetDeviceRepository;

    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ComponentRightRepository componentRightRepository;


    @GetMapping(value = "/devices/page",produces = "application/json")
    public EntityModel Page_getDeviceList() {

        EntityModel entityModel = EntityModel.of(Map.of(

                "device_status_list",EnumDeviceType.from(),

                "device_type_list",EnumDeviceType.from(Arrays.asList(EnumDeviceType.OUTLET,
                        EnumDeviceType.HANDSET,
                        EnumDeviceType.FICIAL_RECOGNITION,
                        EnumDeviceType.QRCODE_READER,EnumDeviceType.BUS_QRCODE_READER))
        ));


        entityModel.add(linkTo(methodOn(DeviceController.class).registerDevice(null)).withRel("createDevice"));

        return entityModel;
    }



    @GetMapping(value = "/devices", produces = "application/json")
    public PagedModel getDeviceList(Pageable pageable, PagedResourcesAssembler<EntityModel<DeviceResp>> assembler) {

        Page<Device> validatorOptional = deviceRepository.findAll(pageable);



        return assembler.toModel(validatorOptional.map(x->{

            DeviceResp deviceResp = DeviceResp.from(x);


            EntityModel entityModel = EntityModel.of(deviceResp);
            DeviceResp.Info info = new DeviceResp.Info();
            info.setLine(DeviceService.deveceInfo.getOrDefault(x.getId(), EnumDeviceOnline.offline));
            info.setLine_text(DeviceService.deveceInfo.getOrDefault(x.getId(), EnumDeviceOnline.offline).toString());
            info.setLastSeen_at(LocalDateTime.now());

            deviceResp.setLive_info(info);

            entityModel.add(linkTo(methodOn(DeviceController.class).getDevice(x.getId())).withSelfRel());

            entityModel.add(linkTo(methodOn(DeviceController.class).delete(x.getId())).withRel("delete"));

            return entityModel;
        }));

    }

    @DeleteMapping(value = "/devices/{device_id}")
    public ResponseEntity<Boolean> delete(@PathVariable long device_id) {
        //@RequestParam("files") List<MultipartFile> files

        Optional<Device> validatorOptional = deviceRepository.findById(device_id);


        if(validatorOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");

        }
        Device device = validatorOptional.get();


        deviceRepository.delete(device);

        return ResponseEntity.ok(true);

    }

    @PostMapping(value = "devices/{device_id}/Deactivate", produces = "application/json")
    public Device Deactivate(@PathVariable long device_id) {

        Optional<Device> validatorOptional = deviceRepository.findById(device_id);
        if(validatorOptional.isEmpty()){
            System.out.println("抛出异常");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }


        return validatorOptional.get();


    }










/*


    @PostMapping(value = "/parking-control/{device_id}/visitor-cars/{visitor_car_id}/delay", produces = "application/json")
    public List<Calendar> ExtendAccessiblePeriodofVisitorVehicle(@PathVariable long device_id,@PathVariable String visitor_car_id,@RequestBody VisitorVehicleDelayPojo visitorVehicleDelayPojo) {

        Optional<Device> validatorOptional = deviceRepository.findById(device_id);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }



    }*/



    @PostMapping(value = "/device-registration/single-bluetooth/actions/active", produces = "application/json")
    public List<Calendar> ActivateDevice(@PathVariable long device_id,@PathVariable String asset_id,@RequestBody VisitorVehicleDelayPojo visitorVehicleDelayPojo) {

        Optional<Device> validatorOptional = deviceRepository.findById(device_id);
        if(validatorOptional.isPresent()){

        }
        Device device = validatorOptional.get();

        AssetDevice assetDevice = new AssetDevice();

        return deviceService.ActivateDevice(device,assetDevice);


    }





  // TODO   先注册,然后激活。 最后绑定给 某个 资产，       某个资产 分配给 某结构

    //把设备绑定给资产把设备绑定给资产把设备绑定给资产把设备绑定给资产把设备绑定给资产

    @PostMapping(value = "/device-registration/single-bluetooth/actions/register", produces = "application/json")
    public ResponseEntity<Device> registerDevice(@RequestBody @Valid DeviceRegisterPojo deviceRegisterPojo) {


 /*       Optional<AssetDevice> assetDeviceOptional = assetDeviceRepository.findById(deviceRegisterPojo.getAsset_id());

        if(assetDeviceOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到 asset");

        }
        AssetDevice assetDevice = assetDeviceOptional.get();
*/
                Device device =  deviceService.register(null,deviceRegisterPojo);

                return ResponseEntity.ok(device);

    }

















    // TODO 这里 是 把资产 赋值给了，   用户（公司或个人）
    @PostMapping(value = "/users/{uid}/actions/batch-assets-authorized", produces = "application/json")
    public  List<AssetAuthorized> AuthorizeAssetsUser(@PathVariable long uid, @RequestBody AuthorizeAssetsUserPojo visitorVehicleDelayPojo) {


        Optional<Supplier> validatorOptional = supplierRepository.findById(uid);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到企业");

        }

        Supplier supplier = validatorOptional.get();



        List<AssetDevice> assetDeviceList = assetDeviceRepository.findAllById(visitorVehicleDelayPojo.getAsset_ids());


        if(assetDeviceList.size() == 0) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到 资产");

        }

        List<AssetAuthorized> device =  deviceService.AuthorizeAssetsUser(supplier,assetDeviceList);


        return device;


    }










/*

    @PostMapping(value = "/users/{uid}/actions/batch-assets-authorized", produces = "application/json")
    public List<Calendar> 把设备绑定给资产(@PathVariable long uid, List<String> asset_ids,Boolean authorized_children,@RequestBody VisitorVehicleDelayPojo visitorVehicleDelayPojo) {

        Optional<Product> validatorOptional = productRepository.findById(PRODUCT_ID);
        if(validatorOptional.isPresent()){
            try {
                return availabilityService.listAvailability(validatorOptional.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));



    }*/






    // TODO 这里 是 把资产 赋值给了，   用户（公司或个人）
    @PostMapping(value = "/devices/{device_id}/commands", produces = "application/json")
    public  ResponseEntity sendCommand(@PathVariable long device_id,  @RequestBody DeviceSendCommandPojo deviceSendCommandPojo) {


        Optional<Device> validatorOptional = deviceRepository.findById(device_id);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");

        }

        Device supplier = validatorOptional.get();



        String device =  deviceService.command(supplier,deviceSendCommandPojo);


        return ResponseEntity.ok(device);


    }

















    // TODO 这里 是 把资产 赋值给了，   用户（公司或个人）
    @PostMapping(value = "/assets", produces = "application/json")
    public  AssetDevice addAsset(@RequestBody AssetAddReq deviceSendCommandPojo) {



        AssetDevice device =  deviceService.createAsset(deviceSendCommandPojo);


        return device;


    }




    // TODO 这里 是 把资产 赋值给了，   用户（公司或个人）
    @GetMapping(value = "/assets_/{asset_id}", produces = "application/json")
    public  AssetNode getAsset(@PathVariable long asset_id) {



        Optional<AssetDevice> validatorOptional = assetDeviceRepository.findById(asset_id);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到企业");

        }


        AssetDevice device = validatorOptional.get() ;

        AssetNode assetNode =  deviceService.queryHierarchyUserMenusByMenusId(device.getId());


        return assetNode;


    }











    // TODO 这里 是 把资产 赋值给了，   用户（公司或个人）
    @GetMapping(value = "/assets/{asset_id}/devices", produces = "application/json")
    public  List<Device> getAssetDeviceList(@PathVariable long asset_id) {


        Optional<AssetDevice> validatorOptional = assetDeviceRepository.findById(asset_id);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到企业");
        }


        AssetDevice assetDevice = validatorOptional.get() ;

        List<Device> deviceList = deviceRepository.findByAssetId(assetDevice.getId());

        return deviceList;

    }









    @GetMapping(value = "/devices/{device_id}/component_vouchers", produces = "application/json")
    public PagedModel getDeviceList(@PathVariable long device_id,
                                    @PageableDefault(sort = {"createdDate", "modifiedDate"}, direction = Sort.Direction.DESC) final Pageable pageable ,


                                    PagedResourcesAssembler<EntityModel<DeviceResp>> assembler) {


        Optional<Device> validatorOptional = deviceRepository.findById(device_id);
        if(validatorOptional.isEmpty()){
            System.out.println("抛出异常");
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }
        Device device = validatorOptional.get();


        Page<ComponentVounchValidatorRecord> componentVounchValidatorRecords =
                componentVounchValidatorRecordRepository.findAllByValidatorTypeAndDevice(EnumValidatorType.特定机器,device.getId(),pageable);



        return assembler.toModel(componentVounchValidatorRecords.map(x->{

            //   DeviceResp deviceResp = DeviceResp.from(x);


            EntityModel entityModel = EntityModel.of(x);

   /*         entityModel.add(linkTo(methodOn(DeviceController.class).getDevice(x.getId())).withSelfRel());

            entityModel.add(linkTo(methodOn(DeviceController.class).delete(x.getId())).withRel("delete"));*/

            return entityModel;
        }));

    }



    // TODO 这里 是 把资产 赋值给了，   用户（公司或个人）
    @GetMapping(value = "/supplier/{uid}/assets", produces = "application/json")
    public  List<AssetDeviceResp> getAuthorizeAssetList(@PathVariable long uid) {


        Optional<Supplier> validatorOptional = supplierRepository.findById(uid);
        if(validatorOptional.isEmpty()) {

            throw new BookNotFoundException(Enumfailures.not_found,"找不到企业");

        }

        Supplier supplier = validatorOptional.get();



        List<AssetAuthorized> assetDeviceList = assetAuthorizedRepository.findAllByUid(supplier.getId());




        List<AssetDevice> device =  assetDeviceRepository.findAllById(assetDeviceList.stream().map(e->e.getAssetId()).collect(Collectors.toList()));

        Map<Long,AssetDevice> longAssetDeviceMap = device.stream().collect(Collectors.toMap(e->e.getId(), e->e));
        return assetDeviceList.stream().map(e->{
            AssetDevice assetDevice = longAssetDeviceMap.get(e.getAssetId());
            AssetDeviceResp assetDeviceResp = AssetDeviceResp.from(assetDevice,e);


            return assetDeviceResp;

        }).collect(Collectors.toList());



    }
















    @GetMapping(value = "/devices/{device_id}", produces = "application/json")
    public EntityModel getDevice(@PathVariable long device_id) {

        Optional<Device> validatorOptional = deviceRepository.findById(device_id);
        if(validatorOptional.isEmpty()){
            System.out.println("抛出异常");
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }

        Device device = validatorOptional.get();
        Optional<Supplier> optionalSupplier = supplierRepository.findById(device.getId() );


        DeviceEditResp deviceResp = DeviceEditResp.from(device);

        deviceResp.withSupplier(optionalSupplier);
















        DeviceEditResp.Info info = new DeviceEditResp.Info();
        info.setLine(DeviceService.deveceInfo.getOrDefault(device.getId(), EnumDeviceOnline.offline));
        deviceResp.setLive_info(info);



        DeviceEditResp.ComponentRightTab componentRightTab = new DeviceEditResp.ComponentRightTab();

        List<ComponentRight> componentRights = componentRightRepository.findAll();
        componentRightTab.setParameterList(
                Map.of(
                        "right_list",ComponentRight.List( componentRights)
                ));
        EntityModel entityModel_componentRightTab =  EntityModel.of(componentRightTab);
        entityModel_componentRightTab.add(linkTo(methodOn(DeviceController.class).updateValidator(device.getId(),null)).withRel("edit"));

        deviceResp.setComponentRightTab(entityModel_componentRightTab);





        List<Validator_> validator_s = validatorRepository.findAllByTypeAndDevice(EnumValidatorType.特定机器,device.getId());

        List<Long> triplet来自设备 =
                validator_s.stream().map(e->e.getComponentRightId()).collect(Collectors.toList());

        List<ComponentRight> componentRights1 = componentRightRepository.findAllById(triplet来自设备);

        List<ComponentRightResp> componentRightRespList = componentRights1.stream().map(e->{
            ComponentRightResp componentRightResp = ComponentRightResp.from(e);
            return componentRightResp;
        }).collect(Collectors.toList());

        deviceResp.setAllowed(componentRightRespList);

        componentRightTab.setRightIds(componentRights1.stream().map(e->e.getId()).collect(Collectors.toList()));


        EntityModel entityModel =  EntityModel.of(deviceResp);
        entityModel.add(linkTo(methodOn(DeviceController.class).delete(device.getId())).withRel("delete"));
        entityModel.add(linkTo(methodOn(DeviceController.class).getDevice(device.getId())).withSelfRel());


        return entityModel;


    }


    @PutMapping(value = "/devices/{device_id}/validators",produces = "application/json")
    public List<Validator_>  updateValidator(@PathVariable Long device_id,@RequestBody DeviceEditResp.ComponentRightTab pojo) {

        Optional<Device> validatorOptional = deviceRepository.findById(device_id);
        if(validatorOptional.isEmpty()){
            System.out.println("抛出异常");
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }
        Device device = validatorOptional.get();


        List<ComponentRight> componentRights = componentRightRepository.findAllById(pojo.getRightIds());

        return validatorService.newValidatorForDevice(device,componentRights);

    }









/*    @GetMapping(value = "/devices/{device_id}/component_vouchers", produces = "application/json")
    public PagedModel getDeviceList(@PathVariable long device_id,Pageable pageable, PagedResourcesAssembler<EntityModel<DeviceResp>> assembler) {


        Optional<Device> validatorOptional = deviceRepository.findById(device_id);
        if(validatorOptional.isEmpty()){
            System.out.println("抛出异常");
            throw new BookNotFoundException(Enumfailures.not_found,"找不到设备");
        }
        Device device = validatorOptional.get();


        Page<ComponentVounchValidatorRecord> componentVounchValidatorRecords =
                componentVounchValidatorRecordRepository.findAllByValidatorTypeAndDevice(EnumValidatorType.特定机器,device.getId(),pageable);



        return assembler.toModel(componentVounchValidatorRecords.map(x->{

         //   DeviceResp deviceResp = DeviceResp.from(x);


            EntityModel entityModel = EntityModel.of(x);

   *//*         entityModel.add(linkTo(methodOn(DeviceController.class).getDevice(x.getId())).withSelfRel());

            entityModel.add(linkTo(methodOn(DeviceController.class).delete(x.getId())).withRel("delete"));*//*

            return entityModel;
        }));

    }*/

}