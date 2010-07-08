/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.Meeting;
import com.steeplesoft.frenchpress.service.MeetingService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="meetingBean")
@SessionScoped
public class MeetingBean extends ControllerBean {
    public static final String NAV_ADD  = "/admin/meetings/form";
    public static final String NAV_EDIT = "/admin/meetings/form";
    public static final String NAV_LIST = "/admin/meetings/list";
    public static final String NAV_VIEW = "/admin/meetings/view";

    public MeetingBean() {
        setNavigationIds(NAV_ADD, NAV_EDIT, NAV_LIST, NAV_VIEW);
    }

    @ManagedProperty("#{meetingService}")
    private MeetingService meetingService;
    private Meeting nextMeeting = null;

    public Meeting getNextMeeting() {
        if (nextMeeting == null) {
            nextMeeting = meetingService.getUpcomingMeeting();
        }

        return nextMeeting;
    }

    public MeetingService getMeetingService() {
        return meetingService;
    }

    public void setMeetingService(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @Override
    public Class getEntityClass() {
        return Meeting.class;
    }

    @Override
    public String edit() {
        nextMeeting = null;
        return super.edit();
    }
}