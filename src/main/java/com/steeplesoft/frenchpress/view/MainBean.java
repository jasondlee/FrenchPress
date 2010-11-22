/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.BlogEntry;
import com.steeplesoft.frenchpress.model.Preference;
import com.steeplesoft.frenchpress.model.Sponsor;
import com.steeplesoft.frenchpress.service.MainService;
import com.steeplesoft.frenchpress.service.PreferencesService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="main")
@RequestScoped
public class MainBean implements Serializable {
    @ManagedProperty("#{prefsService}")
    private PreferencesService prefsService;
    @ManagedProperty("#{mainService}")
    private MainService mainService;
    private Sponsor sponsor ;

    public MainBean() {
        System.out.println(this.getClass().getSimpleName() + " was constructed");
    }

    @PostConstruct
    public void setup() {
//        sponsor = mainService.getRandomSponsor();
    }

    public Map<String, Preference> getPreferences() {
        Map<String, Preference> preferences = prefsService.getPreferences();
        return preferences;
    }

    public PreferencesService getPrefsService() {
        return prefsService;
    }

    public void setPrefsService(PreferencesService prefsService) {
        this.prefsService = prefsService;
    }

    public Sponsor getRandomSponsor() {
        return sponsor;
    }

    public MainService getMainService() {
        return mainService;
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }
}