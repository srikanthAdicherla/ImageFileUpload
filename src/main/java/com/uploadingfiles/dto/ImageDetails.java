package com.uploadingfiles.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;

public class ImageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Integer imageId;

    @Lob
    @JsonProperty("image")
    private byte[] image;

    @JsonProperty("imgName")
    private String imgName;

    @JsonProperty("imgSize")
    private Integer imgSize;

    @JsonProperty("contentType")
    private String contentType;

    public ImageDetails(String imgName, byte[] decompressBytes) {
    }

    public ImageDetails() {

    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getImgSize() {
        return imgSize;
    }

    public void setImgSize(Integer imgSize) {
        this.imgSize = imgSize;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "ImageDetails{" +
                "image=" + Arrays.toString(image) +
                ", imageId='" + imageId + '\'' +
                ", imgSize=" + imgSize +
                ", imgName='" + imgName + '\'' +
                ", contentType=" + contentType +
                '}';
    }
}
