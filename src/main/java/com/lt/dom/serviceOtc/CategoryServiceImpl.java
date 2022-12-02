package com.lt.dom.serviceOtc;


import com.lt.dom.oct.Category;
import com.lt.dom.otcReq.CategoryPojo;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl {

    @Autowired
    private CategoryRepository categoryRepository;



    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;



    public Category create(CategoryPojo categoryPojo) {


        Category category = new Category();


        category.setCode(idGenService.nextId("category_"));

        category.setCategory(categoryPojo.getCategory());
        category.setName(categoryPojo.getName());
        category = categoryRepository.save(category);
        return category;


    }




    public Category edit(Category theatre, CategoryPojo blogReq) {

        theatre.setName(blogReq.getName());
        theatre.setCategory(blogReq.getCategory());
        theatre = categoryRepository.save(theatre);


        if(blogReq.getImages() != null){
            Category finalTheatre = theatre;
            blogReq.getImages().stream().forEach(e->{
                fileStorageService.updateFromTempDocument(finalTheatre.getCode(),e, EnumDocumentType.category_image);
            });
        }
        return theatre;


    }

}
