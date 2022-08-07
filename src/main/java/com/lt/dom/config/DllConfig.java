package com.lt.dom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DllConfig {

    @Value("${dll.path.idcard}")
    private String dllPath_idcard;
    @Value("${dll.path.printer}")
    private String dllPath_printer;

    public String getDllPath_printer() {
        return dllPath_printer;
    }

    public void setDllPath_printer(String dllPath_printer) {
        this.dllPath_printer = dllPath_printer;
    }


    public String getDllPath_idcard() {
        return dllPath_idcard;
    }

    public void setDllPath_idcard(String dllPath_idcard) {
        this.dllPath_idcard = dllPath_idcard;
    }
}