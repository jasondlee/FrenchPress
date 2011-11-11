/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.User;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jdlee
 */
@Model
public class UserBean {
    @PersistenceContext
    protected EntityManager em;
    
    public List<User> getUsers() {
        return (List<User>)em.createQuery("SELECT u FROM User u").getResultList();
    }
    
    public User createUser(User user) {
        em.persist(user);
        return user;
    }
    
    public User getUser(Long id) {
        return em.find(User.class, id);
    }
    
    public void deleteUser(User user) {
        em.merge(user);
        em.remove(user);
    }
}
