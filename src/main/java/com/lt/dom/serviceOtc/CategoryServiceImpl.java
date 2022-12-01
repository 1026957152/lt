package com.lt.dom.serviceOtc;


import com.lt.dom.oct.Blog;
import com.lt.dom.oct.Category;
import com.lt.dom.oct.Comment;
import com.lt.dom.oct.Product;
import com.lt.dom.otcReq.BlogEditReq;
import com.lt.dom.otcReq.BlogReq;
import com.lt.dom.otcReq.CategoryPojo;
import com.lt.dom.otcReq.CommentReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.BlogRepository;
import com.lt.dom.repository.CategoryRepository;
import com.lt.dom.repository.CommentRepository;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl {

    @Autowired
    private CategoryRepository commentRepository;



    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;



    public Category create(CategoryPojo categoryPojo) {


        Category category = new Category();


        category.setCode(idGenService.nextId("category_"));

        category.setName(categoryPojo.getName());
        category = commentRepository.save(category);
        return category;


    }




    public Category edit(Category theatre, CategoryPojo blogReq) {

        theatre.setName(blogReq.getName());
        theatre = commentRepository.save(theatre);


        if(blogReq.getImages() != null){
            Category finalTheatre = theatre;
            blogReq.getImages().stream().forEach(e->{
                fileStorageService.updateFromTempDocument(finalTheatre.getCode(),e, EnumDocumentType.category_image);
            });
        }
        return theatre;


    }

}
