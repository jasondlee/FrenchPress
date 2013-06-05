/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.MediaItem;
import com.steeplesoft.frenchpress.service.MediaService;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.richfaces.component.UIDataTable;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

/**
 *
 * @author jdlee
 */
@Model
public class MediaBean implements Serializable { //implements FileEntryCallback {
    @Inject
    protected MediaService mediaService;
    protected UIDataTable dataTable;
    
    protected MediaItem item = new MediaItem();
    protected ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
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
    
    public String getMediaItemUrl(MediaItem item) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(item.getUploadedDate());
        final String month = "0"+(cal.get(Calendar.MONTH) + 1);
        final String year = "0000"+(cal.get(Calendar.YEAR));
        return "/rest/uploads/" + year.substring(year.length()-4) + "/" + 
                month.substring(month.length()-2) + "/" + 
                item.getName();
    }

    public UIDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(UIDataTable dataTable) {
        this.dataTable = dataTable;
    }
}