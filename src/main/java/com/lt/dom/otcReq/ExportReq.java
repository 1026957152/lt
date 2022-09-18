package com.lt.dom.otcReq;

import com.lt.dom.OctResp.ExportResp;
import com.lt.dom.oct.Export;
import com.lt.dom.otcenum.EnumExportVoucher;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


public class ExportReq {


    @NotNull
    private EnumExportVoucher exported_object;
    private Parameters parameters;
    

private String code;



    public EnumExportVoucher getExported_object() {
        return exported_object;
    }

    public void setExported_object(EnumExportVoucher exported_object) {
        this.exported_object = exported_object;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public class Parameters  {
        private List<String> fields;

        public String filters;

        public List<String> getFields() {
            return fields;
        }

        public void setFields(List<String> fields) {
            this.fields = fields;
        }

        public String getFilters() {
            return filters;
        }

        public void setFilters(String filters) {
            this.filters = filters;
        }
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
