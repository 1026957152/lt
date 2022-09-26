package com.lt.dom.otcenum.enum_;

public enum EnumModule业务模块分类 {
    默认目录(0),
    企业财务附件(1);

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    EnumModule业务模块分类(Integer id){

    }
}
