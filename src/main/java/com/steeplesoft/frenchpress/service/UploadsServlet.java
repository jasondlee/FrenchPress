/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.MediaItem;
import java.io.IOException;
import java.io.OutputStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jdlee
 */
//@WebServlet(urlPatterns = "/uploads/*")
public class UploadsServlet extends HttpServlet {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String path = req.getPathInfo().substring(1);
            TypedQuery<MediaItem> query = em.createQuery("SELECT mi FROM MediaItem mi WHERE mi.name = :NAME", MediaItem.class)
                    .setParameter("NAME", path);

            MediaItem item = query.getSingleResult();
            res.setContentType(item.getMimeType());
            res.setHeader("Content-Length", Long.toString(item.getFileSize()));
            final OutputStream out = res.getOutputStream();
            out.write(item.getContents());
            out.close();
        } catch (Exception e) {
            res.sendError(404);
        }
    }
}
