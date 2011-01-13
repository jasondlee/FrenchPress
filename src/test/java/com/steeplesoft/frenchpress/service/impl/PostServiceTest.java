package com.steeplesoft.frenchpress.service.impl;

import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.frenchpress.service.PostService;
import com.steeplesoft.frenchpress.service.Transactional;
import com.steeplesoft.frenchpress.service.TransactionalInterceptor;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.*;

/**
 *
 * @author jasonlee
 */
@RunWith(Arquillian.class)
public class PostServiceTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "frenchpress.war")
                .addClasses(Post.class, User.class, PostService.class, PostServiceImpl.class, TransactionalInterceptor.class, Transactional.class)
                .addManifestResource("test-persistence.xml", "persistence.xml")
                .addWebResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
//                .addWebResource("beans.xml");
    }
    
    @Inject
    PostServiceImpl postService;

    @Test
    public void temp() {
        System.out.println("TEMP!");
        assertNotNull(postService);
    }
}
