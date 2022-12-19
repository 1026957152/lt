package com.lt.dom.proto.req;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

public class BusAndStopUpdateMessage {


    public static class LineUpdateMessage {

        private String lineCode;

        private List<StopUpdateMessage> stops;


        private List<BusUpdateMessage>  buses;



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
        private String upcomingDistance;
        private String updateTime;

        private String number;

        public String getUpcomingDistance() {
            return upcomingDistance;
        }

        public void setUpcomingDistance(String upcomingDistance) {
            this.upcomingDistance = upcomingDistance;
        }

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

    private static BusUpdateMessage bus;
    private static StopUpdateMessage stop;

    public BusUpdateMessage getBus() {
        return bus;
    }

    public void setBus(BusUpdateMessage bus) {
        this.bus = bus;
    }

    public StopUpdateMessage getStop() {
        return stop;
    }

    public void setStop(StopUpdateMessage stop) {
        this.stop = stop;
    }

    public BusAndStopUpdateMessage() {

    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}

