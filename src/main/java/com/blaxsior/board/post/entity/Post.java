package com.blaxsior.board.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

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

    @Column(name="like")
    private Long like;

    @Column(name="dislike")
    private Long dislike;

    @Column(name="recommendable")
    private boolean recommendable = true;

    @Column(name="editable")
    private boolean editable = true;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
