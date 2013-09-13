/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.model.Post;
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

}
