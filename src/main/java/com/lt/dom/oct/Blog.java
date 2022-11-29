package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;


//https://metmuseum.github.io/

@Entity
public class Blog extends Base {


    @JsonProperty("id")
    private String idX;
    @JsonProperty("title")
    private String title;
    @JsonProperty("excerpt")
    private String excerpt;
    @JsonProperty("contentText")
    @Length(max = 100000)
    private String contentText;
    @JsonProperty("firstPublishedDate")
    private String firstPublishedDate;
    @JsonProperty("lastPublishedDate")


    private String lastPublishedDate;
    @JsonProperty("url")

    @Transient
   private UrlDTO url;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("featured")
    private Boolean featured;
    @JsonProperty("pinned")
    private Boolean pinned;

    @Transient
    @JsonProperty("categoryIds")
    private List<String> categoryIds;

    @Transient
    @JsonProperty("coverMedia")
    private CoverMediaDTO coverMedia;
    @JsonProperty("memberId")
    private String memberId;

    @Transient
    @JsonProperty("hashtags")
    private List<String> hashtags;

    @JsonProperty("commentingEnabled")
    private Boolean commentingEnabled;
    @JsonProperty("minutesToRead")
    private Integer minutesToRead;

    @Transient
    @JsonProperty("tagIds")
    private List<String> tagIds;
    @JsonProperty("pricingPlans")

    @Transient
    private List<String> pricingPlans;
    @JsonProperty("relatedPostIds")

    @Transient
    private List<String> relatedPostIds;
    @JsonProperty("pricingPlanIds")

    @Transient
    private List<String> pricingPlanIds;
    @JsonProperty("language")
    private String language;

    @Transient
    @JsonProperty("metrics")
    private MetricsDTO metrics;
    private String code;
    private Long user;
    private String author_DisplayName;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setUser(Long user) {

        this.user = user;
    }

    public Long getUser() {
        return user;
    }

    public String getAuthor_DisplayName() {

        return author_DisplayName;
    }

    public void setAuthor_DisplayName(String author_displayName) {
        this.author_DisplayName = author_displayName;
    }

    @NoArgsConstructor
    @Data
    public static class UrlDTO {
        @JsonProperty("base")
        private String base;
        @JsonProperty("path")
        private String path;
    }

    @NoArgsConstructor
    @Data
    public static class CoverMediaDTO {
        @JsonProperty("image")
        private ImageDTO image;
        @JsonProperty("displayed")
        private Boolean displayed;

        @NoArgsConstructor
        @Data
        public static class ImageDTO {
            @JsonProperty("id")
            private String idX;
            @JsonProperty("url")
            private String url;
            @JsonProperty("height")
            private Integer height;
            @JsonProperty("width")
            private Integer width;
        }
    }

    @NoArgsConstructor
    @Data
    public static class MetricsDTO {
        @JsonProperty("comments")
        private Integer comments;
        @JsonProperty("likes")
        private Integer likes;
        @JsonProperty("views")
        private Integer views;
    }

    public String getIdX() {
        return idX;
    }

    public void setIdX(String idX) {
        this.idX = idX;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getFirstPublishedDate() {
        return firstPublishedDate;
    }

    public void setFirstPublishedDate(String firstPublishedDate) {
        this.firstPublishedDate = firstPublishedDate;
    }

    public String getLastPublishedDate() {
        return lastPublishedDate;
    }

    public void setLastPublishedDate(String lastPublishedDate) {
        this.lastPublishedDate = lastPublishedDate;
    }

    public UrlDTO getUrl() {
        return url;
    }

    public void setUrl(UrlDTO url) {
        this.url = url;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public CoverMediaDTO getCoverMedia() {
        return coverMedia;
    }

    public void setCoverMedia(CoverMediaDTO coverMedia) {
        this.coverMedia = coverMedia;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public Boolean getCommentingEnabled() {
        return commentingEnabled;
    }

    public void setCommentingEnabled(Boolean commentingEnabled) {
        this.commentingEnabled = commentingEnabled;
    }

    public Integer getMinutesToRead() {
        return minutesToRead;
    }

    public void setMinutesToRead(Integer minutesToRead) {
        this.minutesToRead = minutesToRead;
    }

    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }

    public List<String> getPricingPlans() {
        return pricingPlans;
    }

    public void setPricingPlans(List<String> pricingPlans) {
        this.pricingPlans = pricingPlans;
    }

    public List<String> getRelatedPostIds() {
        return relatedPostIds;
    }

    public void setRelatedPostIds(List<String> relatedPostIds) {
        this.relatedPostIds = relatedPostIds;
    }

    public List<String> getPricingPlanIds() {
        return pricingPlanIds;
    }

    public void setPricingPlanIds(List<String> pricingPlanIds) {
        this.pricingPlanIds = pricingPlanIds;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public MetricsDTO getMetrics() {
        return metrics;
    }

    public void setMetrics(MetricsDTO metrics) {
        this.metrics = metrics;
    }
}
