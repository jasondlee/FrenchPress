/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.service.PreferencesService;
import com.steeplesoft.frenchpress.model.Preference;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="prefsService")
@SessionScoped
public class PreferencesServiceImpl implements PreferencesService, Serializable {
    @PersistenceUnit(unitName="em")
    protected EntityManagerFactory emf;

    public PreferencesServiceImpl() {
    }

    @Override
    public Map<String, Preference> getPreferences() {
        EntityManager em = emf.createEntityManager();
        Map<String, Preference> prefs = new ConcurrentHashMap<String, Preference>();
        Query query = em.createQuery("SELECT p from Preference p");
        List<Preference> list = query.getResultList();
        for (Preference pref : list) {
            prefs.put(pref.getName(), pref);
        }
        return prefs;
    }

    @Override
    public void savePreferences(Map<String, Preference> prefs) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (Preference pref : prefs.values()) {
            em.merge(pref);
            em.persist(pref);
        }
        em.flush();
        em.getTransaction().commit();
        em.close();
    }
}