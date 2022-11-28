package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.CommentResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CommentReq;
import com.lt.dom.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl {

    @Autowired
    private CommentRepository commentRepository;



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
}
