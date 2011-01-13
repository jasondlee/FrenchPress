package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import com.steeplesoft.frenchpress.service.Transactional;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jasonlee
 */
@Model
public class PostServiceImpl implements PostService, Serializable {
    @PersistenceContext(unitName="em")
//    @Inject
    protected EntityManager em;

    @Override
    public Post getPost(Long id) {
        return em.find(Post.class, id);
    }

    @Override
    @Transactional
    public Post createPost(Post entry) {
        em.persist(entry);
        return entry;
    }

    @Override
    @Transactional
    public Post updatePost(Post entry) {
        entry = em.merge(entry);
        return entry;
    }

    public List<Post> getMostRecentPosts(int max) {
        final List results = em.createNamedQuery("Post.findSticky").getResultList();
        int recentMax = max - results.size();
        if (recentMax <= max) {
            final Query recentQuery = em.createNamedQuery("Post.mostRecent");
            if (max > 0) {
                recentQuery.setMaxResults(recentMax);
            }
            results.addAll(recentQuery.getResultList());
        }
        return results;
    }
}