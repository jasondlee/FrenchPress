/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jdlee
 */
@Entity
@Table(name="posts",
        uniqueConstraints=@UniqueConstraint(columnNames="slug"))
@NamedQueries({
    @NamedQuery(name="findBySlug", query="SELECT p FROM Post p WHERE p.slug = :SLUG")
})
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private int version;
    @NotNull
    private String title;
    private String slug;
    private String body;
    @Temporal(TemporalType.TIMESTAMP)
    private Date posted;
    @ManyToOne
    private User author;
    @OneToMany(mappedBy="post", fetch=FetchType.EAGER)
    @OrderBy("id ASC")
    private List<Comment>comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Post other = (Post) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if ((this.body == null) ? (other.body != null) : !this.body.equals(other.body)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 17 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 17 * hash + (this.body != null ? this.body.hashCode() : 0);
        return hash;
    }

//    @Override
//    public String toString() {
//        return "Post{" + "id=" + id + ", title=" + title + ", slug=" + slug + ", body=" + body + ", author=" + author + '}';
//    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", version=" + version + ", title=" + title + ", slug=" + slug + ", body=" + body + ", posted=" + posted + ", author=" + author + ", comments=" + comments + '}';
    }
}
