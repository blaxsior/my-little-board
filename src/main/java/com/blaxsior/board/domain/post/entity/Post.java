package com.blaxsior.board.domain.post.entity;

import com.blaxsior.board.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Table(name = "post")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Lob
    @Column(name="content")
    private String content;

    @Column(name="likes")
    private Long likes;

    @Column(name="dislikes")
    private Long dislikes;

    @Column(name="recommendable")
    private boolean recommendable = true;

    @Column(name="editable")
    private boolean editable = true;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
