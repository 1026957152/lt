package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Employee;
import com.lt.dom.oct.Role;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidatorEditResp {


    private Map parameterList;
    private List<Long> ids;

    public Map getParameterList() {
        return parameterList;
    }

    public void setParameterList(Map parameterList) {
        this.parameterList = parameterList;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }



}
