/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.Comment;
import com.steeplesoft.frenchpress.service.CommentService;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author jdlee
 */
@Model
public class CommentsBean {
    @Inject
    private CommentService commentService;
    private int start = 0;
    private int number = 25;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
    
    public List<Comment> getComments() {
        return commentService.getComments(start, number);
    }
}
