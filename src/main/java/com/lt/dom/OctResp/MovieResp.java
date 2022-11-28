package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Attribute;
import com.lt.dom.oct.Movie;
import com.lt.dom.oct.StarringActor;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Transient;
import java.util.List;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieResp extends BaseResp{
    private PhotoResp cover;
    private PhotoResp video;
    private List blocks;
    private AttributeResp show_intro;
    private AttributeResp story_intro;
    private AttributeResp team_intro;
    private String status_text;

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

    public <R> void setBlocks(List blocks) {
        this.blocks = blocks;
    }

    public List getBlocks() {
        return blocks;
    }

    public void setShow_intro(AttributeResp show_intro) {
        this.show_intro = show_intro;
    }

    public AttributeResp getShow_intro() {
        return show_intro;
    }

    public void setStory_intro(AttributeResp story_intro) {
        this.story_intro = story_intro;
    }

    public AttributeResp getStory_intro() {
        return story_intro;
    }

    public void setTeam_intro(AttributeResp team_intro) {
        this.team_intro = team_intro;
    }

    public AttributeResp getTeam_intro() {
        return team_intro;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public static class StarringActorResp {

        private String name;//	The name or title.
        private String desc;//	The short print friendly name.
        private PhotoResp photo;

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
    private List<StarringActorResp> starringActors;//	A comma seperated list of actors.
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
    private EntityModel theatre;
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

    public List<StarringActorResp> getStarringActors() {
        return starringActors;
    }

    public void setStarringActors(List<StarringActorResp> starringActors) {
        this.starringActors = starringActors;
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

    public static MovieResp from(Movie movie) {
        MovieResp movieResp = MovieResp.of(movie);

        return movieResp;
    }


    public static MovieResp homeFrontSimple(Movie e) {
        MovieResp movieResp = new MovieResp();
        movieResp.setName(e.getName());
        movieResp.setDesc_short(e.getDesc_short()==null?"描述":e.getDesc_short());
        movieResp.setSortableName(e.getSortableName());
        movieResp.setRunTime(e.getRunTime()==null?"2小时":e.getRunTime());


        return movieResp;
    }

    public static MovieResp of(Movie e) {
        MovieResp movieResp = new MovieResp();
        movieResp.setName(e.getName());
        movieResp.setDesc_short(e.getDesc_short()==null?"描述":e.getDesc_short());
        //movieResp.setSynopsis(e.getSynopsis());
        movieResp.setDesc_long(e.getDesc_long());
        movieResp.setSortableName(e.getSortableName());
        movieResp.setCode(e.getCode());
        movieResp.setRunTime(e.getRunTime());
        movieResp.setReleaseDateUtc(e.getReleaseDateUtc());
        movieResp.setEarliestShowingUtc(e.getEarliestShowingUtc());

        movieResp.setStatus_text("status");





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

    public void setTheatre(EntityModel theatre) {
        this.theatre = theatre;
    }

    public EntityModel getTheatre() {
        return theatre;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_long() {
        return desc_long;
    }
}
