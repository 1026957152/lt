package com.lt.dom.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class JacksonConfig {

  @Autowired
  private ObjectMapper objectMapper;

  @PostConstruct
  public void configureObjectMapper() {
      objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
  }

}