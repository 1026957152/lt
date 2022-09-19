package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.ChargeResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.ChargeRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.RefundRepository;
import com.lt.dom.serviceOtc.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/oct")
public class ChargeRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private RefundRepository refundRepository;



    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/charges/{CHARGE_ID}/refunds", produces = "application/json")
    public ResponseEntity RefundCharge(@PathVariable long CHARGE_ID,@RequestBody @Valid BookingPojo pojo) {


        Optional<Charge> optionalProduct = chargeRepository.findById(CHARGE_ID);

        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(Enumfailures.not_found,"找不到产品");

        }

            Charge campaigns = paymentService.refundCompleted(optionalProduct.get());

            return ResponseEntity.ok(campaigns);


    }





    @GetMapping(value = "/charges", produces = "application/json")
    public ResponseEntity<PagedModel> getChargeList(Pageable pageable , PagedResourcesAssembler<EntityModel<ChargeResp>> assembler) {



        Page<Charge> optionalProduct = chargeRepository.findAll(pageable);



        List<Refund> refundList = refundRepository.findAllByChargeIn(optionalProduct.stream().map(e->e.getId()).collect(Collectors.toList()));

        Map<Long,List<Refund>>  longListMap = refundList.stream().collect(Collectors.groupingBy(e->e.getCharge()));

        Page<EntityModel<ChargeResp>> page =  optionalProduct.map(x->{

            EntityModel<ChargeResp> resp =EntityModel.of(ChargeResp.from(x,longListMap));


                return resp;

        });

        return ResponseEntity.ok(assembler.toModel(page));


    }


}