/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.MediaItem;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Stateless
@Path("/uploads")
public class MediaService {
    @PersistenceContext
//    @Inject
    protected EntityManager em;
    
    @GET
    @Path("{id}/")
    public MediaItem getItem(@PathParam("id") Long id) {
        return em.find(MediaItem.class, id);
    }

    @GET
    @Path("{year}/{month}/{name}")
    public MediaItem getItem(
            @PathParam("year") int year, 
            @PathParam("month") int month, 
            @PathParam("name") String name
            ) {
        Calendar startDate = new GregorianCalendar(year, month-1, 1);
        Calendar endDate = new GregorianCalendar(year, month-1, startDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        TypedQuery<MediaItem> query = em.createQuery(
                "SELECT mi from MediaItem mi WHERE mi.uploadedDate >= :START AND mi.uploadedDate <= :END AND mi.name = :NAME",
                MediaItem.class);
        query.setParameter("START", startDate.getTime());
        query.setParameter("END", endDate.getTime());
        query.setParameter("NAME", name);
        return query.getSingleResult();
    }

    public List<MediaItem> getItems() {
        return em.createQuery("SELECT mi from MediaItem mi", MediaItem.class)
                .getResultList();
    }
    
    public void addItem(MediaItem item) {
        em.persist(item);
    }
    
}
