package com.lt.dom.serviceOtc;


import com.lt.dom.oct.Blog;
import com.lt.dom.oct.Comment;
import com.lt.dom.oct.Product;
import com.lt.dom.otcReq.BlogEditReq;
import com.lt.dom.otcReq.BlogReq;
import com.lt.dom.otcReq.CommentReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.BlogRepository;
import com.lt.dom.repository.CommentRepository;
import com.lt.dom.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl {

    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IdGenServiceImpl idGenService;



    public Comment create(Product supplier, CommentReq theatreReq) {
        Comment theatre = new Comment();
        theatre.setText(theatreReq.getText());
        theatre.setProduct(supplier.getId());
        theatre.setBody(theatreReq.getBody());
        theatre.setCreatedAt(theatreReq.getCreatedAt());
        theatre.setCode(idGenService.nextId("comment"));
        theatre.setAuthor(supplier.getSupplierId()+"");
        theatre = commentRepository.save(theatre);


        return theatre;


    }

    public List<Comment> list(Product product) {


        List<Comment> componentRightList = commentRepository.findAllByProduct(product.getId());
        return componentRightList;

    }
    public List<Comment> fromValueList(List<Long> collect) {


        List<Comment> componentRightList = commentRepository.findAllById(collect);
        return componentRightList;

    }


    public Blog edit(Blog theatre, BlogEditReq.EditReq blogReq) {


       // theatre.setUser(blogReq.getUser());

        theatre.setTitle(blogReq.getTitle());
        theatre.setSlug(blogReq.getSlug());
        theatre.setPinned(blogReq.getPinned());

        theatre.setContentText(blogReq.getContentText());
        theatre.setMinutesToRead(blogReq.getMinutesToRead());
        theatre.setCommentingEnabled(blogReq.getCommentingEnabled());
        theatre.setAuthor_DisplayName(blogReq.getAuthor().getDisplayName());


        theatre = blogRepository.save(theatre);

        if(blogReq.getAuthor().getImage()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),blogReq.getAuthor().getImage(), EnumDocumentType.author_avatar);
        }

        if(blogReq.getCoverMedia().getCode()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),blogReq.getCoverMedia(), EnumDocumentType.blog_cover);
        }
        return theatre;


    }
    public Blog create(UserVo userVo, BlogReq blogReq) {

        Blog theatre = new Blog();
        theatre.setCode(idGenService.nextId("blog_"));
        theatre.setTitle(blogReq.getTitle());
      //  theatre.setContentText(blogReq.getContentText());
        theatre.setMinutesToRead(blogReq.getMinutesToRead());
        theatre.setUser(userVo.getUser_id());

        theatre = blogRepository.save(theatre);

        if(blogReq.getCoverMedia()!= null && blogReq.getCoverMedia().getCode()!= null){
            fileStorageService.updateFromTempDocument(theatre.getCode(),blogReq.getCoverMedia(), EnumDocumentType.blog_cover);
        }
        return theatre;

    }
}
