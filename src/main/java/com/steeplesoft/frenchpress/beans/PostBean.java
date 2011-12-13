/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.service.PostService;
import com.steeplesoft.frenchpress.model.Post;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import org.icefaces.ace.component.datatable.DataTable;
/**
 *
 * @author jdlee
 */
@Model
//@SessionScoped
//@Named
public class PostBean implements Serializable {

    @Inject
    private PostService postService;
    private Post post = new Post();
    private DataTable dataTable;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Post> getPosts(int limit) {
        return postService.getPosts(limit);
    }

    public void loadPost() {
        post = postService.getPost(post.getId());
    }

    public String update() {
        /*
         * Post savedPost = postService.getPost(post.getId()); if (savedPost ==
         * null) { throw new RuntimeException("Post not found. Id: " +
         * post.getId()); } else { if (savedPost.getVersion() >
         * post.getVersion()) { // throw new MidErrorCollisionException(); } else
         * {
         */
        postService.updatePost(post);
        return "/admin/posts?faces-redirect=true";
        /*
         * }
         * }
         *
         * return null;
         */
    }

    public String save() {
        postService.createPost(post);
        return "/admin/posts?faces-redirect=true";
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
}