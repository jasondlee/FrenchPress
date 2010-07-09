/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.Attachment;
import com.steeplesoft.frenchpress.util.upload.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jasonlee
 */
@ManagedBean
@SessionScoped
public class AttachmentBean extends ControllerBean {
    public static final String NAV_ADD = "/admin/attachments/form";
    public static final String NAV_EDIT = "/admin/attachments/form";
    public static final String NAV_LIST = "/admin/attachments/list";
    public static final String NAV_VIEW = "/admin/attachments/view";

    @ManagedProperty("#{frecnPressBean}")
    private FrenchPressBean frenchPressBean;

    public AttachmentBean() {
        setNavigationIds(NAV_ADD, NAV_EDIT, NAV_LIST, NAV_VIEW);
    }


    public FrenchPressBean getFrenchPressBean() {
        return frenchPressBean;
    }

    public void setFrenchPressBean(FrenchPressBean frenchPressBean) {
        this.frenchPressBean = frenchPressBean;
    }

    @Override
    public Class getEntityClass() {
        return Attachment.class;
    }

    @Override
    public String create() {
        try {
            System.out.println("create");
            //HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            MultipartRequest request = (MultipartRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String uploadPath = frenchPressBean.getHome() + File.separator + "uploads";

            Attachment attachment = (Attachment) getSelected();
            FileHolder item = request.getFile("contents");

            attachment.setFileName(item.getFileName());
            attachment.setMimeType(item.getMimeType());
            attachment.setPath(uploadPath + item.getFileName());
            attachment.setContent(getFileContents(item.getFile()));
            copyFile(item.getFile(), uploadPath, item.getFileName());
//            attachment.setContent(getFileContents(item.getFile()));

            return super.create();
        } catch (Exception ex) {
            Logger.getLogger(AttachmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public String edit() {
        System.out.println("edit");
        return super.edit();
    }

    protected void copyFile(File source, String dir, String fileName) {
        final File targetDir = new File(dir);
        targetDir.mkdirs();
        final File target = new File(targetDir, fileName);
        source.renameTo(target);
    }

    protected byte[] getFileContents(File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            long length = file.length();
            byte[] bytes = new byte[8192];
            // Read in the bytes
            int numRead = is.read(bytes);
            while (numRead >= 0) {
                baos.write(bytes);
                bytes = new byte[8192];
                numRead = is.read(bytes);
            }
        } catch (Exception ex) {
            Logger.getLogger(AttachmentBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                //
            }
        }
        return baos.toByteArray();
    }
}
