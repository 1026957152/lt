package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.config.Constants;
import com.lt.dom.controllerOct.FileUploadController;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumPhotos;
import com.lt.dom.repository.DocumentRepository;
import com.lt.dom.repository.TempDocumentRepository;
import com.lt.dom.vo.ImageVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


/**
 * @ClassName FileStorageServiceImpl
 * @Description TODO
 * @Author 树下魅狐
 * @Date 2020/4/28 0028 18:38
 * @Version since 1.0
 **/
@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStorageService {

    public static final Path path = Paths.get("fileStorage/static");


    //public static final String static_path ="files/";
    public static final String static_path ="static/";
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
        TempDocument document = new TempDocument();
        document.setCode(idGenService.tempDocumentCode());

        try (InputStream input = x.getInputStream()) {
            try {

                if(ImageIO.read(input) == null){
                    document.setImage(false);
                }else{
                    document.setImage(true);
                }
                // It's an image (only BMP, GIF, JPG and PNG are recognized).
            } catch (Exception e) {
                // It's not an image.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String extension = StringUtils.getFilenameExtension(x.getOriginalFilename());



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
    public List<Document> saveFromTempDocumentCode(String code, List<Pair<EnumDocumentType, TempDocument>> fileNames) {


        List<Document> documents = IntStream.range(0, fileNames.size()).mapToObj(x->{
            Pair<EnumDocumentType, TempDocument> pair=     fileNames.get(x);

            EnumDocumentType documentType = pair.getValue0();
            TempDocument tempDocument = pair.getValue1();

            String extension = StringUtils.getFilenameExtension(tempDocument.getOriginalFilename());

            Document document = new Document();
            document.setIndex(x);
            document.setExtension(extension);
            document.setImage(tempDocument.getImage());
            document.setCode(idGenService.documentCode());
            document.setTempDocumentCode(tempDocument.getCode());

            document.setType(documentType);
            document.setReference(code);
            // document.setRaletiveId(id);
            String filename = document.getTempDocumentCode()+"."+extension;
            document.setFileName(filename);
            document.setCreated_at(LocalDateTime.now());
            document.setUpdated_at(LocalDateTime.now());
            document.setSize(tempDocument.getSize());
            document.setOriginalFilename(tempDocument.getOriginalFilename());
            return document;
        })  .collect(Collectors.toList());



        documents = documentRepository.saveAll(documents);

        return documents;
    }


    // @Override
/*    public List<Document> saveFromTempDocumentWithDetail(String code, List<Pair<EnumDocumentType, TempDocument>> fileNames, PhoneResp) {


        List<Document> documents = IntStream.range(0, fileNames.size()).mapToObj(x->{
            Pair<EnumDocumentType, TempDocument> pair=     fileNames.get(x);

            EnumDocumentType documentType = pair.getValue0();
            TempDocument tempDocument = pair.getValue1();

            String extension = StringUtils.getFilenameExtension(tempDocument.getOriginalFilename());

            Document document = new Document();
            document.setIndex(x);
            document.setExtension(extension);
            document.setImage(tempDocument.getImage());
            document.setCode(idGenService.documentCode());
            document.setTempDocumentCode(tempDocument.getCode());

            document.setType(documentType);
            document.setReference(code);
            // document.setRaletiveId(id);
            String filename = document.getTempDocumentCode()+"."+extension;
            document.setFileName(filename);
            document.setCreated_at(LocalDateTime.now());
            document.setUpdated_at(LocalDateTime.now());
            document.setSize(tempDocument.getSize());
            document.setOriginalFilename(tempDocument.getOriginalFilename());
            return document;
        })  .collect(Collectors.toList());



        documents = documentRepository.saveAll(documents);

        return documents;
    }*/

    private Document saveFromTempDocumentWithRichi(String code, ImageVo imageVo ,Pair<EnumDocumentType, TempDocument> fileNames) {


        EnumDocumentType documentType = fileNames.getValue0();
        TempDocument tempDocument = fileNames.getValue1();

        String extension = StringUtils.getFilenameExtension(tempDocument.getOriginalFilename());

        Document document = new Document();
        document.setExtension(extension);
        document.setImage(tempDocument.getImage());
        document.setCode(idGenService.documentCode());
        document.setTempDocumentCode(tempDocument.getCode());

        document.setType(documentType);
        document.setReference(code);
        // document.setRaletiveId(id);
        String filename = document.getTempDocumentCode()+"."+extension;
        document.setFileName(filename);
        document.setCreated_at(LocalDateTime.now());
        document.setUpdated_at(LocalDateTime.now());
        document.setSize(tempDocument.getSize());
        document.setOriginalFilename(tempDocument.getOriginalFilename());



        document.setIndex(imageVo.getIndex());
        document.setDesc(imageVo.getDesc());
        document.setVisiable(imageVo.isVisible());


        document = documentRepository.save(document);

        return document;
    }



    private Document saveFromTempDocumentWithRichWithPhote(String code, PhotoResp photoResp ,Pair<EnumDocumentType, TempDocument> fileNames) {


        EnumDocumentType documentType = fileNames.getValue0();
        TempDocument tempDocument = fileNames.getValue1();

        String extension = StringUtils.getFilenameExtension(tempDocument.getOriginalFilename());

        Document document = new Document();
        document.setExtension(extension);
        document.setImage(tempDocument.getImage());
        document.setCode(idGenService.documentCode());
        document.setTempDocumentCode(tempDocument.getCode());

        document.setType(documentType);
        document.setReference(code);
        // document.setRaletiveId(id);
        String filename = document.getTempDocumentCode()+"."+extension;
        document.setFileName(filename);
        document.setCreated_at(LocalDateTime.now());
        document.setUpdated_at(LocalDateTime.now());
        document.setSize(tempDocument.getSize());
        document.setOriginalFilename(tempDocument.getOriginalFilename());


        document.setCaption(photoResp.getCaption());
        document.setIndex(photoResp.getIndex());
        document.setDesc(photoResp.getDesc());
        document.setVisiable(photoResp.getVisiable());


        document = documentRepository.save(document);

        return document;
    }



    @Override
    public List<Document> saveFromTempDocumentCode(long id, List<Pair<EnumDocumentType, TempDocument>> fileNames) {

        List<Document> documents = fileNames.stream().map(x->{

            EnumDocumentType documentType = x.getValue0();
            TempDocument tempDocument = x.getValue1();

            String extension = StringUtils.getFilenameExtension(tempDocument.getOriginalFilename());

            Document document = new Document();
            document.setExtension(extension);
            document.setImage(tempDocument.getImage());
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
        String filename = document.getTempDocumentCode()+"."+extension;
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
    public List<PhotoResp> loadDocuments(EnumDocumentType scenario_logo, String reference) {
        List<Document> documents = documentRepository.findAllByTypeAndReference(scenario_logo,reference);

        return documents.stream().map(x->FileStorageServiceImpl.url_range(Arrays.asList(EnumPhotos.full),x)).collect(toList());

    }
    @Override
    public List<PhotoResp> loadDocuments(List<EnumPhotos> list, EnumDocumentType scenario_logo, String reference) {
        List<Document> documents = documentRepository.findAllByTypeAndReference(scenario_logo,reference);

        return documents.stream().map(x->FileStorageServiceImpl.url_range(list,x)).collect(toList());

    }
    @Override
    public List<PhotoResp> loadDocumentsWithCode(EnumDocumentType scenario_logo, String reference) {
        List<Document> documents = documentRepository.findAllByTypeAndReference(scenario_logo,reference);


        if(documents.isEmpty()){

            return Arrays.asList(FileStorageServiceImpl.url_range_default(Arrays.asList(EnumPhotos.thumb)));

        }
        return documents.stream().map(x->FileStorageServiceImpl.url_rangeWithCode(x)).collect(toList());

    }

    @Override
    public List<PhotoResp> loadDocumentsWithCodeEdit(EnumDocumentType scenario_logo, String reference) {
        List<Document> documents = documentRepository.findAllByTypeAndReference(scenario_logo,reference);



        return documents.stream().map(x->FileStorageServiceImpl.url_rangeWithCode(x)).collect(toList());

    }
    @Override
    public PhotoResp loadDocumentWithCodeEdit(EnumDocumentType scenario_logo, String reference) {
        List<Document> documents = documentRepository.findAllByTypeAndReference(scenario_logo,reference);

        if(documents.isEmpty()){
            return null;
        }else{
            return documents.stream().map(x->FileStorageServiceImpl.url_rangeWithCode(x)).collect(toList()).get(0);

        }


    }
    @Override
    public Map<EnumDocumentType, List<String>> loadDocuments(List<EnumDocumentType> scenario_logo, long id) {

        List<Document> documents = documentRepository.findAllByTypeInAndRaletiveId(scenario_logo,id);
        return documents.stream().collect(Collectors.groupingBy(x->x.getType(),
                collectingAndThen(toList(),list ->list.stream().map(x->FileStorageServiceImpl.url(x.getFileName())).collect(toList())) ));//(x->).collect(Collectors.toList());

    }


    public static String url(Document x) {


        return  linkTo(FileUploadController.class).slash(static_path+x.getFileName()).toUriComponentsBuilder().build().toString();

/*        String url = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class,
                        "getFile",
                        x.getFileName()
                ).build().toString();*/

/*        String url = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class,
                        "getFile",
                        x.getFileName()
                ).build().toString();*/


        //  return url;

    }

    public static PhotoResp url(TempDocument tempDocument) {

        return url_range(tempDocument);
    }




    public static PhotoResp url_range(TempDocument tempDocument) {

        String filename = tempDocument.getFileName();
        PhotoResp photoResp = new PhotoResp();

        photoResp.setUrl_thumbnail(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());
        photoResp.setUrl(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());
        photoResp.setUrl_large(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());
        photoResp.setUrl_xlarge(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());
        photoResp.setUrl_original(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());

        photoResp.setDesc("tempDocument.getNote()");



        return photoResp;



    }


    public static PhotoResp url_range(List<EnumPhotos> enumPhotosList,Document tempDocument) {



        String filename = tempDocument.getFileName();
        PhotoResp photoResp = new PhotoResp();

        if(enumPhotosList.contains(EnumPhotos.thumb)){
            photoResp.setUrl_thumbnail(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());

        }
        if(enumPhotosList.contains(EnumPhotos.large)){
            photoResp.setUrl_xlarge(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());

        }
        if(enumPhotosList.contains(EnumPhotos.medium)){
            photoResp.setUrl_large(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());

        }
        if(enumPhotosList.contains(EnumPhotos.full)){
            photoResp.setUrl(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());

        }
        if(enumPhotosList.contains(EnumPhotos.original)){
            photoResp.setUrl_original(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());

        }

        photoResp.setDesc(tempDocument.getDesc());
        //  photoResp.setVisiable(tempDocument.getVisiable());
        //dyrttphotoResp.setCode(tempDocument.getTempDocumentCode());
        return photoResp;



    }


    public static PhotoResp url_range_default(List<EnumPhotos> enumPhotosList) {




        PhotoResp photoResp = new PhotoResp();


        String link =  linkTo(FileUploadController.class).slash("/"+ Constants.STATIC_PATH+"/"+"default_logo.jpg").toUriComponentsBuilder().build().toString();

        //    String link = "https://www.baidu.com/img/flexible/logo/pc/result.png";
        if(enumPhotosList.contains(EnumPhotos.thumb)){
            photoResp.setUrl_thumbnail(link);

        }
        if(enumPhotosList.contains(EnumPhotos.large)){
            photoResp.setUrl_xlarge(link);

        }
        if(enumPhotosList.contains(EnumPhotos.medium)){
            photoResp.setUrl_large(link);

        }
        if(enumPhotosList.contains(EnumPhotos.full)){
            photoResp.setUrl(link);

        }
        if(enumPhotosList.contains(EnumPhotos.original)){
            photoResp.setUrl_original(link);

        }
        return photoResp;



    }


    public static PhotoResp url_rangeWithCode(Document document) {

        String filename = document.getFileName();
        PhotoResp photoResp = new PhotoResp();

        photoResp.setUrl(linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString());
        photoResp.setDesc(document.getDesc()==null?"":document.getDesc());
        photoResp.setCode(document.getTempDocumentCode());
        photoResp.setCaption(document.getCaption()==null?"":document.getCaption());
        photoResp.setVisiable(document.getVisiable()==null?false:document.getVisiable());
        photoResp.setFileName(document.getOriginalFilename());

        return photoResp;



    }

    public static String url_(TempDocument tempDocument) {
        return url(tempDocument.getFileName());
    }



    public static String url(String filename) {

        return  linkTo(FileUploadController.class).slash(static_path+filename).toUriComponentsBuilder().build().toString();

/*        String url = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class,
                        "getFile",
                        filename
                ).build().toString();


        return url;*/

    }

    public static Link url_static(String filename, String name) {

        return  linkTo(FileUploadController.class).slash(static_path+filename).withRel(name);

/*        String url = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class,
                        "getFile",
                        filename
                ).build().toString();


        return url;*/

    }
    public Map<EnumDocumentType,TempDocument> loadTempDocument(List<Pair<EnumDocumentType, String>> asList) {

        asList = asList.stream().filter(e-> !ObjectUtils.isEmpty(e.getValue1())).collect(Collectors.toList());
        Map<String,EnumDocumentType> documentTypeMap = asList.stream().collect(Collectors.toMap(e->e.getValue1(),e->e.getValue0()));

        List<TempDocument> tempDocuments = tempDocumentRepository.findAllByCodeIn(asList.stream().map(x->x.getValue1()).collect(Collectors.toList()));

        Map<EnumDocumentType,TempDocument> m = tempDocuments.stream().collect(Collectors.toMap(x->documentTypeMap.get(x.getCode()),x->x));
        return m;
    }

/*
    @Override
    public PhotoResp loadDocument(String thumbnail_image) {
        if(thumbnail_image != null){
            Optional<Document> documentOptional = documentRepository.findByCode(thumbnail_image);

            if(documentOptional.isPresent()){
                return url_range(Arrays.asList(EnumPhotos.thumb),documentOptional.get());
            }

            return url_range_default(Arrays.asList(EnumPhotos.thumb));
        }
        return null;

    }*/

    @Override
    public PhotoResp loadDocumentWithDefault(EnumDocumentType type, String reference) {


        return loadDocumentWithDefault(Arrays.asList(EnumPhotos.full),type,reference);


    }


    @Override
    public Boolean isExsit(EnumDocumentType type, String reference) {
        List<Document> documentOptional = documentRepository.findAllByTypeAndReference(type,reference);

        return documentOptional.size() > 0;
    }
    @Override
    public PhotoResp loadDocument(List<EnumPhotos> asList, EnumDocumentType type, String reference) {
        List<Document> documentOptional = documentRepository.findAllByTypeAndReference(type,reference);

        if(documentOptional.size() > 0){
            return url_range(asList,documentOptional.get(0));
        }

        return null;
    }


    @Override
    public PhotoResp loadDocumentWithDefault(List<EnumPhotos> asList, EnumDocumentType type, String reference) {
        List<Document> documentOptional = documentRepository.findAllByTypeAndReference(type,reference);

        if(documentOptional.size() > 0){
            return url_range(asList,documentOptional.get(0));
        }

        return url_range_default(asList);
    }

    @Override
    public PhotoResp loadDocumentWithCode(EnumDocumentType type, String code) {

        List<Document> documentOptional = documentRepository.findAllByTypeAndReference(type,code);

        if(documentOptional.size() > 0 ){
            return url_rangeWithCode(documentOptional.get(0));
        }
        return null;
    }

    @Override
    public String loadDocumentWithCodeToUrl(EnumDocumentType type, String code) {

        List<Document> documentOptional = documentRepository.findAllByTypeAndReference(type,code);

        if(documentOptional.size() > 0 ){
            return url_rangeWithCode(documentOptional.get(0)).getUrl_thumbnail();
        }
        return null;
    }


    @Override
    public Document saveFromTempDocumentCode(String objectCode, EnumDocumentType type, String tempDocCode) {


        Optional<TempDocument> tempDocumentOptional_video = tempDocumentRepository.findByCode(tempDocCode);


        if(tempDocumentOptional_video.isPresent()){


            documentRepository.deleteAllByTypeAndReference(type,objectCode);

            List<Document> documents = saveFromTempDocumentCode(
                    objectCode,
                    Arrays.asList(Pair.with(type,tempDocumentOptional_video.get())));

            return documents.get(0);
        }

        return null;

    }
    @Override
    public Document updateFromTempDocumentCode(String objectCode, EnumDocumentType type, String tempDocCode) {


        Optional<TempDocument> tempDocumentOptional_video = tempDocumentRepository.findByCode(tempDocCode);


        if(tempDocumentOptional_video.isPresent()){


            documentRepository.deleteAllByTypeAndReference(type,objectCode);

            List<Document> documents = saveFromTempDocumentCode(
                    objectCode,
                    Arrays.asList(Pair.with(type,tempDocumentOptional_video.get())));

            return documents.get(0);
        }

        return null;

    }

    @Override
    @Transactional
    public Document updateFromTempDocument(String objectCode, PhotoResp photoResp, EnumDocumentType type) {


        Optional<TempDocument> tempDocumentOptional_video = tempDocumentRepository.findByCode(photoResp.getCode());


        if(tempDocumentOptional_video.isPresent()){
            TempDocument tempDocument = tempDocumentOptional_video.get();


            documentRepository.deleteAllByTypeAndReference(type,objectCode);

            Document documents = saveFromTempDocumentWithRichWithPhote(
                    objectCode,
                    photoResp,
                    Pair.with(type,tempDocument));

            return documents;
        }

        return null;

    }

    @Override
    public String saveFromTempDocument(String objectCode, EnumDocumentType type, TempDocument tempDocCode) {
        List<Document> documents = saveFromTempDocumentCode(
                objectCode,
                Arrays.asList(Pair.with(type,tempDocCode)));

        return documents.get(0).getCode();
    }

    @Override
    public Document saveFromTempDocumentWithRich(String objectCode, ImageVo imageVo, EnumDocumentType type, TempDocument tempDocCode) {


        Document documents = saveFromTempDocumentWithRichi(
                objectCode,
                imageVo,
                Pair.with(type,tempDocCode));

        return documents;


    }
    @Override
    public Document saveFromTempDocumentWithRich(String objectCode, PhotoResp imageVo, EnumDocumentType type) {

        Optional<TempDocument> optionalTempDocument = tempDocumentRepository.findByCode(imageVo.getCode());
        TempDocument tempDocument = optionalTempDocument.get();

        Document documents = saveFromTempDocumentWithRichWithPhote(
                objectCode,
                imageVo,
                Pair.with(type,tempDocument));

        return documents;


    }
    @Override
    public Map<String, List<PhotoResp>> loadDocuments(EnumDocumentType scenario_logo, List<String> reference) {

        List<Document> documents = documentRepository.findAllByTypeAndReferenceIn(scenario_logo,reference);

        System.out.println("找到了  document "+ documents);
        Map<String, List<Document>>  stringListMap =  documents.stream().collect(Collectors.groupingBy(e->e.getReference()));


        return stringListMap.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue().stream().map(x->{
            return FileStorageServiceImpl.url_rangeWithCode(x);

        }).collect(Collectors.toList())));

    }





    public Document setupData() {

        String extension = StringUtils.getFilenameExtension("aaa.jpg");

        Document document = new Document();
        document.setExtension(extension);
        document.setCode("tempDocument.getCode()");
        document.setType(EnumDocumentType.default_photo);
        document.setRaletiveId(-1);
        String filename = document.getTempDocumentCode()+"."+extension;
        document.setFileName(filename);
        document.setCreated_at(LocalDateTime.now());
        document.setUpdated_at(LocalDateTime.now());
        document.setSize(100);
        document.setOriginalFilename("tempDocument.getOriginalFilename()");
        return documentRepository.save(document);
    }

    public static PhotoResp loadDocumentWithDefault(String s) {
        PhotoResp photoResp = new PhotoResp();

        photoResp.setUrl( Constants.STATIC_RECOURCE_PATH+s);
        return photoResp;
    }
}