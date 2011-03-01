package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author jasonlee
 */
@ManagedBean
@RequestScoped
public class PostBean {
    @EJB
    private PostService postService;
    private Post selected = new Post();
    
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