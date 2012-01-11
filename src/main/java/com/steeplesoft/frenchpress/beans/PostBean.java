/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.Comment;
import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import org.icefaces.ace.component.datatable.DataTable;
/**
 *
 * @author jdlee
 */
@Model
public class PostBean implements Serializable {
    public static final String VIEW_ADMIN_POSTS_INDEX = "/admin/posts/index.xhtml?faces-redirect=true";

    @Inject
    private PostService postService;
    private Post post = new Post();
    private DataTable dataTable;
    private Comment comment = new Comment();

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Post> getPosts(int limit) {
        if (!FacesContext.getCurrentInstance().getRenderResponse()) {
            return null;
        }
        
        return postService.getPosts(limit);
    }

    public void loadPost() {
        Post requestedPost = (Post) ((ServletRequest)FacesContext.getCurrentInstance()
                .getExternalContext().getRequest()).getAttribute("post");
        post = (requestedPost != null) ? requestedPost : postService.getPost(post.getId());
    }

    public String update() {
        postService.updatePost(post);
        return VIEW_ADMIN_POSTS_INDEX;
    }

    public String save() {
        postService.createPost(post);
        return VIEW_ADMIN_POSTS_INDEX;
    }

    public String delete() {
        Post post = (Post) dataTable.getRowData();
        postService.deletePost(post);

        return null;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    
    public String addComment() {
        loadPost();
        comment.setPost(post);
        post.getComments().add(comment);
        postService.updatePost(post);
        comment = new Comment();
        
        return null;
    }
    
    public void format(ComponentSystemEvent event) throws AbortProcessingException {
        UIComponent uic = event.getComponent();
        if (uic instanceof UIOutput) {
            UIOutput output = (UIOutput)uic;
            String value = (String)output.getValue();
            value = value.replaceAll("\\n", "\n<br/>");
            output.setValue(value);
        }
    }
}