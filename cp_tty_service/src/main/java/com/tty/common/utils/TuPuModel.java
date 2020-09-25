package com.tty.common.utils;

import java.io.InputStream;

/**
 * Created by Administrator on 2017/5/5.
 */
public class TuPuModel {

    private InputStream inputStream;

    private String fileName;


    public TuPuModel(InputStream inputStream, String fileName) {
        this.inputStream = inputStream;
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
