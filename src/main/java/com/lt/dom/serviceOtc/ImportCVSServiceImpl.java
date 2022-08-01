package com.lt.dom.serviceOtc;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.error.ExcelParseException;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportCVSServiceImpl {



    @Autowired
    private BookingAsycServiceImpl bookingAsycService;



    public List<ImportExcelBookingTraveler> importBulkTravelerExcelSubject(MultipartFile file, InputStream inputStream) {





        if(false){
            throw new ExcelParseException(10);
        }


        List<ImportExcelBookingTraveler> list = new ArrayList<>();

        AnalysisEventListener listener = new AnalysisEventListener<ImportExcelBookingTraveler>() {
            @Override
            public void invoke(ImportExcelBookingTraveler data, AnalysisContext context) {
                list.add(data);
                System.out.println( data.toString());

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                System.out.printf("导入数据完毕");
            }
        };

        //List<ImportTestDatasReq> list = EasyExcel.read(file.getInputStream(), ImportTestDatasReq.class, listener).sheet().doReadSync();
        EasyExcel.read(inputStream, ImportExcelBookingTraveler.class, listener).sheet().doRead();


        list.forEach(System.out::println);



        return list;

    }


    public List<PhoneAuth> getDateByImportExcel(ImportExcel importExcel) {

        ObjectMapper mapper = new ObjectMapper();

        List<PhoneAuth> participantJsonList = null;
        try {
            participantJsonList = mapper.readValue(importExcel.getAttachment(),
                    new TypeReference<List<PhoneAuth>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

      //  Gson gson = new Gson();
       // List<PhoneAuth> nameList = gson.fromJson(importExcel.getAttachment(), PhoneAuth.class);

        System.out.println(importExcel.getAttachment()+" aaa");
   ///     List<PhoneAuth> importExcelBookingTravelers =(List<PhoneAuth>) JSONArray.(importExcel.getAttachment());
        return participantJsonList;
    }
}
