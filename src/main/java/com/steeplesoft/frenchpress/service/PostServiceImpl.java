package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.txn.Transactional;
import com.steeplesoft.frenchpress.model.Post;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jasonlee
 */
@Named("postService")
@RequestScoped
public class PostServiceImpl implements PostService {
    @PersistenceContext//(unitName="em")
    EntityManager em;

    @Override
    public Post getPost(Long id) {
        return em.find(Post.class, id);
    }
    
    @Override
    public List<Post> getPosts() {
        final Query query = em.createQuery("select p from Post p");
        return query.getResultList();
    }

    @Override
    @Transactional
    public void create(Post post) {
        em.persist(post);
    }

    @Override
    @Transactional
    public void update(Post post) {
        em.merge(post);
    }
    
    
}