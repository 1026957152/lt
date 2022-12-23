package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ComponentVounchResp;
import com.lt.dom.OctResp.RightRedemptionEntryResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.VoucherTicketResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.QrcodeServiceImpl;
import com.lt.dom.serviceOtc.product.AttractionTicketServiceImpl;
import com.lt.dom.serviceOtc.product.BusTicketServiceImpl;
import com.lt.dom.serviceOtc.product.CityPassServiceImpl;
import com.lt.dom.serviceOtc.product.ShowtimeTicketServiceImpl;
import com.lt.dom.util.ZxingBarcodeGenerator;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class VoucherTicketPcRestController {
    private static final Logger logger = LoggerFactory.getLogger(RedemptionRestController.class);
    @Autowired
    private VoucherTicketRepository voucherTicketRepository;


    @Autowired
    private BusTicketServiceImpl busTicketService;

    @Autowired
    private ShowtimeTicketServiceImpl showtimeTicketService;

    @Autowired
    private AttractionTicketServiceImpl attractionTicketService;

    @Autowired
    private AssetServiceImpl assetService;



    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private RightRedemptionEntryRepository rightRedemptionEntryRepository;


    @Autowired
    QrcodeServiceImpl qrcodeService;


    @Operation(summary = "1、获得")
    @GetMapping(value = "/v/ooo/{VOUCHER_ID}", produces = "application/json")
    public EntityModel<VoucherTicketResp> dig_ticket(@PathVariable long VOUCHER_ID) {


        Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findById(VOUCHER_ID);

        if (optionalVoucher.isEmpty()) {
            throw new BookNotFoundException(VOUCHER_ID, "找不到优惠券");
        }
        VoucherTicket voucherTicket = optionalVoucher.get();


        VoucherTicketResp voucherTicketResp = VoucherTicketResp.from(voucherTicket);

        List<ComponentVounch> components = componentVounchRepository.findAllByReference(voucherTicket.getCode());
        voucherTicketResp.setComponents(components.stream().map(e -> {

            ComponentVounchResp componentResp = ComponentVounchResp.from(e);

            if (e.getType().equals(EnumComponentVoucherType.Right)) {
                ComponentRight componentRight = componentRightRepository.findById(e.getComponentRight()).get();
                componentResp.setComponentRightCode(componentRight.getCode());
            } else {

            }

            return componentResp;
        }).collect(Collectors.toList()));


        List<RightRedemptionEntry> redemptionEntryList =
                rightRedemptionEntryRepository.findAllByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType.voucher, voucherTicket.getId());


        VoucherTicketResp.Redemption redemption = new VoucherTicketResp.Redemption();


        redemption.setRedemption_entries(redemptionEntryList.stream().map(e -> {

            RightRedemptionEntryResp redemptionEntryResp = RightRedemptionEntryResp.from(e);

            return redemptionEntryResp;
        }).collect(Collectors.toList()));

        voucherTicketResp.setRedemption(redemption);

        VoucherTicketResp.Booking booking = new VoucherTicketResp.Booking();
        booking.setCode(voucherTicket.getCode());
        booking.setPaied_at(LocalDateTime.now());
        booking.setCreated_at(LocalDateTime.now());
        EntityModel entityModel = EntityModel.of(booking);
        entityModel.add(linkTo(methodOn(BookingRestController.class).getReservation(voucherTicket.getBooking())).withSelfRel());

        voucherTicketResp.withBookingInfo(entityModel);

        showtimeTicketService.ticketShow(voucherTicketResp, voucherTicket);

        busTicketService.ticketShow(voucherTicketResp, voucherTicket);

        attractionTicketService.ticketShow(voucherTicketResp, voucherTicket);


        Asset asset = assetService.getWithNew(voucherTicket.getCode(), voucherTicket.getId());


        VoucherTicketResp.DeliveryOption deliveryOption1 = new VoucherTicketResp.DeliveryOption();
        deliveryOption1.setDeliveryFormat(EnumDeliveryFormat.QRCODE);
        deliveryOption1.setDeliveryValue(asset.getIdId());
        deliveryOption1.setCode_base64(ZxingBarcodeGenerator.base64_png_src(asset.getIdId()));
        voucherTicketResp.setDeliveryOption(deliveryOption1);


        voucherTicketResp.setCover(fileStorageService.loadDocumentWithCode(EnumDocumentType.artwork_audio, voucherTicket.getCode()));


        return EntityModel.of(voucherTicketResp);
    }


}