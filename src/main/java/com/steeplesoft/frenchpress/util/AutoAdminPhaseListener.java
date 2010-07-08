package com.steeplesoft.frenchpress.util;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.facelets.Facelet;
import com.sun.faces.facelets.FaceletFactory;
import com.sun.faces.facelets.compiler.UIInstructions;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: jasonlee
 * Date: Feb 25, 2010
 * Time: 11:55:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class AutoAdminPhaseListener implements PhaseListener {
    public static final String ADMIN_PATH = "/autoAdmin";
    private Set<String> adminClasses = new TreeSet<String>();

    public AutoAdminPhaseListener() {
        adminClasses.add("Meeting");
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        if (!validateRequestPath(event)) {
            return;
        }

        if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            try {
                doRestoreView(event);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
//        StringBuilder sb = new StringBuilder();
//        dumpTree(event.getFacesContext().getViewRoot(), sb, "");
//        System.out.println(sb.toString());

        if (!validateRequestPath(event)) {
            return;
        }
    }

    private boolean validateRequestPath(PhaseEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getFacesContext().getExternalContext().getRequest();
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        uri = uri.substring(contextPath.length());
        return uri.startsWith(ADMIN_PATH);
    }

    private void doRestoreView(PhaseEvent event) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "      xmlns:f=\"http://java.sun.com/jsf/core\"\n" +
                "      xmlns:h=\"http://java.sun.com/jsf/html\"\n" +
                "      xmlns:ui=\"http://java.sun.com/jsf/facelets\">\n" +
                "<head>\n" +
                "    <title>Should Not Be Displayed</title>\n" +
                "</head>\n" +
                "<body>   <!-- template=\"/WEB-INF/demotemplate.xhtml\" -->\n" +
                "<ui:composition id=\"composition\" template=\"/themes/#{main.preferences['theme'].value}/layout.xhtml\">" +
                "PL Test" +
                "</ui:composition>\n" +
                "</body>\n" +
                "</html>");

        File tempFile = File.createTempFile("autoadmin", ".xhtml");
        tempFile.deleteOnExit();
        FileOutputStream os = new FileOutputStream(tempFile);
        os.write(sb.toString().getBytes());
        os.close();

        Map requestMap = event.getFacesContext().getExternalContext().getRequestMap();
        requestMap.put("javax.servlet.include.path_info", tempFile.getPath());

        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setViewId(tempFile.getName());
        FaceletFactory faceletFactory = ApplicationAssociate.getCurrentInstance().getFaceletFactory();
        Facelet f = faceletFactory.getFacelet(tempFile.toURL());
        f.apply(event.getFacesContext(),viewRoot);
        tempFile.delete();
        event.getFacesContext().setViewRoot(viewRoot);
//        SAXCompiler compiler = new SAXCompiler();
//        compiler.doCompile(null, null);
    }

    private static void dumpTree(UIComponent comp, StringBuilder buf, String indent) {
        // First add the current UIComponent
        Object value = comp.getAttributes().get("value");
        if (comp instanceof UIInstructions) {
            UIInstructions uii = (UIInstructions) comp;
            String string = uii.toString();
        }
        buf.append(indent + comp.getId() + " (" + comp.getClass().getName() + ") = (" + value + ")\n");

        // Children...
        Iterator<UIComponent> it = comp.getChildren().iterator();
        if (it.hasNext()) {
            buf.append(indent + "  Children:\n");
            while (it.hasNext()) {
                dumpTree(it.next(), buf, indent + "    ");
            }
        }
    }

}