package com.blaxsior.board.post.repository;

import com.blaxsior.board.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
