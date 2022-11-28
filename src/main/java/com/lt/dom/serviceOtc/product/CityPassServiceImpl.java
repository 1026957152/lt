package com.lt.dom.serviceOtc.product;


import com.lt.dom.OctResp.*;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.PassServiceImpl;
import com.lt.dom.vo.PlatUserVo;
import org.apache.commons.lang.RandomStringUtils;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.lt.dom.serviceOtc.JsonParse.GSON;

@Service
public class CityPassServiceImpl {

    @Autowired
    private PassServiceImpl passService;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private ReservationRepository reservationRepository;



    @Autowired
    private TravelerRepository travelerRepository
            ;

    @Autowired
    private FulfillmentRepository fulfillmentRepository;
    @Autowired
    private IntelliCodeRepository intelliCodeRepository;

    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private IdGenServiceImpl idGenService;





    // 这里是订单 支付后
    public Asset pay(Supplier supplier) {

        return null;
    }

    // 这里是 订单 确认后
    public Asset fullfil_behaivor(Supplier supplier) {

        return null;
    }
    public List<Asset> component_redeam_behaivor(List<Pair<String, Long>> collect) {
// TODO 权益核销
        return null;
    }

    public List<Asset> bill_behaiver(List<Pair<String, Long>> collect) {
// TODO 对权益进行 核销
        return null;
    }


    public void show(ProductResp productResp, Product product) {

        List<Component> componentRightPage= componentRepository.findAllByProduct(product.getId());

        if(product.getType().equals(EnumProductType.Pass)){

            List<ComponentRight> componentRightList= componentRightRepository.findAllById(componentRightPage.stream().map(e->e.getComponentRightId()).collect(Collectors.toList()));

            ProductOptionResp productOption = new ProductOptionResp();


            productOption.setType(EnumProductOptionType.radio_buttons);
            productOption.setDisplay_name("权益组合");
            productOption.setTip("支持客户自定义游玩景点");
            productOption.setKey("rights");
            productOption.setOption_values(componentRightList.stream().map(e->{

                ProductOptionValueResp productOptionValue = new ProductOptionValueResp();
                productOptionValue.setLabel(e.getName());
                productOptionValue.setId(e.getId());
                productOptionValue.setDefault(true);
                return productOptionValue;

            }).collect(Collectors.toList()));


            ProductOptionResp productOption_guide = new ProductOptionResp();


            productOption_guide.setType(EnumProductOptionType.radio_buttons);
            productOption_guide.setDisplay_name("导游");
            productOption_guide.setTip("支持客户自定义游玩景点");
            productOption_guide.setKey("guide");



            productOption_guide.setOption_values(Arrays.stream(EnumGuideType.values()).map(e->{

                ProductOptionValueRespStringId productOptionValue = new ProductOptionValueRespStringId();
                productOptionValue.setLabel(e.name());
                productOptionValue.setId(e.name());
                productOptionValue.setDefault(true);
                return productOptionValue;

            }).collect(Collectors.toList()));

            productResp.setOptions(Arrays.asList(productOption,productOption_guide));

        }



    }

    private static final EnumProductType productType = EnumProductType.Pass;
    private static final EnumLineType lineType = EnumLineType.Pass;
    private static final EnumVoucherType voucherType = EnumVoucherType.Multi_Ticket;


    public void booking(LineItem lineItem, Product product) {
        if(!product.getType().equals(productType)){
            return;
        }

        lineItem.setLineType(lineType);
        lineItem.setStatus(EnumLineItemStatus.Executing);
        lineItem.setBillingTriggerRule(EnumBillingTriggerRule.WithoutFulfillment);


        lineItem.setFulfillmentInstructionsType(EnumFulfillmentInstructionsType.DIGITAL);


     lineItem.setDeliveryFormats(EnumDeliveryFormats.虚拟卡);

        lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Create_pass);
    }

    public void fullfil(Reservation reservation, List<LineItem> lineItemList, PlatUserVo user) {


/*    Fulfillment fulfillment = new Fulfillment();


        fulfillment.setEnumFulfillmentInstructionType(EnumFulfillmentInstructionsType.DIGITAL);
        fulfillment.setType(EnumFulfillmentType.Delivery);
        fulfillment.setTracking_number("1111111111");
        //fulfillment.setActivityDate();

        fulfillmentRepository.save(fulfillment);*/




        lineItemList = lineItemList.stream().filter(e->e.getLineType().equals(lineType)).collect(Collectors.toList());



/*

        lineItemRepository.saveAll(list.stream().map(e->{

            e.setFullfullmentStatus(EnumProductBookingFullfullmentStatus.Ordered);

            return e;
        }).collect(Collectors.toList()));
*/



/*
        Map<String,List<Traveler>> travelers = travelerRepository.findAllByBooking(reservation.getId())
                .stream().collect(Collectors.groupingBy(e->e.getReferSku()));

*/

        List<LineItem> finalLineItemList = lineItemList;
        IntStream.range(0,lineItemList.size()).forEach(i-> {
            LineItem lineItem = finalLineItemList.get(i);




            List<Traveler> traveler = lineItem.getTravelers() ;//travelers.getOrDefault(lineItem.getReferTraveler(),new ArrayList<>());

            if(lineItem.getDeliveryFormats().equals(EnumDeliveryFormats.虚拟卡)){

                if(lineItem.getQuantity() != traveler.size()){

                    throw new BookNotFoundException(Enumfailures.resource_not_found,"订单数和 游客数不相符");
                }
                traveler.stream().forEach(tra->{

                    Cardholder cardholder = new Cardholder();
                    //cardholder.setCompany(pass.getId());
                    cardholder.setName(tra.getName());
                    cardholder.setIdentity(tra.getIdNo());
                    if(user.getPlatform().equals(EnumPlatform.TS)){
                        Pass pass = passService.create_virtual(lineItem,cardholder,10);

                    }else{
                        Pass pass = passService.create_virtual(lineItem,cardholder,user.getUserVo().getUser_id(),10);

                    }

                    lineItem.setDeliveryFormats(EnumDeliveryFormats.虚拟卡);
                });
            }
            if(lineItem.getDeliveryFormats().equals(EnumDeliveryFormats.虚拟卡)){
                List<IntelliCode> codes =  IntStream.range(0,lineItem.getQuantity()).mapToObj(e-> {
                    IntelliCode intelliCode = new IntelliCode();
                    intelliCode.setCode(idGenService.nextId("jh_"));
                    intelliCode.setMax_redemptions(1);
                    intelliCode.setActive(true);
                    intelliCode.setLineItem(lineItem.getId());
                    String generatedString = RandomStringUtils.randomNumeric(4);
                    intelliCode.setPin(generatedString);
                    intelliCode.setStatus(EnumIntelliCodeStatus.not_redeemed);
                    intelliCode.setBooking(lineItem.getBooking());

                    return intelliCode;
                }).collect(Collectors.toList());


                intelliCodeRepository.saveAll(codes);
                lineItem.setDeliveryFormats(EnumDeliveryFormats.激活码);


            }








          //  bookingProduct.setCVC(idGenService.nextId(""));
            lineItem.setStatus(EnumLineItemStatus.Complete);
            lineItemRepository.save(lineItem);


        });


        reservation.setStatus(EnumBookingStatus.Fulfilled);
        reservationRepository.save(reservation);

    }

    public void show(LineItemResp lineItemResp, LineItem lineItem) {


        if(!lineItem.getLineType().equals(lineType)){
            return;
        }

        List<IntelliCode> intelliCodes = intelliCodeRepository.findByLineItem(lineItem.getId());

        lineItemResp.setIntelliCodes(intelliCodes.stream().map(e->{

            return e;
        }).collect(Collectors.toList()));

    }
}
