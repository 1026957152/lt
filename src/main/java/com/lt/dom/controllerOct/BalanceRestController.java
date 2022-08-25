package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.BalanceResp;
import com.lt.dom.OctResp.BalanceTransactionResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.repository.BalanceRepository;
import com.lt.dom.repository.ChargeRepository;
import com.lt.dom.repository.TransactionRepository;
import com.lt.dom.serviceOtc.BalanceServiceImpl;
import com.lt.dom.serviceOtc.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    @Autowired
    private TransactionRepository transactionRepository;


    @GetMapping(value = "balances/{BALANCE_ID}", produces = "application/json")
    public EntityModel getBalance(@PathVariable long BALANCE_ID) {

        Optional<Balance> validatorOptional = balanceRepository.findById(BALANCE_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(BALANCE_ID,"找不到余额");

        }
        Balance balance = validatorOptional.get();

            EntityModel entityModel = EntityModel.of(BalanceResp.from(balance));

        entityModel.add(linkTo(methodOn(BalanceRestController.class).getBalance(balance.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(BalanceRestController.class).listTransactions(balance.getId(),null,null)).withRel("getTranEntryList"));
        entityModel.add(linkTo(methodOn(BalanceRestController.class).listTransactionSummary(balance.getId(),null,null)).withRel("getTranEntrySummaryList"));

        entityModel.add(linkTo(methodOn(ChargeRestController.class).getChargeList(null,null)).withRel("getChargeList"));
        entityModel.add(linkTo(methodOn(RefundRestController.class).getRefundList(null,null)).withRel("getRefundList"));

        return entityModel;


    }

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
    public PagedModel listTransactions(@PathVariable long BALANCE_ID, Pageable pageable,PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {

        Optional<Balance> validatorOptional = balanceRepository.findById(BALANCE_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(BALANCE_ID,"找不到余额");

        }
            Balance balance = validatorOptional.get();

            Page<BalanceTransaction> transactions = transactionRepository.findAllByBalance(balance.getId(),pageable);

            return assembler.toModel(transactions.map(e->{

                BalanceTransactionResp balanceTransaction = BalanceTransactionResp.from(e);
                EntityModel entityModel = EntityModel.of(balanceTransaction);

                return entityModel;
            }));




    }



    @GetMapping(value = "balances/{BALANCE_ID}/transactions/summary", produces = "application/json")
    public PagedModel listTransactionSummary(@PathVariable long BALANCE_ID, Pageable pageable,PagedResourcesAssembler<EntityModel<BalanceTransaction>> assembler) {

        Optional<Balance> validatorOptional = balanceRepository.findById(BALANCE_ID);
        if(validatorOptional.isEmpty()) {
            throw new BookNotFoundException(BALANCE_ID,"找不到余额");

        }
        Balance balance = validatorOptional.get();

        Page<BalanceTransaction> transactions = transactionRepository.findAllByBalance(balance.getId(),pageable);

        return assembler.toModel(transactions.map(e->{

            BalanceTransactionResp balanceTransaction = BalanceTransactionResp.from(e);
            EntityModel entityModel = EntityModel.of(balanceTransaction);

            return entityModel;
        }));




    }


    @GetMapping(value = "/balances", produces = "application/json")
    public ResponseEntity<PagedModel> balances(Pageable pageable, PagedResourcesAssembler<EntityModel<Balance>> assembler) {

            Page<Balance> campaignResps =  balanceRepository.findAll(pageable);

            return ResponseEntity.ok(assembler.toModel(campaignResps.map(e->{
                EntityModel entityModel = EntityModel.of(BalanceResp.from(e));
                return entityModel;
            })));


    }


}