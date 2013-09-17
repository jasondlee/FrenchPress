package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.service.PostService;
import com.steeplesoft.frenchpress.service.UserService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.File;
import java.util.Random;

/**
 * Created by jdlee on 9/13/13.
 */
public class AbstractServiceTestBase {
    @PersistenceContext
    protected EntityManager em;
    @Inject
    protected UserTransaction utx;

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.steeplesoft.frenchpress")
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsResource(new FileAsset(new File("src/main/webapp/images/fp_logo_32.png")), "image.png")
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/web.xml")), "web.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    protected static int generateRandomNumber() {
        Random r = new Random();
        return Math.abs(r.nextInt()) + 1;
    }

    @Before
    public void preparePersistenceTest() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    @After
    public void commitTransaction() throws Exception {
        utx.commit();
    }
}
