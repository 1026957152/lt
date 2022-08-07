package com.lt.dom.controllerOct;

import com.lt.dom.OctResp.CampaignResp;
import com.lt.dom.OctResp.ClainQuotaStatisticsResp;
import com.lt.dom.OctResp.MessageFileResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CompaignPojo;
import com.lt.dom.otcReq.QuotaReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.ClainQuotaServiceImpl;
import com.lt.dom.serviceOtc.FileStorageService;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.VoucherServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/oct")
public class DocumentRestController {



    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private DocumentRepository documentRepository;



    @Autowired
    private TempDocumentRepository tempDocumentRepository;


    @Autowired
    private LtConfig ltConfig;


    @PostMapping(value = "/documents")
    public ResponseEntity<List<MessageFileResp>> createDocuments(@RequestParam(value = "files",required = true) List<MultipartFile> files) {
        //@RequestParam("files") List<MultipartFile> files


            List<String> fileNames = files.stream().map(x->x.getOriginalFilename()).collect(Collectors.toList());


            try {

                List<String> fileNames_ = new ArrayList<>();
                List<TempDocument> documents = files.stream().map(x->{
                    TempDocument document = fileStorageService.saveWithTempDocument(x);
                    fileNames_.add(x.getOriginalFilename());
                    return document;

                }).collect(Collectors.toList());

                documents = documents.stream().map(x->{


                    x.setUrl(FileStorageServiceImpl.url(x.getFileName()));
                    return x;
                }).collect(Collectors.toList());
                return ResponseEntity.ok(MessageFileResp.from(documents));//new MessageFile("Upload file successfully: "+fileNames_));
            }catch (Exception e){

                e.printStackTrace();
                throw new RuntimeException(e);
                //return ResponseEntity.badRequest()
                   //     .body(new MessageFile("Could not upload the file:"+fileNames));
            }

    }






    @DeleteMapping(value = "/temp_documents/{DOCUMENT_ID}/")
    public ResponseEntity<Boolean> deleteTempDocuments(@PathVariable String DOCUMENT_ID) {
        //@RequestParam("files") List<MultipartFile> files

        Optional<TempDocument> validatorOptional = tempDocumentRepository.findByCode(DOCUMENT_ID);


        if(validatorOptional.isPresent()){
            tempDocumentRepository.delete(validatorOptional.get());
            return ResponseEntity.ok(true);
        }else{
            throw new BookNotFoundException(DOCUMENT_ID,TempDocument.class.getSimpleName());
        }

    }

    @DeleteMapping(value = "/documents/{DOCUMENT_ID}/")
    public ResponseEntity<Boolean> deleteDocuments(@PathVariable String DOCUMENT_ID) {
        //@RequestParam("files") List<MultipartFile> files
        Optional<Document> validatorOptional = documentRepository.findByCode(DOCUMENT_ID);
        if(validatorOptional.isPresent()){
            documentRepository.delete(validatorOptional.get());
            return ResponseEntity.ok(true);
        }else{
            throw new BookNotFoundException(DOCUMENT_ID,TempDocument.class.getSimpleName());
        }

    }


}