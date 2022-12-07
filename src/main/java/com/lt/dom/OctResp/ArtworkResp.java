package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Artwork;
import com.lt.dom.otcReq.MuseumReq;
import org.checkerframework.checker.units.qual.A;


//https://metmuseum.github.io/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtworkResp {

    private String name_long;

    private String desc_short;
    private String desc_long;


    private String name;
    private Integer rank;
    private String description;
    private String completion_year;
    private String created_location;
    private String medium;  //"Oil on canvas", "Watercolor", "Gold"
    private String dimensions;//
    private String genre;
    private String technique;
    private String culture;

    private String city;
    private String county;

    private String state;
    private String country;

    private String region;
        private Boolean isHighlight;


        private String repository;
    private long id;
    private String intro;

    public static ArtworkResp from(Artwork e) {
        ArtworkResp artworkResp = new ArtworkResp();
        artworkResp.setName(e.getName());
        artworkResp.setId(e.getId());
        artworkResp.setName_long(e.getName_long());
        artworkResp.setDesc_short(e.getDesc_short());
        artworkResp.setDesc_long(e.getDesc_long());
        artworkResp.setIntro(e.getIntro());

        return artworkResp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompletion_year() {
        return completion_year;
    }

    public void setCompletion_year(String completion_year) {
        this.completion_year = completion_year;
    }

    public String getCreated_location() {
        return created_location;
    }

    public void setCreated_location(String created_location) {
        this.created_location = created_location;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getHighlight() {
        return isHighlight;
    }

    public void setHighlight(Boolean highlight) {
        isHighlight = highlight;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }
    private MediaResp media;

    public MediaResp getMedia() {
        return media;
    }

    public void setMedia(MediaResp media) {
        this.media = media;
    }

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

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return intro;
    }
}
