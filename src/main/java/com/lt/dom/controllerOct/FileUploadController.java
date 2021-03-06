package com.lt.dom.controllerOct;

import com.lt.dom.oct.MessageFile;
import com.lt.dom.oct.UploadFile;
import com.lt.dom.serviceOtc.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName FileUploadController
 * @Description TODO
 * @Author 树下魅狐
 * @Date 2020/4/28 0028 18:52
 * @Version since 1.0
 **/
@RestController
@RequestMapping("/oct")

public class FileUploadController {

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<MessageFile> upload(@RequestParam("file") MultipartFile file){
        try {
            fileStorageService.save(file);
            return ResponseEntity.ok(new MessageFile("Upload file successfully: "+file.getOriginalFilename()));
        }catch (Exception e){

            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new MessageFile("Could not upload the file:"+file.getOriginalFilename()));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<UploadFile>> files(){
        List<UploadFile> files = fileStorageService.load()
                .map(path->{
                    String fileName = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(FileUploadController.class,
                                    "getFile",
                                    path.getFileName().toString()
                            ).build().toString();
                    return new UploadFile(fileName,url);
                }).collect(Collectors.toList());
        return ResponseEntity.ok(files);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename")String filename){
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\""+file.getFilename()+"\"")
                .body(file);
    }
}