package com.agsa.ruwfl.model;

/**
 * Created by Evillatoro on 22/11/2016.
 */

public class FirmaModel {
    private String fileName;
    private String imageBytes;
    private String token;

    public FirmaModel() {
    }

    public FirmaModel(String imageBytes, String fileName, String token) {
        this.imageBytes = imageBytes;
        this.fileName = fileName;
        this.token = token;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(String imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
