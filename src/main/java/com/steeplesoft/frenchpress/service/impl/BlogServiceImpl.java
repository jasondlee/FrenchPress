package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.model.BlogEntry;
import com.steeplesoft.frenchpress.service.BlogService;
import com.steeplesoft.frenchpress.service.Transactional;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author jasonlee
 */
@Model
public class BlogServiceImpl implements BlogService, Serializable {
//    @PersistenceUnit
    @Inject
    private EntityManagerFactory emf;

    @Override
    public BlogEntry getEntry(Long id) {
        return emf.createEntityManager().find(BlogEntry.class, id);
    }

    @Override
    @Transactional
    public BlogEntry createBlogEntry(BlogEntry entry) {
        final EntityManager em = emf.createEntityManager();
        em.persist(entry);
        return entry;
    }

    @Override
    @Transactional
    public BlogEntry updateBlogEntry(BlogEntry entry) {
        EntityManager em = emf.createEntityManager();
        entry = em.merge(entry);
        em.close();
        return entry;
    }

    public List<BlogEntry> getMostRecentBlogEntries(int max) {
        EntityManager em = emf.createEntityManager();
        final List results = em.createNamedQuery("BlogEntry.findSticky").getResultList();
        int recentMax = max - results.size();
        if (recentMax <= max) {
            final Query recentQuery = em.createNamedQuery("BlogEntry.mostRecent");
            if (max > 0) {
                recentQuery.setMaxResults(recentMax);
            }
            results.addAll(recentQuery.getResultList());
        }
        em.close();
        return results;
    }
}