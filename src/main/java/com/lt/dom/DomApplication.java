package com.lt.dom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

//@lombok.NoArgsConstructor
//@lombok.Data
@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
public class DomApplication {

    @com.fasterxml.jackson.annotation.JsonProperty("errorn")
    private Long errorn;
    @com.fasterxml.jackson.annotation.JsonProperty("g_err")
    private Long gErr;
  //  @com.fasterxml.jackson.annotation.JsonProperty("list")
  //  private java.util.List<ListDTO> list;
    @com.fasterxml.jackson.annotation.JsonProperty("message")
    private String message;
    @com.fasterxml.jackson.annotation.JsonProperty("success")
    private Boolean success;
    @com.fasterxml.jackson.annotation.JsonProperty("total")
    private Long total;

    public static void main(String[] args) {
        SpringApplication.run(DomApplication.class, args);
    }

}
