package com.uploadingfiles.Service;

import com.uploadingfiles.controller.FileUploadController;
import com.uploadingfiles.dao.UploadingImageDao;
import com.uploadingfiles.dto.ImageDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;

@Service
public class ImageService {
    public static Logger logger = LoggerFactory.getLogger(ImageService.class);

    UploadingImageDao uploadingImageDao;

    @Autowired
    public ImageService(UploadingImageDao uploadingImageDao) {
        this.uploadingImageDao = uploadingImageDao;
    }

    @Transactional
    public ImageDetails save_Image(MultipartFile image) {
        ImageDetails imageDetails = new ImageDetails();
        int file_saved = 0;
        File directory = new File("D:\\UPLOADED_IMAGE");

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        logger.info("FileName :::::::: "+fileName);

        File destPath = new File(directory + fileName);
        logger.info("Destination File Path :::::::: "+destPath);
        try (FileOutputStream fileOuputStream = new FileOutputStream(destPath)) {
            fileOuputStream.write(image.getBytes());
            byte[] byteObjects = new byte[image.getBytes().length];
            int i = 0;
            for (byte b : image.getBytes()) {
                byteObjects[i++] = b;
            }

            imageDetails.setContentType(image.getContentType());
            imageDetails.setImgName(fileName);
            imageDetails.setImgSize(Math.toIntExact(image.getSize()));
            imageDetails.setImage(byteObjects);

            file_saved = uploadingImageDao.saveImage(imageDetails);

        } catch (IOException e) {
            System.out.println("Getting IOException while uploading the document " + e);
            e.printStackTrace();
        }
        if (file_saved == 1) {
            return imageDetails;
        } else {
            return null;
        }
    }

    public Blob findByImageName(String imageName) {
        Blob imageDetails = uploadingImageDao.findByImageName(imageName);
        return imageDetails;
    }
}
