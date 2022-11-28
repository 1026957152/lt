package com.lt.dom.thirdTS.domainTsToLt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.threeten.bp.LocalDate;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class LtRespToTs产品列表 {



    @JsonProperty("list")
    private List<ListDTO> list;

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }







}
