/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import java.io.Serializable;

import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jasonlee
 */
@Named
@SessionScoped
public class PostBean implements Serializable {
    @Inject
    protected PostService postService;
    protected Post selected = new Post();
    
    public List<Post> getEntryList() {
        return postService.getMostRecentPosts(-1);
    }

    public List<Post> getLimitedEntryList(int max) {
        final List<Post> entries = postService.getMostRecentPosts(max);
        return entries;
    }

    public PostService getBlogService() {
        return postService;
    }

    public void setBlogService(PostService blogService) {
        this.postService = blogService;
    }

    public Post getSelected() {
        return selected;
    }

    public void setSelected(Post selected) {
        this.selected = selected;
    }

    public String createEntry() {
        selected = new Post();
        return "form?faces-redirect=true";
    }
    public String editEntry() {
//        selected = (BlogEntry) dataTable.getRowData();
        return "form?faces-redirect=true";
    }

    public String create() {
        postService.createPost(selected);
        return "list?faces-redirect=true";
    }

    public String update() {
        postService.updatePost(selected);
        return "list?faces-redirect=true";
    }
}