package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.oct.ValidatorParking.VounchLicense;
import com.lt.dom.otcenum.EnumVoucherType;
import com.lt.dom.repository.*;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WriteoffEquipServiceImpl {

    @Autowired
    private VounchLicenseRepository vounchLicenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private RedemptionEntryRepository redemptionEntryRepositorye;

    @Autowired
    private PublicationEntryRepository publicationEntryRepository;

    @Autowired
    private AssetRepository assetRepository;



    public List<Validator> listAvailability(Voucher voucher,    String type) {


        if(voucher.getType().equals(EnumVoucherType.RIGHT_VOUCHER)){


            ComponentVounch componentVounch = voucher.getComponentVounch();


            Optional<ComponentRight> componentRight = componentRightRepository.findById(componentVounch.getComponentRightId());

            return componentRight.get().getAccessValidators().stream()
                    .filter(x->x.getExtend().equals("type")).map(x->x.getValidators())
                    .flatMap(List::stream)
                    .collect(Collectors.toList());







        };

        throw new RuntimeException();



    }



    private boolean valid(Voucher voucher,    Validator validator) {


        if(voucher.getType().equals(EnumVoucherType.RIGHT_VOUCHER)){


            ComponentVounch componentVounch = voucher.getComponentVounch();


            Optional<ComponentRight> componentRight = componentRightRepository.findById(componentVounch.getComponentRightId());

            return componentRight.get().getAccessValidators().stream()
                    .map(x->x.getValidators())
                    .flatMap(List::stream)
                    .collect(Collectors.toList()).stream().filter(x->x.getId() == validator.getId()).findAny().isPresent();


        };

        throw new RuntimeException();



    }





    public RedemptionEntry writeOff(Validator validator,Voucher voucher) {

        if(valid(voucher,validator)){
            RedemptionEntry redemptionEntry = new RedemptionEntry();
            redemptionEntry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);
            redemptionEntry.setVoucher(voucher.getId());
            redemptionEntry.setValidatorId(validator.getId());
            //  redemptionEntry.setCustomer_id(entry.getCustomerId());
            redemptionEntry =  redemptionEntryRepositorye.save(redemptionEntry);
            voucher.setRedeemed_quantity(voucher.getRedeemed_quantity()+1);

        }
        throw new RuntimeException();
    }



    public RedemptionEntry writeOff_GIFT_VOUCHER(Validator validator,Voucher voucher,int amount) {

        if(!writeOff_Check(voucher)){
            throw new RuntimeException();
        };

        if(voucher.getType().equals(EnumVoucherType.GIFT_VOUCHER))
            throw new RuntimeException();

        if(valid(voucher,validator)){
            RedemptionEntry redemptionEntry = new RedemptionEntry();
            redemptionEntry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);
            redemptionEntry.setVoucher(voucher.getId());
            redemptionEntry.setValidatorId(validator.getId());
            //  redemptionEntry.setCustomer_id(entry.getCustomerId());
            redemptionEntry =  redemptionEntryRepositorye.save(redemptionEntry);
            voucher.setRedeemed_amount(voucher.getRedeemed_amount()+amount);

        }
        throw new RuntimeException();
    }


    private boolean writeOff_Check(Voucher voucher) {


        if(voucher.getExpiration_date().isAfter(LocalDateTime.now()) && voucher.getStart_date().isBefore(LocalDateTime.now())){
            throw new RuntimeException();
        }
        if(voucher.getQuantity() <= voucher.getRedeemed_quantity()){
            throw new RuntimeException();
        }

        return true;
    }








    public void 人脸识别(Validator validator,String 身份证) {


        Optional<User> optionalUser = userRepository.findById(1l); //身份证号码

        if(optionalUser.isEmpty()){
            throw new RuntimeException();
        }


        PublicationEntry publicationEntry = new PublicationEntry();
        publicationEntry.setToWho(optionalUser.get().getId());
        Example<PublicationEntry> exampleB = Example.of(publicationEntry);
        Optional<PublicationEntry> optionalPublicationEntry = publicationEntryRepository.findOne(exampleB);

        if(optionalPublicationEntry.isEmpty()){
            throw new RuntimeException();
        }


        Optional<Voucher> voucher = voucherRepository.findById(optionalPublicationEntry.get().getVoucher());

        if(voucher.isEmpty()){
            throw new RuntimeException();
        }



        writeOff(validator,voucher.get());

    }




    public void 券二维码(Validator validator,String code) {


        Asset asset_e = new Asset();
        asset_e.setIdId(code);
        Example<Asset> exampleB = Example.of(asset_e);
        Optional<Asset> optionalPublicationEntry = assetRepository.findOne(exampleB);

        Asset asset =   optionalPublicationEntry.get();


        Optional<Voucher> voucher = voucherRepository.findById(asset.getSource());



        if(voucher.isEmpty()){
            throw new RuntimeException();
        }



        writeOff(validator,voucher.get());

    }



    public void 文旅码(Validator validator,String code) {


        Optional<User> optionalUser = userRepository.findById(1l); //身份证号码


        if(optionalUser.isEmpty()){
            throw new RuntimeException();
        }


        PublicationEntry publicationEntry = new PublicationEntry();
        publicationEntry.setToWho(optionalUser.get().getId());
        Example<PublicationEntry> exampleB = Example.of(publicationEntry);
        Optional<PublicationEntry> optionalPublicationEntry = publicationEntryRepository.findOne(exampleB);

        if(optionalPublicationEntry.isEmpty()){
            throw new RuntimeException();
        }


        Optional<Voucher> voucher = voucherRepository.findById(optionalPublicationEntry.get().getVoucher());

        if(voucher.isEmpty()){
            throw new RuntimeException();
        }



        writeOff(validator,voucher.get());

    }




    public void 车牌(Validator validator,String license) {



        VounchLicense vounchLicense = vounchLicenseRepository.findByLicense(license);


        Optional<Voucher> voucher = voucherRepository.findById(vounchLicense.getVounchId());

        if(voucher.isEmpty()){
            throw new RuntimeException();
        }



        writeOff(validator,voucher.get());

    }



    public void mqtt(String validators, String value0) throws Exception {

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);


        String publisherId = UUID.randomUUID().toString();
        IMqttClient publisher = new MqttClient("tcp://iot.eclipse.org:1883",publisherId);
        publisher.connect(options);


        String deviceName = validators;
        String productKey = validators;

        // Publish messages by using Paho MQTT.
        String topic = "/sys/" + productKey + "/" + deviceName + "/thing/event/property/post";
        String content = "{\"id\":\"1\",\"version\":\"1.0\",\"params\":{\"LightSwitch\":1}}";
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(0);
        publisher.publish(topic, message);
    }
}
