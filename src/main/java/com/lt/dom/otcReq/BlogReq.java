package com.lt.dom.otcReq;


import com.lt.dom.OctResp.PhoneResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Blog;

import java.util.List;


//https://metmuseum.github.io/

public class BlogReq {


    private String idX;
    private String title;
    private String excerpt;
    private String contentText;
    private String firstPublishedDate;


    private String lastPublishedDate;

   private UrlDTO url;
    private String slug;
    private Boolean featured;
    private Boolean pinned;

    private List<String> categoryIds;

    private PhotoResp coverMedia;
    private String memberId;


    private List<String> hashtags;

    private Boolean commentingEnabled;
    private Integer minutesToRead;


    private List<String> tagIds;

    private List<String> pricingPlans;

    private List<String> relatedPostIds;

    private List<String> pricingPlanIds;

    private String language;


    private MetricsDTO metrics;
    private Long user;

    public static BlogReq from(Blog e) {
        BlogReq blogResp = new BlogReq();
        blogResp.setContentText(e.getContentText());
        blogResp.setMinutesToRead(e.getMinutesToRead());
        blogResp.setTitle(e.getTitle());
        blogResp.setExcerpt(e.getExcerpt());
        return blogResp;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }


    public static class UrlDTO {

        private String base;
        private String path;
    }

    public static class CoverMediaDTO {
        private ImageDTO image;
        private Boolean displayed;


        public static class ImageDTO {
            private String idX;
            private String url;
            private Integer height;
            private Integer width;
        }
    }


    public static class MetricsDTO {
        private Integer comments;
        private Integer likes;
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

    public PhotoResp getCoverMedia() {
        return coverMedia;
    }

    public void setCoverMedia(PhotoResp coverMedia) {
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
