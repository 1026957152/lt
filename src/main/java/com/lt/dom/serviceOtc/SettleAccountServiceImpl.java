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

import java.util.*;

@Service
public class SettleAccountServiceImpl {

    @Autowired
    private EventHandler eventHandler;


    @Autowired
    private SettleAccountRepository settleAccountRepository;
    @Autowired
    private BalanceSettlementRepository balanceSettlementRepository;

    @Autowired
    private ChargeRepository chargeRepository;


    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private BalanceTransactionRepository balanceTransactionRepository;

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
            BalanceSettlement balanceSettlement = new BalanceSettlement();
            balanceSettlement.setAmount(charge.getAmount());
            balanceSettlement = balanceSettlementRepository.save(balanceSettlement);


            Balance balance = new Balance();

            balance.setUser(product.getSupplierId());
            balance.setType(EnumUserType.business);
            Example<Balance> exampleB = Example.of(balance);
            Optional<Balance> optionalBalance = balanceRepository.findOne(exampleB);

            if( optionalBalance.isPresent()){
                throw new Exception();

            }
            BalanceTransaction balanceTransaction = new BalanceTransaction();
            balanceTransaction.setSource(balanceSettlement.getId());
            balanceTransaction.setType(EnumTranType.credited); //入账
            balanceTransaction.setAmount(charge.getAmount());
            balanceTransaction.setAvailable_balance(optionalBalance.get().getBalance());
            balanceTransaction.setUser(product.getSupplierId());
            balanceTransaction = balanceTransactionRepository.save(balanceTransaction);  //  首先结算到 产品发布者的结算账户里，








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
