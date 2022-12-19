package com.lt.dom.serviceOtc.product;


import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcReq.VoucherTicketResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.ComponentRightServiceImpl;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.ProductServiceImpl;
import com.lt.dom.vo.CompoentRightAssigtToTargeVo;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.lt.dom.serviceOtc.JsonParse.GSON;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ShowtimeTicketServiceImpl {
    Logger logger = LoggerFactory.getLogger(ShowtimeTicketServiceImpl.class);


    @Autowired
    private LtConfig ltConfig;

    @Autowired
    private MovieProductRepository movieProductRepository;
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private MovieRepository movieRepository;






    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private VoucherTicketRepository voucherTicketRepository;
    @Autowired
    private ComponentRightServiceImpl componentRightService;

    @Autowired
    private AvailabilityServiceImpl availabilityService;



    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private TheatreRepository theatreRepository;


    private static final EnumProductType productType = EnumProductType.Showtime;
    private static final EnumLineType lineType = EnumLineType.Showtime;
    private static final EnumVoucherType voucherType = EnumVoucherType.TICKET_THEATRE;


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

        if(product.getType().equals(EnumProductType.Showtime)){

            Optional<MovieProduct> passProduct = movieProductRepository.findByProduct(product.getId());
            MovieProduct movieProduct = passProduct.get();

            List<Sku> skuList = passProduct.get().getZonePricings();

            zoneRepository.findAllById(skuList.stream().map(e->e.getZone()).collect(Collectors.toList()));

            productResp.setZones(skuList.stream().map(e->e.getZone()).collect(Collectors.toList()));

            Optional<Movie>  movieOptional = movieRepository.findById(movieProduct.getMovie());
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





    public void fullfil(Reservation reservation, List<LineItem> lineItemList, User payer) {


        lineItemList = lineItemList.stream().filter(e->e.getLineType().equals(lineType)).collect(Collectors.toList());


        lineItemList.stream().forEach(lineItem -> {





            Quartet<Zone,Movie,Theatre,Sku> triplet =  productService.showTime(lineItem.getSku());

            Zone zone = triplet.getValue0();
            Movie movie = triplet.getValue1();
            Theatre theatre = triplet.getValue2();
            Sku sku = triplet.getValue3();


            Optional<Product> productOptional = productRepository.findById(lineItem.getProduct());
            Product product = productOptional.get();

                    List<Component> componentRightList = componentRepository.findAllByProduct(lineItem.getProduct());


                    List<VoucherTicket> vouchers = LongStream.range(0, lineItem.getQuantity()).boxed().map(x -> {
                        VoucherTicket voucher = new VoucherTicket();

                        voucher.setBooking(reservation.getId());
                        voucher.setUser(payer.getId());






                        voucher.setLable(movie.getName());
                        voucher.setCode(idGenService.ticketCode());
                        voucher.setType(voucherType);

                        voucher.setStatus(EnumVoucherStatus.Created);
                        voucher.setPublished(false);

                        voucher.setActive(false);

                        LocalDateTime ex = availabilityService.getExpiry_datetime(product);
                        voucher.setExpiry_datetime(ex);
                        voucher.setExpiration_date(ex);



                        VoucherTicket.Showtime showtime = new VoucherTicket.Showtime();
                        showtime.setEventDate(LocalDate.now());
                        showtime.setVenue(theatre.getId());
                        showtime.setVenueName(theatre.getName());
                        showtime.setSection(zone.getName());
                        showtime.setRow(11);
                        showtime.setSeat(11);
                        voucher.setData_json(GSON.toJson(showtime));






                        return voucher;
                    }).collect(Collectors.toList());

            Long limit = 1l;


                    vouchers = voucherTicketRepository.saveAll(vouchers);
                    vouchers.stream().forEach(e->{

                        CompoentRightAssigtToTargeVo compoentRightAssigtToTargeVo = new CompoentRightAssigtToTargeVo();


                        logger.error("订单数量和 新建一个年卡 "+reservation.getPlatform());
                        compoentRightAssigtToTargeVo.setBooking(reservation);

                        componentRightService.assingtoComponent(compoentRightAssigtToTargeVo,
                                componentRightList.stream().map(ee->{
                                    Triplet<Component,LineItem,EnumComponentVoucherType> componentVounchLineItemPair =
                                            Triplet.with(ee,lineItem,ee.getType());
                                    return componentVounchLineItemPair;
                                }).collect(Collectors.toList()),
                                limit);
                    });


                }

        );


    }

    public void ticketShow(VoucherTicketResp voucherTicketResp, VoucherTicket voucherTicket) {

        if(!voucherTicket.getType().equals(voucherType)) {


            return;
        }

            VoucherTicket.Showtime showtime =  GSON.fromJson(voucherTicket.getData_json(),VoucherTicket.Showtime.class);

        VoucherTicketResp.ShowtimeResp showtimeResp = VoucherTicketResp.ShowtimeResp.from(showtime);


        Optional<Theatre> theatreOptional = theatreRepository.findById(showtime.getVenue());
        if(theatreOptional.isPresent()){
            Theatre theatre = theatreOptional.get();
            VoucherTicketResp.Venue venue1 = new VoucherTicketResp.Venue();
            venue1.setName(theatre.getName());


            venue1.setLocation(LocationResp.from(theatre.getLocation()));
            EntityModel entityModel = EntityModel.of(venue1);
            entityModel.add(linkTo(methodOn(BookingRestController.class).getReservation(voucherTicket.getBooking())).withSelfRel());

            showtimeResp.setVenue(entityModel);


        }
        EntityModel entityModel = EntityModel.of(showtimeResp);
        voucherTicketResp.setPerformance(entityModel);




    }

    public void listShow(VoucherTicketResp voucherTicketResp, VoucherTicket voucherTicket) {




        if(voucherTicket.getType().equals(voucherType)){

            voucherTicketResp.setText("这是演出门票");
            voucherTicketResp.setData(GSON.fromJson(voucherTicket.getData_json(),VoucherTicket.Showtime.class));

            return;
        }

    }
}
