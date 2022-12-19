package com.lt.dom.OctResp;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Attribute;
import com.lt.dom.oct.BusVehicle;
import com.lt.dom.oct.Device;
import com.lt.dom.otcReq.AttributeEditReq;
import com.lt.dom.otcenum.EnumBusType;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusVehicleEdit extends BaseResp{




    private String code;
    private List deviceResps;

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    private Long supplier;
    @Column(name = "platename")
    private String plateName;

    @Column(name = "seatsbooked")
    private Integer seatsbooked = 0;

    @Column(name = "totalseats")
    private Integer totalSeats;


    @Column(name = "dailystarttime")
    private DateTime dailyStartTime;

    @Column(name = "dailyStoptime")
    private DateTime dailyStopTime;

    @Column(name = "busType")
    private EnumBusType busType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public Integer getSeatsbooked() {
        return seatsbooked;
    }

    public void setSeatsbooked(Integer seatsbooked) {
        this.seatsbooked = seatsbooked;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }


    public DateTime getDailyStartTime() {
        return dailyStartTime;
    }

    public void setDailyStartTime(DateTime dailyStartTime) {
        this.dailyStartTime = dailyStartTime;
    }

    public DateTime getDailyStopTime() {
        return dailyStopTime;
    }

    public void setDailyStopTime(DateTime dailyStopTime) {
        this.dailyStopTime = dailyStopTime;
    }

    public EnumBusType getBusType() {
        return busType;
    }

    public void setBusType(EnumBusType busType) {
        this.busType = busType;
    }

    private EntityModel deviceTab;//	A comma seperated list of actors.

    private EntityModel aboutTab;

    public EntityModel getDeviceTab() {
        return deviceTab;
    }

    public void setDeviceTab(EntityModel deviceTab) {
        this.deviceTab = deviceTab;
    }

    public <R> void setDeviceResps(List deviceResps) {
        this.deviceResps = deviceResps;
    }

    public List getDeviceResps() {
        return deviceResps;
    }


    public static class DeviceTab {
        private Map parameterList;


        private List<DeviceReq> deviceReqs;//	A comma seperated list of actors.

        public List<DeviceReq> getDeviceReqs() {
            return deviceReqs;
        }

        public void setDeviceReqs(List<DeviceReq> deviceReqs) {
            this.deviceReqs = deviceReqs;
        }
    }
    private PhotoResp cover;
    private PhotoResp video;
    private Map parameterList;

    public void setCover(PhotoResp cover) {
        this.cover = cover;
    }

    public PhotoResp getCover() {
        return cover;
    }

    public void setVideo(PhotoResp video) {
        this.video = video;
    }

    public PhotoResp getVideo() {
        return video;
    }

    public <V, K> void setParameterList(Map parameterList) {
        this.parameterList = parameterList;
    }

    public Map getParameterList() {
        return parameterList;
    }

    public static class DeviceReq {

        private String name;//	The name or title.
        private Long id;//	The short print friendly name.
        private PhotoResp photo;

        public static DeviceReq from(Device device) {
            DeviceReq deviceTab1 = new DeviceReq();

            return deviceTab1;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public PhotoResp getPhoto() {
            return photo;
        }

        public void setPhoto(PhotoResp photo) {
            this.photo = photo;
        }
    }



    public static BusVehicleEdit from(BusVehicle movie) {
        BusVehicleEdit movieResp = BusVehicleEdit.of(movie);
        movieResp.setBusType(movie.getBusType());
        movieResp.setCode(movie.getCode());
        movieResp.setDailyStartTime(movie.getDailyStartTime());
        movieResp.setDailyStopTime(movie.getDailyStopTime());
        movieResp.setTotalSeats(movie.getTotalSeats());
        movieResp.setPlateName(movie.getPlateName());
        return movieResp;
    }

    public static BusVehicleEdit of(BusVehicle e) {
        BusVehicleEdit movieResp = new BusVehicleEdit();

        movieResp.setCreatedDate(e.getCreatedDate());
        movieResp.setModifiedDate(e.getModifiedDate());
        return movieResp;
    }

    public static class AboutTap {



        private AttributeEditReq show_intro;
        private AttributeEditReq story_intro;
        private AttributeEditReq team_intro;


        public AttributeEditReq getShow_intro() {
            return show_intro;
        }

        public void setShow_intro(AttributeEditReq show_intro) {
            this.show_intro = show_intro;
        }

        public AttributeEditReq getStory_intro() {
            return story_intro;
        }

        public void setStory_intro(AttributeEditReq story_intro) {
            this.story_intro = story_intro;
        }

        public AttributeEditReq getTeam_intro() {
            return team_intro;
        }

        public void setTeam_intro(AttributeEditReq team_intro) {
            this.team_intro = team_intro;
        }

        private Map parameterList;

        private List tips;


        public static AboutTap from(Attribute 演出介绍,Attribute 剧情介绍,Attribute 演出团体) {
            AboutTap availability = new AboutTap();

            AttributeEditReq attributeEditReq = new AttributeEditReq();
            attributeEditReq.setName(演出介绍.getName());
            attributeEditReq.setText(演出介绍.getDescription());
            attributeEditReq.setType(演出介绍.getFeatureType());
            attributeEditReq.setId(演出介绍.getId());
            availability.setShow_intro(attributeEditReq);

            attributeEditReq = new AttributeEditReq();
            attributeEditReq.setName(剧情介绍.getName());
            attributeEditReq.setText(剧情介绍.getDescription());
            attributeEditReq.setType(剧情介绍.getFeatureType());
            attributeEditReq.setId(剧情介绍.getId());
            availability.setStory_intro(attributeEditReq);

            attributeEditReq = new AttributeEditReq();
            attributeEditReq.setName(演出团体.getName());
            attributeEditReq.setText(演出团体.getDescription());
            attributeEditReq.setType(演出团体.getFeatureType());
            attributeEditReq.setId(演出团体.getId());
            availability.setTeam_intro(attributeEditReq);

      /*      availability.setKnowBeforeYouGo(bookingRuleList.stream()
                    .filter(e->"knowBeforeYouGo".equals(e.getKey()))
                    .map(e->{
                        AttributeEditReq attributeEditReq = new AttributeEditReq();
                        attributeEditReq.setName(e.getName());
                        attributeEditReq.setText(e.getDescription());
                        attributeEditReq.setType(e.getFeatureType());
                        attributeEditReq.setId(e.getId());
                        return attributeEditReq;

                    }).collect(Collectors.toList()));
            availability.setGettingThere(bookingRuleList.stream()
                    .filter(e->"gettingThere".equals(e.getKey()))
                    .map(e->{
                        AttributeEditReq attributeEditReq = new AttributeEditReq();
                        attributeEditReq.setName(e.getName());
                        attributeEditReq.setText(e.getDescription());
                        attributeEditReq.setType(e.getFeatureType());
                        attributeEditReq.setId(e.getId());
                        return attributeEditReq;

                    }).collect(Collectors.toList()));
            availability.setHoursOfOperation(bookingRuleList.stream()
                    .filter(e->"hoursOfOperation".equals(e.getKey()))

                    .map(e->{
                        AttributeEditReq attributeEditReq = new AttributeEditReq();
                        attributeEditReq.setName(e.getName());
                        attributeEditReq.setText(e.getDescription());
                        attributeEditReq.setType(e.getFeatureType());
                        attributeEditReq.setId(e.getId());
                        return attributeEditReq;

                    }).collect(Collectors.toList()));*/
/*            availability.setComponents(bookingRuleList.stream().map(e->{
                return ComponentResp.from(e);
            }).collect(Collectors.toList()));*/

            return availability;
        }


        public <V, K> void setParameterList(Map parameterList) {
            this.parameterList = parameterList;
        }

        public Map getParameterList() {
            return parameterList;
        }


        public <T> void setTips(List tips) {
            this.tips = tips;
        }

        public List getTips() {
            return tips;
        }


    }


}
