package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AssetAddReq;
import com.lt.dom.otcReq.DeviceRegisterPojo;
import com.lt.dom.otcReq.DeviceSendCommandPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
/*
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
*/

import com.lt.dom.vo.AssetNode;
import com.lt.dom.vo.DeviceScanValidatorVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.stereotype.Component;
//import ua_parser.Client;
//import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class DeviceService {

    private static final String UNKNOWN = "UNKNOWN";


    public static Map<Long,EnumDeviceOnline> deveceInfo = new HashMap<>();

  //  @Value("${support.email}")
    private String from;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMetadataRepository deviceMetadataRepository;

    @Autowired
    private AssetDeviceRepository assetDeviceRepository;

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private AssetDeviceServiceImpl assetDeviceService;

    @Autowired
    private PassServiceImpl passService;


    @Autowired
    private IntoOnecodeServiceImpl intoOnecodeService;
    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private AutoGateRepository autoGateRepository;

    @Autowired
    private AssetAuthorizedRepository assetAuthorizedRepository;

  //  private DatabaseReader databaseReader;
   // private Parser parser;
 //   private JavaMailSender mailSender;
  //  private MessageSource messages;

    public DeviceService(DeviceMetadataRepository deviceMetadataRepository,
                      //   @Qualifier("GeoIPCity") DatabaseReader databaseReader,
                 //        Parser parser,
                  //       JavaMailSender mailSender,
                         MessageSource messages) {
        this.deviceMetadataRepository = deviceMetadataRepository;
      //  this.databaseReader = databaseReader;
     //   this.parser = parser;
     //   this.mailSender = mailSender;
      //  this.messages = messages;
    }

    public void verifyDevice(User user, HttpServletRequest request) throws IOException{//, GeoIp2Exception {

        String ip = extractIp(request);
        String location = getIpLocation(ip);

        String deviceDetails = getDeviceDetails(request.getHeader("user-agent"));

        DeviceMetadata existingDevice = findExistingDevice(user.getId(), deviceDetails, location);

        if (Objects.isNull(existingDevice)) {
            unknownDeviceNotification(deviceDetails, location, ip, user.getEmail(), request.getLocale());

            DeviceMetadata deviceMetadata = new DeviceMetadata();
            deviceMetadata.setUserId(user.getId());
            deviceMetadata.setLocation(location);
            deviceMetadata.setDeviceDetails(deviceDetails);
            deviceMetadata.setLastLoggedIn(new Date());
            deviceMetadata.setIp(ip);

            deviceMetadataRepository.save(deviceMetadata);
        } else {
            existingDevice.setLastLoggedIn(new Date());
            deviceMetadataRepository.save(existingDevice);
        }

    }

    private String extractIp(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request.getHeader("x-forwarded-for");
        if (nonNull(clientXForwardedForIp)) {
            clientIp = parseXForwardedHeader(clientXForwardedForIp);
        } else {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    private String parseXForwardedHeader(String header) {
        return header.split(" *, *")[0];
    }

    private String getDeviceDetails(String userAgent) {
        String deviceDetails = UNKNOWN;

/*        Client client = parser.parse(userAgent);
        if (Objects.nonNull(client)) {
            deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor +
                    " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
        }*/

        return deviceDetails;
    }

    private String getIpLocation(String ip) throws IOException{//, GeoIp2Exception {

        String location = UNKNOWN;

        InetAddress ipAddress = InetAddress.getByName(ip);

/*
        CityResponse cityResponse = databaseReader.city(ipAddress);
        if (Objects.nonNull(cityResponse) &&
                Objects.nonNull(cityResponse.getCity()) &&
                !Strings.isNullOrEmpty(cityResponse.getCity().getName())) {

            location = cityResponse.getCity().getName();
        }
*/

        return location;
    }

    private DeviceMetadata findExistingDevice(Long userId, String deviceDetails, String location) {

        List<DeviceMetadata> knownDevices = deviceMetadataRepository.findByUserId(userId);

        for (DeviceMetadata existingDevice : knownDevices) {
            if (existingDevice.getDeviceDetails().equals(deviceDetails) &&
                    existingDevice.getLocation().equals(location)) {
                return existingDevice;
            }
        }

        return null;
    }

    private void unknownDeviceNotification(String deviceDetails, String location, String ip, String email, Locale locale) {
        final String subject = "New Login Notification";
        final SimpleMailMessage notification = new SimpleMailMessage();
        notification.setTo(email);
        notification.setSubject(subject);

        String text = getMessage("message.login.notification.deviceDetails", locale) +
                " " + deviceDetails +
                "\n" +
                    getMessage("message.login.notification.location", locale) +
                " " + location +
                "\n" +
                    getMessage("message.login.notification.ip", locale) +
                " " + ip;

        notification.setText(text);
        notification.setFrom(from);

     //   mailSender.send(notification);
    }

    private String getMessage(String code, Locale locale) {
        try {
            return null;//messages.getMessage(code, null, locale);

        } catch (NoSuchMessageException ex) {
            return null;//messages.getMessage(code, null, Locale.ENGLISH);
        }
    }

    public Device register(AssetDevice assetDevice, DeviceRegisterPojo deviceRegisterPojo) {


        Device device = new Device();
        device.setType(deviceRegisterPojo.getType());
    //    device.setAssetId(assetDevice.getId());
        device.setCreate_time(LocalDateTime.now());
        device.setStatus(EnumDeviceStatus.registered);
        device.setName(deviceRegisterPojo.getName());
        device.setSerialNumber(deviceRegisterPojo.getSerialNumber());
        device.setUpdate_time(device.getCreate_time());
        device.setProject_id("project_id");
        device.setManufacturer("Manufacturer");
        device.setId_(UUID.randomUUID().toString());



        if(deviceRegisterPojo.getType().equals(EnumDeviceType.OUTLET)){


            AutoGate autoGate = new AutoGate();
          //  autoGate.setName(deviceRegisterPojo.getAuto_gate().getName());
            autoGate.setSN(deviceRegisterPojo.getSerialNumber());

            autoGateRepository.save(autoGate);
        }

        if(deviceRegisterPojo.getType().equals(EnumDeviceType.HANDSET)){


/*            AutoGate autoGate = new AutoGate();
            autoGate.setName(deviceRegisterPojo.getAuto_gate().getName());
            autoGate.setSN(deviceRegisterPojo.getSerialNumber());

            autoGateRepository.save(autoGate);*/
        }

        if(deviceRegisterPojo.getType().equals(EnumDeviceType.FICIAL_RECOGNITION)){


        }
        device = deviceRepository.save(device);

        return device;
    }

    public List<AssetAuthorized>  AuthorizeAssetsUser(Supplier supplier, List<AssetDevice> assetDeviceList) {


        List<AssetAuthorized> assetDevices = assetDeviceList.stream().map(e->{
            AssetAuthorized device = new AssetAuthorized();
            device.setUid(supplier.getId());
            device.setOwnerType(EnumUserType.business);
            device.setAuthorized_children(true);
           // device.setEnumDeviceType(e.getEnumDeviceType());
            device.setAssetId(e.getId());
            return device;
        }).collect(Collectors.toList());


        return assetAuthorizedRepository.saveAll(assetDevices);
    }

    public List<Calendar> ActivateDevice(Device device, AssetDevice assetDevice) {
        return null;
    }

    public String command(Device device, DeviceSendCommandPojo deviceSendCommandPojo) {


       Optional<DeviceSendCommandPojo.Command> commandOptional =  deviceSendCommandPojo.getCommands().stream().filter(e->e.getCode().equals(EnumDeviceCommand.barcode)).findAny();

       if(commandOptional.isPresent()){
           DeviceSendCommandPojo.Command command = commandOptional.get();

/*           Optional<AssetDevice> assetDevice = assetDeviceRepository.findById(device.getId());

           Optional<AssetAuthorized> optionalAssetAuthorized = assetAuthorizedRepository.findAllByAssetId(assetDevice.get().getId());

           if(optionalAssetAuthorized.isEmpty()){
               throw new BookNotFoundException(Enumfailures.not_found,assetDevice.get().getId()+" 找不到这个资源的 授权用户，资源id 是"+assetDevice.get().getAsset_name());

           }
           AssetAuthorized assetAuthorized = optionalAssetAuthorized.get();

           Optional<Supplier> supplier = supplierRepository.findById(assetAuthorized.getUid());
*/


           List<Validator_> validator_s = validatorRepository.findAllByTypeAndDevice(EnumValidatorType.特定机器,device.getId());

           System.out.println("=============获得 核验对象 "+ validator_s.toString());

           User user = intoOnecodeService.byCode(command.getValue());


           List<ComponentVounch> listListListTriplet = assetDeviceService.通过设备获得管理者(user);


           System.out.println("=============获得 用户现在的 权益 "+ listListListTriplet.toString());
           DeviceScanValidatorVo deviceScanValidatorVo = assetDeviceService.扫设备(user,
                   validator_s.stream().map(e->e.getComponentRightId()).collect(Collectors.toList()),
                   listListListTriplet);

           return deviceScanValidatorVo.toString();

       }else{
           throw new BookNotFoundException(Enumfailures.not_found,"不支持的指令"+deviceSendCommandPojo.getCommands().stream().map(e->e.getCode()).collect(Collectors.toList()));
       }
    }






    public AssetDevice createAsset(AssetAddReq deviceSendCommandPojo) {

        AssetDevice assetDevice = new AssetDevice();
        assetDevice.setAsset_name(deviceSendCommandPojo.getName());
        assetDevice.setAsset_full_name(deviceSendCommandPojo.getFull_name());
        assetDevice.setAsset_id(idGenService.assetDeviceCode());


        if(deviceSendCommandPojo.getParent_asset_id() == null){
            deviceSendCommandPojo.setParent_asset_id(1l);
        }
        Optional<AssetDevice> assetDeviceP = assetDeviceRepository.findById(deviceSendCommandPojo.getParent_asset_id());
        if(assetDeviceP.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到");
        }


        AssetDevice assetDeviceParent = assetDeviceP.get();

        assetDevice.setParent_asset_id(assetDeviceParent.getId());


        assetDevice = assetDeviceRepository.save(assetDevice);

        return assetDevice;
    }


    public AssetDevice setupdata(AssetAddReq deviceSendCommandPojo) {

        AssetDevice assetDevice = new AssetDevice();
        assetDevice.setAsset_name(deviceSendCommandPojo.getName());
        assetDevice.setAsset_full_name(deviceSendCommandPojo.getFull_name());
        assetDevice.setAsset_id(idGenService.assetDeviceCode());

        assetDevice.setParent_asset_id(-1L);
        assetDevice.setLevel(0);
        assetDevice.setId(0);

        assetDevice = assetDeviceRepository.save(assetDevice);

        return assetDevice;
    }







    private List<AssetNode> getSubMenus(List<AssetNode> menuNodes, Long parentId) {
        List<AssetNode> childs = new ArrayList<>();

        for (AssetNode menuNode : menuNodes) {


            if (parentId.equals(menuNode.getCurrent().getParent_asset_id())) {
                List<AssetNode> _childs = getSubMenus(menuNodes, menuNode.getCurrent().getId());
                menuNode.setChilds(_childs);
                childs.add(menuNode);
            }

        }
        return childs;
    }



    public AssetNode queryHierarchyUserMenusByMenusId(Long userId) {


        // List<Menu> menuList = queryUserMenus(user.getId());

        List<AssetDevice> menuList = assetDeviceRepository.findAll();

        if (menuList == null || menuList.size() == 0) {
            return null;
        }
        List<AssetNode> menuNodeList = new ArrayList<>();

        for (AssetDevice menu : menuList) {
            menuNodeList.add(new AssetNode(menu));
        }

        //Menu rootMenu = menuList.get(0);
        AssetDevice rootMenu = menuList.stream().filter(e->e.getId()== userId).findFirst().get();
        AssetNode rootNode = new AssetNode(rootMenu);
        List<AssetNode> menuNodes = getSubMenus(menuNodeList, rootMenu.getId());
        rootNode.setChilds(menuNodes);
        return rootNode;
    }

    public void OnlineDevice(Device device) {

        deveceInfo.put(device.getId(),EnumDeviceOnline.online);
    }
}