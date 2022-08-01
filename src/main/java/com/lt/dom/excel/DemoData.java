package com.lt.dom.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class DemoData {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("入职日期")
    private Date date;
    @ExcelProperty("工资")
    private Double sal;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }
}