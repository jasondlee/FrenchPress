package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.model.BlogEntry;
import com.steeplesoft.frenchpress.service.BlogService;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="blogService")
public class BlogServiceImpl implements BlogService {
    @PersistenceContext(unitName="em")
    private EntityManager em;

    @Override
    public List<BlogEntry> getMostRecentBlogEntries(int max) {
        Query query = em.createNamedQuery("BlogEntry.findAll");
        if (max != -1) {
            query.setMaxResults(max);
        };

        return (List<BlogEntry>)query.getResultList();
    }

}
