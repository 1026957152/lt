package com.lt.dom.controllerOct;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingPojo;
import com.lt.dom.repository.BalanceRepository;
import com.lt.dom.repository.ChargeRepository;
import com.lt.dom.serviceOtc.BalanceServiceImpl;
import com.lt.dom.serviceOtc.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class BalanceRestController {


    @Autowired
    private BalanceServiceImpl balanceService;

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private ChargeRepository chargeRepository;

    @Autowired
    private BalanceRepository balanceRepository;


    @GetMapping(value = "balances/{BALANCE_ID}/transaction_entrys", produces = "application/json")
    public List<TransactionEntry> listEntries(@PathVariable long BALANCE_ID) {

        Optional<Balance> validatorOptional = balanceRepository.findById(BALANCE_ID);
        if(validatorOptional.isPresent()){

                return balanceService.listTransationEntries(validatorOptional.get());

        }else{
            throw new BookNotFoundException(BALANCE_ID,"找不到余额");
        }


    }
    @GetMapping(value = "balances/{BALANCE_ID}/transactions", produces = "application/json")
    public List<BalanceTransaction> listTransactions(@PathVariable long BALANCE_ID) {

        Optional<Balance> validatorOptional = balanceRepository.findById(BALANCE_ID);
        if(validatorOptional.isPresent()){

            return balanceService.listTransations(validatorOptional.get());

        }else{
            throw new BookNotFoundException(BALANCE_ID,"找不到余额");
        }


    }



}