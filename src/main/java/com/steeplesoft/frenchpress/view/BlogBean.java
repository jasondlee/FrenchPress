/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.model.BlogEntry;
import com.steeplesoft.frenchpress.service.BlogService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="blogBean")
@SessionScoped
public class BlogBean {
    @ManagedProperty("#{blogService}")
    protected BlogService blogService;
    protected BlogEntry selected = new BlogEntry();
    
    public List<BlogEntry> getEntryList() {
        return blogService.getMostRecentBlogEntries(-1);
    }

    public List<BlogEntry> getLimitedEntryList(int max) {
        final List<BlogEntry> entries = blogService.getMostRecentBlogEntries(max);
        return entries;
    }

    public BlogService getBlogService() {
        return blogService;
    }

    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    public BlogEntry getSelected() {
        return selected;
    }

    public void setSelected(BlogEntry selected) {
        this.selected = selected;
    }

    public String createEntry() {
        selected = new BlogEntry();
        return "form?faces-redirect=true";
    }
    public String editEntry() {
//        selected = (BlogEntry) dataTable.getRowData();
        return "form?faces-redirect=true";
    }

    public String create() {
        blogService.createBlogEntry(selected);
        return "list?faces-redirect=true";
    }

    public String update() {
        blogService.updateBlogEntry(selected);
        return "list?faces-redirect=true";
    }
}