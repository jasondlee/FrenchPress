/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.beans;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.service.PostService;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



/**
 *
 * @author jdlee
 */
//@WebFilter(filterName="postFilter", urlPatterns={"/*"})
//@RequestScoped
public class PostFilter implements Filter {
    @Inject
    PostService postService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsr = (HttpServletRequest)request;
        String path = hsr.getServletPath();
        if (!path.startsWith("/javax.faces.resource/")) {
            Post post = null;

            String postId = request.getParameter("p");
            if (postId != null) {
                Long id = Long.parseLong(postId);
                post = postService.getPost(id);
            } else {
                path = path.substring(1);
                post = postService.findPostBySlug(path);
            }

            if (post != null) {
                request.setAttribute("post", post);
                hsr.getRequestDispatcher("/viewPost.jsf").forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
