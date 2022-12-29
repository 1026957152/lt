package com.lt.dom.freemarker;

import com.lt.dom.OctResp.ComponentVounchResp;
import com.lt.dom.OctResp.RightRedemptionEntryResp;
import com.lt.dom.controllerOct.BarcodesController;
import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.VoucherTicketResp;
import com.lt.dom.otcenum.EnumComponentVoucherType;
import com.lt.dom.otcenum.EnumDeliveryFormat;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.CryptoServiceImpl;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.QrcodeServiceImpl;
import com.lt.dom.serviceOtc.product.AttractionTicketServiceImpl;
import com.lt.dom.serviceOtc.product.BusTicketServiceImpl;
import com.lt.dom.serviceOtc.product.ShowtimeTicketServiceImpl;
import com.lt.dom.util.ZxingBarcodeGenerator;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Version;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Controller
public class EticketController {

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
    @Autowired
    CryptoServiceImpl cryptoService;
    @Autowired
    private CardholderRepository cardholderRepository;


    @GetMapping(value = "/v/{VOUCHER_ID}")
    public Object dig_ticket(@PathVariable String VOUCHER_ID,Model model) {


        Long id = cryptoService.hashids_decode_(VOUCHER_ID);
        System.out.println("电子票，电子票啊啊"+VOUCHER_ID);

        Optional<VoucherTicket> optionalVoucher = voucherTicketRepository.findById(id);

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

        Optional<Cardholder> cardholder = cardholderRepository.findById(voucherTicket.getRealnameHolder());

        List<RightRedemptionEntry> redemptionEntryList =
                rightRedemptionEntryRepository.findAllByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType.voucher, voucherTicket.getId());


        VoucherTicketResp.Redemption redemption = new VoucherTicketResp.Redemption();

        voucherTicketResp.setHolder(cardholder);

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


        VoucherTicketResp.DeliveryOption deliveryOption1 = new VoucherTicketResp.DeliveryOption();
        deliveryOption1.setDeliveryFormat(EnumDeliveryFormat.QRCODE);
        deliveryOption1.setDeliveryValue(voucherTicket.getCode());
        deliveryOption1.setCode_base64(ZxingBarcodeGenerator.base64_png_src(voucherTicket.getCode()));
        voucherTicketResp.setDeliveryOption(deliveryOption1);


        voucherTicketResp.setCover(fileStorageService.loadDocumentWithCode(EnumDocumentType.artwork_audio, voucherTicket.getCode()));




        String link = null;
        try {
            link = linkTo(methodOn(BarcodesController.class).getZxingQRCode(cryptoService.encode(voucherTicket.getCode()))).withSelfRel().getHref();
        } catch (Exception e) {
            e.printStackTrace();
        }



        model.addAttribute("voucher", voucherTicketResp);
        model.addAttribute("qrcode", link);



        return "index";


    }

}