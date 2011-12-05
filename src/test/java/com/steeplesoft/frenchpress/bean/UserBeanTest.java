/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.bean;

import com.steeplesoft.frenchpress.service.UserService;
import com.steeplesoft.frenchpress.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 * @author jdlee
 */
public class UserBeanTest {

    private EntityManagerFactory emf;
    private EntityManager testEm;
    private UserService userBean;

    public UserBeanTest() {
    }

    @BeforeClass
    public void setUpClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("testEm");
        testEm = emf.createEntityManager();
        userBean = new UserService() {{
            this.em = testEm;
        }};
        startTransaction();
        createUser("Dummy1", "Dummy", "dummy1@dummy.com");
        createUser("Dummy2", "Dummy", "dummy2@dummy.com");
        createUser("Dummy3", "Dummy", "dummy3@dummy.com");
        createUser("Dummy4", "Dummy", "dummy4@dummy.com");
        createUser("Dummy5", "Dummy", "dummy5@dummy.com");
        commitTransaction();
    }

    @AfterClass
    public void tearDownClass() throws Exception {
        if (testEm != null) {
            testEm.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void addUser() {
        startTransaction();

        User user = new User();
        user.setFirstName("Joe");
        user.setLastName("Blow");
        user.setEmailAddress("joe@blow.com");

        User newUser = userBean.createUser(user);
        User user2 = userBean.getUser(newUser.getId());
        assertNotNull(user2);
        assertEquals(user2, user);

        commitTransaction();
    }

    @Test
    public void getUsers() {
        List<User> users = userBean.getUsers();
        assertTrue(!users.isEmpty());
    }

    @Test
    public void deleteUser() {
        User user = createUser("Delete", "Me", "me@foo.com");
        assertNotNull(user);
        userBean.deleteUser(user);
        User newUser = userBean.getUser(user.getId());
        assertNull(newUser);
    }

    protected void startTransaction() {
        testEm.getTransaction().begin();
    }

    protected void commitTransaction() {
        testEm.flush();
        testEm.getTransaction().commit();
    }

    public User createUser(String firstName, String lastName, String emailAddress) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailAddress(emailAddress);

        return userBean.createUser(user);
    }
}
