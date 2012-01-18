/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.MediaItem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MediaService {
    @PersistenceContext
    protected EntityManager em;

    public List<MediaItem> getItems() {
        return em.createQuery("SELECT mi from MediaItem mi", MediaItem.class)
                .getResultList();
    }
    
}
