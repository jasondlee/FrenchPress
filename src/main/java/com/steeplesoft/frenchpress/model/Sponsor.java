/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author jasonlee
 */
@Entity
@Table(name = "sponsors")
@NamedQueries({
    @NamedQuery(name = "Sponsor.findAll", query = "SELECT s FROM Sponsor s"),
    @NamedQuery(name = "Sponsor.findById", query = "SELECT s FROM Sponsor s WHERE s.id = :id"),
    @NamedQuery(name = "Sponsor.findByName", query = "SELECT s FROM Sponsor s WHERE s.name = :name"),
    @NamedQuery(name = "Sponsor.findByEmail", query = "SELECT s FROM Sponsor s WHERE s.email = :email"),
    @NamedQuery(name = "Sponsor.findByContactPerson", query = "SELECT s FROM Sponsor s WHERE s.contactPerson = :contactPerson"),
    @NamedQuery(name = "Sponsor.activeSponsors", query = "SELECT s FROM Sponsor s WHERE s.active = true")
})
public class Sponsor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 255)
    @NotEmpty
    private String name;

    @Column
    @NotEmpty
    private String homePage;

    @Column(name = "email", length = 255)
    @Email
    private String email;

    @Column(name = "contactPerson", length = 255)
    @NotEmpty
    private String contactPerson;

    @Column(name = "logoUrl")
    private String logoUrl;

    @Column()
    private Boolean active = Boolean.TRUE;

    public Sponsor() {
    }

    public Sponsor(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        if (!(object instanceof Sponsor)) {
            return false;
        }
        Sponsor other = (Sponsor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.steeplesoft.frenchpress.model.Sponsor[id=" + id + ", name=" + name + "]";
    }
}
