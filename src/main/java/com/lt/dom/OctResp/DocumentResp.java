package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.controllerOct.FileUploadController;
import com.lt.dom.oct.Document;
import com.lt.dom.otcReq.BookingDocumentIdsResp;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class DocumentResp  {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String source_id;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    private String resultUrl;
    
//##@Column(unique=true)
private String code;
    private String original_filename;
    private EnumDocumentType category;
    private boolean image;

    public static DocumentResp onefrom(Document x) {
        DocumentResp exportReq = new DocumentResp();
        exportReq.setImage(x.getImage());
        exportReq.setCode(x.getCode());
        exportReq.setCategory(x.getType());
        exportReq.setCreated_at(x.getCreated_at());
        exportReq.setUpdated_at(x.getUpdated_at());
        exportReq.setOriginal_filename(x.getOriginalFilename());
        String url = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class,
                        "getFile",
                        x.getFileName()
                ).build().toString();

        exportReq.setResultUrl(url);
        return exportReq;

    }


    public static List<DocumentGroup> groupfrom(List<Document> travelers) {

/*        Map<DocumentResp> documentResps = travelers.stream().collect(Collectors.groupingBy(x->x.getType(),collectingAndThen(toList(),list->{
            return list.stream().map(x->{


            }).collect(toList());

        }) ));*/

        return  travelers.stream().collect(groupingBy(x->x.getType())).entrySet().stream().map(x->{
            DocumentGroup enumResp = new DocumentGroup();
            enumResp.setId(x.getKey().name());
            enumResp.setText(x.getKey().toString());
            enumResp.setDocumentResps(x.getValue().stream().map(xx->{
                DocumentResp exportReq = new DocumentResp();
                exportReq.setImage(xx.getImage());
                exportReq.setCode(xx.getCode());
                exportReq.setCategory(xx.getType());
                exportReq.setCreated_at(xx.getCreated_at());
                exportReq.setUpdated_at(xx.getUpdated_at());
                exportReq.setOriginal_filename(xx.getOriginalFilename());
   /*             String url = MvcUriComponentsBuilder
                        .fromMethodName(FileUploadController.class,
                                "getFile",
                                xx.getFileName()
                        ).build().toString();*/

                exportReq.setResultUrl(FileStorageServiceImpl.url(xx));
                return exportReq;
            }).collect(toList()));

            return enumResp;

        }).collect(toList());


    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public boolean getImage() {
        return image;
    }


    public static class DocumentGroup {

        private String id;
        private String text;
        private List<DocumentResp> documentResps;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<DocumentResp> getDocumentResps() {
            return documentResps;
        }

        public void setDocumentResps(List<DocumentResp> documentResps) {
            this.documentResps = documentResps;
        }
    }
        public static List<DocumentResp> Listfrom(List<Document> travelers) {
        List<DocumentResp> documentResps = travelers.stream().map(x->{
            DocumentResp exportReq = new DocumentResp();
            exportReq.setImage(x.getImage());
            exportReq.setCode(x.getCode());
            exportReq.setCategory(x.getType());
            exportReq.setCreated_at(x.getCreated_at());
            exportReq.setUpdated_at(x.getUpdated_at());
            exportReq.setOriginal_filename(x.getOriginalFilename());
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class,
                            "getFile",
                            x.getFileName()
                    ).build().toString();

            exportReq.setResultUrl(url);
            return exportReq;
        }).collect(toList());
        return documentResps;
    }


    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }



    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }


    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }



    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    public static Map<EnumDocumentType,List<EntityModel<DocumentResp>>> from(List<Document> export) {

        List<EntityModel<DocumentResp>> documentResps = export.stream().map(x->{
            DocumentResp exportReq = new DocumentResp();

            exportReq.setCode(x.getCode());
            exportReq.setCategory(x.getType());
            exportReq.setCreated_at(x.getCreated_at());
            exportReq.setUpdated_at(x.getUpdated_at());
            exportReq.setOriginal_filename(x.getOriginalFilename());
            exportReq.setImage(x.getImage());
            exportReq.setResultUrl(FileStorageServiceImpl.url(x));


            EntityModel<DocumentResp> entityModel = EntityModel.of(exportReq);
            entityModel.add(linkTo(methodOn(BookingRestController.class).createDocuments(x.getId(),new BookingDocumentIdsResp())).withRel("add_documents_url"));
            return entityModel;
        }).collect(toList());


        Map<EnumDocumentType,List<EntityModel<DocumentResp>>> mm = documentResps.stream().collect(groupingBy(x->x.getContent().getCategory()));

        return mm;
    }


    public void setOriginal_filename(String original_filename) {
        this.original_filename = original_filename;
    }

    public String getOriginal_filename() {
        return original_filename;
    }

    public void setCategory(EnumDocumentType category) {
        this.category = category;
    }

    public EnumDocumentType getCategory() {
        return category;
    }
}
