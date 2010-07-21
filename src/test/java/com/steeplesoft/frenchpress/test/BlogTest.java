/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.test;

import com.steeplesoft.jsf.facestester.FacesPage;
import com.steeplesoft.frenchpress.model.BlogEntry;
import java.util.List;
import com.steeplesoft.frenchpress.view.BlogBean;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jasonlee
 */
public class BlogTest extends FrenchPressTestBase {
    @Test
    public void getEntryList() {
        BlogBean blogBean = (BlogBean)ft.getManagedBean("blogBean");
        assertNotNull(blogBean);
        List<BlogEntry> entries = blogBean.getEntryList();
        assertFalse(entries.isEmpty());
    }

    @Test
    public void getLimitedEntryList() {
        BlogBean blogBean = (BlogBean)ft.getManagedBean("blogBean");
        assertNotNull(blogBean);
        List<BlogEntry> entries = blogBean.getLimitedEntryList(5);
        assertEquals(5, entries.size());
    }

    @Test
    public void testAdminListPage() {
        FacesPage page = ft.requestPage("/admin/blogEntries/list.xhtml");
        assertNotNull(page);
        page.dumpComponentTree();
    }
}