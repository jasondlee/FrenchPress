/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.Post;
import java.io.Serializable;
import java.util.List;
/**
 *
 * @author jasonlee
 */
public interface PostService extends Serializable {
    Post getPost(Long id);
    Post createPost(Post entry);
    Post updatePost(Post entry);
    List<Post> getMostRecentPosts(int max);
}
