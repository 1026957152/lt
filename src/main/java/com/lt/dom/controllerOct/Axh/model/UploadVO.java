package com.lt.dom.controllerOct.Axh.model;

import org.springframework.core.io.Resource;

///xydfinanceproductorderinfo/creditWaitConfirm

public class UploadVO {
    private Resource file;
    private Integer module;

    public Resource getFile() {
        return file;
    }

    public void setFile(Resource file) {
        this.file = file;
    }

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }
}
