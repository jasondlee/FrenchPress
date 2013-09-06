/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.rest.resources;

import com.steeplesoft.frenchpress.model.Comment;
import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author jdlee
 */
@Path("posts")
public class PostResource {

    @Inject
    PostService postService;

    @GET
    public GenericEntity<List<Post>> getPosts(@QueryParam("limit") @DefaultValue("-1") int limit) {
        return new GenericEntity<List<Post>>(postService.getPosts(limit)) {};
    }

    @GET
    @Path("id/{id}")
    public Post getPost(@PathParam("id") Long id) {
        return postService.getPost(id);
    }

    @GET
    @Path("id/{id}/comments")
    public List<Comment> getPostComment(@PathParam("id") Long id) {
        final Post post = postService.getPost(id);
        if (post == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
        return post.getComments();
    }

    @POST
    public Post createPost(Post post) {
        if (post == null) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }
        postService.createPost(post);

        return post;
    }
}
