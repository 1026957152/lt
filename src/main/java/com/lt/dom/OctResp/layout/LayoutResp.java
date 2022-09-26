package com.lt.dom.OctResp.layout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.ProductResp;
import org.javatuples.Pair;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LayoutResp {

    private Map layout;
    private Integer version;
    private String name;

    public Map getLayout() {
        return layout;
    }

    public void setLayout(Map layout) {
        this.layout = layout;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
