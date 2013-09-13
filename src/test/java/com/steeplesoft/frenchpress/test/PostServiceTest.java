/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.model.Comment;
import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.frenchpress.service.PostService;
import java.util.List;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;


/**
 *
 * @author jdlee
 */
@RunWith(Arquillian.class)
public class PostServiceTest {
    @Inject
    private PostService postService;

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "postServiceTest.war")
//                .addAsWebInfResource("jbossas-ds.xml")
//                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addPackages(true, "com.steeplesoft.frenchpress")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
//                .addClasses(Post.class, User.class, Comment.class, PostService.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testGetPosts() {
        List<Post> posts = postService.getPosts(-1);
        assertNotNull(posts);
        System.out.println("\n\n\n\n\n\n\n" + posts.toString() + "\n\n\n\n\n\n\n");
    }

}
