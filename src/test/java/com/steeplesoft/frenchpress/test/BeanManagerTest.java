/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.test;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author jdlee
 */
@RunWith(Arquillian.class)
public class BeanManagerTest {
    @Inject
    private BeanManager beanManager;

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar") // archive name optional
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testCdiBootstrap() {
        assertNotNull(beanManager);
        assertFalse(beanManager.getBeans(BeanManager.class).isEmpty());
        printCdiImplementationInfo(beanManager);
    }

    protected void printCdiImplementationInfo(BeanManager beanManager) {
        String impl = beanManager.getClass().getPackage().getImplementationTitle();
        if (impl == null) {
            impl = beanManager.getClass().getPackage().getImplementationVendor();
        }
        if (impl != null) {
            System.out.println("CDI implementation: " + impl.replaceFirst("^([^ ]+)( .*)?$", "$1"));
        }
        else {
            System.out.println("Could not determine CDI implementation");
        }
        System.out.println("BeanManager implementation class: " + beanManager.getClass().getName());
   }
}
