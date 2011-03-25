/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.service.impl;

import java.util.List;
import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.frenchpress.model.Post;
import org.jboss.shrinkwrap.api.ArchivePaths;
import java.io.File;
import com.steeplesoft.frenchpress.txn.Transactional;
import com.steeplesoft.frenchpress.txn.TransactionalInterceptor;
import com.steeplesoft.frenchpress.service.PostServiceImpl;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import com.steeplesoft.frenchpress.service.PostService;
import javax.inject.Inject;
import org.jboss.arquillian.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author jasonlee
 */
@RunWith(Arquillian.class)
public class PostServiceArquillianTest {
    @Inject
    private PostService postService;

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"), "beans.xml")
                .addClasses(Post.class, User.class, PostService.class, PostServiceImpl.class, TransactionalInterceptor.class, Transactional.class)
                .addAsManifestResource("META-INF/persistence.xml", ArchivePaths.create("persistence.xml"))
                ;
    }

    @Test
    public void temp() {
        System.out.println("***** TEMP!");
        assertNotNull(postService);
    }
    
    @Test
    public void getPost() {
        List<Post> list = postService.getPosts();
        assertNotNull(list);
    }
    
    @Test
    public void createAndUpdatePost() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setBody("This is a test body.");
        
        postService.create(post);
        assertNotNull(post.getId());

        post.setTitle("Updated Test Post");
        postService.update(post);
        
        Post newPost = postService.getPost(post.getId());
        assertEquals("Updated Test Post", newPost.getTitle());
    }
}