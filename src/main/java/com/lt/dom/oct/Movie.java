package com.lt.dom.oct;

import com.lt.dom.OctResp.ComponentRightResp;
import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.EnumProductComponentSource;
import com.lt.dom.otcenum.EnumRoyaltyRuleCategory;
import com.lt.dom.otcenum.EnumValidateWay;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Entity
public class Movie extends Base{



    @OneToMany(mappedBy="movie",
            cascade = CascadeType.ALL)
    List<StarringActor> starringActors;
    private String name;//	The name or title.
    private String sortableName;//	The short print friendly name.
  //  private String starringActors;//	A comma seperated list of actors.
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

    @Transient
    List<Attribute> attributes;
    private long supplier;
    private String code;
    private String name_long;
    private String desc_long;
    private String desc_short;
    private Long theatre;


    public static List List(List<Movie> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getName()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
    }


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

    public List<StarringActor> getStarringActors() {
        return starringActors;
    }

    public void setStarringActors(List<StarringActor> starringActors) {
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

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
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

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setTheatre(Long theatre) {
        this.theatre = theatre;
    }

    public Long getTheatre() {
        return theatre;
    }
}
