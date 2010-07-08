/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.Preference;
import java.util.Map;

/**
 *
 * @author jasonlee
 */
public interface PreferencesService {
    public Map<String, Preference> getPreferences();
    public void savePreferences(Map<String, Preference> pref);
}
