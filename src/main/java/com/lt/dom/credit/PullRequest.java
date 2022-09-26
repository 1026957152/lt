package com.lt.dom.credit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Axh.PullFromYxdRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;


public class PullRequest {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("data")
    private List<PullFromYxdRequest> data;
    @JsonProperty("metadata")
    private Object metadata;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<PullFromYxdRequest> getData() {
        return data;
    }

    public void setData(List<PullFromYxdRequest> data) {
        this.data = data;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }
}
