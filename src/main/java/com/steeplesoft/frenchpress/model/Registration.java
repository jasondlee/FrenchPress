/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.model;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author jasonlee
 */
@Entity
@Table(name = "registrations")
@NamedQueries({
        @NamedQuery(name = "Registration.findAll", query = "SELECT r FROM Registration r"),
        @NamedQuery(name = "Registration.findById", query = "SELECT r FROM Registration r WHERE r.id = :id"),
        @NamedQuery(name = "Registration.findByEmailAddress", query = "SELECT r FROM Registration r WHERE r.emailAddress = :emailAddress"),
        @NamedQuery(name = "Registration.findByFullName", query = "SELECT r FROM Registration r WHERE r.fullName = :fullName")
})
public class Registration implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    @Column(name = "emailAddress", length = 255)
    @NotEmpty
    private String emailAddress;

    @Column(name = "fullName", length = 255)
    @NotEmpty
    private String fullName;

    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    @ManyToOne
    private Meeting meeting;
    
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne
    private User memberId;

    public Registration() {
    }

    public Registration(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public User getMemberId() {
        return memberId;
    }

    public void setMemberId(User memberId) {
        this.memberId = memberId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.steeplesoft.frenchpress.model.Registration[id=" + id + "]";
    }

}
