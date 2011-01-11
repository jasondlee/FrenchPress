package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import com.steeplesoft.frenchpress.service.Transactional;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author jasonlee
 */
@Model
public class PostServiceImpl implements PostService, Serializable {
    @PersistenceUnit
//    @Inject
    private EntityManager em;

    @Override
    public Post getPost(Long id) {
        return em.find(Post.class, id);
    }

    @Override
    @Transactional
    public Post createPost(Post entry) {
//        final EntityManager em = emf.createEntityManager();
        em.persist(entry);
        em.close();
        return entry;
    }

    @Override
    @Transactional
    public Post updatePost(Post entry) {
//        EntityManager em = emf.createEntityManager();
        entry = em.merge(entry);
        em.close();
        return entry;
    }

    public List<Post> getMostRecentPosts(int max) {
//        EntityManager em = emf.createEntityManager();
        final List results = em.createNamedQuery("Post.findSticky").getResultList();
        int recentMax = max - results.size();
        if (recentMax <= max) {
            final Query recentQuery = em.createNamedQuery("Post.mostRecent");
            if (max > 0) {
                recentQuery.setMaxResults(recentMax);
            }
            results.addAll(recentQuery.getResultList());
        }
        em.close();
        return results;
    }
}
