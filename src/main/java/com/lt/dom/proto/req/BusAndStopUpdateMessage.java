package com.lt.dom.proto.req;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

public class BusAndStopUpdateMessage {


    private List buses;
    private List stops;

    public <T> void setBuses(List buses) {
        this.buses = buses;
    }

    public List getBuses() {
        return buses;
    }

    public <T> void setStops(List stops) {
        this.stops = stops;
    }

    public List getStops() {
        return stops;
    }

    public static class LineUpdateMessage {

        private String lineCode;

        private List<StopUpdateMessage> stops;


        private List<BusUpdateMessage>  buses;

        public LineUpdateMessage() {
        }

        public LineUpdateMessage(String key, List<StopUpdateMessage> collect, List<BusUpdateMessage> buses) {

            this.lineCode = key;
            this.stops = collect;

            this.buses = buses;
        }

        public List<BusUpdateMessage> getBuses() {
            return buses;
        }

        public void setBuses(List<BusUpdateMessage> buses) {
            this.buses = buses;
        }

        public String getLineCode() {
            return lineCode;
        }

        public void setLineCode(String lineCode) {
            this.lineCode = lineCode;
        }

        public List<StopUpdateMessage> getStops() {
            return stops;
        }

        public void setStops(List<StopUpdateMessage> stops) {
            this.stops = stops;
        }
    }

    public static class StopUpdateMessage {

        public String getUpcomingTime() {
            return upcomingTime;
        }

        public void setUpcomingTime(String upcomingTime) {
            this.upcomingTime = upcomingTime;
        }



        private String upcomingTime;
        private String departure_distance;
        private String departure_time;

        private String number;

        public String getDeparture_distance() {
            return departure_distance;
        }

        public void setDeparture_distance(String departure_distance) {
            this.departure_distance = departure_distance;
        }

        public String getDeparture_time() {
            return departure_time;
        }

        public void setDeparture_time(String departure_time) {
            this.departure_time = departure_time;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }

    }
    public static class BusUpdateMessage {

        private Double lat;
        private Double lng;
        /*        private String nextStop;

                private Double nextStopDistance;*/
        private String updateTime;

        private String number;
        /*        private Integer velocity;*/
        private String label;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }
/*
        public String getNextStop() {
            return nextStop;
        }

        public void setNextStop(String nextStop) {
            this.nextStop = nextStop;
        }

        public Double getNextStopDistance() {
            return nextStopDistance;
        }

        public void setNextStopDistance(Double nextStopDistance) {
            this.nextStopDistance = nextStopDistance;
        }*/

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }



        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }





    public BusAndStopUpdateMessage() {

    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
