/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author jdlee
 */
@Entity
@Table(name = "mediaitems")
@TableGenerator(name = "sequence", pkColumnName = "seq_name", valueColumnName = "seq_count" )
public class MediaItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private int version;
    private String name;
    private String mimeType;
    private long fileSize;
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedDate = new Date();
    @Basic(fetch = FetchType.EAGER)
    @Lob
    @Column(name = "contents")
    private byte[] contents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MediaItem other = (MediaItem) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.mimeType == null) ? (other.mimeType != null) : !this.mimeType.equals(other.mimeType)) {
            return false;
        }
        if (this.fileSize != other.fileSize) {
            return false;
        }
        if (!Arrays.equals(this.contents, other.contents)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 41 * hash + (this.mimeType != null ? this.mimeType.hashCode() : 0);
        hash = 41 * hash + (int) (this.fileSize ^ (this.fileSize >>> 32));
        hash = 41 * hash + (this.uploadedDate != null ? this.uploadedDate.hashCode() : 0);
        hash = 41 * hash + Arrays.hashCode(this.contents);
        return hash;
    }

    @Override
    public String toString() {
        return "MediaItem{" + "id=" + id + ", name=" + name + ", mimeType=" + mimeType + ", fileSize=" + fileSize +
                ", uploadedDate=" + uploadedDate + '}';
    }
}
