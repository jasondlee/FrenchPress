/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.icefaces.ace.component.accordion.AccordionPane;
import org.icefaces.ace.event.AccordionPaneChangeEvent;

/**
 *
 * @author jdlee
 */
@Named
@SessionScoped
public class UiBean implements Serializable {
    int activeAccordionIndex = 0;

    public int getActiveAccordionIndex() {
        return activeAccordionIndex;
    }

    public void setActiveAccordionIndex(int activeAccordionIndex) {
        this.activeAccordionIndex = activeAccordionIndex;
    }

    public void onAccordionPaneChange(AccordionPaneChangeEvent event) {
        AccordionPane pane = event.getTab();
        activeAccordionIndex = pane.getParent().getChildren().indexOf(pane);
    }
}
