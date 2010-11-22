package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.model.BlogEntry;
import com.steeplesoft.frenchpress.service.BlogService;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="blogService")
@SessionScoped
public class BlogServiceImpl implements BlogService {
    @PersistenceUnit(unitName="em")
    private EntityManagerFactory emf;

    @Override
    public BlogEntry getEntry(Long id) {
        return emf.createEntityManager().find(BlogEntry.class, id);
    }

    @Override
    public BlogEntry createBlogEntry(BlogEntry entry) {
        final EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entry);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(BlogServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        }
        return entry;
    }

    @Override
    public BlogEntry updateBlogEntry(BlogEntry entry) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            entry = em.merge(entry);
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            Logger.getLogger(BlogServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entry;
    }

    public List<BlogEntry> getMostRecentBlogEntries(int max) {
        EntityManager em = emf.createEntityManager();
        final List results = em.createNamedQuery("BlogEntry.findSticky").getResultList();
        int recentMax = max - results.size();
        if (recentMax <= max) {
            final Query recentQuery = em.createNamedQuery("BlogEntry.mostRecent");
            if (max >0) {
                recentQuery.setMaxResults(recentMax);
            }
            results.addAll(recentQuery.getResultList());
        }
        em.close();
        return results;
    }
}