/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "blog_entry")
@NamedQueries({
        @NamedQuery(name = "BlogEntry.findAll", query = "SELECT be FROM BlogEntry be"),
        @NamedQuery(name = "BlogEntry.findById", query = "SELECT be FROM BlogEntry be WHERE be.id = :id"),
        @NamedQuery(name = "BlogEntry.findSticky", query = "SELECT be FROM BlogEntry be where be.isSticky = true ORDER by be.createdDate desc"),
        @NamedQuery(name = "BlogEntry.mostRecent", query = "SELECT be FROM BlogEntry be where be.isSticky = false ORDER by be.createdDate desc ")
})
public class BlogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = Integer.MAX_VALUE)
    private String body;

    @Column
    private Boolean isSticky;

    @ManyToOne()
    private BlogEntry postedBy;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date startPublishing;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopPublishing;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public BlogEntry getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(BlogEntry postedBy) {
        this.postedBy = postedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsSticky() {
        return isSticky;
    }

    public void setIsSticky(Boolean sticky) {
        this.isSticky = sticky;
    }

    public Date getStartPublishing() {
        return startPublishing;
    }

    public void setStartPublishing(Date startPublishing) {
        this.startPublishing = startPublishing;
    }

    public Date getStopPublishing() {
        return stopPublishing;
    }

    public void setStopPublishing(Date stopPublishing) {
        this.stopPublishing = stopPublishing;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @PrePersist
    public void prePersist() {
        Date date = new Date();
        setCreatedDate(date);
        setModifiedDate(date);
    }

    @PreUpdate
    public void preUpdate() {
        setModifiedDate(new Date());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlogEntry)) {
            return false;
        }
        BlogEntry other = (BlogEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.steeplesoft.frenchpress.model.BlogEntry[id=" + id + "]";
    }
}
