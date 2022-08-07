package com.lt.dom.finance.DirectConnectionBetweenBanksAndEnterprises.cmb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class 企银支付单笔经办_BB1PAYOP_5_2_1 {


    @JsonProperty("request")
    private RequestDTO request;

    @NoArgsConstructor
    @Data
    public static class RequestDTO {
        @JsonProperty("body")
        private BodyDTO body;
        @JsonProperty("head")
        private HeadDTO head;

        @NoArgsConstructor
        @Data
        public static class BodyDTO {
            @JsonProperty("bb1paybmx1")
            private List<Bb1paybmx1DTO> bb1paybmx1;
            @JsonProperty("bb1payopx1")
            private List<Bb1payopx1DTO> bb1payopx1;

            @NoArgsConstructor
            @Data
            public static class Bb1paybmx1DTO {
                @JsonProperty("busCod")
                private String busCod;
                @JsonProperty("busMod")
                private String busMod;
            }

            @NoArgsConstructor
            @Data
            public static class Bb1payopx1DTO {
                @JsonProperty("ccyNbr")
                private String ccyNbr;
                @JsonProperty("crtAcc")
                private String crtAcc;
                @JsonProperty("crtNam")
                private String crtNam;
                @JsonProperty("dbtAcc")
                private String dbtAcc;
                @JsonProperty("nusAge")
                private String nusAge;
                @JsonProperty("bnkFlg")
                private String bnkFlg;
                @JsonProperty("trsAmt")
                private String trsAmt;
                @JsonProperty("yurRef")
                private String yurRef;
            }
        }

        @NoArgsConstructor
        @Data
        public static class HeadDTO {
            @JsonProperty("funcode")
            private String funcode;
            @JsonProperty("userid")
            private String userid;
            @JsonProperty("reqid")
            private String reqid;
        }
    }
}
