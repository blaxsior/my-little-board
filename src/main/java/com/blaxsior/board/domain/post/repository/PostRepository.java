package com.blaxsior.board.domain.post.repository;

import com.blaxsior.board.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
