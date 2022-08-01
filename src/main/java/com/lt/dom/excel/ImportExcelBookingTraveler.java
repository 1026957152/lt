package com.lt.dom.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;


import java.math.BigDecimal;
import java.util.Date;
 
/**
 * <p>ClassName：ImportTestDatasReq</p >
 * <p>Description：</p >
 * <p>Date：2021/9/2</p >
 */
/**
 * @Author husky
 * @Date 2022/3/14 16:18
 * @Description: Excel 数据封装实体
 **/
@ColumnWidth(15)  //列宽
@HeadRowHeight(20) //标题行高
@HeadFontStyle(fontHeightInPoints = 12, color = 9) //文件头的字体大小和颜色


public class ImportExcelBookingTraveler {
    @ExcelProperty(value = "游客姓名", index = 0)

    private String name;
 

    @ExcelProperty(value = "身份证号码", index = 1)
    private String id;
    @ExcelProperty(value = "游客手机号", index = 2)
    private String tel_home;
    @ExcelProperty(value = "数量", index = 3)
    private BigDecimal price;
 
    @ExcelProperty(value = "时间", index = 4)
    @DateTimeFormat("yyyy-MM-dd")
    private Date createDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel_home() {
        return tel_home;
    }

    public void setTel_home(String tel_home) {
        this.tel_home = tel_home;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}