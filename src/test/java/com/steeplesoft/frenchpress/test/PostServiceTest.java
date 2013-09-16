/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * @author jdlee
 */
@RunWith(Arquillian.class)
@Category(IntegrationTests.class)
public class PostServiceTest extends AbstractServiceTestBase {
    @Inject
    private PostService postService;

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "postServiceTest.war")
                .addPackages(true, "com.steeplesoft.frenchpress")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testGetPosts() {
        List<Post> posts = postService.getPosts(-1);
        assertNotNull(posts);
    }

    @Test
    public void testCreatePost() {
        Post post = createPost();
        postService.createPost(post);
        assertNotNull(post.getId());

        Post newPost = postService.getPost(post.getId());
        assertNotNull(newPost);
    }

    @Test
    public void testDeletePost() {
        Post post = createPost();
        postService.createPost(post);
        Long id = post.getId();
        assertNotNull(id);

        postService.deletePost(post);
        post = postService.getPost(id);
        assertNull(post);
    }

    @Test
    public void testUpdatePost() {
        Post post = createPost();
        postService.createPost(post);
        Long id = post.getId();
        assertNotNull(id);

        post.setBody("This is an updated body");
        Post updatedPost = postService.updatePost(post);
        assertEquals(post.getBody(), updatedPost.getBody());
        Post anotherPost = postService.getPost(post.getId());
        assertEquals(post.getBody(), anotherPost.getBody());
    }

    @Test
    public void testFindingBySlug() {
        Post post = createPost();
        postService.createPost(post);
        Post post2 = postService.findPostBySlug(post.getSlug());
        assertEquals(post, post2);
        postService.deletePost(post);
    }

    private Post createPost() {
        Post post = new Post();
        int rand = generateRandomNumber();
        post.setTitle("Arquillian Post - " + rand);
        post.setBody("Arquillian Body");
        post.setSlug("arquillian-post-" + rand);
        post.setPosted(new Date());

        return post;
    }
}
