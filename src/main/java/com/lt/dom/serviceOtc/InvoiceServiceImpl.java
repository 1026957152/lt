package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.HistoryResp;
import com.lt.dom.credit.EnumActionType;
import com.lt.dom.credit.EnumHistoryType;
import com.lt.dom.oct.History;
import com.lt.dom.oct.Invoice;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.EnumInvoiceTypes;
import com.lt.dom.repository.HistoryRepository;
import com.lt.dom.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    private InvoiceRepository invoiceRepository;



    public List<HistoryResp> list(Long id) {


        return historyRepository.findAllByBillId(id).stream().map(e->{
            HistoryResp historyResp = HistoryResp.from(e);
            return historyResp;
        }).collect(Collectors.toList());
    }


        public Invoice create(Supplier supplier, EnumActionType invoice_created) {


            Invoice asset = new Invoice();
            asset.setCode(idGenService.invoiceNo());
            asset.setType(EnumInvoiceTypes.ACCREC);

            asset = invoiceRepository.save(asset);

            return asset;
        }

}
