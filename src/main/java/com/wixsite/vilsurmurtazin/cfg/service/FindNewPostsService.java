package com.wixsite.vilsurmurtazin.cfg.service;



import java.io.IOException;

/**
 * Service for finding new posts.
 */
public interface FindNewPostsService {

    /**
     * Find new posts and notify subscribers about it.
     */
    void findNewPosts() throws IOException;

}