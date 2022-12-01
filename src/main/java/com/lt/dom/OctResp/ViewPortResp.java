package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewPortResp {


    @JsonProperty("topLeftPoint")
    private PointDTO topLeftPoint;
    @JsonProperty("btmRightPoint")
    private PointDTO btmRightPoint;

    public PointDTO getTopLeftPoint() {
        return topLeftPoint;
    }

    public void setTopLeftPoint(PointDTO topLeftPoint) {
        this.topLeftPoint = topLeftPoint;
    }

    public PointDTO getBtmRightPoint() {
        return btmRightPoint;
    }

    public void setBtmRightPoint(PointDTO btmRightPoint) {
        this.btmRightPoint = btmRightPoint;
    }

    @NoArgsConstructor
    @Data
    public static class PointDTO {
        @JsonProperty("lat")
        private Double lat;
        @JsonProperty("lon")
        private Double lon;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public static PointDTO from() {
            PointDTO pointDTO = new PointDTO();
            pointDTO.setLat(2d);
            pointDTO.setLon(2d);
            return pointDTO;
        }
    }
}
