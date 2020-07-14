package com.uploadingfiles.dao;

import com.uploadingfiles.Service.ImageService;
import com.uploadingfiles.dto.ImageDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UploadingImageDao {
     public static Logger logger = LoggerFactory.getLogger(UploadingImageDao.class);

     private JdbcTemplate jdbcTemplate;

     public static String UPLOAD_DOCUMENT = "INSERT INTO IMAGEDETAILS (ID,IMAGE,IMGNAME,IMGSIZE,CONTENTTYPE) VALUES(?,?,?,?,?)";
     public static String GET_UPLOADED_DOCUMENT_BY_IMGNAME = "SELECT IMAGE FROM IMAGEDETAILS WHERE IMGNAME = ?";

     @Autowired
     public UploadingImageDao(JdbcTemplate jdbcTemplate) {
          this.jdbcTemplate = jdbcTemplate;
     }

     public int saveImage(ImageDetails imageDetails) {
          logger.info("Executing saveImage Method  with parameter imageDetails class::::" +imageDetails);
          return jdbcTemplate.update(UPLOAD_DOCUMENT,
                  imageDetails.getImageId(), imageDetails.getImage(), imageDetails.getImgName(), imageDetails.getImgSize(), imageDetails.getContentType());
     }

     public Blob findByImageName(String imageName) {
          logger.info("Executing findByImageName Method  with parameter imageName ::::" +imageName);

          ImageDetails imageDetails = new ImageDetails();

          Blob photo = jdbcTemplate.queryForObject(GET_UPLOADED_DOCUMENT_BY_IMGNAME, new Object[]{imageName}, Blob.class);
          return photo;

          /*jdbcTemplate.query(new PreparedStatementCreator() {
               @Override
               public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(GET_UPLOADED_DOCUMENT_BY_IMGNAME);
                    ps.setString(1, imageName);
                    return ps;
               }
          }, new RowCallbackHandler() {
               @Override
               public void processRow(ResultSet resultSet) throws SQLException {
                    imageDetails.setImage(resultSet.getBytes("IMAGE"));
                    imageDetails.setContentType(resultSet.getString("CONTENTTYPE"));
               }
          });
          return imageDetails;
     }*/
     }
}