package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.frenchpress.service.UserService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jdlee on 9/16/13.
 */
@RunWith(Arquillian.class)
@Category(IntegrationTests.class)
public class UserServiceTest extends AbstractServiceTestBase {
    @Inject
    protected UserService userService;

    @Test
    public void userCrud() {
        List<User> users = userService.getUsers();
        assertNotNull(users);

        User adminUser = testCreateUser("Administrator");
        User regularUser = testCreateUser("User");
        testUpdateUser(adminUser);
        testDeleteUser(adminUser, regularUser);
    }

    private void testDeleteUser(User... users) {
        for (User user : users) {
            userService.deleteUser(user);
            assertNull(userService.getUser(user.getId()));
        }
    }

    private void testUpdateUser(User user) {
        user.setEmailAddress("fake@example.com");
        userService.updateUser(user);

        User updated = userService.getUser(user.getId());
        assertEquals(user, updated);
    }

    private User testCreateUser(String role) {
        User user = new User();
        user.setFirstName("First");
        user.setLastName("Last");
        user.setPassword("" + generateRandomNumber());
        user.setEmailAddress("user" + generateRandomNumber() + "@example.com");
        user.setRole(role);
        userService.createUser(user);
        assertNotNull(user.getId());

        return user;
    }
}
