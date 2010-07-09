/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.view.DummyBean;
import javax.inject.Inject;
import org.junit.Test;

/**
 *
 * @author jasonlee
 */
//@RunWith(Arquillian.class)
public class SimpleTest {
    @Inject
    DummyBean dummy;

//    @Deployment
//    public static Archive<?> createTestArchive() {
//        return ShrinkWrap.create("test.jar", JavaArchive.class)
//                .addClasses(DummyBean.class, SimpleTest.class);
//    }

    @Test
    public void testDummy() {
//        Assert.assertNotNull(dummy);
    }

    @Test
    public void dummy() {

    }
}
