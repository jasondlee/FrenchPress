package com.steeplesoft.frenchpress.test;

import org.junit.After;
import org.junit.Before;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.Random;

/**
 * Created by jdlee on 9/13/13.
 */
public class AbstractServiceTestBase {
    @PersistenceContext
    protected EntityManager em;
    @Inject
    protected UserTransaction utx;

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
        utx.rollback();
    }

}
