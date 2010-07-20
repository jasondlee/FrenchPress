/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.persistence.Persistence;
import com.steeplesoft.jsf.facestester.injection.InjectionManager;
import com.steeplesoft.jsf.facestester.FacesTester;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jasonlee
 */
public class BlogBeanTest {
    private Connection connection;
    private EntityManagerFactory emFactory;
    private EntityManager em;

    private FacesTester ft;

    @Before
    public void setup() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:frenchpress", "sa", "");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during HSQL database startup.");
        }
        try {
            emFactory = Persistence.createEntityManagerFactory("testPU");
            em = emFactory.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during JPA EntityManager instanciation.");
        }
                
        InjectionManager.registerObject("em", em);
        ft = new FacesTester();
    }

    @Test
    public void test() {
        assertNotNull(ft);
    }
}