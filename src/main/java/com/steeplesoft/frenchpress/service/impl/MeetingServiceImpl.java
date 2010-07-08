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

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="meetingService")
@ApplicationScoped
public class MeetingServiceImpl implements MeetingService, Serializable {
    @PersistenceContext(name = "em")
    private EntityManager em;

    @Override
    public Meeting getUpcomingMeeting() {
        try {
            Meeting meeting = (Meeting) em.createNamedQuery("nextMeeting").getResultList().get(0);
            System.err.println("Meeting = " + meeting);
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
