package com.steeplesoft.frenchpress.view.util;

import com.steeplesoft.frenchpress.model.Attachment;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class AttachmentHandlerPhaseListener implements PhaseListener {
    @PersistenceContext(unitName = "em")
    private EntityManager em;

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        // No-op
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            FacesContext fc = event.getFacesContext();
            String servletPath = fc.getExternalContext().getRequestServletPath();

            if (servletPath.startsWith("/attachments")) {
                HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
                HttpServletResponse resp = (HttpServletResponse) fc.getExternalContext().getResponse();
                try {

                    fc.responseComplete();

                    String uri = req.getRequestURI();
                    String[] parts = uri.split("/");
                    if (parts.length < 5) {
                        resp.sendError(404, "Attachment not found");
                        return;
                    }
                    Integer id = Integer.parseInt(parts[3]);
                    String fileName = parts[4];
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("em");
                    EntityManager em = emf.createEntityManager();
                    Query query = em.createQuery("select a from Attachment a where a.id = :ID and a.fileName = :FILENAME");
                    query.setParameter("ID", id);
                    query.setParameter("FILENAME", fileName);
                    Attachment a = (Attachment) query.getSingleResult();

                    if (a == null) {
                        resp.sendError(404, "Attachment not found");
                        return;
                    }

                    resp.setContentType(a.getMimeType());
                    OutputStream os = resp.getOutputStream();
                    os.write(a.getContent());
                } catch (Exception e) {
                    try {
                        resp.sendError(404, "Attachment not found");
                    } catch (IOException ioe) {
                        // no op
                    }
                }

            }
        }
    }

}
