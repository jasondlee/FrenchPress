package com.steeplesoft.frenchpress.util.upload;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: jasonlee
 * Date: Apr 18, 2010
 * Time: 10:18:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class FileHolder {
    File file;
    String fileName;
    String mimeType;

    public FileHolder(File file, String fileName, String mimeType) {
        this.file = file;
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}