package com.lt.dom.thirdTS.domainTsToLt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LtRespToTs退单接口 {


    @JsonProperty("info")
    private InfoDTO info;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("message")
    private String message;

    @NoArgsConstructor
    @Data
    public static class InfoDTO {
        @JsonProperty("id")
        private Long id; //退票记录ID
        @JsonProperty("status")
        private Integer status;  //  订单状态：2申请退票，3退票成功，4退票不通过（如果产品在天时系统中属于采购产品，退票默认需要审核【退票审核通知接口】）

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

    public InfoDTO getInfo() {
        return info;
    }

    public void setInfo(InfoDTO info) {
        this.info = info;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
