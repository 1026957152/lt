package com.lt.dom.otcReq;

import com.lt.dom.OctResp.AttributeResp;
import com.lt.dom.OctResp.MediaReq;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Attribute;

import javax.persistence.*;
import java.util.List;


public class MovieReq {
    private PhotoResp video;
    private Double lat;
    private Double lng;

    public PhotoResp getVideo() {
        return video;
    }

    public void setVideo(PhotoResp video) {
        this.video = video;
    }

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

    public static class StarringActor {

        private String name;//	The name or title.
        private String desc;//	The short print friendly name.
        private PhotoResp photo;
        private String uuid;

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

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
    private String name;//	The name or title.
    private String sortableName;//	The short print friendly name.
    private List<StarringActor> starringActors;//	A comma seperated list of actors.
   // private List<String> directors;//	A comma seperated list of directors.
    private String name_long;//	The name or title.
    private String desc_short;//	The name or title.

    private String desc_long;//	The name or title.
    private Long theatre;
    private PhotoResp cover;



    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    private Integer runTime;//	The total runtime in minutes.
  //  private String score;//	An Estimate of how the film should perform relative to other films, scored between zero and one; one being best.
    private String slug;//	A dash seperated title.

    private String synopsis;//	A short description of the film.


  //  private String synopsisTagLine;//	A short tag line for the film.
    private String releaseDateUtc;//	The offfical release date in UTC (ISO-8601).
    private String earliestShowingUtc;//	The earliest showing date in UTC (ISO-8601).
    private MediaReq media;

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

/*    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }*/

    public Integer getRunTime() {
        return runTime;
    }

    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
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


// private String hasScheduledShowtimes;//	Indicates whether performances have been scheduled.
  //  private String displayOnlineTicketAvailability;//	Indicates if tickets are available for purchase online.
  //  private String onlineTicketAvailabilityDateUtc;//	The date tickets will be available for online purchase in UTC (ISO-8601).
  //  private String websiteUrl;//	The website url.
  //  private String showtimesUrl;//	The showtimes url.
  //  private String distributorId;//	The unique identifier for the distributor of a film.
  //  private String distributorCode;//	The code to identify the distributor of a film.
  //  private String preferredMediaType;//	The preferred media type (Theatrical or OnDemand)
  //  private String imdbId;//	The external IMDB ID for this movie

    @Transient
    List<AttributeResp> attributes;

    public MediaReq getMedia() {
        return media;
    }

    public void setMedia(MediaReq media) {
        this.media = media;
    }

    public Long getTheatre() {
        return theatre;
    }

    public void setTheatre(Long theatre) {
        this.theatre = theatre;
    }

    public PhotoResp getCover() {
        return cover;
    }

    public void setCover(PhotoResp cover) {
        this.cover = cover;
    }


    public class MediaReq {
         private PhotoResp posterThumbnail;//": "",
        private PhotoResp posterStandard;//": "",
        private PhotoResp posterLarge;//": "",
        private PhotoResp trailerFlv;//": "",
        private PhotoResp trailerHd;//": "",
        private PhotoResp trailerMp4;//": "",
        private PhotoResp heroDesktopDynamic;//": "https://amc-theatres-res.cloudinary.com/v1565718795/amc-cdn/development/2/movies/300/294/MovieStills/155259R7.jpg",
        private PhotoResp heroMobileDynamic;//": "https://amc-theatres-res.cloudinary.com/v1565718795/amc-cdn/development/2/movies/300/294/MovieStills/155259R7.jpg",
        private PhotoResp posterDynamic;//": "https://amc-theatres-res.cloudinary.com/v1569965069/amc-cdn/development/2/movies/300/294/Poster/TopGun_EN_800x1200_Paramount-Redbox_012816.jpg",
        private PhotoResp posterAlternateDynamic;//": "",
        private PhotoResp poster3DDynamic;//": "",
        private PhotoResp posterIMAXDynamic;//": "",
        private PhotoResp trailerTeaserDynamic;//": "",
        private PhotoResp trailerAlternateDynamic;//": "",
        private PhotoResp onDemandTrailerVideoId;//": "",
        private PhotoResp onDemandTrailerExternalVideoId;//": "",
        private PhotoResp onDemandPosterDynamic;//": "",
        private PhotoResp onDemandTrailerMp4Dynamic;//": "",
        private PhotoResp onDemandTrailerHdDynamic;//": ""

        public PhotoResp getPosterThumbnail() {
            return posterThumbnail;
        }

        public void setPosterThumbnail(PhotoResp posterThumbnail) {
            this.posterThumbnail = posterThumbnail;
        }

        public PhotoResp getPosterStandard() {
            return posterStandard;
        }

        public void setPosterStandard(PhotoResp posterStandard) {
            this.posterStandard = posterStandard;
        }

        public PhotoResp getPosterLarge() {
            return posterLarge;
        }

        public void setPosterLarge(PhotoResp posterLarge) {
            this.posterLarge = posterLarge;
        }

        public PhotoResp getTrailerFlv() {
            return trailerFlv;
        }

        public void setTrailerFlv(PhotoResp trailerFlv) {
            this.trailerFlv = trailerFlv;
        }

        public PhotoResp getTrailerHd() {
            return trailerHd;
        }

        public void setTrailerHd(PhotoResp trailerHd) {
            this.trailerHd = trailerHd;
        }

        public PhotoResp getTrailerMp4() {
            return trailerMp4;
        }

        public void setTrailerMp4(PhotoResp trailerMp4) {
            this.trailerMp4 = trailerMp4;
        }

        public PhotoResp getHeroDesktopDynamic() {
            return heroDesktopDynamic;
        }

        public void setHeroDesktopDynamic(PhotoResp heroDesktopDynamic) {
            this.heroDesktopDynamic = heroDesktopDynamic;
        }

        public PhotoResp getHeroMobileDynamic() {
            return heroMobileDynamic;
        }

        public void setHeroMobileDynamic(PhotoResp heroMobileDynamic) {
            this.heroMobileDynamic = heroMobileDynamic;
        }

        public PhotoResp getPosterDynamic() {
            return posterDynamic;
        }

        public void setPosterDynamic(PhotoResp posterDynamic) {
            this.posterDynamic = posterDynamic;
        }

        public PhotoResp getPosterAlternateDynamic() {
            return posterAlternateDynamic;
        }

        public void setPosterAlternateDynamic(PhotoResp posterAlternateDynamic) {
            this.posterAlternateDynamic = posterAlternateDynamic;
        }

        public PhotoResp getPoster3DDynamic() {
            return poster3DDynamic;
        }

        public void setPoster3DDynamic(PhotoResp poster3DDynamic) {
            this.poster3DDynamic = poster3DDynamic;
        }

        public PhotoResp getPosterIMAXDynamic() {
            return posterIMAXDynamic;
        }

        public void setPosterIMAXDynamic(PhotoResp posterIMAXDynamic) {
            this.posterIMAXDynamic = posterIMAXDynamic;
        }

        public PhotoResp getTrailerTeaserDynamic() {
            return trailerTeaserDynamic;
        }

        public void setTrailerTeaserDynamic(PhotoResp trailerTeaserDynamic) {
            this.trailerTeaserDynamic = trailerTeaserDynamic;
        }

        public PhotoResp getTrailerAlternateDynamic() {
            return trailerAlternateDynamic;
        }

        public void setTrailerAlternateDynamic(PhotoResp trailerAlternateDynamic) {
            this.trailerAlternateDynamic = trailerAlternateDynamic;
        }

        public PhotoResp getOnDemandTrailerVideoId() {
            return onDemandTrailerVideoId;
        }

        public void setOnDemandTrailerVideoId(PhotoResp onDemandTrailerVideoId) {
            this.onDemandTrailerVideoId = onDemandTrailerVideoId;
        }

        public PhotoResp getOnDemandTrailerExternalVideoId() {
            return onDemandTrailerExternalVideoId;
        }

        public void setOnDemandTrailerExternalVideoId(PhotoResp onDemandTrailerExternalVideoId) {
            this.onDemandTrailerExternalVideoId = onDemandTrailerExternalVideoId;
        }

        public PhotoResp getOnDemandPosterDynamic() {
            return onDemandPosterDynamic;
        }

        public void setOnDemandPosterDynamic(PhotoResp onDemandPosterDynamic) {
            this.onDemandPosterDynamic = onDemandPosterDynamic;
        }

        public PhotoResp getOnDemandTrailerMp4Dynamic() {
            return onDemandTrailerMp4Dynamic;
        }

        public void setOnDemandTrailerMp4Dynamic(PhotoResp onDemandTrailerMp4Dynamic) {
            this.onDemandTrailerMp4Dynamic = onDemandTrailerMp4Dynamic;
        }

        public PhotoResp getOnDemandTrailerHdDynamic() {
            return onDemandTrailerHdDynamic;
        }

        public void setOnDemandTrailerHdDynamic(PhotoResp onDemandTrailerHdDynamic) {
            this.onDemandTrailerHdDynamic = onDemandTrailerHdDynamic;
        }
    }
}
