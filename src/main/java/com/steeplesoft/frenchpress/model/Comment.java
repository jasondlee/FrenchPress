package com.steeplesoft.frenchpress.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String commenterName;
    @Column(nullable = false)
    private String commenterUrl;

    @ManyToOne()
    private Post post;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommenterUrl() {
        return commenterUrl;
    }

    public void setCommenterUrl(String commenterUrl) {
        this.commenterUrl = commenterUrl;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
}
