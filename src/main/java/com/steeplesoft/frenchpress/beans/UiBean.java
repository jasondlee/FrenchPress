/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
//import org.richfaces.component.UIAccordion;

/**
 *
 * @author jdlee
 */
@Named
@SessionScoped
public class UiBean implements Serializable {
    private String activeItem ="home";
//    private UIAccordion accordion;
//
//    public UIAccordion getAccordion() {
//        return accordion;
//    }
//
//    public void setAccordion(UIAccordion accordion) {
//        this.accordion = accordion;
//    }

    public String getActiveItem() {
        return activeItem;
    }

    public void setActiveItem(String activeItem) {
        this.activeItem = activeItem;
    }
}
