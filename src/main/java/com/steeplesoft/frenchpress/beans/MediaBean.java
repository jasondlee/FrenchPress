/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.MediaItem;
import com.steeplesoft.frenchpress.service.MediaService;
import java.io.ByteArrayOutputStream;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.icefaces.ace.component.fileentry.FileEntryCallback;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;

/**
 *
 * @author jdlee
 */
@Model
public class MediaBean implements FileEntryCallback {
    @Inject
    MediaService mediaService;
    
    MediaItem item = new MediaItem();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    FileEntryResults results;
    
    public List<MediaItem> getItems() {
        return mediaService.getItems();
    }

    @Override
    public void begin(FileEntryResults.FileInfo fi) {
        item.setName(fi.getFileName());
        item.setMimeType(fi.getContentType());
    }

    @Override
    public void write(byte[] bytes, int i, int i1) {
        baos.write(bytes, i, i1);
    }

    @Override
    public void write(int i) {
        System.out.println("Huh? " + i);
    }

    @Override
    public void end(FileEntryResults.FileInfo fi) {
        item.setFileSize(fi.getSize());
        item.setContents(baos.toByteArray());
    }
    
    public String upload() {
        return "";
    }
    
    public void listener(FileEntryEvent event) {
        System.out.println("Blah");
    }

    public FileEntryResults getResults() {
        return results;
    }

    public void setResults(FileEntryResults results) {
        this.results = results;
    }
}
