package com.lt.dom.oct;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.util.List;


@Entity
public class Movie {
    @Version
    private Integer version;
    @Id
    private long id;//	The unique identifier for the given movie.
    private String name;//	The name or title.
    private String sortableName;//	The short print friendly name.
    private String starringActors;//	A comma seperated list of actors.
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
}
