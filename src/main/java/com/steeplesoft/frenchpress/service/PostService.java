/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.Post;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 *
 * @author jdlee
 */
@RequestScoped
public class PostService {

    @PersistenceContext
    protected EntityManager em;

    public List<Post> getPosts(int limit) {
        final TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p ORDER BY p.posted DESC", Post.class);
        if (limit > -1) {
            query.setMaxResults(limit);
        }
        return query.getResultList();
    }

    public Post getPost(Long id) {
        return em.find(Post.class, id);
    }

    @Transactional
    public void createPost(Post post) {
        post.setPosted(new Date());
        em.persist(post);
    }

    @Transactional
    public void updatePost(Post post) {
        em.merge(post);
    }

    @Transactional
    public void deletePost(Post post) {
        Post newPost = getPost(post.getId());
        if (newPost != null) {
            em.remove(newPost);
        }
    }

    public Post findPostBySlug(String slug) {
        Post post = null;

        List<Post> posts = em.createNamedQuery("findBySlug", Post.class)
            .setParameter("SLUG", slug)
            .getResultList();

        if (!posts.isEmpty()) {
            post = posts.get(0);
        }

        return post;
    }
}
