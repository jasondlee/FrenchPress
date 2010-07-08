package com.steeplesoft.frenchpress.util;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;

/**
 * Created by IntelliJ IDEA.
 * User: jasonlee
 * Date: Feb 25, 2010
 * Time: 5:11:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class MeetspaceViewHandlerWrapper extends ViewHandlerWrapper {
    private ViewHandler parent;

    public MeetspaceViewHandlerWrapper(ViewHandler parent) {
        this.parent = parent;
    }

    @Override
    public ViewDeclarationLanguage getViewDeclarationLanguage(FacesContext context, String viewId) {
        if (viewId.startsWith(AutoAdminPhaseListener.ADMIN_PATH)) {
            //return getWrapped().
        } else {
        }
        return getWrapped().getViewDeclarationLanguage(context, viewId);
    }

    @Override
    public ViewHandler getWrapped() {
        return parent;
    }
}
