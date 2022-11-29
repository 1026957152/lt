package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Blog;
import com.lt.dom.oct.Media;
import com.lt.dom.oct.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;


//https://metmuseum.github.io/


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogResp extends BaseResp  {


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

  //  private CoverMediaDTO coverMedia;

    private PhotoResp coverMedia;
    private String code;

    public PhotoResp getCoverMedia() {
        return coverMedia;
    }

    public void setCoverMedia(PhotoResp coverMedia) {
        this.coverMedia = coverMedia;
    }

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

    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public static BlogResp from(Blog e) {
        BlogResp blogResp = new BlogResp();
        blogResp.setContentText(e.getContentText());
        blogResp.setMinutesToRead(e.getMinutesToRead());
        blogResp.setTitle(e.getTitle());
        blogResp.setExcerpt(e.getExcerpt());

        MetricsDTO metricsDTO = new MetricsDTO();
        metricsDTO.setComments(10);
        metricsDTO.setLikes(10);
        metricsDTO.setViews(10);
        blogResp.setMetrics(metricsDTO);
        return blogResp;
    }

    public static BlogResp Pcfrom(Blog e) {
        BlogResp blogResp = new BlogResp();
        blogResp.setCode(e.getCode());

     //   blogResp.setContentText(e.getContentText());
        blogResp.setMinutesToRead(e.getMinutesToRead());
        blogResp.setTitle(e.getTitle());
        blogResp.setExcerpt(e.getExcerpt());

        MetricsDTO metricsDTO = new MetricsDTO();
        metricsDTO.setComments(10);
        metricsDTO.setLikes(10);
        metricsDTO.setViews(10);
        blogResp.setMetrics(metricsDTO);

        blogResp.setCreatedDate(e.getCreatedDate());
        blogResp.setModifiedDate(e.getModifiedDate());

        return blogResp;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
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



    public static class Author {
/*                "id": "401465483996",
                        "displayName": "Brett Wiltshire",
                        "url": "http://www.blogger.com/profile/01430672582309320414",
                        "image": {
            "url": "http://4.bp.blogspot.com/_YA50adQ-7vQ/S1gfR_6ufpI/AAAAAAAAAAk/1ErJGgRWZDg/S45/brett.png"*/
        private String displayName;
        private PhotoResp image;
            private String id;

        public static Author from(User user) {
            Author author1 = new Author();
            author1.setDisplayName(user.getNick_name());
            author1.setId(user.getCode());
            return author1;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public PhotoResp getImage() {
            return image;
        }

        public void setImage(PhotoResp image) {
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class MetricsDTO {
        private Integer comments;
        private Integer likes;
        private Integer views;

        public Integer getComments() {
            return comments;
        }

        public void setComments(Integer comments) {
            this.comments = comments;
        }

        public Integer getLikes() {
            return likes;
        }

        public void setLikes(Integer likes) {
            this.likes = likes;
        }

        public Integer getViews() {
            return views;
        }

        public void setViews(Integer views) {
            this.views = views;
        }
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
