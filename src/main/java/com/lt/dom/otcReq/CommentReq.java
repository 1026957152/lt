package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Comment;


public class CommentReq {

    private String name;
    private String text;
    private String code;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }




    @JsonProperty("article_id")
    private Integer articleId;
    @JsonProperty("author")
    private String author;
    @JsonProperty("blog_id")
    private Integer blogId;
    @JsonProperty("body")
    private String body;
    @JsonProperty("body_html")
    private String bodyHtml;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("email")
    private String email;

    @JsonProperty("ip")
    private String ip;
    @JsonProperty("published_at")
    private String publishedAt;
    @JsonProperty("status")
    private String status;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("user_agent")
    private String userAgent;



    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
