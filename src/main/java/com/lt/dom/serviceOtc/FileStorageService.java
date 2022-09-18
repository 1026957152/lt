package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Document;
import com.lt.dom.oct.Reservation;
import com.lt.dom.oct.TempDocument;
import com.lt.dom.oct.TourBooking;
import com.lt.dom.otcenum.EnumDocumentType;
import com.lt.dom.otcenum.EnumPhotos;
import com.lt.dom.vo.ImageVo;
import org.javatuples.Pair;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @ClassName FileStorageService
 * @Description TODO
 * @Author 树下魅狐
 * @Date 2020/4/28 0028 18:35
 * @Version since 1.0
 **/
public interface FileStorageService {

    void init();

    void save(MultipartFile multipartFile);

    Resource load(String filename);

    Stream<Path> load();

    void clear();

    Document saveWithDocument(TourBooking reservation, EnumDocumentType estimate, MultipartFile x);
    Document saveWithDocument(long id, EnumDocumentType estimate, MultipartFile x);

    TempDocument saveWithTempDocument(MultipartFile x);

    List<Document> saveFromTempDocumentCode(String code, List<Pair<EnumDocumentType, TempDocument>> fileNames);

    List<Document> saveFromTempDocumentCode(long id, List<Pair<EnumDocumentType, TempDocument>> fileNames);


    Document saveFromTempDocument(long id, Pair<EnumDocumentType, TempDocument> x);

    List<String> loadDocuments(EnumDocumentType scenario_logo, long id);

    List<PhotoResp> loadDocuments(EnumDocumentType scenario_logo, String reference);

    List<PhotoResp> loadDocumentsWithCode(EnumDocumentType scenario_logo, String reference);

    Map<EnumDocumentType, List<String>> loadDocuments(List<EnumDocumentType> scenario_logo, long id);

    PhotoResp loadDocument(String thumbnail_image);

    PhotoResp loadDocument(EnumDocumentType type, String thumbnail_image);


    PhotoResp loadDocument(List<EnumPhotos> asList, EnumDocumentType type, String reference);

    PhotoResp loadDocumentWithCode(EnumDocumentType type, String code);

    String saveFromTempDocumentCode(String objectCode, EnumDocumentType type, String tempDocCode);

    String saveFromTempDocument(String objectCode, EnumDocumentType type, TempDocument tempDocCode);


    Document saveFromTempDocumentWithRich(String objectCode, ImageVo imageVo, EnumDocumentType type, TempDocument tempDocCode);

    Map<String,List<PhotoResp>> loadDocuments(EnumDocumentType scenario_logo, List<String> reference);
}