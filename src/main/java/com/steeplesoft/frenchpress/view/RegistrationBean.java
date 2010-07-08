/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.Meeting;
import com.steeplesoft.frenchpress.model.Registration;
import com.steeplesoft.frenchpress.service.MainService;
import com.steeplesoft.frenchpress.service.MeetingService;
import com.steeplesoft.frenchpress.view.util.JsfUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * @author jasonlee
 */
@ManagedBean(name="registrationBean")
@RequestScoped
public class RegistrationBean implements Serializable {

    @ManagedProperty("#{meetingService}")
    private MeetingService meetingService;

    @ManagedProperty("#{mainService}")
    private MainService mainService;

    @ManagedProperty(value = "#{param.meetingId}")
    private Long meetingId;

    private Registration registration = new Registration();

    private Meeting meeting;
    private String captchaText;

    public Meeting getMeeting() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();

        if (meetingId != null) {
            meeting = meetingService.getMeeting(meetingId);
        } else {
            facesContext.responseComplete();
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, "*", "/home");
            //getExternalContext().redirect("/home");
        }
        return meeting;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public String saveRegistration() {
        registration.setMeeting(getMeeting());
        registration = mainService.saveRegistration(registration);
        if (registration == null) {
            return null;
        } else {
            JsfUtil.addSuccessMessage(registration.getFullName() + " has been successfully registered for the meeting.");
            return "/home?faces-redirect=true";
        }
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }
}
