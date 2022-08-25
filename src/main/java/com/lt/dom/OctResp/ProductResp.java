package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumPaymentOption;
import com.lt.dom.otcenum.EnumProductStatus;
import com.lt.dom.otcenum.EnumProductType;
import org.javatuples.Pair;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResp {



    private ProductTourResp tour;
    private String type_text;
    private EnumProductStatus status;
    private String status_text;

    public static Object dayTourFrom(Product product, Tour tour, List<Campaign> campaignAssignToTourProducts) {



        ProductResp resp = new ProductResp();
        resp.setSupplier(product.getSupplierId()+"");
        resp.setPaymentOptionList(Arrays.asList(EnumPaymentOption.giftCard,
                EnumPaymentOption.wechat_pay).stream().map(x->{
                    EnumResp enumResp = new EnumResp();
                    enumResp.setId(x.name());
                    enumResp.setText(x.toString());
                    return enumResp;
        }).collect(Collectors.toList()));
        resp.setType(product.getType());
        resp.setType_text(product.getType().toString());
        resp.setCode(product.getCode());
/*        resp.setSupplier(supplier.getName());
        resp.setSupplierCode(supplier.getCode());*/
        resp.setName(product.getName());
        resp.setName_long(product.getName_long());
        List<EnumLongIdResp> campaignList = campaignAssignToTourProducts.stream().map(x->{
            EnumLongIdResp enumLongIdResp = new EnumLongIdResp();
            enumLongIdResp.setId(x.getId());
            enumLongIdResp.setText(x.getName());
            return enumLongIdResp;
        }).collect(Collectors.toList());
        resp.setTour(ProductTourResp.from(tour,campaignList));
        resp.setStatus(product.getStatus());
        resp.setStatus_text(product.getStatus().toString());

        return resp;
    }



    public ProductTourResp getTour() {
        return tour;
    }

    public void setTour(ProductTourResp tour) {
        this.tour = tour;
    }

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

    public List<EnumResp> getPaymentOptionList() {
        return paymentOptionList;
    }

    public void setPaymentOptionList(List<EnumResp> paymentOptionList) {
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
    List<EnumResp> paymentOptionList;

    @Transient
    private ProductTheatre theatre;
    @Transient
    private ProductAttraction attraction;


    public static ProductResp from(Pair<Product,Supplier> pair) {
        Product product = pair.getValue0();
        Supplier supplier = pair.getValue1();

        ProductResp resp = new ProductResp();
        resp.setSupplier(product.getSupplierId()+"");
        resp.setPaymentOptionList(Arrays.asList(EnumPaymentOption.giftCard,
                EnumPaymentOption.wechat_pay).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList()));
        resp.setType(product.getType());
        resp.setType_text(product.getType().toString());
        resp.setCode(product.getCode());
        resp.setSupplier(supplier.getName());
        resp.setSupplierCode(supplier.getCode());
        resp.setName(product.getName());
        resp.setName_long(product.getName_long());
        resp.setStatus(product.getStatus());
        resp.setStatus_text(product.getStatus().toString());
        return resp;
    }

    public static ProductResp from(Product product) {


        ProductResp resp = new ProductResp();
        resp.setSupplier(product.getSupplierId()+"");
        resp.setPaymentOptionList(Arrays.asList(EnumPaymentOption.giftCard,
                EnumPaymentOption.wechat_pay).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList()));
        resp.setType(product.getType());
        resp.setType_text(product.getType().toString());
        resp.setCode(product.getCode());
      //  resp.setSupplier(supplier.getName());
      //  resp.setSupplierCode(supplier.getCode());
        resp.setName(product.getName());
        resp.setName_long(product.getName_long());
        resp.setStatus(product.getStatus());
        resp.setStatus_text(product.getStatus().toString());


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

    public void setName_long(String nameLong) {
        this.nameLong = nameLong;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setStatus(EnumProductStatus status) {
        this.status = status;
    }

    public EnumProductStatus getStatus() {
        return status;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }


    public class ComponentResp{
        private String name;
        private String note;
    }

}
