package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.frenchpress.service.PostService;
import com.steeplesoft.frenchpress.service.Transactional;
import com.steeplesoft.frenchpress.service.TransactionalInterceptor;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

import static org.testng.Assert.*;

/**
 *
 * @author jasonlee
 */
public class PostServiceTest extends Arquillian {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Post.class, User.class, PostService.class, PostServiceImpl.class, TransactionalInterceptor.class, Transactional.class)
                .addManifestResource("test-persistence.xml", "persistence.xml")
                .addWebResource(new File("src/main/webapp/WEB-INF/beans.xml"), "beans.xml");
    }
    
    @Inject
    PostServiceImpl postService;

    @Test
    public void temp() {
        System.out.println("TEMP!");
        assertNotNull(postService);
    }

    @Test
    public void entityManagerInjected() {
        System.out.println("postService.em = " + postService.em);
        assertNotNull(postService.em);
    }

    @Test
    public void mostRecentEntries() {
        List<Post> mostRecentPosts = postService.getMostRecentPosts(5);
        assertNotNull(mostRecentPosts);
        System.out.println("****** " + mostRecentPosts);
    }
}
