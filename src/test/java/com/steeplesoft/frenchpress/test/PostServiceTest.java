/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.service.PostService;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author jdlee
 */
@RunWith(Arquillian.class)
@Ignore
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
