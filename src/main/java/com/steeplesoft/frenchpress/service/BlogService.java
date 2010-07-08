/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.BlogEntry;
import java.util.List;
/**
 *
 * @author jasonlee
 */
public interface BlogService {
    public List<BlogEntry> getMostRecentBlogEntries(int max);
}
