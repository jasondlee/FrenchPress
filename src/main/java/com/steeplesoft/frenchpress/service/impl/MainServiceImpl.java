/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.model.BlogEntry;
import com.steeplesoft.frenchpress.model.GroupMember;
import com.steeplesoft.frenchpress.model.Registration;
import com.steeplesoft.frenchpress.model.Sponsor;
import com.steeplesoft.frenchpress.service.MainService;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;

/**
 * This will be renamed once I can brand this beast. :)
 *
 * @author jasonlee
 */
@ManagedBean(name="mainService")
@SessionScoped
public class MainServiceImpl implements MainService {
    @Resource
    private UserTransaction txn;
    
    @PersistenceContext(unitName="em")//(name = "em")
    private EntityManager em;

    public GroupMember getMember(Long id) {
        return em.find(GroupMember.class, id);
    }

    @Override
    public Registration saveRegistration(Registration reg) {
        try {
            txn.begin();
            if (!em.contains(reg)) {
                reg = em.merge(reg);
            }
            em.persist(reg);
            em.flush();
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

    public Long saveMember(GroupMember member) {
        if (!em.contains(member)) {
            member = em.merge(member);
        }
        em.persist(member);
        em.flush();
        return member.getId();
    }

    public List<GroupMember> getMembers() {
        return em.createNamedQuery("GroupMember.findAll").getResultList();
    }

    @Override
    public Sponsor getRandomSponsor() {
        Sponsor sponsor = null;
        List<Sponsor> sponsors = em.createNamedQuery("Sponsor.activeSponsors").getResultList();
        if ((sponsors != null) && (sponsors.size() > 0)) {
            Double index = Math.floor(Math.random() * (sponsors.size()-1));
            sponsor = sponsors.get(index.intValue());
        }

        return sponsor;
    }

    public List<BlogEntry> getMostRecentBlogEntries(int max) {
        final List results = em.createNamedQuery("BlogEntry.findSticky").setMaxResults(max).getResultList();
        int recentMax = max - results.size();
        if (recentMax <= max) {
            final List recentQuery = em.createNamedQuery("BlogEntry.mostRecent").setMaxResults(recentMax).getResultList();
            results.addAll(recentQuery);
        }
        return results;
    }
}
