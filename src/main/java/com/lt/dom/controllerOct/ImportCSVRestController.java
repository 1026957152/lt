package com.lt.dom.controllerOct;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lt.dom.RealNameAuthentication.PhoneAuth;
import com.lt.dom.excel.ImportExcelBookingTraveler;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumImportCVS;
import com.lt.dom.repository.ImportExcelRepository;
import com.lt.dom.repository.ReservationRepository;
import com.lt.dom.serviceOtc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class ImportCSVRestController {


    @Autowired
    private ImportCVSServiceImpl importCVSService;
    @Autowired
    private ImportExcelRepository importExcelRepository;

    @Autowired
    private BookingServiceImpl bookingService;
    @Autowired
    private ReservationRepository reservationRepository;


    @RequestMapping("/importCSV/{IMPORT_CSV_ID}/travelers/importCSV/async")
    public ResponseEntity<List<Traveler>> confirm(@PathVariable long IMPORT_CSV_ID) throws IOException {

        Optional<ImportExcel> validatorOptional = importExcelRepository.findById(IMPORT_CSV_ID);
        if(validatorOptional.isPresent()){
            try {

                if(validatorOptional.get().getImportCSV_object().equals(EnumImportCVS.traveler)){
                    //TODO
                    Optional<Reservation> reservationOptional = reservationRepository.findById(validatorOptional.get().getRelativeid());
                    List<PhoneAuth> importExcelBookingTravelers = importCVSService.getDateByImportExcel(validatorOptional.get());


                    List<Traveler> bookings = bookingService.addBulkRealNameTraveler(reservationOptional.get(), importExcelBookingTravelers);
                    return ResponseEntity.ok(bookings);
                }

                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("抛出异常");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
    }



    @RequestMapping("/redemptions/{PRODUCT_ID}/importCSV")
    public ResponseEntity<List<ImportExcelBookingTraveler>> importExcelSubject(@PathVariable long PRODUCT_ID, MultipartFile file) throws IOException {

        ArrayList<ImportExcelBookingTraveler> list = new ArrayList<>();

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
        EasyExcel.read(file.getInputStream(), ImportExcelBookingTraveler.class, listener).sheet().doRead();

/*        List<OutEvalTopic> list = EasyExcelUtil.read(file.getInputStream(),
                OutEvalTopic.class);
        outEvalDeclareService.saveBatchTopic(list);*/
        list.forEach(System.out::println);

        return ResponseEntity.ok(list);
    }
/*————————————————
    版权声明：本文为CSDN博主「码道_00」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/weixin_42811034/article/details/124273319*/


}