package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.oct.CityWalk;
import com.lt.dom.otcReq.LocationResp;
import org.springframework.hateoas.EntityModel;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityWalkStepEditResp {

    public static class DistributionTab {

        private String tour_summary;
        private String name_long;
        private String desc_long;
        private String slug;
        private List<String> majorLandmarks;
        private List<PhotoResp> medias;
        private String cityRegion;

        public static DistributionTab from(CityWalk e) {

            DistributionTab cityWalkResp = new DistributionTab();
            //cityWalkResp.setAddress();
            cityWalkResp.setTitle(e.getTitle());
            cityWalkResp.setIntro(e.getIntro());

            cityWalkResp.setTotal_duration(e.getTotal_duration());
            cityWalkResp.setWalk_duration(e.getWalk_duration());
            cityWalkResp.setWalk_distance(e.getWalk_distance());

            cityWalkResp.setWalk_duration_icon("http://yulinmei.cn:8080/oct/files/citywalk/duration.svg");
            cityWalkResp.setWalk_distance_icon("http://yulinmei.cn:8080/oct/files/citywalk/map-distance.svg");




            if(e.getMajorLandmarks_json() != null){
                cityWalkResp.setMajorLandmarks(Arrays.asList(new Gson().fromJson(e.getMajorLandmarks_json(),String[].class)));

            }else{
                cityWalkResp.setMajorLandmarks(Arrays.asList());

            }


            cityWalkResp.setTour_summary(e.getTour_summary());
            cityWalkResp.setName_long(e.getName_long());
            cityWalkResp.setDesc_long(e.getDesc_long());
            cityWalkResp.setSlug(e.getSlug());


            cityWalkResp.setLocation(LocationResp.from(e.getLocation()));

            cityWalkResp.setCityRegion(e.getCityRegion());
            return cityWalkResp;
        }
        public void setLocation(LocationResp location) {
            this.location = location;
        }

        public LocationResp getLocation() {
            return location;
        }

        private LocationResp location;


        private Long walk_duration;


        private Long walk_distance;
        private Long total_duration;



        private String title;
        private String intro;
        private PhotoResp introductionAudio;
        private String portrait;
        private MediaResp media;
        private String walk_duration_icon;
        private String walk_distance_icon;


        public Long getWalk_duration() {
            return walk_duration;
        }

        public void setWalk_duration(Long walk_duration) {
            this.walk_duration = walk_duration;
        }

        public Long getWalk_distance() {
            return walk_distance;
        }

        public void setWalk_distance(Long walk_distance) {
            this.walk_distance = walk_distance;
        }

        public Long getTotal_duration() {
            return total_duration;
        }

        public void setTotal_duration(Long total_duration) {
            this.total_duration = total_duration;
        }




        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntroductionAudio(PhotoResp introductionAudio) {
            this.introductionAudio = introductionAudio;
        }

        public PhotoResp getIntroductionAudio() {
            return introductionAudio;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setMedia(MediaResp media) {
            this.media = media;
        }

        public MediaResp getMedia() {
            return media;
        }

        public void setWalk_duration_icon(String walk_duration_icon) {
            this.walk_duration_icon = walk_duration_icon;
        }

        public String getWalk_duration_icon() {
            return walk_duration_icon;
        }

        public void setWalk_distance_icon(String walk_distance_icon) {
            this.walk_distance_icon = walk_distance_icon;
        }

        public String getWalk_distance_icon() {
            return walk_distance_icon;
        }

        public void setTour_summary(String tour_summary) {
            this.tour_summary = tour_summary;
        }

        public String getTour_summary() {
            return tour_summary;
        }

        public void setName_long(String name_long) {
            this.name_long = name_long;
        }

        public String getName_long() {
            return name_long;
        }

        public void setDesc_long(String desc_long) {
            this.desc_long = desc_long;
        }

        public String getDesc_long() {
            return desc_long;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getSlug() {
            return slug;
        }

        public <T> void setMajorLandmarks(List<String> majorLandmarks) {
            this.majorLandmarks = majorLandmarks;
        }

        public List<String> getMajorLandmarks() {
            return majorLandmarks;
        }

        public void setMedias(List<PhotoResp> medias) {
            this.medias = medias;
        }

        public List<PhotoResp> getMedias() {
            return medias;
        }

        public void setCityRegion(String cityRegion) {
            this.cityRegion = cityRegion;
        }

        public String getCityRegion() {
            return cityRegion;
        }
    }
    private EntityModel distributionTab;

    public EntityModel getDistributionTab() {
        return distributionTab;
    }

    public void setDistributionTab(EntityModel distributionTab) {
        this.distributionTab = distributionTab;
    }

    public static class PlaceTab {

        private List<EntityModel> way_points;

        public List<EntityModel> getWay_points() {
            return way_points;
        }

        public void setWay_points(List<EntityModel> way_points) {
            this.way_points = way_points;
        }
    }
    private EntityModel placeTab;

    public EntityModel getPlaceTab() {
        return placeTab;
    }

    public void setPlaceTab(EntityModel placeTab) {
        this.placeTab = placeTab;
    }

/*



    public static CityWalkStepEditResp fromSnip(CityWalk e) {

        CityWalkStepEditResp cityWalkResp = new CityWalkStepEditResp();
        cityWalkResp.setTotal_duration(e.getTotal_duration());
        cityWalkResp.setWalk_duration(e.getWalk_duration());
        cityWalkResp.setWalk_distance(e.getWalk_distance());
        cityWalkResp.setWalk_duration_icon("http://yulinmei.cn:8080/oct/files/citywalk/duration.svg");
        cityWalkResp.setWalk_distance_icon("http://yulinmei.cn:8080/oct/files/citywalk/map-distance.svg");

        return cityWalkResp;
    }
*/



    public static class ScriptTab {

        private List<EntityModel> way_points;

        public List<EntityModel> getWay_points() {
            return way_points;
        }

        public void setWay_points(List<EntityModel> way_points) {
            this.way_points = way_points;
        }
    }
    private EntityModel scriptTab;


    public EntityModel getScriptTab() {
        return scriptTab;
    }

    public void setScriptTab(EntityModel scriptTab) {
        this.scriptTab = scriptTab;
    }
}
