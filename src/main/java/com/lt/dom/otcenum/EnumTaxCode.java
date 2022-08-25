package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumTaxCode {
    txcd_3070301_旅游服务("3070301","旅游服务","*旅游服务*旅游",6),



    ;
    String 税收分类编码;
    String 税收分类名称;
    String 开票商品名称;
    int 税率;

    EnumTaxCode(String 税收分类编码,String 税收分类名称,String 开票商品名称, int 税率 ) {
        this.税收分类编码 = 税收分类编码;
        this.税收分类名称 = 税收分类名称;
        this.开票商品名称 = 开票商品名称;
        this.税率 = 税率;

    }
    @Autowired
    private MessageSource messageSource;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.tax.code."
                + this.name());
        return displayStatusString;
    }


}
