/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.Post;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author jdlee
 */
@Model
public class NewPostBean {
    @Inject
    private PostBean postBean;
    private Post post = new Post();

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
 
    public String save() {
        postBean.createPost(post);
        return "/admin/index";
    }
}
