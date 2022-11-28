package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.product.*;
import com.lt.dom.vo.PlatUserVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ComponentRightResolveServiceImpl {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PassServiceImpl passService;
    @Autowired
    private AssetServiceImpl assetService;

 /*   @Autowired
    private BookingServiceImpl bookingService;
*/
    @Autowired
    private ValidatorServiceImpl validatorService;

    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private LineItemRepository lineItemRepository;
    @Autowired
    private TravelerRepository travelerRepository;


    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private VoucherServiceImpl voucherService;

    @Autowired
    private BusTicketServiceImpl busTicketService;

    @Autowired
    private ShowtimeTicketServiceImpl showtimeTicketService;
    @Autowired
    private MultiTicketServiceImpl multiTicketService;


    @Autowired
    private AttractionTicketServiceImpl attractionTicketService;
    @Autowired
    private CityPassServiceImpl cityPassService;


    public void resolve(Reservation reservation, EnumFulfillment_behavior followupPaid) {

      //  if(reservation.getSetValidate_way().equals(EnumValidateWay.none)){
          //  resolve_(reservation,followupPaid);
        resolve_foreach_sku(reservation);

/*
        if(reservation.getSetValidate_way().equals(EnumValidateWay.offline_manual)){

        }*/
    }


    public void resolve_foreach_sku(Reservation reservation) {


        System.out.println("-------------看看fulfillment ----------- 不知道是在哪里啊啊啊啊 ");

        PlatUserVo platUserVo = new PlatUserVo();
        platUserVo.setPlatform(reservation.getPlatform());
        platUserVo.setId_number(reservation.getLog_buyer_id_number());

        if(reservation.getPlatform().equals(EnumPlatform.TS)){

        }else{
            Optional<User> objectUser = userRepository.findById(reservation.getUser());

            platUserVo.setUser(objectUser.get());
        }


        List<LineItem> list = lineItemRepository.findAllByBooking(reservation.getId());



  //voucherService.createVoucherBig_forAttract_entry_ticket(reservation,list,objectUser.get(),12);


     //   voucherService.createVoucherBig(reservation,list,objectUser.get(),12);
        busTicketService.fullfil(reservation,list,platUserVo.getUser());

       cityPassService.fullfil(reservation,list,platUserVo);
       attractionTicketService.fullfil(reservation,list,platUserVo.getUser());
       showtimeTicketService.fullfil(reservation,list,platUserVo.getUser());

        multiTicketService.fullfil(reservation,list,platUserVo.getUser());
        if(true){
            return ;
        }


        Map<String,List<Traveler>> travelers = travelerRepository.findAllByBooking(reservation.getId())
                .stream().collect(Collectors.groupingBy(e->e.getReferSku()));





        IntStream.range(0,list.size()).forEach(i->{
            LineItem bookingProduct = list.get(i);
            List<Traveler> traveler = travelers.get(bookingProduct.getReferTraveler());




            switch (bookingProduct.getFulfillment_behavior()){

                case Universal:{

                    System.out.println("-------------看看fulfillment --看看一般的完工啊啊啊 "+bookingProduct.getFulfillment_behavior());


                    List<ComponentVounch> componentVounchList =componentRightService.createComponentVounch(reservation,platUserVo.getUser());


                    Map<Long,User> longUserMap = userRepository.findAllById(componentVounchList.stream().map(e->e.getUser()).collect(Collectors.toList()))
                            .stream().collect(Collectors.toMap(e->e.getId(),e->e));

                    validatorService.push( componentVounchList.stream().map(e->{
                        User user = longUserMap.get(e.getUser());

                        return Pair.with(e,user);
                    }).collect(Collectors.toList()));
                }
                break;
                case Create_pass:{

                    System.out.println("-------------看看fulfillment --新建 主人卡的完工 "+bookingProduct.getFulfillment_behavior());

/*                    List<ComponentVounch> componentVounchList = componentRightService.createComponentVounch(reservation,objectUser.get());
                    Map<Long,User> longUserMap = userRepository.findAllById(componentVounchList.stream().map(e->e.getUser()).collect(Collectors.toList()))
                            .stream().collect(Collectors.toMap(e->e.getId(),e->e));
                    validatorService.push( componentVounchList.stream().map(e->{
                        User user = longUserMap.get(e.getUser());
                        return Pair.with(e,user);
                    }).collect(Collectors.toList()));*/




/*
                    traveler.stream().forEach(tra->{
                        Pass pass = passService.create_virtual(reservation,bookingProduct,tra,objectUser.get(),12);
                    });

                    bookingProduct.setCVC(idGenService.nextId(""));
                    bookingProduct.setFullfullmentStatus(EnumProductBookingFullfullmentStatus.Ordered);
                    bookingProductRepository.save(bookingProduct);
*/


/*                  componentRightService.groupby(componentVounchList,pass);
                    componentRightService.bulkUpdata(componentVounchList);*/
                }
                break;

                case Create_voucher: {

                    System.out.println("-------------看看fulfillment --新建票务系统 "+bookingProduct.getFulfillment_behavior());


/*                    traveler.stream().forEach(tra->{
                        Pass pass = voucherService.createVoucher(reservation,bookingProduct,tra,objectUser.get(),12);
                    });*/



 /*                   List<VoucherTicket> pass = voucherService.createVoucher(reservation,bookingProduct,objectUser.get(),12);

                    bookingProduct.setCVC(idGenService.nextId(""));

                    bookingProduct.setFullfullmentStatus(EnumProductBookingFullfullmentStatus.Ordered);

                    bookingProductRepository.save(bookingProduct);*/

                }


                case Free_ticket:{
                }
            }


            assetService.getWithNew(reservation.getCode(),reservation.getId());


        });





    }

    public void resolve_(Reservation reservation, EnumFulfillment_behavior fulfillmentBehavior) {


        System.out.println("-------------看看fulfillment ----------- 不知道是在哪里啊啊啊啊 "+fulfillmentBehavior);

        Optional<User> objectUser = userRepository.findById(reservation.getUser());

        List<LineItem> list = lineItemRepository.findAllByBooking(reservation.getId());



        switch (fulfillmentBehavior){

            case Universal:{

                System.out.println("-------------看看fulfillment --看看一般的完工啊啊啊 "+fulfillmentBehavior);





                List<ComponentVounch> componentVounchList =componentRightService.createComponentVounch(reservation,objectUser.get());


                Map<Long,User> longUserMap = userRepository.findAllById(componentVounchList.stream().map(e->e.getUser()).collect(Collectors.toList()))
                        .stream().collect(Collectors.toMap(e->e.getId(),e->e));

               ;
                validatorService.push( componentVounchList.stream().map(e->{
                    User user = longUserMap.get(e.getUser());

                    return Pair.with(e,user);
                }).collect(Collectors.toList()));
            }
            break;
            case Create_pass:{

                System.out.println("-------------看看fulfillment --新建 主人卡的完工 "+fulfillmentBehavior);

                List<ComponentVounch> componentVounchList = componentRightService.createComponentVounch(reservation,objectUser.get());

                Map<Long,User> longUserMap = userRepository.findAllById(componentVounchList.stream().map(e->e.getUser()).collect(Collectors.toList()))
                        .stream().collect(Collectors.toMap(e->e.getId(),e->e));

                validatorService.push( componentVounchList.stream().map(e->{
                    User user = longUserMap.get(e.getUser());

                    return Pair.with(e,user);
                }).collect(Collectors.toList()));

                Pass pass = passService.create_virtual(objectUser.get(),12);
                componentRightService.groupby(componentVounchList,pass);
                componentRightService.bulkUpdata(componentVounchList);
            }
            break;
            case Free_ticket:{
            }
        }
        assetService.getWithNew(reservation.getCode(),reservation.getId());




    }
 /*   public void resolve_with_manual_validate(Reservation reservation, EnumFulfillment_behavior followupPaid) {

        resolve_(reservation,followupPaid);

        reservation.setValidationStatus(EnumValidationStatus.ValidationSucceeded);

        bookingService.update(reservation);

    }*/

}
