/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.service.impl;

import com.steeplesoft.frenchpress.service.*;
import com.steeplesoft.frenchpress.model.Meeting;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name = "meetingService")
public class MeetingServiceImpl implements MeetingService, Serializable {

    @PersistenceContext(unitName = "em")
    private EntityManager em;
    private Meeting meeting;

    @Override
    public Meeting getUpcomingMeeting() {
        try {
            if (meeting == null) {
                List<Meeting> list = (List<Meeting>) em.createNamedQuery("nextMeeting").getResultList();
                if (list.size() > 0) {
                    meeting = list.get(0);
                }
            }
            return meeting;
        } catch (NoResultException nre) {
        }
        return null;

    }

    @Override
    public Meeting getMeeting(Long meetingId) {
        return (Meeting) em.createNamedQuery("getMeeting").setParameter("meetingId", meetingId).getSingleResult();
    }
}
