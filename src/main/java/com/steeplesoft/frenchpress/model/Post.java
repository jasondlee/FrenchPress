/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "posts")
@NamedQueries({
        @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p ORDER BY p.createdDate, p.modifiedDate"),
        @NamedQuery(name = "Post.findById", query = "SELECT p FROM Post p WHERE p.id = :id"),
        @NamedQuery(name = "Post.findSticky", query = "SELECT p FROM Post p where p.isSticky = true ORDER by p.createdDate desc"),
        @NamedQuery(name = "Post.mostRecent", query = "SELECT p FROM Post p where p.isSticky = false ORDER by p.createdDate desc ")
})
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true, length = Integer.MAX_VALUE)
    private String body;

    @Column
    private Boolean isSticky;

    @ManyToOne()
    private User postedBy;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date startPublishing;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopPublishing;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate = new Date();
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate = new Date();

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

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
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
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.steeplesoft.frenchpress.model.Post[id=" + id + ", title='" + title +"', owner=" +
                ((postedBy != null) ? postedBy.getFullName() : "") + "]";
    }
}
