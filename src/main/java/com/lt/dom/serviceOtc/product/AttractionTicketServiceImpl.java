package com.lt.dom.serviceOtc.product;


import com.lt.dom.OctResp.AttractionResp;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.VoucherTicketResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.AttractionRepository;
import com.lt.dom.repository.ComponentRepository;
import com.lt.dom.repository.VoucherTicketRepository;
import com.lt.dom.requestvo.BookingTypeTowhoVo;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.ComponentRightServiceImpl;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.ProductServiceImpl;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.lt.dom.serviceOtc.JsonParse.GSON;

@Service
public class AttractionTicketServiceImpl {

    @Autowired
    private LtConfig ltConfig;

    @Autowired
    private AttractionRepository attractionRepository;


    @Autowired
    private AvailabilityServiceImpl availabilityService;


    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private VoucherTicketRepository voucherTicketRepository;
    @Autowired
    private ComponentRightServiceImpl componentRightService;

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

        if(product.getType().equals(EnumProductType.Attraction)){
            Optional<Attraction> attractionOptional = attractionRepository.findById(product.getTypeTo());


            if(attractionOptional.isPresent()){
                Attraction  attraction = attractionOptional.get();
                productResp.setAttraction(AttractionResp.simpleFrom(attraction));
            }
            productResp.setOptions(Arrays.asList());

        }
    }

    public void booking(LineItem lineItem, Product product) {
        if(!product.getType().equals(productType)){

            return;
        }
        lineItem.setLineType(lineType);
        lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Create_voucher);
        lineItem.setFulfillmentInstructionsType(EnumFulfillmentInstructionsType.DIGITAL);

    }


    private static final EnumProductType productType = EnumProductType.Attraction;
    private static final EnumLineType lineType = EnumLineType.TICKIT;
    private static final EnumVoucherType voucherType = EnumVoucherType.TICKET;
    public void fullfil(Reservation reservation, List<LineItem> lineItemList, User payer) {


        lineItemList = lineItemList.stream().filter(e->e.getLineType().equals(lineType)).collect(Collectors.toList());




        lineItemList.stream().forEach(e->{
            System.out.println(e.getQuantity()+"建立  取票却啊啊啊啊  ，团购票啊啊啊啊啊啊"+e.getProduct());



        });

         lineItemList.stream().collect(Collectors.groupingBy(e->e.getProduct()))
                .entrySet().stream().forEach(e->{

System.out.println("建立  取票却啊啊啊啊  ，团购票啊啊啊啊啊啊");


                     Pair<Product,Attraction> productAttractionPair = productService.attract(e.getKey());

                     Attraction attraction = productAttractionPair.getValue1();
                     Product product = productAttractionPair.getValue0();

                    VoucherTicket voucher_voucher = new VoucherTicket();
                    voucher_voucher.setBooking(reservation.getId());
                    voucher_voucher.setUser(payer.getId());
                    voucher_voucher.setCode(idGenService.ticketCode());
                    voucher_voucher.setType(EnumVoucherType.VOUCHER);
                    voucher_voucher.setStatus(EnumVoucherStatus.Created);
                    voucher_voucher.setPublished(false);
                    voucher_voucher.setActive(false);


                    voucher_voucher.setLable(attraction.getName());


                     VoucherTicket.Attraction attraction1 = new VoucherTicket.Attraction();
                     attraction1.setAttraction(attraction.getId());
                     voucher_voucher.setData_json(GSON.toJson(attraction1));




                     LocalDateTime ex = availabilityService.getExpiry_datetime(product);
                     voucher_voucher.setExpiry_datetime(ex);
                     voucher_voucher.setExpiration_date(ex);

                    voucher_voucher = voucherTicketRepository.save(voucher_voucher);

                });






        lineItemList.stream().forEach(bookingProduct -> {


            Pair<Product,Attraction> productAttractionPair = productService.attract(bookingProduct.getProduct());

            Attraction attraction = productAttractionPair.getValue1();
            Product product = productAttractionPair.getValue0();



                    List<Component> componentRightList = componentRepository.findAllByProduct(bookingProduct.getProduct());


                    List<VoucherTicket> vouchers = LongStream.range(0, bookingProduct.getQuantity()).boxed().map(x -> {

                        System.out.println("建立  取票却啊啊啊啊  ，团购票啊啊啊啊啊啊"+x+"第几啊啊");

                        VoucherTicket voucher = new VoucherTicket();

                        voucher.setBooking(reservation.getId());
                        voucher.setUser(payer.getId());
                        voucher.setCode(idGenService.ticketCode());
                        voucher.setType(voucherType);

                        voucher.setStatus(EnumVoucherStatus.Created);
                        voucher.setPublished(false);
                        voucher.setActive(false);

                        voucher.setLable(attraction.getName());





                        LocalDateTime ex = availabilityService.getExpiry_datetime(product);
                        voucher.setExpiry_datetime(ex);
                        voucher.setExpiration_date(ex);



                        VoucherTicket.Attraction attraction1 = new VoucherTicket.Attraction();
                        attraction1.setAttraction(attraction.getId());
                        voucher.setData_json(GSON.toJson(attraction1));





                        return voucher;
                    }).collect(Collectors.toList());


            Long limit = 1l;

                    vouchers = voucherTicketRepository.saveAll(vouchers);
                    vouchers.stream().forEach(e->{
                        componentRightService.assingtoTicket(e,componentRightList, limit);

                    });


                }

        );


    }

    public void ticketShow(VoucherTicketResp voucherTicketResp, VoucherTicket voucherTicket) {
        if(!voucherTicket.getType().equals(voucherType)) {

            return;
        }
            VoucherTicket.Attraction showtime =  GSON.fromJson(voucherTicket.getData_json(),VoucherTicket.Attraction.class);

            Optional<Attraction> theatreOptional = attractionRepository.findById(showtime.getAttraction());
            if(theatreOptional.isPresent()){
                Attraction theatre = theatreOptional.get();
                VoucherTicketResp.Venue venue1 = new VoucherTicketResp.Venue();
                venue1.setName(theatre.getName());

                venue1.setLocation(LocationResp.from(theatre.getLocation()));
                EntityModel entityModel = EntityModel.of(venue1);
                voucherTicketResp.setVenue(entityModel);
            }




    }

    public void listShow(VoucherTicketResp voucherResp, VoucherTicket voucherTicket) {

        if(!voucherTicket.getType().equals(voucherType)) {

            return;
        }
        voucherResp.setText("这是景区入口门票");

            voucherResp.setData(GSON.fromJson(voucherTicket.getData_json(),VoucherTicket.Attraction.class));


    }







    public void booking_trial(List<BookingTypeTowhoVo> list) {

        List<BookingTypeTowhoVo> bookingTypeTowhoVoList = list.stream().filter(e->e.getProduct().getType().equals(productType)).map(e->{

            return e;
        }).collect(Collectors.toList());

        if(bookingTypeTowhoVoList.size() == 0){
            return;
        }


        List<String> id_number_list = bookingTypeTowhoVoList.stream().map(e->{


            return e.getTraveler().stream().map(travelerReq->travelerReq.getId_card()).collect(Collectors.toList());
        }).flatMap(List::stream).collect(Collectors.toList());

        if(id_number_list.size()==0){
            throw new ExistException(Enumfailures.invalid_amount,"至少需要添加一名旅客");

        }

      /*  List<String> ids = id_number_list.stream().map(e->{
            List<Cardholder> optionalLong = cardholderRepository.findByIdentity(e);
            if(optionalLong.size()>0){
                return Pair.with(true,optionalLong.get(0).getIdentity());
            }else{
                return Pair.with(false,"null");
            }
        }).filter(e->e.getValue0()).map(e->e.getValue1()).collect(Collectors.toList());

        if(ids.size()>0){
            throw new ExistException(Enumfailures.invalid_amount,"这些游客存在主人卡，不能购买"+ids);
        }*/
    }
}
