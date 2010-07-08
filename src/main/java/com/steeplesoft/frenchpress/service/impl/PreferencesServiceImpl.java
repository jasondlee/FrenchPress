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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="prefsService")
@ApplicationScoped
public class PreferencesServiceImpl implements PreferencesService, Serializable {
    @PersistenceContext(unitName="em")
    protected EntityManager em;

    public PreferencesServiceImpl() {
    }

    @Override
    public Map<String, Preference> getPreferences() {
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