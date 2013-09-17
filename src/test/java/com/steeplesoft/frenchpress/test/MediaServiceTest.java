package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.model.MediaItem;
import com.steeplesoft.frenchpress.service.MediaService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jdlee on 9/16/13.
 */
@RunWith(Arquillian.class)
@Category(IntegrationTests.class)
public class MediaServiceTest extends AbstractServiceTestBase {
    @Inject
    protected MediaService mediaService;

    @Test
    public void crud() {
        assertNotNull(mediaService.getItems());
        MediaItem item = createMediaItem();
        Calendar cal = Calendar.getInstance();
        cal.setTime(item.getUploadedDate());
        MediaItem item2 = mediaService.getItem(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, item.getName());
        assertEquals(item, item2);

        deleteMediaItem(item);
    }

    private MediaItem createMediaItem() {
        MediaItem item = new MediaItem();
        item.setName("test" + generateRandomNumber() + ".png");
        item.setMimeType("image/png");
        getBytes(item);
        mediaService.addItem(item);
        assertNotNull(item.getId());
        assertNotNull(item.getUploadedDate());

        return item;
    }

    private void deleteMediaItem(MediaItem item) {
        mediaService.deleteItem(item);
        assertNull(mediaService.getItem(item.getId()));

        mediaService.deleteItem(Long.MIN_VALUE);
    }

    private void getBytes(MediaItem item) {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("image.png");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int read = is.read(buffer);
            int totalRead = 0;
            while (read > -1) {
                baos.write(buffer, 0, read);
                totalRead += read;
                read = is.read(buffer);
            }
            item.setContents(baos.toByteArray());
            item.setFileSize(totalRead);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
