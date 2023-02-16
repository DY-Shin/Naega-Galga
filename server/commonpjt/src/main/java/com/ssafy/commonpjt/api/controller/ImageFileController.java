package com.ssafy.commonpjt.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image")
public class ImageFileController {

    private String imagePath = "product/img/";
    @GetMapping("/display")
    public ResponseEntity<?> displayImage(@RequestParam(value="path")String path){
        StringBuilder imagepathSb = new StringBuilder(imagePath);
        imagepathSb.append(path);
        Resource image = new FileSystemResource(imagepathSb.toString());
        HttpHeaders headers = new HttpHeaders();

        try{
            if(!image.exists())
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            Path filePath;

            filePath = Paths.get(path);
            headers.add("Content-Type", Files.probeContentType(filePath));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(image, headers, HttpStatus.OK);
    }
}
