/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.model;

/**
 *
 * @author jdlee
 */
public enum Role {
    ADMINISTRATOR("administrator");
    
    private String name;
    
    private Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
