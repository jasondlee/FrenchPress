/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.model.BlogEntry;
import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.frenchpress.model.Registration;
import com.steeplesoft.frenchpress.model.Sponsor;
import com.steeplesoft.frenchpress.service.MainService;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * This will be renamed once I can brand this beast. :)
 *
 * @author jasonlee
 */
@ManagedBean(name="mainService")
//@SessionScoped
public class MainServiceImpl implements MainService {
    @Resource
    private UserTransaction txn;
    
    @PersistenceUnit(unitName="em")
    private EntityManagerFactory emf;

    public User getMember(Long id) {
        return emf.createEntityManager().find(User.class, id);
    }

    /*
    @Override
    public Registration saveRegistration(Registration reg) {
        try {
            txn.begin();
            EntityManager em = emf.createEntityManager();
            if (!em.contains(reg)) {
                reg = em.merge(reg);
            }
            em.persist(reg);
            em.close();
            txn.commit();
            return reg;
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            try {
                txn.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public Sponsor getRandomSponsor() {
        Sponsor sponsor = null;
        EntityManager em = emf.createEntityManager();
        List<Sponsor> sponsors = em.createNamedQuery("Sponsor.activeSponsors").getResultList();
        if ((sponsors != null) && (sponsors.size() > 0)) {
            Double index = Math.floor(Math.random() * (sponsors.size()-1));
            sponsor = sponsors.get(index.intValue());
        }
        em.close();

        return sponsor;
    }
    */

    public Long saveMember(User member) {
        try {
            txn.begin();
            EntityManager em = emf.createEntityManager();
            if (!em.contains(member)) {
                member = em.merge(member);
            }
            em.persist(member);
            em.close();
            txn.commit();
        } catch (Exception ex) {
            Logger.getLogger(MainServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return member.getId();
    }

    public List<User> getMembers() {
        return emf.createEntityManager().createNamedQuery("GroupMember.findAll").getResultList();
    }
}