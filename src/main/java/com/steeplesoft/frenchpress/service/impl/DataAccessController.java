/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.service.impl;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author jasonlee
 */
@ManagedBean
@SessionScoped
public class DataAccessController implements Serializable {

    @PersistenceContext(unitName = "em")
    protected EntityManager em;
    @Resource
    private UserTransaction utx;

    public <T> void create(T entity) {
        try {
            utx.begin();
            em.persist(entity);
            utx.commit();
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (SystemException ex) {
                //
            }
            throw new RuntimeException(e);
        }
    }

    public <T> void edit(T entity) {
        try {
            utx.begin();
            em.merge(entity);
            utx.commit();
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (SystemException ex) {
                //
            }
            throw new RuntimeException(e);
        }
    }

    public <T> void remove(T entity) {
        try {
            utx.begin();
            em.remove(em.merge(entity));
            utx.commit();
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (SystemException ex) {
                //
            }
            throw new RuntimeException(e);
        }
    }

    public <T> T find(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }

    public Query createQuery(String query) {
        return em.createNamedQuery(query);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(clazz));
        return em.createQuery(cq).getResultList();
    }

    public <T> List<T> findRange(Class<T> clazz, int begin, int end) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(clazz));
        Query q = em.createQuery(cq);
        q.setMaxResults(end - begin);
        q.setFirstResult(begin);
        return q.getResultList();
    }

    public <T> int count(Class<T> clazz) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(clazz);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}