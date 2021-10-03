package com.wixsite.vilsurmurtazin.cfg.repository;

import com.wixsite.vilsurmurtazin.cfg.repository.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link Repository} for {@link Post} entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByHref(String href);
}
