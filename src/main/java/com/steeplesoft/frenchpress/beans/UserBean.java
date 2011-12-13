/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.frenchpress.service.UserService;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.icefaces.ace.component.datatable.DataTable;

/**
 *
 * @author jdlee
 */
@Model
public class UserBean {
    @Inject
    UserService userService;
    private DataTable dataTable;
    private User user = new User();
    
    public List<User> getUsers() {
        return userService.getUsers();
    }
    
    public void loadUser() {
        user = userService.getUser(user.getId());
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String save() {
        userService.createUser(user);
        return "/admin/users/index?faces-redirect=true";
    }
    
    public String update() {
        userService.updateUser(user);
        return "/admin/users/index?faces-redirect=true";
    }
    
    public String delete() {
        userService.deleteUser(user);
        return null;
    }
}
