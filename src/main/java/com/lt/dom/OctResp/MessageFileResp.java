package com.lt.dom.OctResp;


import com.lt.dom.controllerOct.DocumentRestController;
import com.lt.dom.oct.Document;
import com.lt.dom.oct.TempDocument;
import org.javatuples.Pair;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @ClassName Message
 * @Description TODO
 * @Author 树下魅狐
 * @Date 2020/4/28 0028 19:21
 * @Version since 1.0
 **/
public class MessageFileResp extends RepresentationModel<MessageFileResp> {

    private String message;
    private String code;
    private String fileName;
    private String originalFilename;
    private LocalDateTime created_at;
    private String url;

    public MessageFileResp(String message) {
        this.message = message;
    }

    public MessageFileResp() {

    }

    public static List<MessageFileResp> from(List<TempDocument> documents) {

        return documents.stream().map(x->{
            MessageFileResp tempDocument = new MessageFileResp();
            tempDocument.setCode(x.getCode());
            tempDocument.setFileName(x.getFileName());
            tempDocument.setOriginalFilename(x.getOriginalFilename());
            tempDocument.setCreated_at(x.getCreated_at());
            tempDocument.setUrl(x.getUrl());
            tempDocument.add(linkTo(methodOn(DocumentRestController.class).createDocuments(null)).withSelfRel());
            tempDocument.add(linkTo(methodOn(DocumentRestController.class).deleteTempDocuments(x.getCode())).withRel("delete_url"));


            return tempDocument;
        }).collect(Collectors.toList());

    }
    public static List<MessageFileResp> fromDocuments(List<Pair<String,Document>> documents) {

        return documents.stream().map(x->{

            String url = x.getValue0();
            Document tempDocument = x.getValue1();
            MessageFileResp messageFileResp = new MessageFileResp();
            messageFileResp.setCode(tempDocument.getCode());
            messageFileResp.setFileName(tempDocument.getFileName());
            messageFileResp.setOriginalFilename(tempDocument.getOriginalFilename());
            messageFileResp.setCreated_at(tempDocument.getCreated_at());
            messageFileResp.setUrl(url);
            messageFileResp.add(linkTo(methodOn(DocumentRestController.class).createDocuments(null)).withSelfRel());
            messageFileResp.add(linkTo(methodOn(DocumentRestController.class).deleteDocuments(tempDocument.getCode())).withRel("delete_url"));
            return messageFileResp;
        }).collect(Collectors.toList());

    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}