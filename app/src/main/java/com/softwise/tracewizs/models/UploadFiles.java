package com.softwise.tracewizs.models;

public class UploadFiles {
    private int id;
    private String fileName;
    private String filePath;
    private String callingType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCallingType() {
        return callingType;
    }

    public void setCallingType(String callingType) {
        this.callingType = callingType;
    }
}
