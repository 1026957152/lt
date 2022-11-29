package com.lt.dom.otcReq;


import com.lt.dom.OctResp.BlogResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Blog;
import com.lt.dom.oct.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.hateoas.EntityModel;

import java.util.List;


//https://metmuseum.github.io/

public class BlogEditReq {

    private BlogResp info;


    private EntityModel editReq;

    public BlogResp getInfo() {
        return info;
    }

    public void setInfo(BlogResp info) {
        this.info = info;
    }

    public EntityModel getEditReq() {
        return editReq;
    }

    public void setEditReq(EntityModel editReq) {
        this.editReq = editReq;
    }

    public static class EditReq {




        public static class Author {

            private String displayName;
            private PhotoResp image;

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

            public static BlogResp.Author from(User user) {
                BlogResp.Author author1 = new BlogResp.Author();
                author1.setDisplayName(user.getNick_name());
                author1.setId(user.getCode());
                return author1;
            }

        }

        private Author author;

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        private String title;

        private String contentText;


        private String slug;

        private Boolean pinned;



        private PhotoResp coverMedia;


        private Boolean commentingEnabled;
        private Integer minutesToRead;






        private List<String> relatedPostIds;






        public static EditReq from(Blog e,PhotoResp authImage) {
            EditReq blogResp = new EditReq();
            blogResp.setContentText(e.getContentText());
            blogResp.setMinutesToRead(e.getMinutesToRead());
            blogResp.setCommentingEnabled(e.getCommentingEnabled());
            blogResp.setTitle(e.getTitle());
            blogResp.setPinned(e.getPinned());
            blogResp.setSlug(e.getSlug());

            Author author1 = new Author();
            author1.setDisplayName(e.getAuthor_DisplayName());
            author1.setImage(authImage);
            blogResp.setAuthor(author1);
            return blogResp;
        }



        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }


        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }



        public Boolean getPinned() {
            return pinned;
        }

        public void setPinned(Boolean pinned) {
            this.pinned = pinned;
        }


        public PhotoResp getCoverMedia() {
            return coverMedia;
        }

        public void setCoverMedia(PhotoResp coverMedia) {
            this.coverMedia = coverMedia;
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



        public List<String> getRelatedPostIds() {
            return relatedPostIds;
        }

        public void setRelatedPostIds(List<String> relatedPostIds) {
            this.relatedPostIds = relatedPostIds;
        }


    }
}
