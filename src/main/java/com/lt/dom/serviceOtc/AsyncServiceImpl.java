package com.lt.dom.serviceOtc;


import com.lt.dom.excel.EasyWriteDemo1;
import com.lt.dom.excel.ExcelRedemption;
import com.lt.dom.excel.ExcelVoucher;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumExportStatus;
import com.lt.dom.otcenum.EnumExportVoucher;
import com.lt.dom.repository.*;
import com.lt.dom.util.ExcelPOIHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AsyncServiceImpl {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ExportRepository exportRepository;

    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;
/*
    @Async
    public void asyncMethodWithVoidReturnType(Export export) {

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());
        ExcelPOIHelper excelPOIHelper = new ExcelPOIHelper();
        try {

            String fileLocation = FileStorageServiceImpl.path.resolve(export.getCode()+".xlsx").toString();

            String url = excelPOIHelper.writeExcel(fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }


        export.setStatus(EnumExportStatus.DONE);
        export.setDone_at(LocalDateTime.now());
        export = exportRepository.save(export);
    }

*/









    @Async
    public void asyncExport(Export export) {


        if(export.getExported_object().equals(EnumExportVoucher.voucher)){
            String fileLocation = FileStorageServiceImpl.path.resolve(export.getCode()+".xlsx").toString();

            List<Voucher> vouchers = voucherRepository.findAll();


            EasyWriteDemo1 excelPOIHelper = new EasyWriteDemo1(fileLocation);


            List<ExcelVoucher> collect = vouchers.stream().map(x->{
                return ExcelVoucher.from(x);
            }).collect(Collectors.toList());

            excelPOIHelper.exportVoucher(collect);

            export.setTotal_succeeded(collect.size());
            export.setStatus(EnumExportStatus.DONE);
            export.setDone_at(LocalDateTime.now());
            export = exportRepository.save(export);
        }



        if(export.getExported_object().equals(EnumExportVoucher.redemption)){
            String fileLocation = FileStorageServiceImpl.path.resolve(export.getCode()+".xlsx").toString();



            List<RedemptionEntry> redemptionEntryList = redemptionEntryRepository.findAll();

            Map<Long,Supplier> longSupplierMap = supplierRepository.findAllById(redemptionEntryList.stream().map(x->{
                return x.getSupplier();
            }).collect(Collectors.toList())).stream().collect(Collectors.toMap(x->x.getId(),x->x));



            List<Traveler> travelers = travelerRepository.findAllById(redemptionEntryList.stream().map(x->{

                System.out.println("这里打印一下 错误的几个数据"+x.getRelatedObjectType() +"------------" +x.getRelatedObjectId());
                return x.getRelatedObjectId();


            }).collect(Collectors.toList()));


            Map<Long,Traveler> travelerMap = travelers.stream().collect(Collectors.toMap(x->x.getId(),x->x));
            EasyWriteDemo1 excelPOIHelper = new EasyWriteDemo1(fileLocation);



            List<ExcelRedemption> collect = redemptionEntryList.stream().map(x->{
                Traveler traveler = travelerMap.getOrDefault(x.getRelatedObjectId(),new Traveler());
                Supplier supplier = longSupplierMap.get(x.getSupplier());
                return ExcelRedemption.from(x,traveler,supplier);
            }).collect(Collectors.toList());

            excelPOIHelper.exportRedemptionEntry(collect);

            export.setTotal_succeeded(collect.size());
            export.setStatus(EnumExportStatus.DONE);
            export.setDone_at(LocalDateTime.now());
            export = exportRepository.save(export);
        }
    }
}
