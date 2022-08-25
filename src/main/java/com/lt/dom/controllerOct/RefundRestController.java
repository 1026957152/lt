package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.RefundResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Refund;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.otcenum.EnumRefundStatus;
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
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class RefundRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private RefundRepository refundRepository;



    @Operation(summary = "2、下单购买")
    @PostMapping(value = "/refunds/{REFUND_ID}", produces = "application/json")
    public ResponseEntity RefundCharge(@PathVariable long REFUND_ID,@RequestBody @Valid BookingPojo pojo) {


        Optional<Refund> optionalProduct = refundRepository.findById(REFUND_ID);

        if(optionalProduct.isEmpty()) {
            throw new BookNotFoundException(pojo.getProductId(),"找不到产品");

        }
        Refund refund = optionalProduct.get();

/*        if(refund.getStatus().equals(EnumRefundStatus.succeeded)) {
            throw new BookNotFoundException(pojo.getProductId(),"找不到产品");

        }*/
            Refund campaigns = paymentService.refundCompleted(optionalProduct.get());

            return ResponseEntity.ok(campaigns);



    }





    @GetMapping(value = "/refunds", produces = "application/json")
    public ResponseEntity<PagedModel> getRefundList(Pageable pageable , PagedResourcesAssembler<EntityModel<RefundResp>> assembler) {



        Page<Refund> optionalProduct = refundRepository.findAll(pageable);

        Page<EntityModel<RefundResp>> page =  optionalProduct.map(x->{

            EntityModel<RefundResp> resp =EntityModel.of(RefundResp.from(x));
                return resp;

        });

        return ResponseEntity.ok(assembler.toModel(page));


    }


}