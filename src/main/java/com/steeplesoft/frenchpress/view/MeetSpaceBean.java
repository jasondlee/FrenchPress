/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.Preference;
import com.steeplesoft.frenchpress.service.PreferencesService;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author jasonlee
 */
@ManagedBean(eager=true)
@ApplicationScoped
public class MeetSpaceBean implements Serializable {
    @ManagedProperty("#{prefsService}")
    private PreferencesService prefsService;

    private String meetspaceHome;

    @PostConstruct
    public void checkForDefaults() {
        meetspaceHome = System.getenv("MEETSPACE_HOME");
        if (meetspaceHome == null) {
            meetspaceHome = System.getProperty("user.home") + File.separator + ".meetspace";
        }

        Map<String, Preference> prefs = prefsService.getPreferences();
        if (prefs.get("theme") == null) {
            prefs.put("theme", new Preference("theme", "default"));
        }

//        prefsService.savePreferences();
    }

    public PreferencesService getPrefsService() {
        return prefsService;
    }

    public void setPrefsService(PreferencesService prefsService) {
        this.prefsService = prefsService;
    }

    public String getHome() {
        return meetspaceHome;
    }
}
