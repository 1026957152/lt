package com.lt.dom.controllerOct;

import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.FulfillServiceImpl;
import com.lt.dom.state.ApplicationReviewStates;
import com.lt.dom.state.WorkflowOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oct")
public class TestOrderRestController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private FulfillServiceImpl vonchorService;
    @Autowired
    private WorkflowOrderService orderServer;

    @GetMapping(value = "/test/create", produces = "application/json")
    public ApplicationReviewStates create() {


        return null;//orderServer.create(new Date()).getTest_Status();

    }


    @GetMapping(value = "/test/pay", produces = "application/json")
    public ApplicationReviewStates pay() {


        return null;//orderServer.pay(1l,"12").getState().getId();

    }

    @GetMapping(value = "/test/fulfill", produces = "application/json")
    public ApplicationReviewStates fulfill() {

        return null;// orderServer.fulfill(1l).getState().getId();

    }
}