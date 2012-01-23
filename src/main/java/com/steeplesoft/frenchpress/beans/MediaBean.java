/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.MediaItem;
import com.steeplesoft.frenchpress.service.MediaService;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

/**
 *
 * @author jdlee
 */
@Named
@SessionScoped
public class MediaBean implements Serializable { //implements FileEntryCallback {
    @Inject
    MediaService mediaService;
    
    MediaItem item = new MediaItem();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    public List<MediaItem> getItems() {
        return mediaService.getItems();
    }

    public void listener(FileUploadEvent event) throws Exception{
        UploadedFile file = event.getUploadedFile();
        MediaItem item = new MediaItem();
        item.setName(file.getName());
        item.setMimeType(file.getContentType());
        item.setFileSize(file.getSize());
        item.setContents(file.getData());
        mediaService.addItem(item);
    }  
    
    public String upload() {
        return "";
    }
    
}
