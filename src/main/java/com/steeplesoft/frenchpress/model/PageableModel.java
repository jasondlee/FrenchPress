/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.model;

import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

/**
 *
 * @author jdlee
 */
public abstract class PageableModel<T extends FpEntity> extends ExtendedDataModel<T> implements Serializable {
    @PersistenceUnit
    EntityManagerFactory emf;
    
    protected Class<T> entityClass;
    private Integer rowCount;
    private List<T> cachedItems;
    private Object rowKey;
    private SequenceRange cachedRanged;
    
    public PageableModel() {
        super();
    }

    @Override
    public void setRowKey(Object o) {
        this.rowKey = o;
    }

    @Override
    public Object getRowKey() {
        return rowKey;
    }

    @Override
    public void walk(FacesContext fc, DataVisitor dv, Range range, Object o) {
        SequenceRange sr = (SequenceRange)range;
        if (sr != null) {
            EntityManager em = emf.createEntityManager();
            int rows = sr.getRows();
            CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
            TypedQuery<T> query = em.createQuery(cq);
            query.setFirstResult(sr.getFirstRow());
            if (rows > 0) {
                query.setMaxResults(rows);
            }
            this.cachedRanged = sr;
            this.cachedItems = query.getResultList();
            
            em.close();
        }
        
        for (T item : cachedItems) {
            dv.process(fc, item.getId(), o);
        }
        
    }

    @Override
    public boolean isRowAvailable() {
        return (getRowData() != null) ;
    }

    @Override
    public int getRowCount() {
        if (rowCount == null) {
            EntityManager em = emf.createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            cq.select(cb.count(cq.from(entityClass)));
            rowCount = em.createQuery(cq).getSingleResult().intValue();
            em.close();
        }
        
        return rowCount;
    }

    @Override
    public T getRowData() {
        for (FpEntity item : cachedItems) {
            if (item.getId().equals(getRowKey())){
                return (T)item;
            }
        }
        return null;
    }

    @Override
    public int getRowIndex() {
        return -1;
    }

    @Override
    public void setRowIndex(int i) {
        
    }

    @Override
    public Object getWrappedData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setWrappedData(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
