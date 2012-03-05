/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author jdlee
 */
@Named("commentModel")
@RequestScoped
public class CommentPageableModel extends PageableModel<Comment> {
    public CommentPageableModel() {
        super();
        entityClass = Comment.class;
    }
}
