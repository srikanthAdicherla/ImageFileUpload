package com.uploadingfiles.controller;

import com.uploadingfiles.Service.ImageService;
import com.uploadingfiles.dto.ImageDetails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;


@Controller
public class FileUploadController {
    public static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    ImageService imageService;

    @Autowired
    public FileUploadController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value = "/downloadImageByImgName", method = RequestMethod.GET)
    public ResponseEntity downloadImage_By_ImgName(HttpServletResponse response, @RequestParam(value = "imageName") String imageName) throws IOException, SQLException {
        logger.info("Executing downloadImage_By_ImgName Method  with parameter imageName::::" + imageName);
        response.setContentType("image/jpeg");

        Blob ph = imageService.findByImageName(imageName);

        byte[] bytes = ph.getBytes(1, (int) ph.length());
        InputStream inputStream = new ByteArrayInputStream(bytes);
        IOUtils.copy(inputStream, response.getOutputStream());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/uploadImage", produces = {"application/json"}, consumes = {"multipart/form-data"}, method = RequestMethod.POST)
    public ResponseEntity<ImageDetails> uploadImage(@RequestParam("image") MultipartFile image) {
        logger.info("Executing uploadImage Method  with parameter imageFile::::" + image);

        if (!image.isEmpty()) {
            ImageDetails imageDetails = imageService.save_Image(image);
            return new ResponseEntity(imageDetails, HttpStatus.OK);
        } else {
            System.out.println("File is Empty");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}


