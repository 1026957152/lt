package com.lt.dom.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Slot {

    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("timestamp_end")
    private String timestampEnd;
    @JsonProperty("formatted_timestamp")
    private String formattedTimestamp;
    @JsonProperty("formatted_timestamp_end")
    private String formattedTimestampEnd;
    @JsonProperty("free")
    private Integer free;
    @JsonProperty("available_resources")
    private List<Integer> availableResources;
    @JsonProperty("maximum_capacity")
    private Integer maximumCapacity;
}
