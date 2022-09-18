package com.lt.dom.rabbit;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record CustomMessage(@JsonProperty("text") String text,
                         @JsonProperty("priority") int priority,
                         @JsonProperty("secret") boolean secret) implements Serializable {
}