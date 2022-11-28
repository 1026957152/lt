package com.lt.dom.serviceOtc.product;


import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.VoucherTicketResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.ComponentRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherTicketRepository;
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
public class BusTicketServiceImpl {

    @Autowired
    private LtConfig ltConfig;

    @Autowired
    private AvailabilityServiceImpl availabilityService;

    @Autowired
    private ProductRepository productRepository;

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

        if(product.getType().equals(EnumProductType.BusTicket)){

            productResp.setOptions(Arrays.asList());


        }
    }


    private static final  EnumLineType type = EnumLineType.BUS_TICKET;


    public void booking(LineItem lineItem, Product product) {
        if(!product.getType().equals(EnumProductType.BusTicket)){

            return;
        }
        lineItem.setFulfillmentInstructionsType(EnumFulfillmentInstructionsType.DIGITAL);
        lineItem.setLineType(type);

        lineItem.setFulfillment_behavior(EnumFulfillment_behavior.Create_voucher);
    }





    public void fullfil(Reservation reservation, List<LineItem> lineItemList, User payer) {


        lineItemList = lineItemList.stream().filter(e->e.getLineType().equals(type)).collect(Collectors.toList());


        lineItemList.stream().forEach(bookingProduct -> {




            Optional<Product> productOptional = productRepository.findById(bookingProduct.getProduct());

            Product product = productOptional.get();



            List<Component> componentRightList = componentRepository.findAllByProduct(bookingProduct.getProduct());


                    List<VoucherTicket> vouchers = LongStream.range(0, bookingProduct.getQuantity()).boxed().map(x -> {
                        VoucherTicket voucher = new VoucherTicket();

                        voucher.setBooking(reservation.getId());
                        voucher.setUser(payer.getId());




                        voucher.setLable("同城小易-观光车电子票"+product.getName());
                      //  voucher.setEventDate(LocalDate.now());


                        LocalDateTime ex = availabilityService.getExpiry_datetime(product);
                        voucher.setExpiry_datetime(ex);
                        voucher.setExpiration_date(ex);

                        VoucherTicket.BusTicket busTicket = new VoucherTicket.BusTicket();
                        busTicket.setSku(bookingProduct.getProduct());
                      voucher.setData_json(GSON.toJson(busTicket));

            /*            voucher.setVenue(theatre.getId());
                        voucher.setVenueName(theatre.getName());
                        voucher.setSection(zone.getName());
                        voucher.setRow(11);
                        voucher.setSeat(11);*/


                        voucher.setCode(idGenService.ticketCode());
                        voucher.setType(EnumVoucherType.TICKET_BUS);

                        voucher.setStatus(EnumVoucherStatus.Created);
                        voucher.setPublished(false);

                        voucher.setActive(false);

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

    private static final EnumVoucherType voucherType = EnumVoucherType.TICKET_BUS;

    public void ticketShow(VoucherTicketResp voucherTicketResp, VoucherTicket voucherTicket) {
        if(!voucherTicket.getType().equals(voucherType)) {
            return;
        }

        VoucherTicketResp.BusTicket venue1 = new VoucherTicketResp.BusTicket();
        venue1.setName("同城小易逛逛车");

        EntityModel entityModel = EntityModel.of(venue1);
        voucherTicketResp.setVenue(entityModel);

    }

    public void listShow(VoucherTicketResp voucherTicketResp, VoucherTicket voucherTicket) {

        if(!voucherTicket.getType().equals(voucherType)) {


            return;
        }
        voucherTicketResp.setText("这是车票");
        voucherTicketResp.setData(GSON.fromJson(voucherTicket.getData_json(),VoucherTicket.BusTicket.class));

    }
}
