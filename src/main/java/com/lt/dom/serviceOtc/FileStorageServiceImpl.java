package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.DocumentResp;
import com.lt.dom.controllerOct.FileUploadController;
import com.lt.dom.oct.Document;
import com.lt.dom.oct.Reservation;
import com.lt.dom.oct.TempDocument;
import com.lt.dom.oct.TourBooking;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.repository.DocumentRepository;
import com.lt.dom.repository.TempDocumentRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;


/**
 * @ClassName FileStorageServiceImpl
 * @Description TODO
 * @Author 树下魅狐
 * @Date 2020/4/28 0028 18:38
 * @Version since 1.0
 **/
@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStorageService {

    public static final Path path = Paths.get("fileStorage");

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private TempDocumentRepository tempDocumentRepository;


    @Autowired
    private IdGenServiceImpl idGenService;

    @Override
    public void init() {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile multipartFile) {
        try {
            Files.copy(multipartFile.getInputStream(),this.path.resolve(multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error:"+e.getMessage());
        }
    }
    public void saveWithFileName(MultipartFile multipartFile,String fileName) {
        try {
            Files.copy(multipartFile.getInputStream(),this.path.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error:"+e.getMessage());
        }
    }
    @Override
    public Resource load(String filename) {
        Path file = path.resolve(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("Could not read the file.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error:"+e.getMessage());
        }
    }

    @Override
    public Stream<Path> load() {
        try {
            return Files.walk(this.path,1)
                    .filter(path ->!path.equals(this.path))
                    .map(this.path::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files.");
        }
    }

    @Override
    public void clear() {
        FileSystemUtils.deleteRecursively(path.toFile());
    }

    @Override
    public Document saveWithDocument(TourBooking reservation, EnumDocumentType estimate, MultipartFile x) {
        String extension = StringUtils.getFilenameExtension(x.getOriginalFilename());

        Document document = new Document();
        document.setCode(idGenService.documentCode());
        document.setType(estimate);
        document.setRaletiveId(reservation.getId());
        String filename = document.getCode()+"."+extension;
        document.setFileName(filename);
        document.setCreated_at(LocalDateTime.now());
        document.setUpdated_at(LocalDateTime.now());

        document.setOriginalFilename(x.getOriginalFilename());
        document = documentRepository.save(document);



        saveWithFileName(x,filename);

        return document;
    }

    @Override
    public Document saveWithDocument(long id, EnumDocumentType estimate, MultipartFile x) {

        String extension = StringUtils.getFilenameExtension(x.getOriginalFilename());

        Document document = new Document();
        document.setCode(idGenService.documentCode());
        document.setType(estimate);
        document.setRaletiveId(id);
        String filename = document.getCode()+"."+extension;
        document.setFileName(filename);
        document.setCreated_at(LocalDateTime.now());
        document.setUpdated_at(LocalDateTime.now());

        document.setOriginalFilename(x.getOriginalFilename());
        document = documentRepository.save(document);



        saveWithFileName(x,filename);

        return document;

    }



    @Override
    public TempDocument saveWithTempDocument(MultipartFile x) {

        String extension = StringUtils.getFilenameExtension(x.getOriginalFilename());

        TempDocument document = new TempDocument();
        document.setCode(idGenService.tempDocumentCode());


        String filename = document.getCode()+"."+extension;
        document.setFileName(filename);
        document.setCreated_at(LocalDateTime.now());
        document.setUpdated_at(LocalDateTime.now());
        document.setSize(x.getSize());
        document.setOriginalFilename(x.getOriginalFilename());
        document = tempDocumentRepository.save(document);

        saveWithFileName(x,filename);

        return document;

    }

    @Override
    public List<Document> saveFromTempDocumentList(long id, List<Pair<EnumDocumentType, TempDocument>> fileNames) {

        List<Document> documents = fileNames.stream().map(x->{

            EnumDocumentType documentType = x.getValue0();
            TempDocument tempDocument = x.getValue1();

            String extension = StringUtils.getFilenameExtension(tempDocument.getOriginalFilename());

            Document document = new Document();
            document.setExtension(extension);
            document.setCode(tempDocument.getCode());
            document.setType(documentType);
            document.setRaletiveId(id);
            String filename = document.getCode()+"."+extension;
            document.setFileName(filename);
            document.setCreated_at(LocalDateTime.now());
            document.setUpdated_at(LocalDateTime.now());
            document.setSize(tempDocument.getSize());
            document.setOriginalFilename(tempDocument.getOriginalFilename());
            return document;
        }).collect(toList());


        documents = documentRepository.saveAll(documents);

        return documents;
    }

    @Override
    public Document saveFromTempDocument(long id, Pair<EnumDocumentType, TempDocument> x) {
        EnumDocumentType documentType = x.getValue0();
        TempDocument tempDocument = x.getValue1();

        String extension = StringUtils.getFilenameExtension(tempDocument.getOriginalFilename());

        Document document = new Document();
        document.setExtension(extension);
        document.setCode(tempDocument.getCode());
        document.setType(documentType);
        document.setRaletiveId(id);
        String filename = document.getCode()+"."+extension;
        document.setFileName(filename);
        document.setCreated_at(LocalDateTime.now());
        document.setUpdated_at(LocalDateTime.now());
        document.setSize(tempDocument.getSize());
        document.setOriginalFilename(tempDocument.getOriginalFilename());
        return documentRepository.save(document);
    }

    @Override
    public List<String> loadDocuments(EnumDocumentType scenario_logo, long id) {
        List<Document> documents = documentRepository.findAllByTypeAndRaletiveId(scenario_logo,id);
        return documents.stream().map(x->FileStorageServiceImpl.url(x.getFileName())).collect(toList());


    }


    @Override
    public Map<EnumDocumentType, List<String>> loadDocuments(List<EnumDocumentType> scenario_logo, long id) {

        List<Document> documents = documentRepository.findAllByTypeInAndRaletiveId(scenario_logo,id);
        return documents.stream().collect(Collectors.groupingBy(x->x.getType(),
                collectingAndThen(toList(),list ->list.stream().map(x->FileStorageServiceImpl.url(x.getFileName())).collect(toList())) ));//(x->).collect(Collectors.toList());

    }


    public static String url(Document x) {

        String url = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class,
                        "getFile",
                        x.getFileName()
                ).build().toString();


        return url;

    }
    public static String url(String filename) {

        String url = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class,
                        "getFile",
                        filename
                ).build().toString();


        return url;

    }
}