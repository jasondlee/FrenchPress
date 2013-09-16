package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.frenchpress.service.UserService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jdlee on 9/16/13.
 */
@RunWith(Arquillian.class)
@Category(IntegrationTests.class)
public class UserServiceTest extends AbstractServiceTestBase {
    @Inject
    protected UserService userService;

    @Test
    public void getUsers() {
        List<User> users = userService.getUsers();
        Assert.assertNotNull(users);
        System.out.println(users.toString());
    }
}
