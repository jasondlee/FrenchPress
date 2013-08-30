/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.MediaItem;
import com.steeplesoft.frenchpress.service.MediaService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author jdlee
 */
@Model
public class MediaBean implements Serializable {
    @Inject
    protected MediaService mediaService;
    protected HtmlDataTable dataTable;

    protected MediaItem item = new MediaItem();
    protected Part uploadedFile;

    public List<MediaItem> getItems() {
        return mediaService.getItems();
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    /*
    public void listener(FileUploadEvent event) throws Exception {
        UploadedFile file = event.getUploadedFile();
        MediaItem item = new MediaItem();
        item.setName(file.getName());
        item.setMimeType(file.getContentType());
        item.setFileSize(file.getSize());
        try {
            item.setContents(file.getData());
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        mediaService.addItem(item);
    }
    */

    public String upload() {
        String fileName = getFilename(uploadedFile);
        MediaItem newItem = new MediaItem();
        newItem.setName(fileName);
        newItem.setMimeType(uploadedFile.getContentType());
        newItem.setFileSize(uploadedFile.getSize());
        newItem.setContents(getBytes(uploadedFile));
        mediaService.addItem(newItem);
        return "";
    }

    public String getMediaItemUrl(MediaItem item) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(item.getUploadedDate());
        final String month = "0" + (cal.get(Calendar.MONTH) + 1);
        final String year = "0000" + (cal.get(Calendar.YEAR));
        return "/rest/uploads/" + year.substring(year.length() - 4) + "/"
                + month.substring(month.length() - 2) + "/"
                + item.getName();
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    private String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    private byte[] getBytes(Part part) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            int nRead;
            byte[] data = new byte[16384];
            InputStream is = part.getInputStream();

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                baos.write(data, 0, nRead);
            }
        } catch (IOException ioe) {
        }

        return baos.toByteArray();
    }
}
