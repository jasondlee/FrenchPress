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

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="blogBean")
public class BlogBean {
    @ManagedProperty("#{blogService}")
    BlogService blogService;

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
}