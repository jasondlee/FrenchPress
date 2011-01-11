/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import java.io.Serializable;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.faces.context.FacesContext;

/**
 *
 * @author jasonlee
 */
@Model
public class PostBean implements Serializable {
    @Inject
    protected PostService postService;

    protected Post selected = new Post();

    @Inject
    private FacesContext facesContext;
    
    public List<Post> getEntryList() {
        List<Post> posts = postService.getMostRecentPosts(-1);
        return posts;
    }

    public List<Post> getLimitedEntryList(int max) {
        final List<Post> entries = postService.getMostRecentPosts(max);
        return entries;
    }

    public Post getSelected() {
        return selected;
    }

    public void setSelected(Post selected) {
        this.selected = selected;
    }

    public String create() {
        postService.createPost(selected);
        return "list?faces-redirect=true";
    }

    public String update() {
        postService.updatePost(selected);
        return "list?faces-redirect=true";
    }

    @PostConstruct
    public void loadPost() {
        String id = (facesContext.isPostback() ?
            facesContext.getExternalContext().getRequestParameterMap().get("postForm:id") :
            facesContext.getExternalContext().getRequestParameterMap().get("id"));

        if ((id != null) && !id.isEmpty()) {
            try {
                selected = postService.getPost(Long.parseLong(id));
            } catch (NumberFormatException nfe) {
                facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, "/admin");
            }
        }
    }
}