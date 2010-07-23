/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.BlogEntry;
import com.steeplesoft.frenchpress.service.BlogService;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.richfaces.component.UIDataTable;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="blogBean")
public class BlogBean {
    @ManagedProperty("#{blogService}")
    protected BlogService blogService;
    protected UIDataTable dataTable;
    protected BlogEntry selected;

    public List<BlogEntry> getEntryList() {
        return blogService.getMostRecentBlogEntries(-1);
    }

    public List<BlogEntry> getLimitedEntryList(int max) {
        return blogService.getMostRecentBlogEntries(max);
    }

    public BlogService getBlogService() {
        return blogService;
    }

    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    public UIDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(UIDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public BlogEntry getSelected() {
        return selected;
    }

    public void setSelected(BlogEntry selected) {
        this.selected = selected;
    }

    public String editEntry() {
        selected = (BlogEntry) dataTable.getRowData();
        return "/admin/blogEntries/form";
    }
}