package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumFinancialAccountStatus;
import com.lt.dom.otcenum.EnumUserType;
import com.lt.dom.repository.BalanceRepository;
import com.lt.dom.repository.TransactionEntryRepository;
import com.lt.dom.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceServiceImpl {


    @Autowired
    private TransactionEntryRepository transactionEntryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BalanceRepository balanceRepository;



    public List<TransactionEntry> listTransationEntries(Balance balance) {
        return transactionEntryRepository.findByBalance(balance.getId());
    }

    public List<BalanceTransaction> listTransations(Balance balance) {

        return transactionRepository.findByBalance(balance.getId());

    }

    public Balance AppBalance() {
        Optional<Balance> balanceOptional = balanceRepository.findByType(EnumUserType.app);

        return balanceOptional.get();

    }

    public Balance balance(long user, EnumUserType payment) {
        Optional<Balance> balanceOptional = balanceRepository.findByUserAndType(user,payment);
        if(balanceOptional.isEmpty()){
            Balance balance = new Balance();
            balance.setUser(user);
            balance.setType(payment);
            balance.setAvailable_amount(0);
            balance.setStatus(EnumFinancialAccountStatus.open);
            return balanceRepository.save(balance);
        }

        return balanceOptional.get();

    }



    public Balance updateBalance(Balance balance, int net) {

        balance.setAvailable_amount(net+balance.getAvailable_amount());

        return balanceRepository.save(balance);
    }
}
