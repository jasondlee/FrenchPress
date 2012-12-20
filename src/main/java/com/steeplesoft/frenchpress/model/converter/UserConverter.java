/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.model.converter;

import com.steeplesoft.frenchpress.service.UserService;
import com.steeplesoft.frenchpress.model.User;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jdlee
 */
@Named
// NOTE: Be sure to implement equals()
public class UserConverter implements Converter {
    @Inject
    UserService userService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        final User user = userService.getUser(Long.parseLong(string));
        return user;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof Long) {
            return o.toString();
        }
        return ((User)o).getId().toString();
    }
}
