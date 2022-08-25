package com.lt.dom.serviceOtc;


import com.lt.dom.domain.SettleAccount;
import com.lt.dom.notification.EventHandler;
import com.lt.dom.notification.OrderPaidVo;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.SettleAccountPojo;
import com.lt.dom.otcenum.EnumTranType;
import com.lt.dom.otcenum.EnumUserType;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SettleAccountServiceImpl {

    @Autowired
    private EventHandler eventHandler;


    @Autowired
    private SettleAccountRepository settleAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ChargeRepository chargeRepository;


    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private TransactionEntryRepository transactionEntryRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private PublicationServiceImpl publicationService;



    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private RoyaltySettlementServiceImpl royaltySettlementService;



    public SettleAccount add(Supplier supplier, SettleAccountPojo pojo) {

        SettleAccount settleAccount = new SettleAccount();


        settleAccount.setAccountName(pojo.getAccountName());
        settleAccount.setBankAccount(pojo.getBankAccountNumber());
        settleAccount.setBankName(pojo.getBankName());
        settleAccount.setCreated_at(LocalDateTime.now());
        settleAccount.setSupplierId(supplier.getId());



        return settleAccountRepository.save(settleAccount);
    }













    public void paySuccess(Reservation reservation_) throws Exception {

        Charge user = new Charge();

        Example<Charge> example = Example.of(user);
        Optional<Charge> optionalCharge = chargeRepository.findOne(example);

        Reservation reservation = reservationRepository.findById(reservation_.getId()).get();










        Product product = productRepository.findById(reservation_.getProductId()).get();









        if(optionalCharge.isPresent()){


            Charge charge = optionalCharge.get();
            BalanceTransaction balanceTransaction = new BalanceTransaction();
            balanceTransaction.setAmount(charge.getAmount());
            balanceTransaction.setFlowType(EnumTranType.payment_refund);
            balanceTransaction = transactionRepository.save(balanceTransaction);


            Balance balance = new Balance();

            balance.setUser(product.getSupplierId());
            balance.setType(EnumUserType.business);
            Example<Balance> exampleB = Example.of(balance);
            Optional<Balance> optionalBalance = balanceRepository.findOne(exampleB);

            if( optionalBalance.isPresent()){
                throw new Exception();

            }
            TransactionEntry transactionEntry = new TransactionEntry();
            transactionEntry.setTransactionId(balanceTransaction.getId());
            transactionEntry.setType(EnumTranType.credited); //入账
            transactionEntry.setAmount(charge.getAmount());
            transactionEntry.setAvailable_balance(optionalBalance.get().getAvailable_amount());
            transactionEntry.setUser(product.getSupplierId());
            transactionEntry = transactionEntryRepository.save(transactionEntry);  //  首先结算到 产品发布者的结算账户里，








            //以下结算到各个分润账户里啊




            OrderPaidVo orderPaidVo = new OrderPaidVo();
            orderPaidVo.setComponents(reservation.getComponents());
            orderPaidVo.setUser(new User());

            eventHandler.order_paid(orderPaidVo);











/*            BalanceTransaction balanceTransaction = new BalanceTransaction();
            balanceTransaction = balanceTransactionRepository.save(balanceTransaction);


            BalanceSettlement balanceSettlement = new BalanceSettlement();
            balanceSettlement.setTransaction_no(balanceSettlement.getId());
            balanceSettlement = balanceSettlementRepository.save(balanceSettlement);


            balanceTransaction.setType(EnumTranType.payment);
            balanceTransaction.setSource(optionalCharge.get().getId());
            balanceTransaction.setSource(balanceSettlement.getId());
            balanceTransactionRepository.save(balanceTransaction);*/

        }

    }








}
