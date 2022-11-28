package com.lt.dom.controllerOct;

import com.google.gson.Gson;
import com.lt.dom.OctResp.CampaignResp;
import com.lt.dom.OctResp.ClainQuotaStatisticsResp;
import com.lt.dom.OctResp.MessageFileResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CompaignPojo;
import com.lt.dom.otcReq.LinkDocumentReq;
import com.lt.dom.otcReq.QuotaReq;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.ClainQuotaServiceImpl;
import com.lt.dom.serviceOtc.FileStorageService;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import com.lt.dom.serviceOtc.VoucherServiceImpl;
import com.lt.dom.thiirdAli.idcard.IdCardOcrService;
import com.lt.dom.thiirdAli.idcard.IdcardFaceVo;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityOcrService;
import com.lt.dom.thiirdAli.idfaceIdentity.IdfaceIdentityVo;
import com.lt.dom.thiirdAli.ocr_business_license.BusinessLicenseVo;
import com.lt.dom.thiirdAli.ocr_business_license.BussinessLicenseOcrService;
import io.swagger.v3.oas.annotations.Operation;
import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.io.IOException;
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

    @Autowired
    IdCardOcrService idCardOcrService;


    @Autowired
    BussinessLicenseOcrService bussinessLicenseOcrService;

    @Autowired
    IdfaceIdentityOcrService idfaceIdentityOcrService;



    @PostMapping(value = "/documents")
    public CollectionModel<EntityModel<MessageFileResp>> createDocuments(@RequestParam(value = "files",required = true) List<MultipartFile> files) {
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


                return CollectionModel.of(MessageFileResp.from(documents));//new MessageFile("Upload file successfully: "+fileNames_));
            }catch (Exception e){

                e.printStackTrace();
                throw new RuntimeException(e);
                //return ResponseEntity.badRequest()
                   //     .body(new MessageFile("Could not upload the file:"+fileNames));
            }

    }




    @PostMapping(value = "/document")
    public EntityModel<MessageFileResp> uploadDocument(@RequestParam(value = "file",required = true) MultipartFile file) {


        try {

            TempDocument document = fileStorageService.saveWithTempDocument(file);
            document.setUrl(FileStorageServiceImpl.url(document.getFileName()));

            return MessageFileResp.from(document);//new MessageFile("Upload file successfully: "+fileNames_));
        }catch (Exception e){

            e.printStackTrace();
            throw new RuntimeException(e);
            //return ResponseEntity.badRequest()
            //     .body(new MessageFile("Could not upload the file:"+fileNames));
        }

    }

    @PostMapping(value = "/ocr_id_scan")
    public EntityModel<IdcardFaceVo.IdcardFaceVoSimple> idScan(@RequestParam(value = "file",required = true) MultipartFile file) {




        IdcardFaceVo idcardFaceVo = idCardOcrService.main(file);

        return EntityModel.of(idcardFaceVo.toSimple());

    }

    @PostMapping(value = "/ocr_bussiness")
    public EntityModel<BusinessLicenseVo.BusinessLicenseVoSimple> BusinessLicenseVo(@RequestParam(value = "file",required = true) MultipartFile file) {




        BusinessLicenseVo idcardFaceVo = bussinessLicenseOcrService.main(file);

        return EntityModel.of(idcardFaceVo.toSimple());

    }

    @PostMapping(value = "/idfaceIdentity")
    public EntityModel<IdfaceIdentityVo> idfaceIdentity(@RequestParam(value = "file",required = true) MultipartFile file) {




        IdfaceIdentityVo idcardFaceVo = idfaceIdentityOcrService.main(null,null,file);

        return EntityModel.of(idcardFaceVo);

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













    @PostMapping(value = "/document/link")
    public ResponseEntity linkDocument(@RequestBody @Valid LinkDocumentReq linkDocumentReq) {

        Optional<TempDocument> optionalTempDocument = tempDocumentRepository.findByCode(linkDocumentReq.getTempDocumentCode());


        if(optionalTempDocument.isEmpty()){
            throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到零时文档");

        }


        TempDocument tempDocument = optionalTempDocument.get();

        String document = fileStorageService.saveFromTempDocument(linkDocumentReq.getObjectCode(),linkDocumentReq.getDocumentType(),tempDocument);


        return ResponseEntity.ok(document);

    }

    @PutMapping(value = "/document/all")
    public ResponseEntity updateDocument(@RequestBody  List<Document> documentList) {


    //    Document[] doc = new Gson().fromJson("",Document[].class);

        System.out.println("------------------"+documentList.toString());
 /*       Arrays.stream(doc).map(e->{
            e.setId(null);
            return e;
        });*/
        documentList = documentRepository.saveAll(documentList);
          System.out.println("--参数---的值------"+ documentList.toString());



        return ResponseEntity.ok(documentList);

    }
    @GetMapping(value = "/document/all")
    public ResponseEntity getDocumentAll() {


        List<Document> documentList = documentRepository.findAll();
        System.out.println("--参数---的值------"+ documentList.toString());

        //TempDocument tempDocument = optionalTempDocument.get();

        // fileStorageService.saveFromTempDocument(linkDocumentReq.getObjectCode(),linkDocumentReq.getDocumentType(),tempDocument);


        return ResponseEntity.ok(documentList);

    }
    @GetMapping(value = "/document")
    public ResponseEntity getDocument( LinkDocumentReq linkDocumentReq) {

        System.out.println("--参数---------"+ linkDocumentReq.toString());
        System.out.println("--参数---的值------"+ linkDocumentReq.getDocumentType().value());
        List<Document> optionalTempDocument = documentRepository.findAllByTypeAndReference(linkDocumentReq.getDocumentType(),linkDocumentReq.getObjectCode());


/*        if(optionalTempDocument.isEmpty()){
            throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到零时文档");

        }*/
        List<Document> documentList = documentRepository.findAllByReference(linkDocumentReq.getObjectCode());
        System.out.println("--参数---的值------"+ documentList.toString());

        //TempDocument tempDocument = optionalTempDocument.get();

       // fileStorageService.saveFromTempDocument(linkDocumentReq.getObjectCode(),linkDocumentReq.getDocumentType(),tempDocument);


        return ResponseEntity.ok(documentList);

    }
    @PutMapping(value = "/documents/{id}")
    public ResponseEntity changeDocument(@PathVariable Long id,@RequestBody LinkDocumentReq linkDocumentReq) {

        System.out.println("--参数---------"+ linkDocumentReq.toString());
        System.out.println("--参数---的值------"+ linkDocumentReq.getDocumentType().value());
        Optional<Document> optionalTempDocument = documentRepository.findById(id);


       if(optionalTempDocument.isEmpty()){
            throw  new BookNotFoundException(Enumfailures.resource_not_found,"找不到零时文档");

        }

        Document document = optionalTempDocument.get();
        document.setType(linkDocumentReq.getDocumentType());


        document   = documentRepository.save(document);

        //TempDocument tempDocument = optionalTempDocument.get();

        // fileStorageService.saveFromTempDocument(linkDocumentReq.getObjectCode(),linkDocumentReq.getDocumentType(),tempDocument);


        return ResponseEntity.ok(document);

    }
}