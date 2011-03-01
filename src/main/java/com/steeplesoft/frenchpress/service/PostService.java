package com.steeplesoft.frenchpress.service;

import com.steeplesoft.frenchpress.model.Post;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jasonlee
 */
@Local
public interface PostService {
    List<Post> getPosts();
    void create(Post post);
    void update(Post post);
}
