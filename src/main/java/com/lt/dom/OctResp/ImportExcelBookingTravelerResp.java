package com.lt.dom.OctResp;

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

public class ImportExcelBookingTravelerResp {

    private String name;
 

    private String id;
    private String tel_home;

/*    @DateTimeFormat("yyyy-MM-dd")
    private Date createDate;*/
    private String result;

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


/*    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }*/

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}