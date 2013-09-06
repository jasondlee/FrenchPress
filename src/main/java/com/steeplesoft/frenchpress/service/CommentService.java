/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.Comment;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 *
 * @author jdlee
 */
public class CommentService {

    @PersistenceContext
    protected EntityManager em;

    public List<Comment> getComments(int limit) {
        final TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c ORDER BY c.commentDate", Comment.class);
        if (limit > -1) {
            query.setMaxResults(limit);
        }
        return query.getResultList();
    }

    public List<Comment> getComments(int start, int number) {
        List<Comment> comments = Collections.EMPTY_LIST;
        try {
            TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c ORDER BY c.commentDate", Comment.class)
                    .setFirstResult(start);
            if (number > -1) {
                query.setMaxResults(number);
            }
            comments = query.getResultList();
        } catch (Exception e) {
        }

        return comments;
    }

    public Comment getComment(Long id) {
        return em.find(Comment.class, id);
    }

    @Transactional
    public void updateComment(Comment comment) {
        em.merge(comment);
    }

    @Transactional
    public void deleteComment(Comment comment) {
        Comment toDelete = em.find(Comment.class, comment.getId());
        if (toDelete != null) {
            em.remove(toDelete);
        }
    }
}
