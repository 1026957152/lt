package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Category;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResp {


    @NotNull
    private String title;//	The staff entered "pinned note" on the booking

    @NotNull
    private String category;


    private Long parent_category_id;

    private List<PhotoResp> images;
    private PhotoResp icon;
    private String keyword;

    public static CategoryResp simpleFrom(Category e) {
        CategoryResp categoryResp = new CategoryResp();
        categoryResp.setTitle(e.getName());
        categoryResp.setKeyword(e.getName());

        categoryResp.setParent_category_id(e.getParent_category_id());
        return categoryResp;
    }

    public List<PhotoResp> getImages() {
        return images;
    }

    public void setImages(List<PhotoResp> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(Long parent_category_id) {
        this.parent_category_id = parent_category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public void setIcon(PhotoResp icon) {
        this.icon = icon;
    }

    public PhotoResp getIcon() {
        return icon;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
