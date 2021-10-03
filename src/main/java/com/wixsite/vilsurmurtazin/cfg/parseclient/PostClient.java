package com.wixsite.vilsurmurtazin.cfg.parseclient;


import com.wixsite.vilsurmurtazin.cfg.parseclient.dto.PostParse;

import java.io.IOException;
import java.util.List;

/**
 * Client for parse site.
 */
public interface PostClient {

    /**
     * Find new posts since lastPostId in provided group.
     */
    List<PostParse> findNewPosts() throws IOException;
}
