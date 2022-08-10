package com.lt.dom.OctResp;

import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductType;
import org.javatuples.Pair;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;


public class ProductResp {



    private String supplier;
    private String code;
    private String supplierCode;
    private String name;
    private String nameLong;


    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<ComponentResp> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentResp> components) {
        this.components = components;
    }


    public EnumProductType getType() {
        return type;
    }

    public void setType(EnumProductType type) {
        this.type = type;
    }

    public List<AttributeResp> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeResp> attributes) {
        this.attributes = attributes;
    }

    public List<String> getPaymentOptionList() {
        return paymentOptionList;
    }

    public void setPaymentOptionList(List<String> paymentOptionList) {
        this.paymentOptionList = paymentOptionList;
    }

    public ProductTheatre getTheatre() {
        return theatre;
    }

    public void setTheatre(ProductTheatre theatre) {
        this.theatre = theatre;
    }

    public ProductAttraction getAttraction() {
        return attraction;
    }

    public void setAttraction(ProductAttraction attraction) {
        this.attraction = attraction;
    }

    List<ComponentResp> components;



    private EnumProductType type ;

    List<AttributeResp> attributes;
    List<String> paymentOptionList;

    @Transient
    private ProductTheatre theatre;
    @Transient
    private ProductAttraction attraction;


    public static ProductResp from(Pair<Product,Supplier> pair) {
        Product product = pair.getValue0();
        Supplier supplier = pair.getValue1();

        ProductResp resp = new ProductResp();
        resp.setSupplier(product.getSupplierId()+"");
        resp.setPaymentOptionList(Arrays.asList(EnumPaymentOption.giftCard.name(),
                EnumPaymentOption.loyalty.name()));
        resp.setType(product.getType());
        resp.setCode(product.getCode());
        resp.setSupplier(supplier.getName());
        resp.setSupplierCode(supplier.getCode());
        resp.setName(product.getName());
        resp.setNameLong(product.getName_long());
        return resp;
    }

    public static ProductResp from(Product product) {


        ProductResp resp = new ProductResp();
        resp.setSupplier(product.getSupplierId()+"");
        resp.setPaymentOptionList(Arrays.asList(EnumPaymentOption.giftCard.name(),
                EnumPaymentOption.loyalty.name()));
        resp.setType(product.getType());
        resp.setCode(product.getCode());
      //  resp.setSupplier(supplier.getName());
      //  resp.setSupplierCode(supplier.getCode());
        resp.setName(product.getName());
        resp.setNameLong(product.getName_long());
        return resp;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }

    public String getNameLong() {
        return nameLong;
    }


    public class ComponentResp{
        private String name;
        private String note;
    }

}
