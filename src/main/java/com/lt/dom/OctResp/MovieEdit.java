package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Attribute;
import com.lt.dom.oct.Movie;
import com.lt.dom.otcReq.AttributeEditReq;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Transient;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieEdit extends BaseResp{

    private EntityModel starringActorTab;//	A comma seperated list of actors.
    private String name_long;
    private Long theatre;
    private EntityModel aboutTab;

    public EntityModel getStarringActorTab() {
        return starringActorTab;
    }

    public void setStarringActorTab(EntityModel starringActorTab) {
        this.starringActorTab = starringActorTab;
    }

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public Long getTheatre() {
        return theatre;
    }

    public void setTheatre(Long theatre) {
        this.theatre = theatre;
    }

    public void setAboutTab(EntityModel aboutTab) {
        this.aboutTab = aboutTab;
    }

    public EntityModel getAboutTab() {
        return aboutTab;
    }

    public static class StarringActorTab  {
        private Map parameterList;


        private List<StarringActorResp> deviceResps;//	A comma seperated list of actors.
        public List<StarringActorResp> getDeviceResps() {
            return deviceResps;
        }

        public void setDeviceResps(List<StarringActorResp> deviceResps) {
            this.deviceResps = deviceResps;
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

    public static class StarringActorResp {

        private String name;//	The name or title.
        private String desc;//	The short print friendly name.
        private PhotoResp photo;

        public static StarringActorTab from() {
            StarringActorTab starringActorTab1 = new StarringActorTab();

            return starringActorTab1;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public PhotoResp getPhoto() {
            return photo;
        }

        public void setPhoto(PhotoResp photo) {
            this.photo = photo;
        }
    }

    private String name;//	The name or title.
    private String sortableName;//	The short print friendly name.
    private String directors;//	A comma seperated list of directors.


    private String wwmReleaseNumber;//	The West World Media Release Number.
    private String runTime;//	The total runtime in minutes.
    private String score;//	An Estimate of how the film should perform relative to other films, scored between zero and one; one being best.
    private String slug;//	A dash seperated title.
    private String synopsis;//	A short description of the film.
    private String synopsisTagLine;//	A short tag line for the film.
    private String releaseDateUtc;//	The offfical release date in UTC (ISO-8601).
    private String earliestShowingUtc;//	The earliest showing date in UTC (ISO-8601).
    private String hasScheduledShowtimes;//	Indicates whether performances have been scheduled.
    private String displayOnlineTicketAvailability;//	Indicates if tickets are available for purchase online.
    private String onlineTicketAvailabilityDateUtc;//	The date tickets will be available for online purchase in UTC (ISO-8601).
    private String websiteUrl;//	The website url.
    private String showtimesUrl;//	The showtimes url.
    private String distributorId;//	The unique identifier for the distributor of a film.
    private String distributorCode;//	The code to identify the distributor of a film.
    private String preferredMediaType;//	The preferred media type (Theatrical or OnDemand)
    private String imdbId;//	The external IMDB ID for this movie
    private String code;
    private PhotoResp thumb;
    private String desc_short;

    private String desc_long;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortableName() {
        return sortableName;
    }

    public void setSortableName(String sortableName) {
        this.sortableName = sortableName;
    }



    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getWwmReleaseNumber() {
        return wwmReleaseNumber;
    }

    public void setWwmReleaseNumber(String wwmReleaseNumber) {
        this.wwmReleaseNumber = wwmReleaseNumber;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getSynopsisTagLine() {
        return synopsisTagLine;
    }

    public void setSynopsisTagLine(String synopsisTagLine) {
        this.synopsisTagLine = synopsisTagLine;
    }

    public String getReleaseDateUtc() {
        return releaseDateUtc;
    }

    public void setReleaseDateUtc(String releaseDateUtc) {
        this.releaseDateUtc = releaseDateUtc;
    }

    public String getEarliestShowingUtc() {
        return earliestShowingUtc;
    }

    public void setEarliestShowingUtc(String earliestShowingUtc) {
        this.earliestShowingUtc = earliestShowingUtc;
    }

    public String getHasScheduledShowtimes() {
        return hasScheduledShowtimes;
    }

    public void setHasScheduledShowtimes(String hasScheduledShowtimes) {
        this.hasScheduledShowtimes = hasScheduledShowtimes;
    }

    public String getDisplayOnlineTicketAvailability() {
        return displayOnlineTicketAvailability;
    }

    public void setDisplayOnlineTicketAvailability(String displayOnlineTicketAvailability) {
        this.displayOnlineTicketAvailability = displayOnlineTicketAvailability;
    }

    public String getOnlineTicketAvailabilityDateUtc() {
        return onlineTicketAvailabilityDateUtc;
    }

    public void setOnlineTicketAvailabilityDateUtc(String onlineTicketAvailabilityDateUtc) {
        this.onlineTicketAvailabilityDateUtc = onlineTicketAvailabilityDateUtc;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getShowtimesUrl() {
        return showtimesUrl;
    }

    public void setShowtimesUrl(String showtimesUrl) {
        this.showtimesUrl = showtimesUrl;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getPreferredMediaType() {
        return preferredMediaType;
    }

    public void setPreferredMediaType(String preferredMediaType) {
        this.preferredMediaType = preferredMediaType;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Transient
    List<Attribute> attributes;

    public static MovieEdit from(Movie movie) {
        MovieEdit movieResp = MovieEdit.of(movie);

        return movieResp;
    }

    public static MovieEdit of(Movie e) {
        MovieEdit movieResp = new MovieEdit();
        movieResp.setName(e.getName());
        movieResp.setDesc_short(e.getDesc_short()==null?"描述":e.getDesc_short());
        //movieResp.setSynopsis(e.getSynopsis());
        movieResp.setDesc_long(e.getDesc_long());
        movieResp.setSortableName(e.getSortableName());
        movieResp.setCode(e.getCode());
        movieResp.setRunTime(e.getRunTime());
        movieResp.setSlug(e.getSlug());
        movieResp.setReleaseDateUtc(e.getReleaseDateUtc());
        movieResp.setEarliestShowingUtc(e.getEarliestShowingUtc());






        movieResp.setDirectors(e.getDirectors());
        movieResp.setCreatedDate(e.getCreatedDate());
        movieResp.setModifiedDate(e.getModifiedDate());
        return movieResp;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setThumb(PhotoResp thumb) {
        this.thumb = thumb;
    }

    public PhotoResp getThumb() {
        return thumb;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_short() {
        return desc_short;
    }



    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_long() {
        return desc_long;
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
