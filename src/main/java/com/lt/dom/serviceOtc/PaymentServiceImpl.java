package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl {
    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionEntryRepository transactionEntryRepository;


    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private ApplicationFeeRepository applicationFeeRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private ApplicationFeeServiceImpl applicationFeeService;


    public Charge createCharge(Charge charge) {


        charge.setCode(idGenService.chargeCode());
        return chargeRepository.save(charge);
    }

    public Charge getOneOrderStatusByPayNo(String out_trade_no) {

        Optional<Charge> optional = chargeRepository.findByOrderId(out_trade_no);
        Charge charge = optional.get();


        return charge;
    }

    public Balance balance(long user, EnumUserType payment) {
        Optional<Balance> balanceOptional = balanceRepository.findByUserAndType(user,payment);
        if(balanceOptional.isEmpty()){
            Balance balance = new Balance();
            balance.setUser(user);
            balance.setType(payment);
            balance.setAmount(0);
            return balanceRepository.save(balance);
        }

        return balanceOptional.get();

    }
    public Balance updateBalance(Balance balance, int net) {

            balance.setAmount(net+balance.getAmount());
            balance.setAmount(0);
            return balanceRepository.save(balance);
        }

    public void paidChage(Charge charge, Payment payment) {

        List<Integer> fees = applicationFeeService.getFees();
        Balance balance = balance(payment.getRecipient(),EnumUserType.business);


        charge.setPaid(true);
        charge.setStatus(EnumChargeStatus.succeeded);
        charge.setTimePaid(LocalDateTime.now());
        charge = chargeRepository.save(charge);







        BalanceTransaction balanceTransaction = new BalanceTransaction();
        balanceTransaction.setAmount(charge.getAmount());
        balanceTransaction.setBalance(balance.getId());
        balanceTransaction.setSource(charge.getId());
        balanceTransaction.setSourceId(charge.getCode());
        balanceTransaction.setTransaction_no(charge.getAmount());
        balanceTransaction.setFlowType(EnumTranType.payment);
        balanceTransaction.setStatus(EnumBalanceTranStatus.available);
        balanceTransaction.setFee(charge.getAmount());
        balanceTransaction.setNet(charge.getAmount());
        balanceTransaction = transactionRepository.save(balanceTransaction);

        updateBalance(balance,balanceTransaction.getNet());


        BalanceTransaction finalBalanceTransaction = balanceTransaction;
        Charge finalCharge = charge;
        List<ApplicationFee> applicationFees = fees.stream().map(x->{
            ApplicationFee applicationFee = new ApplicationFee();
            applicationFee.setAmount(x);
            applicationFee.setBalance(balance.getId());
            applicationFee.setBalanceTransaction(finalBalanceTransaction.getId());
            applicationFee.setCharge(finalCharge.getId());
            return applicationFee;
        }).collect(Collectors.toList());

        applicationFeeRepository.saveAll(applicationFees);





       // Balance customerBalance = balance(charge.getCustomer(),EnumUserType.customer);

/*
        if( optionalBalance.isPresent()){
            throw new Exception();

        }*/




/*
        TransactionEntry transactionEntry = new TransactionEntry();
        transactionEntry.setTransactionId(transaction.getId());
        transactionEntry.setType(EnumTranType.payment); //入账
        transactionEntry.setAmount(charge.getAmount());
        transactionEntry.setAvailable_balance(balance.getCash());
        //transactionEntry.setUser(charge.getCustomer());
        transactionEntry.setBalance(balance.getId());

        transactionEntry = transactionEntryRepository.save(transactionEntry);  //  首先结算到 产品发布者的结算账户里，

*/


        }

    public Refund refundCharge(Refund refund) {


        Optional<Charge> optional = chargeRepository.findById(refund.getCharge());
        Charge charge = optional.get();//

        Optional<Payment> optionalPayment = paymentRepository.findById(charge.getOrderNo());

        Payment payment = optionalPayment.get();//
        Balance balance = balance(payment.getRecipient(),EnumUserType.business);

        charge.setRefunded(true);
        charge = chargeRepository.save(charge);



        refund.setStatus(EnumRefundStatus.succeeded);
        refundRepository.save(refund);




        BalanceTransaction balanceTransaction = new BalanceTransaction();
        balanceTransaction.setAmount(charge.getAmount());
        balanceTransaction.setSource(refund.getId());
        balanceTransaction.setSourceId(refund.getCode());
        balanceTransaction.setBalance(balance.getId());
        balanceTransaction.setTransaction_no(charge.getAmount());
        balanceTransaction.setFlowType(EnumTranType.payment_refund);
        balanceTransaction.setStatus(EnumBalanceTranStatus.available);
        balanceTransaction.setFee(refund.getAmount());
        balanceTransaction.setNet(-refund.getAmount());
        balanceTransaction = transactionRepository.save(balanceTransaction);

        updateBalance(balance,balanceTransaction.getNet());


        List<ApplicationFee> applicationFees = applicationFeeRepository.findByCharge(charge.getId());



        applicationFees = applicationFees.stream().map(x->{
            x.setRefunded(true);
            x.setAmount_refunded(x.getAmount());
            x.setRefund(refund.getId());
            return x;
        }).collect(Collectors.toList());

        applicationFeeRepository.saveAll(applicationFees);



        return refund;
    }

    public Payment createPayment(Payment payment) {
        payment.setCode(idGenService.paymentNo());
        payment = paymentRepository.save(payment);
        return payment;
    }


    public Refund createCharge(Refund refund1, Payment payment) {

        refund1.setCode(idGenService.refundCode());
        return refundRepository.save(refund1);
    }

    public Charge refundCharge(Charge charge) {
        return null;
    }




}
