package com.lt.dom.serviceOtc;


import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Departures;
import com.lt.dom.oct.Export;
import com.lt.dom.oct.Product;
import com.lt.dom.otcReq.ExportReq;
import com.lt.dom.otcenum.EnumExportStatus;
import com.lt.dom.otcenum.EnumExportVoucher;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.ExportRepository;
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
import java.util.stream.Collectors;

@Service
public class ExportServiceImpl {
    @Autowired
    private AsyncServiceImpl asyncService;

    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    private ExportRepository exportRepository;


    public Export createExport(ExportReq pojo) {
        Export export = new Export();

        export.setCreated_at(LocalDateTime.now());
        export.setStatus(EnumExportStatus.SCHEDULED);
        export.setExported_object(pojo.getExported_object());
        export.setCode(idGenService.exportNo());


        export = exportRepository.save(export);


        asyncService.asyncExport(export);
        return export;
    }



}
