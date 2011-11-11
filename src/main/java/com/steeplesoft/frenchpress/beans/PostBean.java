/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.Post;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author jdlee
 */
@Model
public class PostBean {
    @PersistenceContext
    protected EntityManager em;
    
    public List<Post> getPosts(int limit) {
        final TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p", Post.class);
        if (limit > -1) {
            query.setMaxResults(limit);
        }
        return query.getResultList();
    }
    
    public void createPost(Post post) {
        em.persist(post);
    }
}
