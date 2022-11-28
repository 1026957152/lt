package com.lt.dom.controllerOct;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import com.lt.dom.oct.Passenger;
import com.lt.dom.oct.Product;
import com.lt.dom.repository.PassengerRepository;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.VoucherRepository;
import com.lt.dom.serviceOtc.AvailabilityServiceImpl;
import com.lt.dom.serviceOtc.VonchorServiceImpl;
import com.lt.dom.thirdMt.mtTolt.MtpOrderPayResponse实名;
import com.lt.dom.vo.AgeRang_bigData;
import org.checkerframework.checker.units.qual.A;
import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oct/statistic")
public class StatisticsRestController {


    @Autowired
    private ProductRepository productRepository;


    private IdcardUtil idcardUtil;

    @Autowired
    private VonchorServiceImpl vonchorService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;

    @Autowired
    private PassengerRepository passengerRepository;


    @GetMapping(value = "version", produces = "application/json")
    public Map version(@PathVariable long PRODUCT_ID) {


        AgeRang_bigData ageRang_bigData = new AgeRang_bigData();
        ageRang_bigData.setFrom(10);
        ageRang_bigData.setTo(20);
        ageRang_bigData.setLable("20<30");

        List<AgeRang_bigData> ageRang_bigData1 = Arrays.asList(ageRang_bigData);


        List<Passenger> passengers = passengerRepository.findAll();



        Map<String, Integer> MtpOrderPayResponse实名 = passengers.stream().map(e->{
            int age = IdcardUtil.getAgeByIdCard(e.getIdNo(), new Date());
            Assert.assertEquals(age, 38);
            return ageRang_bigData1.stream().filter(range->(range.getFrom() < age) & (range.getTo() > age))
                    .findAny().get().getLable();
        }).collect(Collectors.groupingBy(eee->eee)).entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue().size()));

        Map<String, Integer> aral = passengers.stream().map(e->{
            int age = IdcardUtil.getAgeByIdCard(e.getIdNo(), new Date());
            Assert.assertEquals(age, 38);
            return ageRang_bigData1.stream().filter(range->(range.getFrom() < age) & (range.getTo() > age))
                    .findAny().get().getLable();
        }).collect(Collectors.groupingBy(eee->eee)).entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue().size()));



        Map<String, Integer> sta = passengers.stream().map(e->{
            int age = IdcardUtil.getAgeByIdCard(e.getIdNo(), new Date());
            Assert.assertEquals(age, 38);
            return ageRang_bigData1.stream().filter(range->(range.getFrom() < age) & (range.getTo() > age))
                    .findAny().get().getLable();
        }).collect(Collectors.groupingBy(eee->eee)).entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue().size()));



        List<Map> list = Arrays.asList(Map.of("12点","两日"));

        Map map = Map.of(
                "游客承载能力",20,
                "实时在园人数",10,
                "age",MtpOrderPayResponse实名,
                "area",aral,
            "游客驻留时间",sta,
                "车位使用率",20,
                "近两日数据统计",Arrays.asList(list,list)
                );



        return map;


    }

    @GetMapping(value = "health", produces = "application/json")
    public List<Calendar> health(@PathVariable long PRODUCT_ID) {

        String ID_18 = "321083197812162119";
        String ID_15 = "150102880730303";

//是否有效
        boolean valid = IdcardUtil.isValidCard(ID_18);
        boolean valid15 = IdcardUtil.isValidCard(ID_15);

//转换
        String convert15To18 = IdcardUtil.convert15To18(ID_15);
        Assert.assertEquals(convert15To18, "150102198807303035");

//年龄
        DateTime date = DateUtil.parse("2017-04-10");

        int age = IdcardUtil.getAgeByIdCard(ID_18, date);
        Assert.assertEquals(age, 38);

        int age2 = IdcardUtil.getAgeByIdCard(ID_15, date);
        Assert.assertEquals(age2, 28);

//生日
        String birth = IdcardUtil.getBirthByIdCard(ID_18);
        Assert.assertEquals(birth, "19781216");

        String birth2 = IdcardUtil.getBirthByIdCard(ID_15);
        Assert.assertEquals(birth2, "19880730");

//省份
        String province = IdcardUtil.getProvinceByIdCard(ID_18);
        Assert.assertEquals(province, "江苏");

        String province2 = IdcardUtil.getProvinceByIdCard(ID_15);
        Assert.assertEquals(province2, "内蒙古");
        return null;//

    }

}