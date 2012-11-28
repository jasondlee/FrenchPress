/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.beans.PostBean;
import com.steeplesoft.frenchpress.beans.UserBean;
import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import java.io.File;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author jdlee
 */
@RunWith(Arquillian.class)
public class PostServiceTest {
    @Inject
    PostService postService;
    
    @Deployment
    public static Archive createDeployment() {
        return UserBeanTest.createDeployment();
//                .addClasses(PostService.class)
//                .addPackages(true, Post.class.getPackage().toString());
    }

    @Test
    public void testInjection() {
        Assert.assertNotNull(postService);
    }

}
