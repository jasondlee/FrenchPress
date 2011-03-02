package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author jasonlee
 */
@Model
public class PostBean {
    @Inject
    private PostService postService;
    private Post selected = new Post();
    private String postId = null;
    
    @PostConstruct
    public void postConstruct() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if ((id != null) && (!id.isEmpty())) {
            setPostId(id); 
        }
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
        selected = postService.getPost(Long.parseLong(postId));
    }
    
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    public Post getSelected() {
        return selected;
    }

    public void setSelected(Post selected) {
        this.selected = selected;
    }
    
    public String create() {
        postService.create(selected);
        selected = new Post();
        return "/admin/posts/index";
    }
    
    public String update() {
        postService.update(selected);
        return "/admin/posts/index";
    }
}