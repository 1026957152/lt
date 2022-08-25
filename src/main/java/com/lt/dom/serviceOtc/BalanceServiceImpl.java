package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.repository.TransactionEntryRepository;
import com.lt.dom.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceServiceImpl {


    @Autowired
    private TransactionEntryRepository transactionEntryRepository;

    @Autowired
    private TransactionRepository transactionRepository;




    public List<TransactionEntry> listTransationEntries(Balance balance) {
        return transactionEntryRepository.findByBalance(balance.getId());
    }

    public List<BalanceTransaction> listTransations(Balance balance) {

        return transactionRepository.findByBalance(balance.getId());

    }
}
