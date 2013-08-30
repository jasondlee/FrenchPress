/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.Comment;
import com.steeplesoft.frenchpress.service.CommentService;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletRequest;

/**
 *
 * @author jdlee
 */
@Model
public class CommentsBean {
    @Inject
    private CommentService commentService;
    private HtmlDataTable dataTable;
    private Comment comment = new Comment();

    private int start = 0;
    private int number = 25;

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

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

    public String update() {
        comment.setPost(commentService.getComment(comment.getId()).getPost());
        commentService.updateComment(comment);
        return Constants.VIEW_ADMIN_COMMENTS_INDEX;
    }

    public String delete() {
        // TODO
//        Comment toDelete = (Comment) dataTable.getRowData();
//        commentService.deleteComment(toDelete);

        return null;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void loadComment() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.isPostback()) {
            Comment requestedComment = (Comment) ((ServletRequest) facesContext.getExternalContext().getRequest())
                    .getAttribute("commentId");
            comment = (requestedComment != null) ? requestedComment :
                    commentService.getComment(comment.getId());
        }
    }
}
