package com.lt.dom.domain;

import java.util.List;

@lombok.NoArgsConstructor
@lombok.Data
public class 产品分类 {
    @com.fasterxml.jackson.annotation.JsonProperty("id")
    private Long id;
    @com.fasterxml.jackson.annotation.JsonProperty("parent_id")
    private Long parentId;
    @com.fasterxml.jackson.annotation.JsonProperty("user_id")
    private Long userId;
    @com.fasterxml.jackson.annotation.JsonProperty("type")
    private String type;
    @com.fasterxml.jackson.annotation.JsonProperty("status")
    private Long status;
    @com.fasterxml.jackson.annotation.JsonProperty("name")
    private String name;
    @com.fasterxml.jackson.annotation.JsonProperty("sort_order")
    private Long sortOrder;
}
