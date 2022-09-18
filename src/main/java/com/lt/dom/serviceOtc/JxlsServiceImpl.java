package com.lt.dom.serviceOtc;


import com.lt.dom.repository.BookingRuleRepository;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class JxlsServiceImpl {


    public JxlsServiceImpl() throws IOException {
    }

    public static class Employee {
        private String name;
        private int age;
        private Double payment;
        private Double bonus;
        private Date birthDate;
        private Employee superior;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPayment() {
            return payment;
        }

        public void setPayment(Double payment) {
            this.payment = payment;
        }

        public Double getBonus() {
            return bonus;
        }

        public void setBonus(Double bonus) {
            this.bonus = bonus;
        }

        public Date getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(Date birthDate) {
            this.birthDate = birthDate;
        }

        public Employee(String name, Double payment, Double bonus, Date birthDate) {
            this.name = name;
            this.payment = payment;
            this.bonus = bonus;
            this.birthDate = birthDate;
        }
    }

    @Autowired
    private BookingRuleRepository bookingRuleRepository;

    public static void getAvailability(String name) throws IOException {
        Employee employee = new Employee("DDD", 12D, 12D, new Date());
        //   logger.info("Running Object Collection demo");
        List<Employee> employees = Arrays.asList(employee);//generateSampleEmployeeData();

        InputStream aa = new ClassPathResource("object_collection_template.xlsx").getInputStream();

        System.out.println("=============== 生成Excel 模板" + aa);
        try (InputStream is = JxlsServiceImpl.class.getResourceAsStream("object_collection_template.xlsx")) {


         //   String exportFilePath = "C:\Users\l'j'm\Desktop\";
           // try (OutputStream os = new FileOutputStream("D:\\object_collection_output.xlsx")) {
         File file =   Paths.get("fileStorage").resolve(name+"object_collection_output_01.xlsx").toFile();
                try (OutputStream os = new FileOutputStream(file.getAbsolutePath())) {
                    System.out.println("=============== 生成Excel 模板" + file.getAbsolutePath());
                    Context context = new Context();
                  //  context.putVar("code", name);
                context.putVar("title", "我的模板标题");
                context.putVar("employees", employees);
                JxlsHelper.getInstance().processTemplate(aa, os, context);
            }
        }


    }
}
