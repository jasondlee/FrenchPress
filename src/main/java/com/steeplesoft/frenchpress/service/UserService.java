/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jdlee
 */
@Stateless
@Named
public class UserService {
    @PersistenceContext
    protected EntityManager em;

    @Inject
    PostService postService;
    
    public List<User> getUsers() {
        return (List<User>)em.createQuery("SELECT u FROM User u ORDER BY u.firstName, u.lastName").getResultList();
    }

    public List<User> getUsersForRole(String role) {
        return (List<User>)em.createQuery("SELECT u FROM User u WHERE u.userRole = :role")
                .setParameter("role", role)
                .getResultList();
    }
    
    public void createUser(User user) {
        em.persist(user);
    }
    
    public User getUser(Long id) {
        return em.find(User.class, id);
    }
    
    public void deleteUser(User user) {
        List<User> admins = getUsersForRole("Administrator");
        if (!admins.isEmpty()) {
            User admin = admins.get(0);
//            postService.migratePostsToUser
        }

        em.merge(user);
        em.remove(user);
    }
    
    public void updateUser(User user) {
        em.merge(user);
    }
}
