/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author jdlee
 */
@Model
public class UserBean {
    @Inject
    UserService userService;
    
    public List<User> getUsers() {
        return userService.getUsers();
    }
    
    public List<SelectItem> getUsers1() {
        List<SelectItem> si = new ArrayList<SelectItem>();
        for (User user : userService.getUsers()) {
            si.add(new SelectItem(user, user.getFirstName() + " " + user.getLastName()));
        }
        
        return si;
    }
}
