/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.beans.PostBean;
import com.steeplesoft.frenchpress.beans.UserBean;
import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.frenchpress.service.TestResource;
import com.steeplesoft.frenchpress.service.UserService;
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
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author jdlee
 */
@RunWith(Arquillian.class)
public class UserBeanTest {

    @Inject
    UserBean userBean;

    @Deployment
    public static WebArchive createDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers.use(
                MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        return ShrinkWrap.create(WebArchive.class)
                .addPackage("org.ajax4jsf.model")
                .addPackages(true, "com.steeplesoft.frenchpress")
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/web.xml")), "web.xml")
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new FileAsset(new File("src/main/resources-glassfish-managed/META-INF/glassfish-resources.xml")),
                    "glassfish-resources.xml")
                .addAsLibraries(
                    resolver.artifacts("org.richfaces.ui:richfaces-components-ui",
                    "org.richfaces.core:richfaces-core-impl",
                    "org.atmosphere:atmosphere-runtime"
                ).resolveAsFiles())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public static Archive createBaseDeployment() {
        return createDeployment();
//                .addPackages(true, UserService.class.getPackage());
    }

    @Test
    public void testInjection() {
        Assert.assertNotNull(userBean);
    }
}
