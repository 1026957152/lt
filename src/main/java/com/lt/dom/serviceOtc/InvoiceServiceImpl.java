package com.lt.dom.serviceOtc;


import com.lt.dom.InvoiceItem;
import com.lt.dom.OctResp.HistoryResp;
import com.lt.dom.OctResp.InvoiceResp;
import com.lt.dom.credit.EnumActionType;
import com.lt.dom.credit.EnumHistoryType;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.InvoiceReq;
import com.lt.dom.otcenum.EnumInvoiceStatus;
import com.lt.dom.otcenum.EnumInvoiceTypes;
import com.lt.dom.repository.HistoryRepository;
import com.lt.dom.repository.InvoiceRepository;
import com.lt.dom.repository.LineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private HistoryServiceImpl historyService;

    @Autowired
    private LineItemRepository lineItemRepository;



    @Autowired
    private InvoiceRepository invoiceRepository;



    public List<HistoryResp> list(Long id) {


        return historyRepository.findAllByBillId(id).stream().map(e->{
            HistoryResp historyResp = HistoryResp.from(e);
            return historyResp;
        }).collect(Collectors.toList());
    }


        public Invoice create(Supplier supplier, EnumActionType invoice_created, InvoiceReq req) {

            Invoice asset = new Invoice();
            asset.setCode(idGenService.invoiceNo());
            asset.setType(EnumInvoiceTypes.ACCREC);
            asset.setSupplier(supplier.getId());
            asset.setAutopay(false);
            asset.setPaid(false);



            asset.setTotalAmount(req.getTotalAmount());
            asset.setSubTotal(req.getSubTotal());
            asset.setTaxAmount(req.getTaxAmount());
            asset.setAmountDue(req.getAmountDue());

            asset.setStatus(EnumInvoiceStatus.draft);
            Invoice finalAsset = asset;
            List<LinkedTxn> linkedTxns = req.getLinkedTxns().stream().map(e->{

                LinkedTxn linkedTxn = new LinkedTxn();
                linkedTxn.setType(e.getType());
                linkedTxn.setAmount(e.getAmount().intValue());
                linkedTxn.setTxnLineId(e.getLineId());
                linkedTxn.setInvoice(finalAsset);
                return linkedTxn;
            }).collect(Collectors.toList());


            asset.setGeneratdOn(LocalDateTime.now());
            asset.setLinkedTxns(linkedTxns);
            asset = invoiceRepository.save(asset);
            historyService.create(asset.getId(),EnumActionType.invoice_created,"新建 账单");


            return asset;
        }





    public Invoice processing(Supplier supplier, LocalDateTime targetDate) {





        List<LineItem> usageList = lineItemRepository.findAll();




        List<LineItem> usages = usageList.stream()
                .filter(e->e.getCreatedDate().isBefore(targetDate))
                .collect(Collectors.toList());


        Double total = usages.stream().mapToDouble(e->{

            return e.getUnitPrice()*e.getQuantity();

        }).sum();
        Long qty = usages.stream().mapToLong(e->{

            return e.getQuantity();

        }).sum();

        InvoiceReq invoiceReq = new InvoiceReq();
        invoiceReq.setLinkedTxns(Arrays.asList());



        invoiceReq.setSubTotal(total);
        invoiceReq.setTaxAmount(0d);
        invoiceReq.setTotalAmount(invoiceReq.getSubTotal()+invoiceReq.getTaxAmount());
        invoiceReq.setAmountDue(0d);



        Invoice invoice = create(supplier,null,invoiceReq);


        Invoice finalInvoice = invoice;
        invoice.setItems(usages.stream().map(e->{
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setUnitCost(e.getUnitPrice());
            invoiceItem.setQuantity(qty.intValue());
            invoiceItem.setAmount(invoiceItem.getQuantity()*invoiceItem.getUnitCost());

            invoiceItem.setInvoice(finalInvoice);
            return invoiceItem;
        }).collect(Collectors.toList()));



     //   historyService.create(invoice.getId(), EnumActionType.invoice_created,"新建");

        invoice = invoiceRepository.save(invoice);

        return invoice;
    }






    public InvoiceResp Trial(Supplier supplier, LocalDateTime targetDate) {





        List<LineItem> usageList = lineItemRepository.findAll();




        List<LineItem> usages = usageList.stream()
                .filter(e->e.getCreatedDate().isBefore(targetDate))
                .collect(Collectors.toList());


        Double total = usages.stream().mapToDouble(e->{

            return e.getUnitPrice()*e.getQuantity();

        }).sum();
        Long qty = usages.stream().mapToLong(e->{

            return e.getQuantity();

        }).sum();

        InvoiceReq invoiceReq = new InvoiceReq();
        invoiceReq.setLinkedTxns(Arrays.asList());



        invoiceReq.setSubTotal(total);
        invoiceReq.setTaxAmount(0d);
        invoiceReq.setTotalAmount(invoiceReq.getSubTotal()+invoiceReq.getTaxAmount());
        invoiceReq.setAmountDue(0d);



        ;


        InvoiceResp invoiceResp = new InvoiceResp();

        invoiceResp.setItems(usages.stream().map(e->{
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setUnitCost(e.getUnitPrice());
            invoiceItem.setQuantity(qty.intValue());
            invoiceItem.setAmount(invoiceItem.getQuantity()*invoiceItem.getUnitCost());

            return invoiceItem;
        }).collect(Collectors.toList()));


        invoiceResp.setSubTotal(invoiceResp.getSubTotal());
        invoiceResp.setTaxAmount(invoiceResp.getTaxAmount());
        invoiceResp.setTotalAmount(invoiceResp.getTotalAmount());
        invoiceResp.setAmountDue(invoiceResp.getAmountDue());

        return invoiceResp;

    }
}
